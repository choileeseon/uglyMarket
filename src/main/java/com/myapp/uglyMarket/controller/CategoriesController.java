package com.myapp.uglyMarket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myapp.uglyMarket.dao.CategoryRepository;
import com.myapp.uglyMarket.dao.ProductRepository;
import com.myapp.uglyMarket.entities.Category;
import com.myapp.uglyMarket.entities.Product;

@Controller
@RequestMapping("/category")
public class CategoriesController {

	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	//페이지네이션
	@GetMapping("/{slug}")
	public String category(@PathVariable String slug, Model model,@RequestParam(value = "page",defaultValue = "0") int page) {
		
		int perPage = 4; //한페이지에 4개
		Pageable pageable = PageRequest.of(page, perPage); //표시할페이지, 한페이지당 몇개(4개)
		long count = 0;
		
		//카테고리 선택 (전체,과일,채소)
		if(slug.equals("전체")) {
			Page<Product> products = productRepo.findAll(pageable);
			count = productRepo.count(); //전체 제품 수
			
			model.addAttribute("products", products);			
		}else { //카테고리별 페이징
			Category category = categoryRepo.findBySlug(slug);
			if(category == null) { //카테고리 없으면
				return "redirect:/"; //홈페이지로
			}
			String categoryId = Integer.toString(category.getId()); //카테고리id는 int, prodcut의 카테고리id는 String이라서 형변환 동일하게
			String categoryName = category.getName();
			//해당 페에지에 제품 수 (페이지네이션)
			List<Product> products = productRepo.findAllByCategoryId(categoryId,pageable);
			count = productRepo.countByCategoryId(categoryId);
			
			model.addAttribute("products", products); //선택한 카테고리의 제품들
			model.addAttribute("categoryName", categoryName);
		}
		
		double pageCount = Math.ceil((double)count / (double)perPage); // 13/6개 = 2.1(3페이지) double(소수점나오도록)

		model.addAttribute("pageCount", (int)pageCount); //총페이지
		model.addAttribute("perPage", perPage); 		 //한 페이지당 상품갯수
		model.addAttribute("count", count);				 //전체 상품개수
		model.addAttribute("page", page); 				 //현재 페이지
		
		
		return "products";
	}
}
