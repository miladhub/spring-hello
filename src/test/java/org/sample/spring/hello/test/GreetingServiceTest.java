package org.sample.spring.hello.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.sample.spring.hello.EnvService;
import org.sample.spring.hello.GreetingServiceImpl;

public class GreetingServiceTest {
	@Test
	public void testName() {
		assertEquals("name", "greeting impl",
				new GreetingServiceImpl().getName());
	}
	
	@Test
	public void testMessage() {
		EnvMock env = new EnvMock();
		GreetingServiceImpl gs = new GreetingServiceImpl();
		gs.setEnv(env);
		String greeting = "Greetings, outlander!";
		gs.setGreeting(greeting);
		gs.sayGreeting();
		assertEquals("message", greeting, env.getLastMsg());		
	}
	
	public class EnvMock implements EnvService {
		private String lastMsg = "";
		public void println(String msg) {
			lastMsg = msg;
		}			
		public String getLastMsg() {
			return lastMsg;
		}
	}
}
