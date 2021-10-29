package com.cos.blog.service;


import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;


//스프링 컴포넌트 스캔을 통해서 Bean에 등록을 해줌 IoC
@Service
@Slf4j
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Transactional
	public void deleteReply(int replyId ) {
		replyRepository.deleteById(replyId);
	}
		
		
	@Transactional
	public void saveReply(User reqUser, int boardId, Reply reqReply ) {
		
		User user = userRepository.findById(reqUser.getId())
				.orElseThrow(()->{
					return new IllegalArgumentException("댓글 작성 실패 : 유저 아이디를 찾을 수 없습니다.");
		});
		
		Board board = boardRepository.findById(boardId)
				.orElseThrow(()->{
					return new IllegalArgumentException("댓글 작성 실패 : 게시글 아이디를 찾을 수 없습니다.");
		});


		log.info("resert test");

		log.info("commit 2");

		log.info("yamma");
		
		reqReply.setUser(user);
		reqReply.setBoard(board);		
		replyRepository.save(reqReply);
	}
	
	
	@Transactional
	public void deleteById(int id) {
		boardRepository.deleteById(id);
	}
	
	
	@Transactional(readOnly = true)
	public Board getBoardDetail(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
		});
	}
	
	@Transactional(readOnly = true)
	public Page<Board> getBoardAll(Pageable pageable){
		return boardRepository.findAll(pageable); 
	}
	
	@Transactional
	public void write(Board board, User user) {
		board.setUser(user);
		board.setCount(0);
		boardRepository.save(board);			
	}

	@Transactional
	public void update(int id, Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패 : "+ id+"아이디를 찾을 수 없습니다.");		
				});// 영속화 완료
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		//해당 함수 종료시(서비스가 종료시) 트랜잭션이 종료됩니다.
		//이때 더티체킹이 되면서 자동 업데이트(db로 flush)가 됩니다.
		
		
	}
	
}
