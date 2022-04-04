package com.onlineBanking.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.onlineBanking.mailConfig.MailVerif;
import com.onlineBanking.model.CcpAccount;
import com.onlineBanking.model.LivretA;
import com.onlineBanking.model.User;
import com.onlineBanking.model.UserDetails;
import com.onlineBanking.service.CcpAccountService;
import com.onlineBanking.service.LivretAService;
import com.onlineBanking.service.UserServices;


@Controller
public class Register {
	@Autowired
	private UserServices userService;
	
	@Autowired
	private CcpAccountService ccpAccountService;
	@Autowired
	private LivretAService livretAService;
	
	@GetMapping("/register")
	public String register() {
		
		
		return "user/registration";
	}
	
	
//	@PostMapping("/beforeRegister")
//public String beforeRegister(@Validated UserDetails userDetails, BindingResult bindingResult, Model model,HttpSession session) {
//		System.out.println("before mail");
//		if(bindingResult.hasErrors()) {
//        	return ("user/registration");
//        }
//	//MailVerif.sendMail(userDetails.getEmail(), "1234");
//		System.out.println("after mail");
//		 userService.createUserDetails(userDetails);
//		 return "redirect:/register";
//		
////        return "redirect:/register/success"; 
//        
//	}
	@GetMapping("/final")
	public String registerFinal() {
		
		
       return ""; 
        
	}
	
//	@PostMapping("/final/{id}")
//	public String registerFinal(@Validated User user,@PathVariable(value="id") Long id, BindingResult bindingResult) {
//		Optional<UserDetails> userDetails=userService.findByUserDetailsId(id);
//		if(bindingResult.hasErrors()) {
//        	return ("user/registration");
//        }
//		user.setUserDetails(userDetails);
//        userService.createUser(user);
//       
//       
//        //return ("user/success");
//       return "redirect:/register"; 
//        
//	}
//	
	@PostMapping("/final/{id}")
	public String registerFinal( @Validated User user,BindingResult bindingResult,@PathVariable(value="id") Long id) {
		UserDetails userDet= userService.findByUserDetailsId(id).get();
		if(bindingResult.hasErrors()) {
        	return ("user/registration");
        }
		user.setUserDetails(userDet);
        userService.createUser(user);
       
       
        //return ("user/success");
       return "redirect:/register"; 
        
	}
	@PostMapping("/register")
	public String register(@Validated UserDetails userDetails, BindingResult bindingResult, Model model,HttpSession session) {
		
		
		if(bindingResult.hasErrors()) {
        	return ("user/registration");
        }
		//MailVerif.sendMail(userDetails.getEmail(), "1234");
		userDetails.setCcpAccount(ccpAccountService.findTopByOrderByIdDesc());
		userDetails.setLivretA(livretAService.findTopByOrderByIdDesc());
        userService.createUserDetails(userDetails);
        CcpAccount ccpAccount=new CcpAccount();
        ccpAccount.setBalance(0.0);
        ccpAccountService.save(ccpAccount);
        LivretA livretA=new LivretA();
        livretA.setBalance(0.0);
        livretAService.save(livretA);
        UserDetails userDet= userService.findByEmail(userDetails.getEmail());
       System.out.println(userDet.getId());
       
        //return ("user/success");
       return "redirect:/register?code=1234&userId="+userDet.getId(); 
        
	}
}
