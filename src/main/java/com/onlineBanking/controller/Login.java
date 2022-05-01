package com.onlineBanking.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.onlineBanking.mailConfig.MailVerif;
import com.onlineBanking.model.User;
import com.onlineBanking.model.UserDetails;
import com.onlineBanking.service.UserServices;

@Controller

public class Login {
@Autowired
private UserServices userServices;

	@GetMapping("/login")
	public String login() {
		return "user/login";
	}
	@GetMapping("/forgetPassword")
	public String forgetPassword() {
		return "user/forgetPassword";
	}
	@PostMapping("/forgetPassword")
	public String forgetPassword(@ModelAttribute("username") String username,Model model) {
		System.out.println(username);
		User user=userServices.findByUserName(username);
		String email=user.getUserDetails().getEmail();
		 Random rnd = new Random();
		 int otpCode = rnd.nextInt(9999);
	        String otpCodeString= String.format("%04d", otpCode);
		MailVerif.sendMail(email, "Votre OTP code pour changer mot de passe: "+otpCodeString);
		System.out.println(otpCodeString);
		model.addAttribute("otpSys",otpCodeString);
		model.addAttribute("email",email);
		model.addAttribute("username",username);
		return "user/forgetPassword";
	}
	@GetMapping("/changePassword")
	public String changePassword(@ModelAttribute("username") String username,Model model) {
		model.addAttribute("username",username);
		return "user/changePassword";
	}
	@PostMapping("/changePassword")
	public String changePassword(@ModelAttribute("username") String username,@ModelAttribute("password") String password,Model model) {
		User user=userServices.findByUserName(username);
		user.setPassword(password);
		userServices.changePassword(user);
		return "redirect:/login";
	}
}
