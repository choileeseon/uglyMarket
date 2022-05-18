package com.myapp.uglyMarket;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.myapp.uglyMarket.dao.CategoryRepository;
import com.myapp.uglyMarket.dao.PageRepository;
import com.myapp.uglyMarket.entities.Cart;
import com.myapp.uglyMarket.entities.Category;
import com.myapp.uglyMarket.entities.Page;

//모든 컨트롤러에 적용(모든 페이지에 적용)
@ControllerAdvice
public class Common {

	@Autowired
	private PageRepository pageRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	//모델에 추가한다.
	@ModelAttribute
	public void sharedData(Model model,HttpSession session,Principal principal) {
		
		if(principal != null) { //인증된상태 , principal : 인증정보 저장 
			model.addAttribute("principal", principal.getName()); //유저네임을 전달
		}
		
		// cpages에 모든 페이지를 순서대로 담아서 네브바에 전달
		List<Page> cpages = pageRepo.findAllByOrderBySortingAsc();
		
		// 모든 카테고리
		List<Category> categories = categoryRepo.findAllByOrderBySortingAsc();
		
		// 현재 카트 상태 (없으면 false)
		boolean cartActive = false; 
		
		//카트가 있으면
		if(session.getAttribute("cart") != null) {
			@SuppressWarnings("unchecked")
			HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>)session.getAttribute("cart"); 
		
			int size = 0; //장바구니 상품 갯수
			int totalPrice = 0; //총 가격
			
			//장바구니 cart객체 반복 (1,cart객체) (2,cart객체)... id빼고 객체만
			for(Cart item : cart.values()) {
				size += item.getQuantity(); // 장바구니 갯수
				totalPrice += item.getQuantity() * Integer.parseInt(item.getPrice()); //가격 문자형인데 int형변환 해주기
			}
			model.addAttribute("csize", size);
			model.addAttribute("ctotal", totalPrice);
			
			cartActive = true;
		}
		
		model.addAttribute("cpages", cpages);
		model.addAttribute("ccategories", categories);
		model.addAttribute("cartActive", cartActive); //없으면 false 있으면 true
	}
}




