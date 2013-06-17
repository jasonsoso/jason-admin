package com.jason.admin.application.security.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jason.admin.application.security.AuthorityService;
import com.jason.admin.domain.security.authority.AuthorityRepository;
import com.jason.framework.orm.Page;
import com.jason.security.model.Authority;


@Transactional
public class AuthorityServiceImpl implements AuthorityService {

	private AuthorityRepository authorityRepository;
	@Autowired
	public void setAuthorityRepository(AuthorityRepository authorityRepository) {
		this.authorityRepository = authorityRepository;
	}

	@Override
	public Authority get(String id) {
		return authorityRepository.get(id);
	}

	@Override
	public void store(Authority entity) {
		authorityRepository.store(entity);
	}

	@Override
	public void delete(String id) {
		authorityRepository.delete(id);
	}

	@Override
	public List<Authority> query(String queryString, Object... values) {
		return authorityRepository.query(queryString,values);
	}

	@Override
	public Page<Authority> queryPage(Page<Authority> page, String hql, Map<String, Object> values) {
		return authorityRepository.queryPage(page,hql,values);
	}

}
