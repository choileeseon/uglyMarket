package com.myapp.uglyMarket.security;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.uglyMarket.dao.UserRepository;
import com.myapp.uglyMarket.entities.User;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping
	public String register(User user) {
		return "register"; //가입하기 화면 뷰 보여주기
	}

	@PostMapping
	public String register(@Valid User user, BindingResult bindingResult,Model model) {
		
		//1. 유효성 검사 틀릴경우
		if(bindingResult.hasErrors()) {
			return "register";
		}
		
		//2. 비밀번호 확인시 안맞을 경우
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			model.addAttribute("passwordNotMatch", "패스워드 확인이 틀립니다.");
			return "register";
		}
		
		//3. 패스워드 암호화 해서 입력함
		user.setPassword(passwordEncoder.encode(user.getPassword())); //패스워드 가져오서 비번 인코더
		
		//4. DB에 새 유저 저장
		userRepo.save(user);
		
		return "redirect:/login"; //로그인 페이지로
	}
}
