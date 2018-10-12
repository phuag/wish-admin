package com.phuag.sample.modules.sys.controller;

import com.github.pagehelper.PageInfo;
import com.phuag.sample.common.config.Constants;
import com.phuag.sample.common.model.ResponseMessage;
import com.phuag.sample.modules.sys.domain.SysMenu;
import com.phuag.sample.modules.sys.domain.SysUser;
import com.phuag.sample.modules.sys.model.SysUserDetail;
import com.phuag.sample.modules.sys.model.SysUserForm;
import com.phuag.sample.modules.sys.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping(value = Constants.URI_API + Constants.URI_SYS_USER)
@Slf4j
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<PageInfo<SysUserDetail>> getAllSysUser(
            @RequestParam(value = "office", required = false) String officeId,
            @RequestParam(value = "q", required = false) String keyword,
            @PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable page) {
        log.debug("get all SysUser of office@{},name@{},page@{}", officeId, keyword, page);
        PageInfo<SysUserDetail> sysUsers = sysUserService.searchSysUser(officeId, keyword, page);
        log.debug("get all SysUser, num:{}", sysUsers.getSize());
        return new ResponseEntity<>(sysUsers, HttpStatus.OK);
    }

    /**
     * Get the authenticated user info.
     *
     * @return
     */
    @PostMapping("/me")
    public ResponseEntity<SysUserDetail> user() {
        Subject subject = SecurityUtils.getSubject();
        SysUser principal = (SysUser) subject.getPrincipal();
        log.debug("get principal @" + principal.toString());
        SysUserDetail sysUserDetail = sysUserService.fillOfficeInfo(principal);
        return new ResponseEntity<>(sysUserDetail, HttpStatus.OK);
    }

    @PostMapping("/myMenu")
    public ResponseEntity<List<SysMenu>> menu() {
        SysUser prinvipal = (SysUser) SecurityUtils.getSubject().getPrincipal();
        List<SysMenu> menus = sysUserService.getSysMenu(prinvipal);
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseMessage> logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            // session 会销毁，在SessionListener监听session销毁，清理权限缓存
            subject.logout();
        }
        return new ResponseEntity<>(ResponseMessage.info("logouted"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity deleteSysUser(@PathVariable("id") String id) {
        log.debug("delete sysUser id@{}", id);
        int res = sysUserService.delete(id);
        log.debug("deleted res is @{}", res);
        return new ResponseEntity(ResponseMessage.info("delete staff success:" + res), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity updateSysUser(@PathVariable("id") String sysUserId, @RequestBody SysUserForm form) {
        log.debug("update sysUser id@{},sysUser@{}", sysUserId, form);
        int res = sysUserService.updateSysUser(sysUserId, form);
        log.debug("updated res is @{}", res);
        return new ResponseEntity(ResponseMessage.info("update sysUser success,res=" + res), HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity insertSysUser(@RequestBody SysUserForm form) {
        log.debug("insert sysUser@{}", form);
        int res = sysUserService.insertSysUser(form);
        log.debug("saved res is @{}", res);
        return new ResponseEntity(ResponseMessage.info("insert success,res=" + res), HttpStatus.OK);
    }

    @GetMapping("/checkLoginName")
    @ResponseBody
    public ResponseEntity<Boolean> checkLoginName(@RequestParam(value = "oldLoginName", required = false) String oldLoginName,
                                                  @RequestParam(value = "loginName") String loginName) {
        if (loginName != null && loginName.equals(oldLoginName)) {
            return new ResponseEntity(true, HttpStatus.OK);
        } else if (loginName != null && sysUserService.getSysUserByLoginName(loginName) == null) {
            return new ResponseEntity(true, HttpStatus.OK);
        }
        return new ResponseEntity(false, HttpStatus.OK);
    }

    @GetMapping("/modifyPwd")
    @ResponseBody
    public ResponseEntity modifyPwd(@RequestParam(value = "oldPassword") String oldPassword,
                                    @RequestParam(value = "newPassword") String newPassword) {
        Subject subject = SecurityUtils.getSubject();
        SysUser principal = (SysUser) subject.getPrincipal();
        Assert.hasText(oldPassword, "oldPassword must have text");
        Assert.hasText(newPassword, "new password must have text");
        if (sysUserService.validatePassword(oldPassword, principal.getPassword())) {
            principal.setPassword(newPassword);
            sysUserService.updatePassword(principal);
            return new ResponseEntity(ResponseMessage.success("modifyPwd success"), HttpStatus.OK);
        }

        return new ResponseEntity(ResponseMessage.info("modifyPwd failed, cause old password is wrong"), HttpStatus.OK);
    }


}
