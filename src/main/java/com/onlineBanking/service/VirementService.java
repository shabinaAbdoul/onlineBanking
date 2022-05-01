package com.onlineBanking.service;


import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineBanking.mailConfig.MailVerif;
import com.onlineBanking.model.CcpAccount;
import com.onlineBanking.model.CcpOperation;
import com.onlineBanking.model.LivretA;
import com.onlineBanking.model.LivretAOperation;
import com.onlineBanking.model.Receiver;
import com.onlineBanking.model.User;
import com.onlineBanking.model.UserDetails;
import com.onlineBanking.repository.ReceiverRepository;


@Service
public class VirementService {
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
@Autowired
private ReceiverRepository receiverRepository;

public void entreMonCompte(String compteD, String compteC, double amount, Principal principal) {
	 User user = userService.findByUserName(principal.getName());
	 CcpAccount ccpAccount = user.getUserDetails().getCcpAccount();
	 LivretA livretA  = user.getUserDetails().getLivretA();
	    if (compteD.equalsIgnoreCase("Ccp")) {         
         ccpAccount.setBalance(ccpAccount.getBalance()-amount);
         ccpAccountService.save(ccpAccount);
         CcpOperation ccpOperation= new CcpOperation("Virement", "débit", "Terminé", amount, ccpAccount.getBalance(), ccpAccount);
         Date date=new Date();
         ccpOperation.setCreationDateTime(date);
         ccpOperationService.save(ccpOperation);        
         livretA.setBalance(livretA.getBalance()+amount);
         livretAService.save(livretA);
         LivretAOperation livretAOperation= new LivretAOperation("Virement", "crédit", "Terminé", amount, livretA.getBalance(), livretA);
         livretAOperation.setCreationDateTime(date);
         livretAOperationService.save(livretAOperation);
//         
     } else if (compteD.equalsIgnoreCase("LivretA")) {
         livretA.setBalance(livretA.getBalance()-amount);
         livretAService.save(livretA);
         LivretAOperation livretAOperation= new LivretAOperation("Virement", "débit", "Terminé", amount, livretA.getBalance(), livretA);
         Date date=new Date();
         livretAOperation.setCreationDateTime(date);
         livretAOperationService.save(livretAOperation);
         ccpAccount.setBalance(ccpAccount.getBalance()+amount);
         ccpAccountService.save(ccpAccount);
         CcpOperation ccpOperation= new CcpOperation("Virement", "crédit", "Terminé", amount, ccpAccount.getBalance(), ccpAccount);
         ccpOperation.setCreationDateTime(date);
         ccpOperationService.save(ccpOperation);     
     }
	
}
public void aAutreCompte(String compteD, Long compteC, double amount, Principal principal) {
	 Date date=new Date();	
	User user = userService.findByUserName(principal.getName());
	
	user.getUserDetails().getCcpAccount().getId();
	Receiver receiver=findById(compteC).get();
	 if (compteD.equalsIgnoreCase("Ccp")) {
		 CcpAccount ccpAccount = user.getUserDetails().getCcpAccount();
         ccpAccount.setBalance(ccpAccount.getBalance()-amount);
         ccpAccountService.save(ccpAccount);
         CcpOperation ccpOperation= new CcpOperation("Virement à "+receiver.getNom(), "débit", "Terminé", amount, ccpAccount.getBalance(), ccpAccount);
         ccpOperation.setCreationDateTime(date);
         ccpOperationService.save(ccpOperation); 
	 }
	 else {
		 LivretA livretA = user.getUserDetails().getLivretA();
		 livretA.setBalance(livretA.getBalance()-amount);
         livretAService.save(livretA);
         LivretAOperation livretAOperation= new LivretAOperation("Virement à "+receiver.getNom(), "débit", "Terminé", amount, livretA.getBalance(), livretA);
         livretAOperation.setCreationDateTime(date);
         livretAOperationService.save(livretAOperation);
         }
         if (!ccpAccountService.findByNumIban(receiver.getNumIban()).isEmpty()) {  
         CcpAccount ccpAccountCrediter=ccpAccountService.findByNumIban(receiver.getNumIban()).get();             
         //ccpAccountCrediter.setBalance(ccpAccountCrediter.getBalance()+amount);
         CcpOperation ccpOperationCrediter= new CcpOperation("Virement de "+user.getUserDetails().getFirstName()+" "+user.getUserDetails().getLastName(), "crédit", "Encours...", amount, ccpAccountCrediter.getBalance(), ccpAccountCrediter);
         ccpOperationCrediter.setCreationDateTime(date);
         ccpOperationService.save(ccpOperationCrediter);   
         }
         else if (!livretAService.findByNumIban(receiver.getNumIban()).isEmpty()) {
        	 LivretA livretACrediter=livretAService.findByNumIban(receiver.getNumIban()).get();
        	// livretACrediter.setBalance(livretACrediter.getBalance()+amount); 
        	 LivretAOperation livretAOperationCrediter= new LivretAOperation("Virement de "+user.getUserDetails().getFirstName()+" "+user.getUserDetails().getLastName(), "crédit", "Encours...", amount, livretACrediter.getBalance(), livretACrediter);
        	 livretAOperationCrediter.setCreationDateTime(date);
             livretAOperationService.save(livretAOperationCrediter);  
         }
         UserDetails userDetails=user.getUserDetails();
         MailVerif.sendMail(userDetails.getEmail(), "Bonjour, "+userDetails.getGenre()+" "+ userDetails.getFirstName()+" virement de "+amount+"€ est efféctué par votre compte "+compteD);
 		
	
}

public void save(Receiver receiver) {
	receiverRepository.save(receiver);
	
}

public List<Receiver> findByUserDetails(UserDetails userDetails) {
	// TODO Auto-generated method stub
	return receiverRepository.findByUserDetails(userDetails);
}
public Optional<Receiver> findById(Long id) {
	return receiverRepository.findById(id);
}
public void deleteById(Long id) {
	
	receiverRepository.deleteById(id);
}






}
