package com.onlineBanking.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.onlineBanking.model.CcpAccount;
import com.onlineBanking.model.CcpOperation;
import com.onlineBanking.model.LivretA;
import com.onlineBanking.model.LivretAOperation;
import com.onlineBanking.model.User;
import com.onlineBanking.model.UserDetails;
import com.onlineBanking.service.CcpOperationService;
import com.onlineBanking.service.LivretAOperationService;
import com.onlineBanking.service.UserServices;

@Controller
public class Operations {
	@Autowired
	private UserServices userService;
	@Autowired
	private CcpOperationService ccpOperationService;
	@Autowired
	private LivretAOperationService livretAOperationService;
	

	@GetMapping("/cca")
	public String cca(Model model, Principal principal) {
		User user = userService.findByUserName(principal.getName());
		List<CcpOperation> ccpOperations = ccpOperationService.findByCcpAccount(user.getUserDetails().getCcpAccount());
				
        CcpAccount ccpAccount = user.getUserDetails().getCcpAccount();

        model.addAttribute("ccpAccount", ccpAccount);
        model.addAttribute("ccpOperations", ccpOperations);
		
        return "banking/cca";
	}

	@GetMapping("/livretA")
	public String livretA(Model model, Principal principal) {
		User user = userService.findByUserName(principal.getName());
		List<LivretAOperation> livretAOperations = livretAOperationService.findByLivretA(user.getUserDetails().getLivretA());
				
        LivretA livretA = user.getUserDetails().getLivretA();

        model.addAttribute("livretA", livretA);
        model.addAttribute("livretAOperations", livretAOperations);
		
        return "banking/livretA";
	}
	
	
}
