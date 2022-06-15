package com.myapp.uglyMarket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myapp.uglyMarket.dao.ReplyMapper;
import com.myapp.uglyMarket.entities.ReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService{

	//매퍼 객체 (필드주입)
	@Autowired
	private ReplyMapper replyMapper;
	
	@Override
	public void enroll(ReplyVO reply) {
		replyMapper.enroll(reply);
	}

	@Override
	public List<ReplyVO> getReplyList(int reply_bno) {
		return replyMapper.getReplyList(reply_bno);
	}

	@Override
	public int modify(ReplyVO reply) {
		return replyMapper.modify(reply);
	}

	@Override
	public int delete(int reply_no) {
		return replyMapper.delete(reply_no);
	}
}
