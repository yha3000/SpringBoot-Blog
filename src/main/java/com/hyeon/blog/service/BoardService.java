package com.hyeon.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyeon.blog.model.Board;
import com.hyeon.blog.model.User;
import com.hyeon.blog.repository.BoardRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	@Transactional
	public void save(Board board, User user) { // title, content, username
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	@Transactional(readOnly = true)
	public Page<Board> list(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Board detail(int id) {
		return boardRepository.findById(id)
			.orElseThrow(()->{
				return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
			});
	}
	
	@Transactional
	public void delete(int id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void update(int id, Board requestBoard) {
		Board board = boardRepository.findById(id)
			.orElseThrow(()->{
				return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
			}); // 영속화 완료
		
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		// 해당 함수로 종료시 (Service가 종료될 때) 트랜젝션이 종료됩니다
		// 이때 더티체킹 - 자동 업데이트가 됨. DB flush
	}
}
