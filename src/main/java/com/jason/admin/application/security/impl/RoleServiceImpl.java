package com.jason.admin.application.security.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jason.admin.application.security.RoleService;
import com.jason.admin.domain.security.RoleRepository;
import com.jason.framework.orm.Page;
import com.jason.security.model.Role;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void delete(String id) {
		roleRepository.delete(id);
	}

	@Override
	public void store(Role entity) {
		roleRepository.store(entity);
	}

	@Override
	public Role get(String id) {
		return roleRepository.get(id);
	}

	@Override
	public List<Role> query(String queryString, Object... values) {
		return roleRepository.query(queryString, values);
	}

	@Override
	public Page<Role> queryPage(Page<Role> page, String hql, Map<String, Object> values) {
		return roleRepository.queryPage(page, hql, values);
	}

}
