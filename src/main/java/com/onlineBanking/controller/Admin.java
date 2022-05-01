package com.onlineBanking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.onlineBanking.model.CcpAccount;
import com.onlineBanking.model.CcpOperation;
import com.onlineBanking.model.LivretA;
import com.onlineBanking.model.LivretAOperation;
import com.onlineBanking.model.UserDetails;
import com.onlineBanking.service.AdminService;
import com.onlineBanking.service.CcpAccountService;
import com.onlineBanking.service.CcpOperationService;
import com.onlineBanking.service.LivretAOperationService;
import com.onlineBanking.service.LivretAService;
import com.onlineBanking.service.UserServices;

@Controller
public class Admin {
@Autowired
private AdminService adminService;
@Autowired
private UserServices userService;
@Autowired
private CcpAccountService ccpAccountService;
@Autowired
private LivretAService livretAService;
@Autowired
private CcpOperationService ccpOperationService;
@Autowired
private LivretAOperationService livretAOperationService;


	@GetMapping("/admin")
	public String admin(Model model){
		List<UserDetails> users=adminService.findAllUsers();
		List<CcpOperation> ccpOperations= ccpOperationService.findByStatus("Encours...");
		 model.addAttribute("totalCcpOperations",ccpOperations.size());
		 List<LivretAOperation> livretAOperations= livretAOperationService.findByStatus("Encours...");
   		 model.addAttribute("totalLivretAOperations",livretAOperations.size());
		
		model.addAttribute("totalUsers", users.size());
	return "admin/admin";
	}
	@GetMapping("/users")
	public String users(Model model){
		List<UserDetails> users=adminService.findAllUsers();
		model.addAttribute("users", users);
	return "admin/users";
	}
	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable(value="id") Long id) {
		userService.deleteById(id);		
		return "redirect:/receiver";
	}
	@GetMapping("/ccaListe/{id}")
	public String ccaListe(@PathVariable(value="id") Long id, Model model) {
		UserDetails userDetails=userService.findByUserDetailsId(id).get();		
		List<CcpOperation> ccpOperations = ccpOperationService.findByCcpAccount(userDetails.getCcpAccount());
		 CcpAccount ccpAccount = userDetails.getCcpAccount();
		 model.addAttribute("user", userDetails);
		model.addAttribute("ccpAccount", ccpAccount);
        model.addAttribute("ccpOperations", ccpOperations);
        return "admin/ccaListe";
	}
	@GetMapping("/livretAListe/{id}")
	public String livretAListe(@PathVariable(value="id") Long id, Model model) {
		UserDetails userDetails=userService.findByUserDetailsId(id).get();
		List<LivretAOperation> livretAOperations = livretAOperationService.findByLivretA(userDetails.getLivretA());
		LivretA livretA = userDetails.getLivretA();
		model.addAttribute("user", userDetails);
		 model.addAttribute("livretA", livretA);
         model.addAttribute("livretAOperations", livretAOperations);		
        return "admin/livretAListe";
	}
	
	
    @GetMapping("/virementAttendsCcp")
	public String virementAttends(Model model) {
    	List<CcpOperation> ccpOperations= ccpOperationService.findByStatus("Encours...");
		 model.addAttribute("ccpOperations",ccpOperations);
		return "admin/virementAttendsCcp";
	}
    @GetMapping("/virementAttendsLivretA")
   	public String virementAttendsLivretA(Model model) {
        List<LivretAOperation> livretAOperations= livretAOperationService.findByStatus("Encours...");
   		 model.addAttribute("livretAOperations",livretAOperations);
   		return "admin/virementAttendsLivretA";
   	}
    
    @GetMapping("/acceptOpCcp/{id}")
   	public String acceptOpCcp(@PathVariable(value="id") Long id,Model model) {
    	CcpOperation ccpOperation= ccpOperationService.findById(id).get();
    	if(ccpOperation.getType().equalsIgnoreCase("crédit")) {
    		ccpOperation.getCcpAccount().setBalance(ccpOperation.getAvailableBalance()+ccpOperation.getAmount());
    	}
    	else {
    		ccpOperation.getCcpAccount().setBalance(ccpOperation.getAvailableBalance()-ccpOperation.getAmount());
        }
    	ccpOperation.setStatus("Terminé");
    	ccpOperationService.save(ccpOperation);
    	return "redirect:/virementAttendsCcp";
    }
    @GetMapping("/refuseOpCcp/{id}")
   	public String refuseOpCcp(@PathVariable(value="id") Long id,Model model) {
    	CcpOperation ccpOperation= ccpOperationService.findById(id).get();
    	ccpOperation.setStatus("Annulé");
    	ccpOperationService.save(ccpOperation);
    	return "redirect:/virementAttendsCcp";
    }
    @GetMapping("/acceptOpLivretA/{id}")
   	public String acceptOpLivretA(@PathVariable(value="id") Long id,Model model) {
    	LivretAOperation livretAOperation= livretAOperationService.findById(id).get();
    	if(livretAOperation.getType().equalsIgnoreCase("credit")) {
    		livretAOperation.getLivretA().setBalance(livretAOperation.getAvailableBalance()+livretAOperation.getAmount());
    	}
    	else {
    		livretAOperation.getLivretA().setBalance(livretAOperation.getAvailableBalance()-livretAOperation.getAmount());
        }
    	livretAOperation.setStatus("Terminé");
    	livretAOperationService.save(livretAOperation);
    	return "redirect:/virementAttendsLivretA";
    }
    @GetMapping("/refuseOpLivretA/{id}")
   	public String refuseOpLivretA(@PathVariable(value="id") Long id,Model model) {
    	LivretAOperation livretAOperation= livretAOperationService.findById(id).get();
    	livretAOperation.setStatus("Annulé");
    	livretAOperationService.save(livretAOperation);
    	return "redirect:/virementAttendsLivretA";
    }
    
	
}
