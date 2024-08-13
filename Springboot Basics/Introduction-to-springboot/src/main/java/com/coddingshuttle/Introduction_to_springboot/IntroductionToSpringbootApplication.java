package com.coddingshuttle.Introduction_to_springboot;

import jakarta.annotation.security.RunAs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class IntroductionToSpringbootApplication implements CommandLineRunner {

//	@Autowired
//	Apple obj;  //Injecting the Bean from IoC Container (Dependency Injection)
//
//	@Autowired
//	Apple obj2;

	@Autowired
	DBService dbService;

	public static void main(String[] args)  {
		SpringApplication.run(IntroductionToSpringbootApplication.class, args);

//		Apple obj=new Apple(); //creating object of apple class to call the method
//		obj.eatApple();
	}

	@Override //overriding run method
	public void run(String... args) throws Exception {
//		obj.eatApple();
//		obj2.eatApple();
//
//		System.out.println(obj.hashCode());
//		System.out.println(obj2.hashCode());

		System.out.println(dbService.getData());
	}


}
