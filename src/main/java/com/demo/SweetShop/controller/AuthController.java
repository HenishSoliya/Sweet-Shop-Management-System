package com.demo.SweetShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.SweetShop.model.MyUser;
import com.demo.SweetShop.service.AuthService;

@RestController
@RequestMapping("api/auth")
public class AuthController {

	@Autowired
	private AuthService service;

	@PostMapping("login")
	public String login(@RequestBody MyUser user) {
		return service.verify(user);
	}

	@PostMapping("register")
	public String register(@RequestBody MyUser user) {
		return service.register(user);
	}

}
