package com.appsdeveloperblog.clients.sociallogin.SocialLoginWebClient.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexPageController {

	@GetMapping("/")
	public String displayIndexPage(Model model) {
		
		return "index";
		
	}
	
}
