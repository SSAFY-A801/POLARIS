package com.ssafy.polaris.main;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

	@GetMapping("/")
	public String main() {
		System.out.println("Hello world");
		return "hello";
	}
}
