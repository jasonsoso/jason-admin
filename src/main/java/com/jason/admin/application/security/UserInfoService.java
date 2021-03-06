package com.jason.admin.application.security;

import java.util.List;
import java.util.Map;

import com.jason.framework.orm.Page;
import com.jason.security.model.UserInfo;


public interface UserInfoService {

	void delete(Long id);

	void store(UserInfo entity);

	UserInfo get(Long id);

	/**
	 * 根据用户名查询用户对象
	 * @param username
	 * @return
	 */
	UserInfo queryByName(String username);

	List<UserInfo> query(String queryString, Object... values);

	Page<UserInfo> queryPage(Page<UserInfo> page, String hql, Map<String, Object> values);

	/**
	 * 更新用户的头像路径
	 * @param photo
	 * @param user
	 */
	void updatePhoto(String photo,UserInfo user);

}
