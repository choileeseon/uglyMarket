package com.myapp.uglyMarket.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myapp.uglyMarket.dao.AdminRepository;
import com.myapp.uglyMarket.dao.UserRepository;
import com.myapp.uglyMarket.entities.Admin;
import com.myapp.uglyMarket.entities.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private AdminRepository adminRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 유저DB에서 필요한 유저,관리자 정보를 읽어온다. (입력파라메터는 username)
		
		User user = userRepo.findByUsername(username);
		Admin admin = adminRepo.findByUsername(username);
		
		if(admin != null) return admin; // 먼저! 관리자가 있으면 관리자로 리턴
		if(user != null) return user;	// 유저가 있으면 유저로 리턴
		
		//관리자 유저 모두 없으면
		throw new UsernameNotFoundException("유저 " + username + "이 없습니다.");
		
	}
}
