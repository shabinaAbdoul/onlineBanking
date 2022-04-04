package com.onlineBanking.service;


import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.onlineBanking.model.CcpAccount;
import com.onlineBanking.model.CcpOperation;
import com.onlineBanking.model.LivretA;
import com.onlineBanking.model.LivretAOperation;
import com.onlineBanking.model.User;
import com.onlineBanking.model.UserDetails;
import com.onlineBanking.repository.CcpAccountRepository;
import com.onlineBanking.repository.CcpOperationRepository;
import com.onlineBanking.repository.LivretARepository;
import com.onlineBanking.repository.UserDetailsRepository;
import com.onlineBanking.repository.UserRepository;


@Service
public class ActionService {
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

public void versement(String account,Double amount,Principal principal) {
	 User user = userService.findByUserName(principal.getName());
	 user.getUserDetails().getCcpAccount().getId();
	    if (account.equalsIgnoreCase("Ccp")) {
         CcpAccount ccpAccount = user.getUserDetails().getCcpAccount();
         ccpAccount.setBalance(ccpAccount.getBalance()+amount);
         ccpAccountService.save(ccpAccount);
         CcpOperation ccpOperation= new CcpOperation("Veresement à Compte CCP", "crédit", "Terminé", amount, ccpAccount.getBalance(), ccpAccount);
         Date date=new Date();
         ccpOperation.setCreationDateTime(date);
         ccpOperationService.save(ccpOperation);
//         
     } else if (account.equalsIgnoreCase("LivretA")) {
         LivretA livretA  = user.getUserDetails().getLivretA();
         livretA.setBalance(livretA.getBalance()+amount);
         livretAService.save(livretA);
         LivretAOperation livretAOperation= new LivretAOperation("Veresement à Compte CCP", "crédit", "Terminé", amount, livretA.getBalance(), livretA);
         Date date=new Date();
         livretAOperation.setCreationDateTime(date);
         livretAOperationService.save(livretAOperation);
     }
	
}
public void retrait(String account,Double amount,Principal principal) {
	 User user = userService.findByUserName(principal.getName());
	 user.getUserDetails().getCcpAccount().getId();
	    if (account.equalsIgnoreCase("Ccp")) {
        CcpAccount ccpAccount = user.getUserDetails().getCcpAccount();
        ccpAccount.setBalance(ccpAccount.getBalance()-amount);
        ccpAccountService.save(ccpAccount);
        CcpOperation ccpOperation= new CcpOperation("Retrait de Compte CCP", "débit", "Terminé", amount, ccpAccount.getBalance(), ccpAccount);
        Date date=new Date();
        ccpOperation.setCreationDateTime(date);
        ccpOperationService.save(ccpOperation);
//        
    } else if (account.equalsIgnoreCase("LivretA")) {
        LivretA livretA  = user.getUserDetails().getLivretA();
        livretA.setBalance(livretA.getBalance()-amount);
        livretAService.save(livretA);
        LivretAOperation livretAOperation= new LivretAOperation("Retrait de Compte CCP", "débit", "Terminé", amount, livretA.getBalance(), livretA);
        Date date=new Date();
        livretAOperation.setCreationDateTime(date);
        livretAOperationService.save(livretAOperation);
    }
	
}



}
