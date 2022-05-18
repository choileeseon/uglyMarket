package com.myapp.uglyMarket.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.uglyMarket.entities.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer>{

	Admin findByUsername(String username);

}
