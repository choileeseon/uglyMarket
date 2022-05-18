package com.myapp.uglyMarket.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myapp.uglyMarket.dao.CategoryRepository;
import com.myapp.uglyMarket.entities.Category;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

	@Autowired
	private CategoryRepository categoryRepo;
	
	// 전체 카테고리 리스트
	@GetMapping
	public String index(Model model) {
		
		List<Category> categories = categoryRepo.findAllByOrderBySortingAsc(); //카테고리 전체 리스트(ASC?)
		model.addAttribute("categories", categories);
		
		return "/admin/categories/index";
	}
	
	// 카테고리 추가
	@GetMapping("/add")
	public String add(@ModelAttribute Category category) {
		
		return "/admin/categories/add";
	}
	
	@PostMapping("/add")
	public String add(@Valid Category category, BindingResult bindingResult, RedirectAttributes attr) {
		
		if(bindingResult.hasErrors()) {
			return "/admin/categories/add";
		}
		
		attr.addFlashAttribute("message", "카테고리 추가되었습니다.");
		attr.addFlashAttribute("alertClass", "alert-success");
		
		//슬러그 적는 칸은 없어서 name을 소문자로, 공백은 -로 채워준다
		String slug = category.getName().toLowerCase().replace(" ", "-");
		
		//카테고리 이름을 가져온다
		Category categoryExists = categoryRepo.findByName(category.getName());
		
		//이미 카테고리가 있을 경우
		if(categoryExists != null) {
			attr.addFlashAttribute("message", "이미 같은 카테고리가 있습니다.");
			attr.addFlashAttribute("alertClass", "alert-danger");
			attr.addFlashAttribute("category", category); //입력내용 그대로 가져오기
		}else { //기존 카테고리 없을 경우
			category.setSlug(slug);
			//category.setSorting(100); //기본 솔팅값
			long num = categoryRepo.count();
			category.setSorting((int)num);
			categoryRepo.save(category); //DB에 저장
		}
		
		return "redirect:/admin/categories/add"; //post-redirect-get 
		
	}
	
	// 카테고리 수정
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model model) {
		
		Category category = categoryRepo.getById(id);
		model.addAttribute("category", category);
		
		return "/admin/categories/edit";
	}
	
	@PostMapping("/edit")
	public String edit(@Valid Category category, BindingResult bindingResult, RedirectAttributes attr) {
		
		if(bindingResult.hasErrors()) {
			return "/admin/categories/edit";
		}
		
		attr.addFlashAttribute("message", "카테고리 수정되었습니다.");
		attr.addFlashAttribute("alertClass", "alert-success");
		
		//슬러그 적는 칸은 없어서 name을 소문자로, 공백은 -로 채워준다
		String slug = category.getName().toLowerCase().replace(" ", "-");
		
		//카테고리 이름을 가져온다
		Category categoryExists = categoryRepo.findByName(category.getName());
		
		//이미 카테고리가 있을 경우
		if(categoryExists != null) {
			attr.addFlashAttribute("message", "이미 같은 카테고리가 있습니다.");
			attr.addFlashAttribute("alertClass", "alert-danger");
			attr.addFlashAttribute("category", category); //입력내용 그대로 가져오기
		}else { //기존 카테고리 없을 경우
			category.setSlug(slug);
			//category.setSorting(100); //기본 솔팅값
			
			categoryRepo.save(category); //DB에 저장
		}
		
		return "redirect:/admin/categories/edit/" + category.getId(); // 수정시 id가져오기 꼭 중요! /조심
		
	}
	
	// 카테고리 삭제
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id,RedirectAttributes attr) {
		
		categoryRepo.deleteById(id);
		attr.addFlashAttribute("message", "메세지가 삭제되었습니다.");
		attr.addFlashAttribute("alertClass", "alert-success");
		
		return "redirect:/admin/categories";
	}
	
	//sorting 
	//AJAX에서 요청을 한다. view대신 데이터(문자열 "ok")만 가게하기 위해 @ResponseBody
	@PostMapping("/reorder")
	public @ResponseBody String reorder(@RequestParam("id[]") int[] id) { //파라미터이름 id[], 정수형 배열
		
		int count = 1;
		Category category;
		
		for(int categoryId : id) {
			category = categoryRepo.getById(categoryId); //db에서 id로 page객체 검색
			category.setSorting(count); //sorting 값에 1을 넣고
			categoryRepo.save(category); 	//DB에 저장
			count++;				//그 다음은 2
		}
		
		return "ok";
	}
}
