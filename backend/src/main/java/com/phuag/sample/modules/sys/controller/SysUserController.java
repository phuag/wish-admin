package com.phuag.sample.modules.sys.controller;

import com.github.pagehelper.PageInfo;
import com.phuag.sample.common.config.Constants;
import com.phuag.sample.common.model.ResponseMessage;
import com.phuag.sample.modules.sys.domain.SysUser;
import com.phuag.sample.modules.sys.model.SysUserDetail;
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
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = Constants.URI_API + Constants.URI_SYSUSER)
@Slf4j
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<PageInfo<SysUserDetail>> getAllSysUser(
            @RequestParam(value = "office", required = false) String officeId,
            @RequestParam(value = "name", required = false) String name,
            @PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable page) {
        log.debug("get all SysUser of office@{},name@{},page@{}", officeId,name, page);
        PageInfo<SysUserDetail> sysUsers = sysUserService.searchSysUser(officeId,name, page);
        log.debug("get all SysUser, num:{}", sysUsers.getSize());
        return new ResponseEntity<PageInfo<SysUserDetail>>(sysUsers, HttpStatus.OK);
    }
    
    /**
     * Get the authenticated user info.
     * 
     * @return
     */
    @PostMapping("/me")
    public ResponseEntity<Map<String, Object>> user() {
        SysUser principal =  (SysUser) SecurityUtils.getSubject().getPrincipal();
        log.debug("get principal @" +principal.toString());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", principal.getId());
        map.put("username", principal.getLoginName());
        map.put("name",principal.getName());
        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseMessage> logout(){
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            // session 会销毁，在SessionListener监听session销毁，清理权限缓存
            subject.logout();
        }
        return new ResponseEntity<ResponseMessage>(ResponseMessage.info("logouted"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity deleteSysUser(@PathVariable("id") String id) {
        log.debug("delete sysUser id@{}", id);
        int res = sysUserService.delete(id);
        log.debug("deleted res is @{}", res);
        return new ResponseEntity(ResponseMessage.info("delete staff success:" + res), HttpStatus.OK);
    }
    
   
}
