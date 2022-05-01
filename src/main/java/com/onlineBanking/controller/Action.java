package com.onlineBanking.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.onlineBanking.model.CcpAccount;
import com.onlineBanking.model.LivretA;
import com.onlineBanking.model.User;
import com.onlineBanking.service.ActionService;
import com.onlineBanking.service.UserServices;

@Controller
public class Action {
	@Autowired
	private ActionService actionService;
	@Autowired
	private UserServices userService;
	
	@GetMapping("/retrait")
	public String retrait(){
	return "banking/retrait";
	}
	@GetMapping("/versement")
	public String versement(){
	return "banking/versement";
	}
	@PostMapping("/versement")
	public String versement(@ModelAttribute("amount") String amount, @ModelAttribute("account") String account, Principal principal) {
		actionService.versement(account, Double.parseDouble(amount), principal); 
		return "redirect:/home";
	}
	@PostMapping("/retrait")
	public String retrait(@ModelAttribute("amount") String amount, @ModelAttribute("account") String account, Principal principal) {
		 User user = userService.findByUserName(principal.getName());
		 CcpAccount ccpAccount = user.getUserDetails().getCcpAccount();
		 LivretA livretA  = user.getUserDetails().getLivretA();
		if (account.equalsIgnoreCase("Ccp")) {
	         if(ccpAccount.getBalance()< Double.parseDouble(amount)) {
	        	 return "redirect:/retrait?error";
	         }
		    }
		    else if (account.equalsIgnoreCase("LivretA")) {
		         if(livretA.getBalance()< Double.parseDouble(amount)) {
		        	 return "redirect:/retrait?error";
		         }
			    }
		actionService.retrait(account, Double.parseDouble(amount), principal); 
		return "redirect:/home";
	}
	

}
