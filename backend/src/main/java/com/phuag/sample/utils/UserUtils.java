/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.phuag.sample.utils;

import com.phuag.sample.common.utils.SpringContextHolder;
import com.phuag.sample.modules.sys.dao.SysMenuMapper;
import com.phuag.sample.modules.sys.dao.SysRoleMapper;
import com.phuag.sample.modules.sys.dao.SysUserMapper;
import com.phuag.sample.modules.sys.domain.SysMenu;
import com.phuag.sample.modules.sys.domain.SysRole;
import com.phuag.sample.modules.sys.domain.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;

import java.util.List;

/**
 * 用户工具类
 * @author ThinkGem
 * @version 2013-12-05
 */
public class UserUtils {

	private static SysUserMapper userDao = SpringContextHolder.getBean(SysUserMapper.class);
	private static SysRoleMapper roleDao = SpringContextHolder.getBean(SysRoleMapper.class);
	private static SysMenuMapper menuDao = SpringContextHolder.getBean(SysMenuMapper.class);
//	private static AreaDao areaDao = SpringContextHolder.getBean(AreaDao.class);
//	private static OfficeDao officeDao = SpringContextHolder.getBean(OfficeDao.class);

	public static final String USER_CACHE = "userCache";
	public static final String USER_CACHE_ID_ = "id_";
	public static final String USER_CACHE_LOGIN_NAME_ = "ln";
	public static final String USER_CACHE_LIST_BY_OFFICE_ID_ = "oid_";

	public static final String CACHE_ROLE_LIST = "roleList";
	public static final String CACHE_MENU_LIST = "menuList";
	public static final String CACHE_AREA_LIST = "areaList";
	public static final String CACHE_OFFICE_LIST = "officeList";
	public static final String CACHE_OFFICE_ALL_LIST = "officeAllList";
	
	/**
	 * 根据ID获取用户
	 * @param id
	 * @return 取不到返回null
	 */
	public static SysUser get(String id){
//		SysUser user = (SysUser) CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id);
		SysUser user = null;
		if (user ==  null){
			user = userDao.selectByPrimaryKey(id);
			if (user == null){
				return null;
			}
//			user.setRoleList(roleDao.findList(new SysRole(user)));
//			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
//			CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
		}
		return user;
	}
	
	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return 取不到返回null
	 */
	public static SysUser getByLoginName(String loginName){
//		SysUser user = (SysUser) CacheUtils.get(USER_CACHE, USER_CACHE_LOGIN_NAME_ + loginName);
		SysUser user = null;
		if (user == null){
			user = userDao.selectSysUserByLoginName(loginName);
			if (user == null){
				return null;
			}
//			user.setRoleList(roleDao.findList(new SysRole(user)));
//			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
//			CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
		}
		return user;
	}
	
	/**
	 * 清除当前用户缓存
	 */
	public static void clearCache(){
//		removeCache(CACHE_ROLE_LIST);
//		removeCache(CACHE_MENU_LIST);
//		removeCache(CACHE_AREA_LIST);
//		removeCache(CACHE_OFFICE_LIST);
//		removeCache(CACHE_OFFICE_ALL_LIST);
		UserUtils.clearCache(getUser());
	}
	
	/**
	 * 清除指定用户缓存
	 * @param user
	 */
	public static void clearCache(SysUser user){
//		CacheUtils.remove(USER_CACHE, USER_CACHE_ID_ + user.getId());
//		CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName());
//		CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getOldLoginName());
//		if (user.getOffice() != null && user.getOffice().getId() != null){
//			CacheUtils.remove(USER_CACHE, USER_CACHE_LIST_BY_OFFICE_ID_ + user.getOffice().getId());
//		}
	}
	
	/**
	 * 获取当前用户
	 * @return 取不到返回 new User()
	 */
	public static SysUser getUser(){
		SysUser principal = getPrincipal();
		if (principal!=null){
			return principal;
//			SysUser user = get(principal.getName());
//			if (user != null){
//				return user;
//			}
//			return new SysUser();
		}
		// 如果没有登录，则返回实例化空的User对象。
		return new SysUser();
	}

