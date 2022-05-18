package com.myapp.uglyMarket.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.uglyMarket.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	User findByUsername(String username);

}
