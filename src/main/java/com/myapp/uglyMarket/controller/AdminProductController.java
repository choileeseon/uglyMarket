package com.myapp.uglyMarket.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myapp.uglyMarket.dao.CategoryRepository;
import com.myapp.uglyMarket.dao.ProductRepository;
import com.myapp.uglyMarket.entities.Category;
import com.myapp.uglyMarket.entities.Product;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {
	
	@Autowired
	ProductRepository productRepo;
	
	@Autowired
	CategoryRepository categoryRepo;
	
	//전체 리스트
	@GetMapping
	public String index(Model model,@RequestParam(value = "page",defaultValue = "0") int page) {
		
		int perPage = 4; //한페이지에 4개
		Pageable pageable = PageRequest.of(page, perPage); //표시할페이지, 한페이지당 몇개(4개)
		
		//data.domain.Page
		Page<Product> products = productRepo.findAll(pageable);
		List<Category> categories = categoryRepo.findAll();
		
		model.addAttribute("products", products);
		
		//map으로 카테고리 id, name을 같이 불러와서 카테고리id으로 name이 화면에 나오도록 
		HashMap<Integer, String> categoryIdAndName = new HashMap<>();
		for(Category category : categories) {
			categoryIdAndName.put(category.getId(), category.getName());
		}
		
		model.addAttribute("categoryIdAndName", categoryIdAndName);
		
		// 페이지를 보여주기 위한 계산
		long count = productRepo.count(); //전체 상품갯수(long타입 리턴)
		double pageCount = Math.ceil((double)count / (double)perPage); // 13/6개 = 2.1(3페이지) double(소수점나오도록)

		model.addAttribute("pageCount", (int)pageCount); //총페이지
		model.addAttribute("perPage", perPage); 		 //한 페이지당 상품갯수
		model.addAttribute("count", count);				 //전체 상품개수
		model.addAttribute("page", page); 				 //현재 페이지
		
		
		return "/admin/products/index";
	}
	
	//상품 추가
	@GetMapping("/add")
	public String add(@ModelAttribute Product product,Model model) {
		
		//카테고리 선택하도록
		List<Category> categories = categoryRepo.findAll();
		model.addAttribute("categories", categories);
		
		return "/admin/products/add";
	}
	
	@PostMapping("/add")
	public String add(@Valid Product product,BindingResult bindingResult,MultipartFile file ,Model model,RedirectAttributes attr) throws IOException {
		
		//에러났을때 카테고리 다시 불러오기
		if(bindingResult.hasErrors()) {
			List<Category> categories = categoryRepo.findAll();
			model.addAttribute("categories", categories);
			return "/admin/products/add";
		}
		
		boolean fileOk = false;
		byte[] bytes = file.getBytes(); 	//파일의 데이터 (jpg,png 둘다 가능하도록 리스트[])
		String fileName = file.getOriginalFilename(); //파일 이름 (확장자까지 all)
		Path path = Paths.get("src/main/resources/static/media/"+fileName); //파일 저장할 경로
	
		// 파일의 확장자 jpg , png
		if(fileName.endsWith("jpg")||fileName.endsWith("png")) {
			fileOk = true;
		}
		
		// 상품 추가가 성공적일때
		attr.addFlashAttribute("message", "상품이 추가되었습니다.");
		attr.addFlashAttribute("alertClass", "alert-success");
		
		//slug 설정 
		String slug = product.getName().toLowerCase().replace(" ", "-");
		
		//이미 DB에 존재하는
		Product productExists = productRepo.findByName(product.getName());
		
		if(!fileOk) { //위에 확장자 if절에서 false라면 확장자가 틀린 것
			attr.addFlashAttribute("message", "이미지를 jpg 또는 png를 사용하세요");
			attr.addFlashAttribute("alertClass", "alert-success");
			attr.addFlashAttribute("product", product);
			
		}else if (productExists != null) {//이미 등록한 상품이름 있다
			attr.addFlashAttribute("message", "등록한 상품이 있습니다. 다른 상품을 적으세요");
			attr.addFlashAttribute("alertClass", "alert-success");
			attr.addFlashAttribute("product", product);
		}else { //슬러그, 이미지 저장
			product.setSlug(slug);
			product.setImage(fileName);
			productRepo.save(product);
			
			Files.write(path, bytes);
		}
		return "redirect:/admin/products/add";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model model) {
		
		Product product = productRepo.getById(id);
		List<Category> categories = categoryRepo.findAll();

		model.addAttribute("product", product);
		model.addAttribute("categories", categories);
		
		return "/admin/products/edit";
	}
	
	@PostMapping("/edit")
	public String edit(@Valid Product product,BindingResult bindingResult,
			MultipartFile file ,Model model,RedirectAttributes attr) throws IOException {
		
		//수정 전 상품 불러오기(id로 검색) -> 수정할게 있다면 삭제하도록
		Product currentProduct = productRepo.getById(product.getId());
		
		//에러났을때 카테고리 다시 불러오기
		if(bindingResult.hasErrors()) {
			List<Category> categories = categoryRepo.findAll();
			model.addAttribute("categories", categories);
			return "/admin/products/edit";
		}
		
		boolean fileOk = false;
		byte[] bytes = file.getBytes(); 	//파일의 데이터 (jpg,png 둘다 가능하도록 리스트[])
		String fileName = file.getOriginalFilename(); //파일 이름 (확장자까지 all)
		Path path = Paths.get("src/main/resources/static/media/"+fileName); //파일 저장할 경로
	
		// 새 이미지 파일 있으면
		if(!file.isEmpty()) {
			if(fileName.endsWith("jpg") || fileName.endsWith("png")) {
				fileOk = true; // 확장자가 .jpg .png 만 ok
			}
		}else { // 수정 안하면
			fileOk  = true; //기존이미지 사용  ??? 수정안하면 안넘어감
		}
		
		// 상품 추가가 성공적일때
		attr.addFlashAttribute("message", "상품이 수정되었습니다.");
		attr.addFlashAttribute("alertClass", "alert-success");
		
		//slug 설정 
		String slug = product.getName().toLowerCase().replace(" ", "-");
		
		//이미 DB에 존재하는
		//똑같은 상품명이 있는지 검사 (이름을 수정안해도 에러안뜨도록)
		Product productExists = productRepo.findBySlugAndIdNot(slug, product.getId());


		
		if(!fileOk) { //위에 확장자 if절에서 false라면 확장자가 틀린 것
			attr.addFlashAttribute("message", "이미지를 jpg 또는 png를 사용하세요");
			attr.addFlashAttribute("alertClass", "alert-success");
			attr.addFlashAttribute("product", product);
			
		}else if (productExists != null) {//이미 등록한 상품이름 있다
			attr.addFlashAttribute("message", "등록한 상품이 있습니다. 다른 상품을 적으세요");
			attr.addFlashAttribute("alertClass", "alert-success");
			attr.addFlashAttribute("product", product);
		}else { //슬러그, 이미지 저장
			product.setSlug(slug);
			
			//수정 할 이미지 파일이 있다
			if(!file.isEmpty()) { 
				//수정 전 이미지 주소 삭제
				Path currentPath = Paths.get("src/main/resources/static/media/"+ currentProduct.getImage());
				Files.delete(currentPath);
				
				//다시 올라간 수정한 이미지로 저장
				product.setImage(fileName);
				Files.write(path, bytes);
			}else { //수정할 이미지가 없다
				product.setImage(currentProduct.getImage());
			}
			
			productRepo.save(product);
		}
		return "redirect:/admin/products/edit/" + product.getId();
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id, RedirectAttributes attr) throws IOException {
		
		//id로 상품객체 불러와서 이미지파일 삭제 -> 상품삭제
		Product currentProduct = productRepo.getById(id);
		Path currentPath = Paths.get("src/main/resources/static/media/"+currentProduct.getImage());
		
		//상품 이미지 삭제
		Files.delete(currentPath);
		
		productRepo.deleteById(id);
		
		attr.addFlashAttribute("message", "성공적으로 삭제 되었습니다.");
		attr.addFlashAttribute("alertClass", "alert-success");		

		return "redirect:/admin/products";
	}
}




