package com.myapp.uglyMarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myapp.uglyMarket.entities.Criteria;
import com.myapp.uglyMarket.entities.PageMakerDTO;
import com.myapp.uglyMarket.entities.RecipeVO;
import com.myapp.uglyMarket.service.RecipeService;

import lombok.extern.java.Log;

@Controller
@RequestMapping("/recipe/board")
@Log //콘솔 로그 출력(print out 대신 사용)
public class RecipeController {
	
	private RecipeService recipeService;
	
	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	
	@GetMapping("/enroll")
	public String recipeEnrollGet(Model model) {
		model.addAttribute("board", new RecipeVO());
		return "recipe_enroll";
	}
	
	@PostMapping("/enroll")
	public String recipeEnrollPost(RecipeVO board, RedirectAttributes attr) {
		//log.info("RecipeVO : " + board);
		recipeService.enroll(board);
		attr.addFlashAttribute("message", "게시물 등록 성공!");
		return "redirect:/recipe/board/list";
	}
	
//	@GetMapping("/list")
//	public String recipeListGet(Model model) {
//		//boardList에 모든 게시글을 전달
//		model.addAttribute("boardList", recipeService.getList());
//		return "recipe_list";
//	}
	
	//페이징 되는 목록 Criteria(pageNum,amount)추가 
	@GetMapping("/list")
	public String recipeListGet(Model model, Criteria criteria) {
		//boardList에 페이징 된 게시글을 전달
		model.addAttribute("boardList", recipeService.getListPaging(criteria));
		
		//마지막페이지,시작페이지,실제끝페이지,이전버튼,다음버튼 모두 계산 가능
		int totalPost = recipeService.getTotalPost(criteria);
		PageMakerDTO pmk = new PageMakerDTO(totalPost, criteria);
		model.addAttribute("pmk", pmk);
		
		return "recipe_list";
	}
	
	
	//게시판 글 조회
	@GetMapping("/get")
	public String recipePageGet(@RequestParam("id") int id, Model model) {
		model.addAttribute("board", recipeService.getPage(id));
		return "get";
	}
	
	//게시판 글 수정
	@GetMapping("/modify")
	public String recipeModifyGet(@RequestParam("id") int id, Model model) {
		model.addAttribute("board", recipeService.getPage(id));
		return "modify";
	}
	
	@PostMapping("/modify")
	public String recipeModifyPost(RecipeVO boardVo,RedirectAttributes attr) {
		recipeService.modify(boardVo); // modify페이지에서 수정된 내용을 업뎃함
		attr.addFlashAttribute("message", "수정 성공!"); 
		return "redirect:/recipe/board/list"; //post - redirect - get
	}
	
	@GetMapping("/delete")
	public String recipeDeleteGet(@RequestParam("id") int id,  RedirectAttributes attr) {
		recipeService.delete(id);
		attr.addFlashAttribute("message", "삭제 성공!");
		return "redirect:/recipe/board/list";
	}
	
	
}







