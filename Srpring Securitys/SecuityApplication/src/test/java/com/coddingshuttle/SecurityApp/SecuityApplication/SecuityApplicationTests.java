package com.coddingshuttle.SecurityApp.SecuityApplication;

import com.coddingshuttle.SecurityApp.SecuityApplication.entities.User;
import com.coddingshuttle.SecurityApp.SecuityApplication.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

class SecuityApplicationTests {

	@Autowired
	JwtService jwtService;

	@Test
	void contextLoads() {
//		User user=new User(4L, "anuj@gmail.com", "1234");
		//create a token
//		String token=jwtService.generateToken(user);
//		System.out.println(token);

		//get the UserId from the Token
//		Long userId=jwtService.getUserIdFromToken(token);
//		System.out.println(userId);
	}

}
