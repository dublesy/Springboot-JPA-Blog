package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Board;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.service.BoardService;

@Controller
public class BoardController {

	
	@Autowired
	private BoardService boardService;
	
	//@AuthenticationPrincipal PrincipalDetail principal //컨트롤러에서 세션을 어떻게 찾는가?
	//System.out.println("로그인 사용자 아이디 : "+principal.getUsername());
	@GetMapping({"/", ""})
	public String index(Model model, @PageableDefault(size = 3, sort="id", direction = Sort.Direction.DESC )Pageable pageable) {
		Page<Board> pagingBoard = boardService.getBoardAll(pageable);
		model.addAttribute("boards", pagingBoard);
		return "index";
	}
	
	// USER  권한이 필요함, 글쓰기
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		
		model.addAttribute("board", boardService.getBoardDetail(id));
		return "board/updateForm";
	}
	
	@GetMapping("/board/{id}")
		public String getBoardDetail(@PathVariable int id,  Model model) {
			model.addAttribute("board", boardService.getBoardDetail(id));	
			return "board/detail";
		}
	
	
}
