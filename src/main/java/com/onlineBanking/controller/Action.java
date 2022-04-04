package com.onlineBanking.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.onlineBanking.service.ActionService;

@Controller
public class Action {
	@Autowired
	private ActionService actionService;
	
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
		actionService.retrait(account, Double.parseDouble(amount), principal); 
		return "redirect:/home";
	}
	

}
