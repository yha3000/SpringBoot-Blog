package com.hyeon.blog.test;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// @Getter
// @Setter
// @RequiredArgsConstructor // final이 붙은 애들의 constructor가 자동으로 생성됨

@Data
// @AllArgsConstructor // 전체 생성자
@NoArgsConstructor // 빈 생성자
public class Member {
	private int id;
	private String username;
	private String password;
	private String email;
	
	@Builder
	// 생성자에 순서를 지키지 않아도 됨.
	// Member m = Member.builder().username("hyeonah").password("1234").email("yha3000@naver.com").build();
	public Member(int id, String username, String password, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
}