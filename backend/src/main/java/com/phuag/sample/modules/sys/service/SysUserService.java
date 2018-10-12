package com.phuag.sample.modules.sys.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phuag.sample.common.security.Salt;
import com.phuag.sample.common.service.CrudService;
import com.phuag.sample.common.utils.DTOUtils;
import com.phuag.sample.common.utils.Encodes;
import com.phuag.sample.configuration.shiro.ShiroProperties;
import com.phuag.sample.modules.sys.dao.SysUserMapper;
import com.phuag.sample.modules.sys.domain.SysMenu;
import com.phuag.sample.modules.sys.domain.SysOffice;
import com.phuag.sample.modules.sys.domain.SysRole;
import com.phuag.sample.modules.sys.domain.SysUser;
import com.phuag.sample.modules.sys.model.SysUserDetail;
import com.phuag.sample.modules.sys.model.SysUserForm;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @date 2016/3/27 0027
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserService extends CrudService<SysUserMapper, SysUser> {

    @Autowired
    private SysOfficeService sysOfficeService;

    @Autowired
    private ShiroProperties properties;


    @Resource
    private SessionDAO sessionDao;

    public SessionDAO getSessionDao() {
        return sessionDao;
    }

    public int insertSysUser(SysUserForm form) {
        SysUser sysUser = DTOUtils.map(form, SysUser.class);
        if (StringUtils.isEmpty(sysUser.getPassword())) {
            sysUser.setPassword("123456");
            updatePassword(sysUser);
        }
        return super.save(sysUser);
    }

    public SysUser getSysUserById(String id) {
        return dao.selectByPrimaryKey(id);
    }

    public Page<SysUser> getAllSysUser(Pageable page) {
        PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        //TODO need to add sort function ,but the pagerhelper is not support well.
        // 添加排序 PageHelper.orderBy("STAFF_NAME desc");
        return (Page<SysUser>) dao.selectAll();
    }


    public SysUser getSysUserByLoginName(String username) {
        return dao.selectSysUserByLoginName(username);
    }

    public int updateByPrimaryKey(SysUser record) {
        return dao.updateByPrimaryKey(record);
    }

    public List<SysRole> getSysUserRolesByUser(SysUser user) {
        return dao.getSysUserRolesByUser(user);
    }

    /**
     * 将SysUser中的password从明文转换为带盐密文后存储
     *
     * @param user
     * @return
     */
    public SysUser updatePassword(SysUser user) {
        String hashedPassword = encryptPassword(user.getPassword());
        user.setPassword(hashedPassword);
        dao.updateByPrimaryKeySelective(user);
        return user;
    }

    /**
     * 生成安全的密码，生成随机的16位salt并经过1024次sha-1 hash
     */
    public String encryptPassword(String plainPassword) {
        byte[] salt = Salt.generateSalt(properties.getSaltSize());
        byte[] hashPassword = new SimpleHash(properties.getHashAlgorithmName(), plainPassword, salt, properties.getHashIterations()).getBytes();
        return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
    }

    public boolean validatePassword(String plainPassword, String password) {
        String plain = Encodes.unescapeHtml(plainPassword);
        byte[] salt = Encodes.decodeHex(password.substring(0, 16));
        byte[] hashPassword = new SimpleHash(properties.getHashAlgorithmName(), plain, salt, properties.getHashIterations()).getBytes();
        return password.equals(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword));
    }

    public PageInfo<SysUserDetail> searchSysUser(String officeId, String keyword, Pageable page) {
        PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        List<SysUser> sysUsers = dao.getByOfficeAndName(officeId, keyword);

        List<SysUserDetail> staffDetails = sysUsers.stream().map(sysUser -> {
            SysUserDetail sysUserDetail = fillOfficeInfo(sysUser);
            return sysUserDetail;
        }).collect(Collectors.toList());

        return new PageInfo<>(staffDetails);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateSysUser(String sysUserId, SysUserForm form) {
        Assert.hasText(sysUserId, "sysUser id can not be null");
        SysUser sysUser = dao.selectByPrimaryKey(sysUserId);
        DTOUtils.mapTo(form, sysUser);
        sysUser.preUpdate();
        return dao.updateByPrimaryKey(sysUser);
    }

    public SysUserDetail fillOfficeInfo(SysUser sysUser) {
        Assert.notNull(sysUser, "syUser can not be null");
        SysUserDetail sysUserDetail = DTOUtils.map(sysUser, SysUserDetail.class);
        SysOffice office = dao.getSysUserOffice(sysUser);
        sysUserDetail.setOffice(office);
        sysUserDetail.setOfficeNameWithPath(sysOfficeService.getOfficeNameWithPath(office));
        //添加officeId sysUserDetail.setOfficeIdWithPath();
        return sysUserDetail;
    }

    public List<SysMenu> getSysMenu(SysUser sysUser){
        Assert.notNull(sysUser, "syUser can not be null");
        List<SysMenu> sysMenus = dao.getSysMenu(sysUser);
        return sysMenus;
    }


}
