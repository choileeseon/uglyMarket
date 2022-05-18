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

import com.myapp.uglyMarket.dao.PageRepository;
import com.myapp.uglyMarket.entities.Page;

@Controller
@RequestMapping("/admin/pages")
public class AdminPageController {
	
	@Autowired
	private PageRepository pageRepo; 
	
	@GetMapping
	public String index(Model model) {
		
		List<Page> pages = pageRepo.findAllByOrderBySortingAsc(); //DB에 sorting 오름차순 값을 가져와 정렬해서 새로고침시 그대로
		model.addAttribute("pages", pages);
		
		return "/admin/pages/index";
	}
	
	@GetMapping("/add")
	public String add(@ModelAttribute Page page) { //(@ModelAttribute Page page) 써서 간략하게 쓰기도 가능 
		
		//model.addAttribute("page", new Page() );
		
		return "/admin/pages/add";
	}
	
	@PostMapping("/add")
	public String add(@Valid Page page, BindingResult bindingResult, RedirectAttributes attr) {
		
		//에러 있으면 다시 에러페이지로
		if(bindingResult.hasErrors()) {
			return "admin/pages/add";
		}
		attr.addFlashAttribute("message", "페이지 추가되었습니다.");
		attr.addFlashAttribute("alertClass", "alert-success");

		//슬러그가 없으면 제목을 소문자로,공백은 "-"로 대체 : 있으면 슬러그를 소문자로,공백을 "-"로 대체
		String slug = (page.getSlug() == "") ? page.getTitle().toLowerCase().replace(" ", "-"):page.getSlug().toLowerCase().replace(" ", "-");
		Page sulgExist = pageRepo.findBySlug(slug);
		
		if(sulgExist != null) {
			attr.addFlashAttribute("message", "이미 같은 슬러그가 존재합니다.");
			attr.addFlashAttribute("alertClass", "alert-danger");
			attr.addFlashAttribute("page", page); // 안지워지고 다시 불러오는 @@안됨 get매핑에서 @ModelAttribute써야 나옴??
			
		}else {
			page.setSlug(slug); //수정된 슬러그 저장
			//page.setSorting(100); // 기본 0으로 저장이 됨
			long num = pageRepo.count();
			page.setSorting((int)num);
			
			System.out.println(page.getSorting());
			pageRepo.save(page);
		}
		return "redirect:/admin/pages/add";
	}
	
	// 페이지 수정
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model model) {
		Page page = pageRepo.getById(id);
		model.addAttribute("page", page); //수정페이지에 page정보객체를 전달
		
		return "/admin/pages/edit";
	}
	
	@PostMapping("/edit")
	public String edit(@Valid Page page, BindingResult bindingResult, RedirectAttributes attr) {
		
		//에러 있으면 다시 에러페이지로
		if(bindingResult.hasErrors()) {
			return "/admin/pages/edit";
		}
		attr.addFlashAttribute("message", "페이지 수정되었습니다.");
		attr.addFlashAttribute("alertClass", "alert-success");

		//슬러그가 없으면 제목을 소문자로,공백은 "-"로 대체 : 있으면 슬러그를 소문자로,공백을 "-"로 대체
		String slug = (page.getSlug() == "") ? page.getTitle().toLowerCase().replace(" ", "-")
				: page.getSlug().toLowerCase().replace(" ", "-");
		Page sulgExist = pageRepo.findBySlugAndIdNot(slug,page.getId()); //슬러그를 수정안할때를 대비해 같은 슬러그라고 에러 안뜨도록 id도 추가해서 구분하기
		//슬러그를 찾는데 현재id가 아닌 다른 id로 동일한 slug가 있는지 확인하는 메서드!
		
		//있다
		if(sulgExist != null) {
			attr.addFlashAttribute("message", "이미 같은 슬러그가 존재합니다.");
			attr.addFlashAttribute("alertClass", "alert-danger");
			attr.addFlashAttribute("page", page); //또안됨 안지워지고 다시 불러오는 @@안됨 get매핑에서 @ModelAttribute써야 나옴??
			
		}else {
			page.setSlug(slug); //수정된 슬러그 저장
			//page.setSorting(100); // 기본 0으로 저장이 됨 수정할땐 필요없음
			
			pageRepo.save(page);
		}
		return "redirect:/admin/pages/edit/" + page.getId(); //post-redirect-get
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id, RedirectAttributes attr) {
		
		pageRepo.deleteById(id);
		attr.addFlashAttribute("message", "페이지가 삭제되었습니다.");
		attr.addFlashAttribute("alertClass", "alert-success");
		
		return "redirect:/admin/pages";
	}
	
	//sorting 
	//AJAX에서 요청을 한다. view대신 데이터(문자열 "ok")만 가게하기 위해 @ResponseBody
	@PostMapping("/reorder")
	public @ResponseBody String reorder(@RequestParam("id[]") int[] id) { //파라미터이름 id[], 정수형 배열
		
		int count = 1;
		Page page;
		
		for(int pageId : id) {
			page = pageRepo.getById(pageId); //db에서 id로 page객체 검색
			page.setSorting(count); //sorting 값에 1을 넣고
			pageRepo.save(page); 	//DB에 저장
			count++;				//그 다음은 2
		}
		
		return "ok";
	}
	
	
}
