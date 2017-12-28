package com.phuag.sample.modules.sys.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phuag.sample.common.security.Salt;
import com.phuag.sample.common.service.CrudService;
import com.phuag.sample.common.utils.DTOUtils;
import com.phuag.sample.common.utils.Encodes;
import com.phuag.sample.configuration.shiro.ShiroProperties;
import com.phuag.sample.modules.sys.dao.StaffMapper;
import com.phuag.sample.modules.sys.dao.SysUserMapper;
import com.phuag.sample.modules.sys.domain.Staff;
import com.phuag.sample.modules.sys.domain.SysRole;
import com.phuag.sample.modules.sys.domain.SysUser;
import com.phuag.sample.modules.sys.model.SysUserDetail;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/3/27 0027.
 */
@Service
@Transactional
public class SysUserService extends CrudService<SysUserMapper, SysUser> {

    @Autowired
    private ShiroProperties properties;

    @Resource
    private SessionDAO sessionDao;

    public SessionDAO getSessionDao() {
        return sessionDao;
    }

    public int insertSysUser(SysUser sysUser) {
        return dao.insert(sysUser);
    }

    public SysUser getSysUserById(String id) {
        return dao.selectByPrimaryKey(id);
    }
    public Page<SysUser> getAllSysUser(Pageable page) {
        PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        //TODO need to add sort function ,but the pagerhelper is not support well.
        //PageHelper.orderBy("STAFF_NAME desc");
        return (Page<SysUser>) dao.selectAll();
    }


    public SysUser getSysUserByLoginName(String username) {
        return dao.selectSysUserByLoginName(username);
    }

    public int updateByPrimaryKey(SysUser record){
        return dao.updateByPrimaryKey(record);
    }

    public List<SysRole> getSysUserRolesByUser(SysUser user) {
        return dao.getSysUserRolesByUser(user);
    }

    /**
     * 将SysUser中的password从明文转换为带盐密文后存储
     * @param user
     * @return
     */
    public SysUser updatePassword(SysUser user){
        String hashedPassword = entryptPassword(user.getPassword());
        user.setPassword(hashedPassword);
        dao.updateByPrimaryKeySelective(user);
        return user;
    }

    /**
     * 生成安全的密码，生成随机的16位salt并经 过1024次sha-1 hash
     */
    public String entryptPassword(String plainPassword) {
        byte[] salt = Salt.generateSalt(properties.getSaltSize());
//        byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
        byte[] hashPassword = new SimpleHash(properties.getHashAlgorithmName(),plainPassword,salt,properties.getHashIterations()).getBytes();
        return Encodes.encodeHex(salt)+ Encodes.encodeHex(hashPassword);
    }

    public PageInfo<SysUserDetail> searchSysUser(String officeId, Pageable page) {
        PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        List<SysUser> sysUsers;
        if (StringUtils.isNotBlank(officeId)) {
            SysUser q = new SysUser();
            q.setOfficeId(officeId);
            sysUsers = dao.select(q);
        } else {
            sysUsers = dao.logicalSelectAll();
        }

        List<SysUserDetail> staffDetails = sysUsers.stream().map(sysUser -> {
            SysUserDetail sysUserDetail = DTOUtils.map(sysUser,SysUserDetail.class);
            sysUserDetail.setOffice(dao.getSysUserOffice(sysUser));
            return sysUserDetail;
        }).collect(Collectors.toList());

        return new PageInfo<>(staffDetails);
    }
}
