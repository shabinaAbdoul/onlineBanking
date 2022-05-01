package com.onlineBanking.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.onlineBanking.model.CcpAccount;
import com.onlineBanking.model.LivretA;
import com.onlineBanking.model.User;
import com.onlineBanking.model.UserDetails;
import com.onlineBanking.service.UserServices;

@Controller
public class Home {
	@Autowired
	private UserServices userService;
	
	
	@GetMapping("/home")
	public String home(Principal principal, Model model) {
		
		User user = userService.findByUserName(principal.getName());
        UserDetails userDetails = user.getUserDetails();
        CcpAccount ccpAccount = userDetails.getCcpAccount();
        LivretA livretA = userDetails.getLivretA();

       model.addAttribute("ccpAccount", ccpAccount);
        model.addAttribute("livretA", livretA);
		return "banking/home";
	}
	
}
