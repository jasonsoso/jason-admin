package com.jason.admin.domain.security;

import java.util.List;
import java.util.Map;

import com.jason.framework.orm.Page;
import com.jason.security.model.UserInfo;

public interface UserInfoRepository {
	
	UserInfo queryByName(String username);

	Page<UserInfo> queryPage(Page<UserInfo> page, String hql, Map<String, Object> values);

	List<UserInfo> query(String queryString, Object... values);

	UserInfo get(Long id);

	void store(UserInfo entity);

	void delete(Long id);
	
	void updatePhoto(String photo, UserInfo user) ;
}
