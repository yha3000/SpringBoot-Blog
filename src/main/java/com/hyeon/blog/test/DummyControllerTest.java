package com.hyeon.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hyeon.blog.model.RoleType;
import com.hyeon.blog.model.User;
import com.hyeon.blog.repository.UserRepository;

// html파일이 아니라 data를 리턴해주는 controller = RestController
@RestController
public class DummyControllerTest {
	
	@DeleteMapping("/dummy/user/{id}")
	public String deleteUser(@PathVariable int id) {
		try {
			userRepository.deleteById(id);	
		} catch (EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		}		
		return "삭제되었습니다. id : " + id;
	}
	
	@Transactional 	// 더티 체킹 // 함수 종료시에 자동 commit
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) { 
		// json 데이터를 받으려면 @RequestBody 사용
		// json 데이터를 요청 => Java Object(MessageConverter의 Jackson라이브러리)로 변환해서 받아줌
		System.out.println("id : " + id);
		System.out.println("password : " + requestUser.getPassword());
		System.out.println("email : " + requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		// userRepository.save(user);
		return user;
	}
	
	@Autowired // 의존성 주입 (DI)
	private UserRepository userRepository;
	
	// http://localhost:8000/blog/dummy/users
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	// 한페이지당 2건에 데이터를 리턴받아 볼 예정
	// 기본이 0 다음페이지는 page=1 파라미터를 붙여준다
	// http://localhost:8000/blog/dummy/user?page=1
	// http://localhost:8000/blog/dummy/user
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
		// pagingUser.isFirst() 첫페이지, pagingUser.isLast() 끝페이지
		Page<User> pagingUser = userRepository.findAll(pageable);
		List<User> user =  pagingUser.getContent();
		return user;
	}
	
	// {id} 주소로 파라미터를 전달 받을 수 있음
	// http://localhost:8000/blog/dummy/user/1
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		// user에 4번을 찾으면 내가 데이터베이스에서 못 찾아 오게 될 때 user가 null이 되므로 Optional을 사용한다.
		// return 이 null이 되면 문제가 있기 때문에 Optional을 사용한다.
		
		// 1번 방법
		//	Optional<User> user = userRepository.findById(id);
		//	if(user.isPresent()) return user.get();
		//	else return null;
		
		// 2번 방법
		// User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
		// 	@Override
		// 	public User get() {
		// 		return new User();
		// 	}
		// });
		// return user;
		
		// 3번 방법
		 User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
		 	@Override
		 	public IllegalArgumentException get() {
		 		return new IllegalArgumentException("해당 사용자가 없습니다.");
		 	}
		 });
		 // 요청 : 웹브라우저
		 // user 객체 = 자바 오브젝트
		 // 변환 ( 웹브라우저가 이해할 수 있는 데이터) -> json (Gson 라이브러리)
		 // 스프링부트 = MessageConverter라는 애가 응답시에 자동 작동
		 // 만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
		 // user 오브젝트를 json 으로 변환해서  브라우저에게 던져줍니다
		 return user;
		 
		// 람다식
		// User user = userRepository.findById(id).orElseThrow(()->{
		// 	return new IllegalArgumentException("해당 사용자는 없습니다.");
		// });
	}
	
	// http://localhost:8000/blog/dummy/join (요청)
	// http의 body에 username, password, email 데이터를 가지고 요청
	@PostMapping("/dummy/join")
	public String join(User user) {
		//	public String join(String username, String password, String email) {
		// @RequestParam(" ") 어노테이션은 param과 변수명이 같으면 생략 가능, key = value (규칙)
		System.out.println("id : " + user.getId());
		System.out.println("username : " + user.getUsername());
		System.out.println("password : " + user.getPassword());
		System.out.println("email : " + user.getEmail());
		System.out.println("role : " + user.getRole());
		System.out.println("createDate : " + user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		// save함수는 id를 전달하지 않으면 insert를 해주고
		// save함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
		// save함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 해준다
		return "회원가입이 완료되었습니다.";
	}
}