package com.jason.admin.application.security.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jason.admin.application.security.UserInfoService;
import com.jason.admin.domain.security.UserInfoRepository;
import com.jason.framework.orm.Page;
import com.jason.security.model.UserInfo;

@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {
	
	@Autowired
	private UserInfoRepository userInfoRepository;

	@Override
	public void delete(Long id) {
		userInfoRepository.delete(id);
	}

	@Override
	public void store(UserInfo entity) {
		userInfoRepository.store(entity);
	}

	@Override
	public UserInfo get(Long id) {
		return userInfoRepository.get(id);
	}

	@Override
	public List<UserInfo> query(String queryString, Object... values) {
		return userInfoRepository.query(queryString, values);
	}

	@Override
	public Page<UserInfo> queryPage(Page<UserInfo> page, String hql, Map<String, Object> values) {
		return userInfoRepository.queryPage(page, hql, values);
	}

	@Override
	public UserInfo queryByName(String username) {
		return userInfoRepository.queryByName(username);
	}

	@Override
	public void updatePhoto(String photo, UserInfo user) {
		userInfoRepository.updatePhoto(photo,user);
	}

}
