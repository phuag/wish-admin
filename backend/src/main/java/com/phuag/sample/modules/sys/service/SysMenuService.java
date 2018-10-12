package com.phuag.sample.modules.sys.service;

import com.phuag.sample.common.service.CrudService;
import com.phuag.sample.modules.sys.dao.SysMenuMapper;
import com.phuag.sample.modules.sys.domain.SysMenu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author phuag
 * @date 2018/9/1
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class SysMenuService extends CrudService<SysMenuMapper,SysMenu> {
}
