package com.jason.admin.infrastruture.persist.hibernate.impl.security;

import org.springframework.stereotype.Repository;

import com.jason.admin.domain.security.AuthorityRepository;
import com.jason.framework.orm.hibernate.HibernateRepositorySupport;
import com.jason.security.model.Authority;

@Repository
public class HibernateAuthorityRepository extends HibernateRepositorySupport<String, Authority> implements AuthorityRepository{

	
}
