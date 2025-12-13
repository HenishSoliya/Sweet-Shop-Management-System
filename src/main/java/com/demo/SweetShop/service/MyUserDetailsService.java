package com.demo.SweetShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.SweetShop.model.MyUser;
import com.demo.SweetShop.repository.MyUserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private MyUserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		MyUser user = repository.findByUserName(userName);
		if (user == null) {
			throw new UsernameNotFoundException("user not found");
		}
		return User.builder().username(user.getUserName()).password(user.getPassword()).roles(user.getRole()).build();
	}
}