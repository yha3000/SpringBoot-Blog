package com.hyeon.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// 사용자가 요청 -> 응답(HTML 파일) -> @Controller

// 사용자가 요청 -> 응답(Data)
@RestController
public class HttpControllerTest {

	private static final String TAG = "HttpControllerTest : ";
	
	@GetMapping("/http/lombok")
	public String lombokTest() {
		// Member m = new Member(1,"hyeonah", "1234", "yha3000@naver.com");
		// Builder를 사용하면 자동으로 0으로 들어감.
		Member m = Member.builder().username("hyeonah").password("1234").email("yha3000@naver.com").build();
		System.out.println(TAG + "getter : " + m.getId());
		m.setId(5000);
		System.out.println(TAG + "setter : " + m.getId());
		return "lombok test 완료";
	}
	
	// http://localhost:8080/http/get
	@GetMapping("/http/get")
	//public String getTest(@RequestParam int id, @RequestParam String username) { 
	public String getTest(Member m) { 
		return "get 요청 : " + m.getId() + " , " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
	}
	
	// http://localhost:8080/http/post
	@PostMapping("/http/post")
	//	public String postTest(Member m) { // Body -> x-www-form-urlencoded 
	//	public String postTest(@RequestBody String text) { // Body -> raw -> text/plain
	public String postTest(@RequestBody Member m) { // Body -> raw -> application/json
																											//  MessageConverter (스프링부트)
		return "post 요청 : " + m.getId() + " , " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
	}
	
	// http://localhost:8080/http/put
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청 : " + m.getId() + " , " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
	}
	
	// http://localhost:8080/http/delete
	@DeleteMapping("/http/delete")
	public String deleteTest(@RequestBody Member m) {
		return "delete 요청";
	}
}