package com.hyeon.blog.Controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hyeon.blog.dto.ResponseDto;
import com.hyeon.blog.model.User;
import com.hyeon.blog.service.UserService;

@RestController 
public class UserApiController {
	
	@Autowired
	private UserService userService;

//	@PostMapping("/api/user")
	@PostMapping("/auth/joinProc") // 시큐리티
	public ResponseDto<Integer> save(@RequestBody User user) { // username, password, email
		System.out.println("UserApiController : save 호출됨");
		// 실제로 DB에 insert를 하고 아래에서 return이 되면 된다.
		userService.save(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 자바오브젝트를 JSON으로 변환해서 리턴 (JackSon)
	}
	
//	전통적인 로그인 방법
//	@Autowired
//	private HttpSession session;
//	@PostMapping("/api/user/login")
//	public ResponseDto<Integer> login(@RequestBody User user) {
//		System.out.println("UserApiController : login 호출됨");
//		User principal = userService.login(user); // principal (접근주체)
//		if(principal != null) {
//			session.setAttribute("principal", principal);
//		}
//		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
//	}
}