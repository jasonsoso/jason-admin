package com.jason.admin.application.security;

import java.util.List;
import java.util.Map;

import com.jason.admin.domain.security.authority.Authority;
import com.jason.framework.orm.Page;

public interface AuthorityService {

	Authority get(String id);

	void store(Authority entity);

	void delete(String id);

	List<Authority> query(String queryString, Object... values);

	Page<Authority> queryPage(Page<Authority> page, String hql, Map<String, Object> values);
}
