package com.myapp.uglyMarket.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.uglyMarket.entities.ReplyVO;
import com.myapp.uglyMarket.service.ReplyService;

@RestController
@RequestMapping(value="/reply", method={RequestMethod.POST})
public class ReplyController {

	//ReplyService 객체를 생성자 주입
	private ReplyService replyService;
	
	//생성자 객체 주입 시 @Autowired 필요없음
	public ReplyController(ReplyService replyService) {
		this.replyService = replyService;
	}
	
	@PostMapping("/reply")
	public ReplyVO replyEnrollPOST(@RequestBody ReplyVO reply) {
		
		//입력된 json타입인 데이터를 받아서 reply객체 json타입으로 리턴
		replyService.enroll(reply); //DB저장
		return reply; //제이슨 타입
		
	}
	
	@GetMapping("/{bno}") //게시글에 달린 댓글들 불러오기
	public List<ReplyVO> replyListGET(@PathVariable int bno){
		return replyService.getReplyList(bno);
	}
	
	//id가 없는건 reply에 id가 포함돼있어서
	@PutMapping
	public ReplyVO replyUpdatePUT(@RequestBody ReplyVO reply) {
		replyService.modify(reply); //DB에 댓글 수정하기
		return reply;	//테스트로 reply 리턴
	}
	
	@DeleteMapping("/{id}")
	public void replyDELETE(@PathVariable("id") int reply_no) {
		replyService.delete(reply_no);
	}
}






