package com.onlineBanking.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lowagie.text.DocumentException;
import com.onlineBanking.mailConfig.CcpOperationPdf;
import com.onlineBanking.mailConfig.CcpRibPdf;
import com.onlineBanking.mailConfig.LivretAOperationPdf;
import com.onlineBanking.mailConfig.LivretARibPdf;
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
		//List<CcpOperation> ccpOperations =ccpOperationService.findByCcpAccount(user.getUserDetails().getCcpAccount());
		List<CcpOperation> ccpOperations =ccpOperationService.findByCcpAccountOrderByCreationDateTimeDesc(user.getUserDetails().getCcpAccount());
		//findByUserNameOrderByCreatedDateDesc
		//List<CcpOperation> ccpOperations =ccpOperationService.findByCcpAccount(user.getUserDetails().getCcpAccount());
		
		
        CcpAccount ccpAccount = user.getUserDetails().getCcpAccount();

        model.addAttribute("ccpAccount", ccpAccount);
        model.addAttribute("ccpOperations", ccpOperations);
        int lastMonth=Integer.parseInt(ccpOperations.get(0).getCreationDateTime().toString().split("-")[1]);
        int firstMonth=Integer.parseInt(ccpOperations.get(ccpOperations.size()-1).getCreationDateTime().toString().split("-")[1]);
		
		 model.addAttribute("lastMonth", lastMonth);
	        model.addAttribute("firstMonth", firstMonth);
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
	
	 @GetMapping("/operationsCcp/export/pdf/{month}")
	    public void exportToPDF(HttpServletResponse response,Principal principal,@PathVariable(value="month") int month) throws DocumentException, IOException {
	        response.setContentType("application/pdf");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=ccpOperations_" + currentDateTime + ".pdf";
	        response.setHeader(headerKey, headerValue);
	         
	        User user = userService.findByUserName(principal.getName());
	        UserDetails userDetails=user.getUserDetails();
			List<CcpOperation> ccpOperations =ccpOperationService.findByCcpAccountOrderByCreationDateTimeDesc(userDetails.getCcpAccount());
			List<CcpOperation> ccpOperations1 =new ArrayList<>();
			for(int i=0;i<ccpOperations.size();i++) {
				int monthEach=Integer.parseInt(ccpOperations.get(i).getCreationDateTime().toString().split("-")[1]);
				 if(monthEach==month)	{
					 ccpOperations1.add(ccpOperations.get(i));
				 }
			 }			
	        CcpOperationPdf exporter = new CcpOperationPdf(ccpOperations1,userDetails);
	        exporter.export(response);
	         
	    }
	 
	 @GetMapping("/operationsLivretA/export/pdf/{month}")
	    public void exportToPDFLivretA(HttpServletResponse response,Principal principal,@PathVariable(value="month") int month) throws DocumentException, IOException {
	        response.setContentType("application/pdf");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=LivretAOperations_" + currentDateTime + ".pdf";
	        response.setHeader(headerKey, headerValue);
	         
	        User user = userService.findByUserName(principal.getName());
	        UserDetails userDetails=user.getUserDetails();
			List<LivretAOperation> livretAOperations =livretAOperationService.findByLivretAOrderByCreationDateTimeDesc(userDetails.getLivretA());
			List<LivretAOperation> livretAOperations1 =new ArrayList<>();
			for(int i=0;i<livretAOperations.size();i++) {
				int monthEach=Integer.parseInt(livretAOperations.get(i).getCreationDateTime().toString().split("-")[1]);
				 if(monthEach==month)	{
					 livretAOperations1.add(livretAOperations.get(i));
				 }
			 }			
	        LivretAOperationPdf exporter = new LivretAOperationPdf(livretAOperations1,userDetails);
	        exporter.export(response);
	         
	    }
	 @GetMapping("/ribCcp/export/pdf")
	    public void ribCcp(HttpServletResponse response,Principal principal) throws DocumentException, IOException {
	        response.setContentType("application/pdf");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=Rib_ccp" + currentDateTime + ".pdf";
	        response.setHeader(headerKey, headerValue);
	         
	        User user = userService.findByUserName(principal.getName());
	        UserDetails userDetails=user.getUserDetails();
						
	        CcpRibPdf exporter = new CcpRibPdf(userDetails);
	        exporter.export(response);
	         
	    }
	 @GetMapping("/ribLivretA/export/pdf")
	    public void ribLivretA(HttpServletResponse response,Principal principal) throws DocumentException, IOException {
	        response.setContentType("application/pdf");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=Rib_LivretA" + currentDateTime + ".pdf";
	        response.setHeader(headerKey, headerValue);
	         
	        User user = userService.findByUserName(principal.getName());
	        UserDetails userDetails=user.getUserDetails();
						
	        LivretARibPdf exporter = new LivretARibPdf(userDetails);
	        exporter.export(response);
	         
	    }
	
}
