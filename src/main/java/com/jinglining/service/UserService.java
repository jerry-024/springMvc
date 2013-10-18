package com.jinglining.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.jinglining.dao.UserDao;
import com.jinglining.pojo.User;

@Named
@Transactional
public class UserService {
	
	@Inject
	private UserDao userDao;
	
	public List<User> findAll(){
		
		return userDao.findAll();
	}

	public Object findById(int id) {
		
		return userDao.findById(id);
	}

	public void save(User user) {
		userDao.save(user);
		
	}

	public void del(int id) {
		
		userDao.del(id);
		
	}

}
