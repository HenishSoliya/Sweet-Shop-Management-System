package com.demo.SweetShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.SweetShop.model.MyUser;

public interface MyUserRepository extends JpaRepository<MyUser, Integer> {

	MyUser findByUserName(String userName);

}
