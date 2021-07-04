package com.hyeon.blog;

import static org.hamcrest.CoreMatchers.nullValue;

import org.junit.Test;

import com.hyeon.blog.model.Reply;

public class ReplyObjectTest {

	@Test
	public void toStringTest() {
		Reply reply = Reply.builder()
									.id(1)
									.user(null)
									.board(null)
									.content("안녕")
									.build();
		
		System.out.println(reply); // Object를 출력하게 되면 자동으로 toString()이 호출됨.
	}
}
