package com.phuag.sample.modules.sys.controller;

import com.github.pagehelper.PageInfo;
import com.phuag.sample.common.config.Constants;
import com.phuag.sample.common.model.ResponseMessage;
import com.phuag.sample.modules.sys.domain.Staff;
import com.phuag.sample.modules.sys.model.StaffDetail;
import com.phuag.sample.modules.sys.model.StaffForm;
import com.phuag.sample.modules.sys.service.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2015/11/17 0017.
 */
@RestController
@RequestMapping(value = Constants.URI_API + Constants.URI_STAFF)
@Slf4j
public class StaffController {

    @Autowired
    private StaffService staffService;

    //page stuff is config to controller layer,i wonder if it was the best way
    @GetMapping
    @ResponseBody
    public ResponseEntity<PageInfo<StaffDetail>> getAllStaff(
            @RequestParam(value = "q", required = false) String keyword,
            @PageableDefault(page = 0, size = 20, sort = "staffId", direction = Direction.DESC) Pageable page) {
        log.debug("get all staff Info of q@{},page@{}", keyword, page);
        PageInfo<StaffDetail> staff = staffService.searchStaff(keyword, page);
        log.debug("get all Staff, num:{}", staff.getSize());
        return new ResponseEntity<PageInfo<StaffDetail>>(staff, HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity insertStaff(@RequestBody StaffForm form) {
        log.debug("save staff @{}", form);
        int res = staffService.saveStaff(form);
        log.debug("saved res is @{}", res);
        return new ResponseEntity(ResponseMessage.info("insert success,res=" + res), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity updateStaff(@PathVariable("id") String staffId, @RequestBody StaffForm form) {
        log.debug("update staff id@{},staff@{}", staffId, form);
        int res = staffService.updateStaff(staffId,form);
        log.debug("updated res is @{}", res);
        return new ResponseEntity(ResponseMessage.info("update Staff success,res=" + res), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<StaffDetail> getStaff(@PathVariable("id") String staffId) {
        log.debug("get staffInfo by id @{}", staffId);
        StaffDetail staff = staffService.getStaffById(staffId);
        log.debug("get staff name @{}", staff.getName());
        return new ResponseEntity<StaffDetail>(staff, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity deleteStaff(@PathVariable("id") String id) {
        log.debug("delete staff id@{}", id);
        int res = staffService.delete(id);
        log.debug("deleted res is @{}", res);
        return new ResponseEntity(ResponseMessage.info("delete staff success:" + res), HttpStatus.OK);
    }

    @DeleteMapping("/batchremove")
    @ResponseBody
    public ResponseEntity batchRemove(String[] ids) {
        for (String id : ids) {
            Staff f = new Staff();
            f.setId(id);
            staffService.delete(f);
        }
        return new ResponseEntity(ResponseMessage.info("batchremove staff success"), HttpStatus.OK);
    }

}
