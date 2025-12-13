package com.demo.SweetShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.SweetShop.model.MyUser;
import com.demo.SweetShop.repository.MyUserRepository;

@Service
public class AuthService {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private MyUserRepository repository;

	@Autowired
	private JWTService jwtService;

	@Value("${sweetshop.security.bcrypt.round}")
	private int round;

	public String verify(MyUser user) {
		Authentication authentication = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
		if (authentication.isAuthenticated()) {
			return jwtService.generateToken(user.getUserName());
		} else {
			return "fail";
		}
	}

	public String register(MyUser user) {
		if (repository.findByUserName(user.getUserName()) == null) {
			user.setPassword(new BCryptPasswordEncoder(round).encode(user.getPassword()));
			user.setRole("USER");
			repository.save(user);
			return "Registration successful";
		}
		return "Username already exists";
	}

}
