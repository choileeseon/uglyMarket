package com.myapp.uglyMarket.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myapp.uglyMarket.dao.ProductRepository;
import com.myapp.uglyMarket.entities.Cart;
import com.myapp.uglyMarket.entities.Product;

@Controller
@RequestMapping("/cart")
@SuppressWarnings("unchecked")
public class CartController {
	
	@Autowired
	private ProductRepository productRepo;
	
	@GetMapping("/add/{id}")
	public String add(@PathVariable int id,HttpSession session,Model model,
						@RequestParam(required = false) String cartPage){
		
		Product product = productRepo.getById(id);
		
		// 1. 카트가 없을때(이미 만들어진 장바구니가 없을때,처음만들때)
		if(session.getAttribute("cart") == null) { //getAttribute : 세션값 조회 
			//1-1. 맵<id,카트> 로 리스트를 만든다.
			HashMap<Integer, Cart> cart = new HashMap<>();
			//1-2. id,카트객체들을 넣는다.
			cart.put(id, new Cart(id, product.getName(), product.getPrice(),1, product.getImage()));
			//1-3. 세션에 저장
			session.setAttribute("cart", cart);
		}else { // 2.장바구니 만들어진 경우 (2-1. 제품이 담긴경우 / 2-2. 제품 없는경우)
			//카트를 getAttribute 가져올때 object로 저장되기 때문에 해쉬맵으로 타입변환해서 가져오기
			HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>)session.getAttribute("cart"); 
			// 2-1. 제품이 담긴경우
			if(cart.containsKey(id)) { 
				int qty = cart.get(id).getQuantity(); //현재카트의 수량
				cart.put(id, new Cart(id, product.getName(), product.getPrice(),++qty, product.getImage())); //수량추가			
			}else { //2-2. 제품 없는경우 새카트 저장
				cart.put(id, new Cart(id, product.getName(), product.getPrice(),1, product.getImage()));
				session.setAttribute("cart", cart);
			}
		
		}
		
		//카트 보기 
		HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>)session.getAttribute("cart");
		
		int size = 0; //장바구니 상품 갯수
		int totalPrice = 0; //총 가격
		
		//장바구니 cart객체 반복 (1,cart객체) (2,cart객체)... id빼고 객체만
		for(Cart item : cart.values()) {
			size += item.getQuantity(); // 장바구니 갯수
			totalPrice += item.getQuantity() * Integer.parseInt(item.getPrice()); //가격 문자형인데 int형변환 해주기
		}
		model.addAttribute("size", size);
		model.addAttribute("totalPrice", totalPrice);
		
		//cart.html에서 +버튼 눌렀을때 다시 장바구니 페이지로
		if(cartPage != null) { 
			return "redirect:/cart/view";
		}
		
		return "cart_view";
	}
	
	@GetMapping("/view")
	public String view(HttpSession session, Model model) {
		
		if(session.getAttribute("cart") == null) {
			return "redirect:/";
		}
		
		//장바구니 가져오기
		HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>)session.getAttribute("cart");
		model.addAttribute("cart", cart);
		model.addAttribute("noCartView", true);
		
		
		return "cart";
	}
	
	// (-)버튼을 눌렀을때
	@GetMapping("/subtract/{id}")
	public String subtract(@PathVariable int id,HttpSession session,Model model, HttpServletRequest httpServletRequest) {
		
		//카트보기 
		HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>)session.getAttribute("cart");
		
		
		int qty = cart.get(id).getQuantity(); //해당 상품의 갯수
		
		// - 버튼 누를때 해당상품 수량이 1 이면 삭제하도록
		if(qty == 1) {
			cart.remove(id); //key값으로 삭제
			if(cart.size() == 0) { // 카트가 하나도 없으면 장바구니 자체를 삭제
				session.removeAttribute("cart"); //세션에서 삭제
			}
		}else { //아니면 수량만 삭제
			cart.get(id).setQuantity(--qty); 
		}
		
		String refererLink = httpServletRequest.getHeader("Referer"); //요청된 이전주소의 정보가 들어있음 
		
		return "redirect:" + refererLink;
	}
	
	// 상품 '삭제' 버튼을 눌렀을때
	@GetMapping("/remove/{id}")
	public String remove(@PathVariable int id,HttpSession session,Model model, HttpServletRequest httpServletRequest) {
		
		//카트보기 
		HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>)session.getAttribute("cart");
		
		//id로 삭제
		cart.remove(id);
		
		// 카트가 하나도 없으면 장바구니 자체를 삭제
		if(cart.size() == 0) { 
			session.removeAttribute("cart"); //세션에서 삭제
		}
		
		String refererLink = httpServletRequest.getHeader("Referer"); //요청된 이전주소의 정보가 들어있음 
		
		return "redirect:" + refererLink;
	}
	
	// 장바구니 '비우기' 버튼을 눌렀을때
	@GetMapping("/clear")
	public String clear(HttpSession session,Model model,HttpServletRequest httpServletRequest) {
		
		
		session.removeAttribute("cart"); //세션에서 삭제
		
		String refererLink = httpServletRequest.getHeader("Referer"); //요청된 이전주소의 정보가 들어있음 
		
		return "redirect:" + refererLink;
	}
}








