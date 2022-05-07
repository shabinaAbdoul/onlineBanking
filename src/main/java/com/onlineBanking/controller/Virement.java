package com.onlineBanking.controller;

import java.security.Principal;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.onlineBanking.mailConfig.MailVerif;
import com.onlineBanking.model.CcpAccount;
import com.onlineBanking.model.CcpOperation;
import com.onlineBanking.model.LivretA;
import com.onlineBanking.model.LivretAOperation;
import com.onlineBanking.model.Receiver;
import com.onlineBanking.model.User;
import com.onlineBanking.model.UserDetails;
import com.onlineBanking.service.CcpAccountService;
import com.onlineBanking.service.LivretAService;
import com.onlineBanking.service.UserServices;
import com.onlineBanking.service.VirementService;

@Controller
public class Virement {
	@Autowired
	private VirementService virementService;
	@Autowired
	private UserServices userService;
	@Autowired
	private CcpAccountService ccpAccountService;
	@Autowired
	private LivretAService livretAService;
	
	
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
		 User user = userService.findByUserName(principal.getName());
		 CcpAccount ccpAccount = user.getUserDetails().getCcpAccount();
		 LivretA livretA  = user.getUserDetails().getLivretA();
		    if (compteD.equalsIgnoreCase("Ccp")) {
	         if(ccpAccount.getBalance()< Double.parseDouble(amount)) {
	        	 return "redirect:/aMonCompte?error";
	         }
		    }
		    else if (compteD.equalsIgnoreCase("LivretA")) {
		         if(livretA.getBalance()< Double.parseDouble(amount)) {
		        	 return "redirect:/aMonCompte?error";
		         }
			    }
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
		 Boolean ccpReceiver=ccpAccountService.findByNumIban(receiver.getNumIban()).isEmpty();             
		 Boolean livretAReceiver=livretAService.findByNumIban(receiver.getNumIban()).isEmpty();
		if(ccpReceiver && livretAReceiver) {
			return ("redirect:/receiver?notExist");
		}
		User user = userService.findByUserName(principal.getName());
        UserDetails userDetails = user.getUserDetails();
        receiver.setUserDetails(userDetails);
		virementService.save(receiver); 
		return ("redirect:/receiver");
	}
	@GetMapping("/updateReceiver/{id}")
	public String updateReceiver(@PathVariable(value="id") Long id, Model model) {
		 model.addAttribute("receiver",virementService.findById(id).get()); 
		return "banking/updateReceiver";
	}
	@PostMapping("/updateReceiver/{id}")
	public String updateReceiver(@PathVariable(value="id") Long id, Model model,@ModelAttribute("receiver") Receiver receiver,
			 BindingResult bindingResult) {
		 Receiver receiver1 = virementService.findById(id).orElseThrow();
		 receiver1.setNom(receiver.getNom()) ;
		 receiver1.setEmail(receiver.getEmail()) ;
		 receiver1.setMobile(receiver.getMobile()) ;
		 receiver1.setNumIban(receiver.getNumIban()) ;			
		 virementService.save(receiver1);
		 return "redirect:/receiver";
	}
	
	
	//Delete
	@GetMapping("/deleteReceiver/{id}")
	public String deleteReceiver(@PathVariable(value="id") Long id) {
		virementService.deleteById(id);		
		return "redirect:/receiver";
	}
	
	@PostMapping("/aAutreCompte")
	public String virement(Model model,@ModelAttribute("amount") String amount, @ModelAttribute("compteD") String compteD, @ModelAttribute("compteC") String compteC, Principal principal) {
		 User user = userService.findByUserName(principal.getName());
		 CcpAccount ccpAccount = user.getUserDetails().getCcpAccount();
		 LivretA livretA  = user.getUserDetails().getLivretA();
		    if (compteD.equalsIgnoreCase("Ccp")) {
	         if(ccpAccount.getBalance()< Double.parseDouble(amount)) {
	        	 return "redirect:/aAutreCompte?error";
	         }
		    }
		    else if (compteD.equalsIgnoreCase("LivretA")) {
		         if(livretA.getBalance()< Double.parseDouble(amount)) {
		        	 return "redirect:/aAutreCompte?error";
		         }
			    }
		//virementService.aAutreCompte(compteD,Long.parseLong(compteC), Double.parseDouble(amount), principal); 
		//return "redirect:/home";
		    Random rnd = new Random();
			 int otpCode = rnd.nextInt(9999);
		        String otpCodeString= String.format("%04d", otpCode);
			//MailVerif.sendMail(user.getUserDetails().getEmail(), "Votre OTP code pour efféctuer le virement: "+otpCodeString);
			System.out.println(otpCodeString);
		        model.addAttribute("codeByAdmin", otpCodeString);
		        model.addAttribute("compteD", compteD);
		        model.addAttribute("compteC", compteC);
		        model.addAttribute("amount", amount);
		    return "banking/aAutreCompte";
	}
	
	@PostMapping("/codeBon")
	public String codeBonVirement(Model model,@ModelAttribute("amount") String amount, @ModelAttribute("compteD") String compteD, @ModelAttribute("compteC") String compteC, Principal principal) {
		virementService.aAutreCompte(compteD,Long.parseLong(compteC), Double.parseDouble(amount), principal); 
		return "redirect:/home";	
		
	}
	

}
