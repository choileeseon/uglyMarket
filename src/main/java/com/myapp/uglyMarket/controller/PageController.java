package com.myapp.uglyMarket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.uglyMarket.dao.CategoryRepository;
import com.myapp.uglyMarket.dao.PageRepository;
import com.myapp.uglyMarket.entities.Category;
import com.myapp.uglyMarket.entities.Page;


/*
 * 쇼핑몰 기본 홈페이지
 */

@Controller
@RequestMapping("/")
public class PageController {
	
	@Autowired
	private PageRepository pageRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@GetMapping
	public String home(Model model) {
		//슬러그를 가져와서 메인홈페이지를 만들어준다.
		Page page = pageRepo.findBySlug("home");
		model.addAttribute("page", page);
		
		return "page";
	}
	
	//슬러그가 들어올경우 슬러그가 해당하는 페이지를 출력한다.
	@GetMapping("/{slug}")
	public String home(@PathVariable String slug, Model model) {
		//슬러그를 가져와서 메인홈페이지를 만들어준다.
		Page page = pageRepo.findBySlug(slug);
		
		if(page == null) {
			return "redirect:/"; //페이지가 없으면 홈페이지로 이동
		}
		else if(slug.equals("shop")) {
			List<Category> categories = categoryRepo.findAll();
		
			model.addAttribute("ccategories", categories);
			return "shopPage";
		}
		
		model.addAttribute("page", page);
	
		
		return "page";
	}
	//###shop페이지에만 카테고리 나오게!!
	//위에 else if와 결과는 같다 
	
//	@GetMapping("/shop")
//	public String shop(Page page, Model model) {
//		//슬러그를 가져와서 메인홈페이지를 만들어준다.
//		//Page page = pageRepo.findBySlug(slug);
//		
//		List<Category> categories = categoryRepo.findAll();
//		
//		model.addAttribute("ccategories", categories);	
//		
//		return "shopPage";
//	}
	
//	
//	@ModelAttribute
//	public void sharedData(Page pag,Model model) {
//		Page page = pageRepo.findBySlug(slug);
//		if(page.getSlug()=="shop") {
//			List<Category> categories = categoryRepo.findAll();
//			
//			model.addAttribute("ccategories", categories);
//		}
//	}
	
	@GetMapping("/login")
	public String login(Model model) {
		
		return "login";
	}

	
}






