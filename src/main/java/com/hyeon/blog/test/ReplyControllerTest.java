package com.hyeon.blog.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hyeon.blog.model.Board;
import com.hyeon.blog.model.Reply;
import com.hyeon.blog.repository.BoardRepository;
import com.hyeon.blog.repository.ReplyRepository;

@RestController
public class ReplyControllerTest {

	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@GetMapping("/test/board/{id}")
	public Board getBoard(@PathVariable int id) {
		// jackson 라이브러리 (Object를 json으로 리턴) => 모델의 getter를 호출
		return boardRepository.findById(id).get();
	}
	
	@GetMapping("/test/reply")
	public List<Reply> getReply() {
		// jackson 라이브러리 (Object를 json으로 리턴) => 모델의 getter를 호출
		return replyRepository.findAll();
	}
}