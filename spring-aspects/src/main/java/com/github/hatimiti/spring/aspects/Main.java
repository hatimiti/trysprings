package com.github.hatimiti.spring.aspects;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import org.springframework.context.annotation.EnableAspectJAutoProxy;
////SpringBootを利用しない場合は Configuration に @EnableAspectJAutoProxy の付加が必要
//@EnableAspectJAutoProxy
@SpringBootApplication
public class Main {

	public static void main(final String[] args) {
		SpringApplication.run(Main.class, args);
	}

}
