package com.phuag.sample.modules.sys.controller;

import com.github.pagehelper.PageInfo;
import com.phuag.sample.common.config.Constants;
import com.phuag.sample.modules.sys.domain.SysOffice;
import com.phuag.sample.modules.sys.model.SysUserDetail;
import com.phuag.sample.modules.sys.service.SysOfficeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by vvvvvv on 2017/12/28.
 */
@RestController
@RequestMapping(value = Constants.URI_API + Constants.URI_SYSOFFICE)
@Slf4j
public class SysOfficeController {
    @Autowired
    private SysOfficeService sysOfficeService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<SysOffice>> getAllSysOffice(
            @RequestParam(value = "officeId", required = false) String officeId) {
        log.debug("get all SysOffice of officeId@{}", officeId);
        List<SysOffice> sysUsers = sysOfficeService.searchSysOffice(officeId);
        log.debug("get all SysOffice, num:{}", sysUsers.size());
        return new ResponseEntity<List<SysOffice>>(sysUsers, HttpStatus.OK);
    }
}
