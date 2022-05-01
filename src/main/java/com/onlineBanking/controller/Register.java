package com.onlineBanking.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

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
import com.onlineBanking.model.Role;
import com.onlineBanking.model.User;
import com.onlineBanking.model.UserDetails;
import com.onlineBanking.service.CcpAccountService;
import com.onlineBanking.service.CcpOperationService;
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
	@Autowired
	private CcpOperationService ccpOperationService;
	
	@GetMapping("/register")
	public String register() {
		return "user/registration";
	}
//	
//	@GetMapping("/final")
//	public String registerFinal() {
//		
//		
//       return ""; 
//        
//	}
	
	@PostMapping("/final/{id}")
	public String registerFinal( @Validated User user,BindingResult bindingResult,@PathVariable(value="id") Long id,Model model) {
		UserDetails userDet= userService.findByUserDetailsId(id).get();
		if(bindingResult.hasErrors()) {
        	return ("user/registration");
        }
		if(userService.findByUserName( user.getUserName()) != null) {
			model.addAttribute("userId",id);
			model.addAttribute("error","error");
			return "user/final";
		}
		user.setUserDetails(userDet);
		user.setRoles(Arrays.asList(new Role("USER")));
        userService.createUser(user);       
        //return ("user/success");
       return "redirect:/login"; 
        
	}
	@GetMapping("/tryAgainRegister/{id}")
	public String tryAgainRegister(@PathVariable(value="id") Long id) {
		userService.deleteById(id);
       return "redirect:/register"; 
        
	}
	
	@PostMapping("/register")
	public String register(@Validated UserDetails userDetails, BindingResult bindingResult, Model model,HttpSession session) {
		if(bindingResult.hasErrors()) {
        	return ("user/registration");
        }
		if(userService.findByEmail( userDetails.getEmail()) != null) {
			
			return ("redirect:/register?errorEmail");
		}
		 Random rnd = new Random();
		 int otpCode = rnd.nextInt(9999);
	        String otpCodeString= String.format("%04d", otpCode);
		MailVerif.sendMail(userDetails.getEmail(), "Votre OTP code: "+otpCodeString);
		System.out.println(otpCodeString);
	        model.addAttribute("code", otpCodeString);
		userDetails.setCcpAccount(ccpAccountService.findTopByOrderByIdDesc());
		userDetails.setLivretA(livretAService.findTopByOrderByIdDesc());
        userService.createUserDetails(userDetails);
        CcpAccount ccpAccount=ccpAccountService.findTopByOrderByIdDesc();
        Long ccpAccountNumber=ccpAccount.getId();
       
        int threeNumber = rnd.nextInt(999);
        String threedigit= String.format("%03d", threeNumber);
        int fiveNumber = rnd.nextInt(99999);
        String fivedigit= String.format("%05d", fiveNumber);
        int twoNumber = rnd.nextInt(99);
        String twodigit= String.format("%02d", twoNumber);
        String codeIban="FR"+threedigit+"1212"+fivedigit+ccpAccountNumber+twodigit;
        ccpAccount.setNumIban(codeIban);        
        ccpAccountService.save(ccpAccount);
        ccpAccount=new CcpAccount();
        ccpAccount.setBalance(0.0);        
        ccpAccountService.save(ccpAccount);

        LivretA livretA=livretAService.findTopByOrderByIdDesc();
       
         threeNumber = rnd.nextInt(999);
         threedigit= String.format("%03d", threeNumber);
         fiveNumber = rnd.nextInt(99999);
         fivedigit= String.format("%05d", fiveNumber);
         twoNumber = rnd.nextInt(99);
         twodigit= String.format("%02d", twoNumber);
         codeIban="FR"+threedigit+"1212"+fivedigit+livretA.getId()+twodigit;
        livretA.setNumIban(codeIban);        
        livretAService.save(livretA);
        livretA=new LivretA();
        livretA.setBalance(0.0);
        livretAService.save(livretA);
        
        UserDetails userDet= userService.findByEmail(userDetails.getEmail()); 
        model.addAttribute("userId", userDet.getId());
      // return "redirect:/register?code=1234&userId="+userDet.getId(); 
        //return "user/registration";
        return "user/otpVerif";
	}
	@PostMapping("/otpVerif/{id}")
	public String otpVerif(@PathVariable(value="id") Long id,Model model,@ModelAttribute("otpBySys") String otpBySys,@ModelAttribute("otpByUser") String otpByUser) {
		model.addAttribute("userId",id);
		if(!otpBySys.equals(otpByUser)) {
			model.addAttribute("error","error");
			return "user/otpVerif";
		}
		//return "redirect:/final{id}(id="+id+")";
		return "user/final";
	}
//	@GetMapping("/final/{userId}")
//	public String registerFinal(Model model,@PathVariable(value="userId") Long id) {
//		model.addAttribute("userId",id);
//       return "user/final";
//        
//	}
	
	@GetMapping("/updateProfile")
	public String updateProfile(Model model,Principal principal){	
		User user=userService.findByUserName(principal.getName());
		model.addAttribute("user",user.getUserDetails());
		return "user/monProfile";
		
	}
	
	@PostMapping("/updateProfile")
	public String Update(Model model,@ModelAttribute("user") UserDetails userDetails,
			 BindingResult bindingResult,Principal principal) {
		User user=userService.findByUserName(principal.getName());
		UserDetails userdetails1=user.getUserDetails();
		userdetails1.setFirstName(userDetails.getFirstName()) ;
		userdetails1.setLastName(userDetails.getLastName()) ;
		userdetails1.setMobile(userDetails.getMobile()) ;
		userdetails1.setAddress(userDetails.getAddress()) ;
		userdetails1.setVille(userDetails.getVille()) ;
		userdetails1.setCodePostal(userDetails.getCodePostal()) ;
		userService.createUserDetails(userdetails1);
		 return "redirect:/home";
	}
	@GetMapping("/pdf")
	public String pdf(Principal principal,Model model){			
		User user = userService.findByUserName(principal.getName());
		List<CcpOperation> ccpOperations =ccpOperationService.findByCcpAccountOrderByCreationDateTimeDesc(user.getUserDetails().getCcpAccount());
		String lastMonth[] = ccpOperations.get(0).getCreationDateTime().toString().split("-");
		String firstMonth[] = ccpOperations.get(ccpOperations.size()-1).getCreationDateTime().toString().split("-");
//		int lastMonthInt=Integer.parseInt(lastMonth[1]);
//		model.addAttribute("lastMonth", lastMonthInt-1 );
		model.addAttribute("lastMonth", lastMonth[1] );
		model.addAttribute("firstMonth", firstMonth[1]);
		return "user/pdf";
		
	}
	@GetMapping("/rib")
	public String rib(Principal principal,Model model){	
		User user = userService.findByUserName(principal.getName());
		UserDetails userDetails=user.getUserDetails();
		
		//model.addAttribute("ccpIBAN",userDetails.getCcpAccount().getNumIban());
		//model.addAttribute("livretAIBAN",userDetails.getLivretA().getNumIban());
		model.addAttribute("userDetails",userDetails);
		return "user/rib";		
	}
}
