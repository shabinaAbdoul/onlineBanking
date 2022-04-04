package com.onlineBanking.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.onlineBanking.model.Receiver;
import com.onlineBanking.model.User;
import com.onlineBanking.model.UserDetails;
import com.onlineBanking.service.UserServices;
import com.onlineBanking.service.VirementService;

@Controller
public class Virement {
	@Autowired
	private VirementService virementService;
	@Autowired
	private UserServices userService;
	
	@GetMapping("/aMonCompte")
	public String retrait(){
	return "banking/aMonCompte";
	}
	@GetMapping("/aAutreCompte")
	public String versement(Model model,Principal principal){
		User user = userService.findByUserName(principal.getName());
		List<Receiver> receivers = virementService.findByUserDetails(user.getUserDetails());			
       
        model.addAttribute("receivers", receivers);
	return "banking/aAutreCompte";
	}
	@PostMapping("/aMonCompte")
	public String versement(@ModelAttribute("amount") String amount, @ModelAttribute("compteD") String compteD, @ModelAttribute("compteC") String compteC, Principal principal) {
		virementService.entreMonCompte(compteD,compteC, Double.parseDouble(amount), principal); 
		return "redirect:/home";
	}
	@GetMapping("/receiver")
	public String receiver(Model model,Principal principal){
		User user = userService.findByUserName(principal.getName());
		List<Receiver> receivers = virementService.findByUserDetails(user.getUserDetails());			
       
        model.addAttribute("receivers", receivers);
	return "banking/receiver";
	}
	@PostMapping("/receiver")
	public String addReceiver(@Validated Receiver receiver, BindingResult bindingResult, Model model,Principal principal) {
				
		if(bindingResult.hasErrors()) {
        	return ("banking/receiver");
        }

		User user = userService.findByUserName(principal.getName());
        UserDetails userDetails = user.getUserDetails();
        receiver.setUserDetails(userDetails);
		virementService.save(receiver); 
		
		return ("banking/receiver");
	}
	@PostMapping("/aAutreCompte")
	public String virement(@ModelAttribute("amount") String amount, @ModelAttribute("compteD") String compteD, @ModelAttribute("compteC") String compteC, Principal principal) {
		virementService.aAutreCompte(compteD,Long.parseLong(compteC), Double.parseDouble(amount), principal); 
		return "redirect:/home";
	}
//	@PostMapping("/aAutreCompte")
//	public String retrait(@ModelAttribute("amount") String amount, @ModelAttribute("account") String account, Principal principal) {
//		virementService.retrait(account, Double.parseDouble(amount), principal); 
//		return "redirect:/home";
//	}s
	

}
