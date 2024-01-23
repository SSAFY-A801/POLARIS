package com.ssafy.polaris.domain.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/")
	public String main(){
		System.out.println("Hello world");
		return "hello";
	}
}
