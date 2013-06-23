package com.jason.admin.infrastruture.persist.hibernate.impl.security;

import org.springframework.stereotype.Repository;

import com.jason.admin.domain.security.UserInfoRepository;
import com.jason.framework.orm.hibernate.HibernateRepositorySupport;
import com.jason.security.model.UserInfo;

@Repository
public class HibernateUserInfoRepository extends HibernateRepositorySupport<Long, UserInfo> implements UserInfoRepository {

	@Override
	public UserInfo queryByName(String username) {
		return (UserInfo) super.queryUnique("from UserInfo where username=?", username);
	}

	@Override
	public void updatePhoto(String photo, UserInfo user) {
		super.createHqlQuery("update UserInfo u set u.photo=? where u.id=?", photo,user.getId());
	}

}
