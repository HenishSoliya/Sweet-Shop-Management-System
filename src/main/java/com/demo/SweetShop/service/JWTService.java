package com.demo.SweetShop.service;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.SweetShop.model.MyUser;
import com.demo.SweetShop.repository.MyUserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JWTService {

	private SecretKey key;

	@Autowired
	private MyUserRepository repository;

	public JWTService() {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
			key = keyGen.generateKey();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public String generateToken(String userName) {
		MyUser user = repository.findByUserName(userName);

		return Jwts.builder().subject(user.getUserName()).claim("id", user.getId()).claim("role", user.getRole())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 30 * 60 * 60 * 1000)).signWith(key).compact();
	}

	public Claims verifyClaims(String token) {
		try {
			return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
