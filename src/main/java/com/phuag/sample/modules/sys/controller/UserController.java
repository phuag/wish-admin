package com.phuag.sample.modules.sys.controller;

import com.phuag.sample.common.config.Constants;
import com.phuag.sample.common.model.ResponseMessage;
import com.phuag.sample.modules.sys.domain.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = Constants.URI_API)
public class UserController {
    private static final Logger log = LoggerFactory
            .getLogger(UserController.class);
    
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
    
   
}
