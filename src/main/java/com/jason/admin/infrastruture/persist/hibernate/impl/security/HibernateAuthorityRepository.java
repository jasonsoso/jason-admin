package com.jason.admin.infrastruture.persist.hibernate.impl.security;

import org.springframework.stereotype.Repository;

import com.jason.admin.domain.security.authority.Authority;
import com.jason.admin.domain.security.authority.AuthorityRepository;
import com.jason.framework.orm.hibernate.HibernateRepositorySupport;

@Repository
public class HibernateAuthorityRepository extends HibernateRepositorySupport<String, Authority> implements AuthorityRepository{

	@Override
	public void delete(String id) {
		super.delete(super.get(id));
	}
	
}
