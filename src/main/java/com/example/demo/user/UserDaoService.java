package com.example.demo.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {
	 
	private static List<UserOld_SinJPA> users = new ArrayList<>();
	
	private static int userCount = 3;
	
	static {
		users.add(new UserOld_SinJPA(1, "Fer", new Date()));
		users.add(new UserOld_SinJPA(2, "Fer1", new Date()));
		users.add(new UserOld_SinJPA(3, "Fer2", new Date()));
	}
	
	public List<UserOld_SinJPA> findAll() {
		return users;
	}
	
	public UserOld_SinJPA save(UserOld_SinJPA user) {
		if(user.getId()==null) {
			user.setId(++userCount);
		}
		users.add(user);
		return user;
	}
	
	public UserOld_SinJPA findOne(int id) {
		for (UserOld_SinJPA user : users) {
			if(user.getId() == id) {
				return user;
			}
		}
		return null;
	}
	
	public UserOld_SinJPA deleteById(int id) {
		Iterator<UserOld_SinJPA> iterator = users.iterator();
		
		while (iterator.hasNext()) {
			UserOld_SinJPA user = iterator.next();
			if(user.getId() == id) {
				iterator.remove();
				return user;
			}
		} 
		
		return null;
	}
	
	public boolean deleteByIdOwn(int id) {
		for (UserOld_SinJPA user : users) {
			if(user.getId() == id) {
				return users.remove(user);
			}
		}
		return false;
	}
}