	/**
	 * 获取当前用户角色列表
	 * @return
	 */
	public static List<SysRole> getRoleList(){
		@SuppressWarnings("unchecked")
//		List<SysRole> roleList = (List<SysRole>)getCache(CACHE_ROLE_LIST);
		List<SysRole> roleList =null;
		if (roleList == null){
			SysUser user = getUser();
			if (user.isAdmin()){
				roleList = roleDao.selectAll();
			}else{
				SysRole role = new SysRole();
//				role.getSqlMap().put("dsf", BaseService.dataScopeFilter(user.getCurrentUser(), "o", "u"));
//				roleList = roleDao.findList(role);
			}
//			putCache(CACHE_ROLE_LIST, roleList);
		}
		return roleList;
	}
	
	/**
	 * 获取当前用户授权菜单
	 * @return
	 */
	public static List<SysMenu> getMenuList(){
		@SuppressWarnings("unchecked")
//		List<SysMenu> menuList = (List<SysMenu>)getCache(CACHE_MENU_LIST);
		List<SysMenu> menuList=null;
		if (menuList == null){
			SysUser user = getUser();
			if (user.isAdmin()){
				menuList = menuDao.selectAll();
			}else{
				SysMenu m = new SysMenu();
//				m.setUserId(user.getId());
//				menuList = menuDao.findByUserId(m);
			}
//			putCache(CACHE_MENU_LIST, menuList);
		}
		return menuList;
	}
	
	/**
	 * 获取当前用户授权的区域
	 * @return
	 */
//	public static List<Area> getAreaList(){
//		@SuppressWarnings("unchecked")
//		List<Area> areaList = (List<Area>)getCache(CACHE_AREA_LIST);
//		if (areaList == null){
//			areaList = areaDao.findAllList(new Area());
//			putCache(CACHE_AREA_LIST, areaList);
//		}
//		return areaList;
//	}
	
	/**
	 * 获取当前用户有权限访问的部门
	 * @return
	 */
//	public static List<Office> getOfficeList(){
//		@SuppressWarnings("unchecked")
//		List<Office> officeList = (List<Office>)getCache(CACHE_OFFICE_LIST);
//		if (officeList == null){
//			SysUser user = getUser();
//			if (user.isAdmin()){
//				officeList = officeDao.findAllList(new Office());
//			}else{
//				Office office = new Office();
//				office.getSqlMap().put("dsf", BaseService.dataScopeFilter(user, "a", ""));
//				officeList = officeDao.findList(office);
//			}
//			putCache(CACHE_OFFICE_LIST, officeList);
//		}
//		return officeList;
//	}

	/**
	 * 获取当前用户有权限访问的部门
	 * @return
	 */
//	public static List<Office> getOfficeAllList(){
//		@SuppressWarnings("unchecked")
//		List<Office> officeList = (List<Office>)getCache(CACHE_OFFICE_ALL_LIST);
//		if (officeList == null){
//			officeList = officeDao.findAllList(new Office());
//		}
//		return officeList;
//	}
	
	/**
	 * 获取授权主要对象
	 */
//	public static Subject getSubject(){
//		return SecurityUtils.getSubject();
//	}
//
	/**
	 * 获取当前登录者对象
	 */
	public static SysUser getPrincipal(){
		try{
			Subject subject = SecurityUtils.getSubject();
			SysUser principal = (SysUser)subject.getPrincipal();
			if (principal != null){
				return principal;
			}
//			subject.logout();
		}catch (UnavailableSecurityManagerException e) {

		}catch (InvalidSessionException e){

		}
		return null;
	}
//
//	public static Session getSession(){
//		try{
//			Subject subject = SecurityUtils.getSubject();
//			Session session = subject.getSession(false);
//			if (session == null){
//				session = subject.getSession();
//			}
//			if (session != null){
//				return session;
//			}
////			subject.logout();
//		}catch (InvalidSessionException e){
//
//		}
//		return null;
//	}
//
//	// ============== User Cache ==============
//
//	public static Object getCache(String key) {
//		return getCache(key, null);
//	}
//
//	public static Object getCache(String key, Object defaultValue) {
////		Object obj = getCacheMap().get(key);
//		Object obj = getSession().getAttribute(key);
//		return obj==null?defaultValue:obj;
//	}
//
//	public static void putCache(String key, Object value) {
////		getCacheMap().put(key, value);
//		getSession().setAttribute(key, value);
//	}
//
//	public static void removeCache(String key) {
////		getCacheMap().remove(key);
//		getSession().removeAttribute(key);
//	}

//	public static Map<String, Object> getCacheMap(){
//		Principal principal = getPrincipal();
//		if(principal!=null){
//			return principal.getCacheMap();
//		}
//		return new HashMap<String, Object>();
//	}
	
}
