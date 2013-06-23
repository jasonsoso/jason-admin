package com.jason.admin.infrastruture.persist.hibernate.impl.security;

import org.springframework.stereotype.Repository;

import com.jason.admin.domain.security.RoleRepository;
import com.jason.framework.orm.hibernate.HibernateRepositorySupport;
import com.jason.security.model.Role;
	
@Repository
public class HibernateRoleRepository extends HibernateRepositorySupport<String, Role> implements RoleRepository{

	
}
