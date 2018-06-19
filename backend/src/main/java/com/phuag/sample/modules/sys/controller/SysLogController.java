package com.phuag.sample.modules.sys.controller;

import com.github.pagehelper.PageInfo;
import com.phuag.sample.common.config.Constants;
import com.phuag.sample.modules.sys.domain.SysLog;
import com.phuag.sample.modules.sys.model.StaffDetail;
import com.phuag.sample.modules.sys.service.StaffService;
import com.phuag.sample.modules.sys.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by vvvvvv on 2017/12/4.
 */
@RestController
@RequestMapping(value = Constants.URI_API + Constants.URI_LOG)
@Slf4j
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<PageInfo<SysLog>> getAllStaff(
            @RequestParam(value = "q", required = false) String keyword,
            @PageableDefault(page = 0, size = 20, sort = "staffId", direction = Sort.Direction.DESC) Pageable page) {
        log.debug("get all staffInfo of q@{},page@{}", keyword, page);
        PageInfo<SysLog> logs = sysLogService.searchSysLog(keyword, page);
        log.debug("get allStaff, num:{}", logs.getPageSize());
        return new ResponseEntity<PageInfo<SysLog>>(logs, HttpStatus.OK);
    }
}
