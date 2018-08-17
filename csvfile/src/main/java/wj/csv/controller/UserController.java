package wj.csv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

	@RequestMapping("/login")
	public String login(String username, String password) {
		return "none";
	}
	
	@RequestMapping("/register")
	public String register() {
		return "none";
	}
		
	
}
