package com.onlineBanking.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.onlineBanking.model.CcpAccount;
import com.onlineBanking.model.LivretA;
import com.onlineBanking.model.Receiver;
import com.onlineBanking.model.User;
import com.onlineBanking.model.UserDetails;
import com.onlineBanking.repository.CcpAccountRepository;
import com.onlineBanking.repository.UserDetailsRepository;
import com.onlineBanking.repository.UserRepository;


@Service
public class CcpAccountService {

	@Autowired
	private CcpAccountRepository ccpAccountRepository;
	

public CcpAccount findTopByOrderByIdDesc() {
	return ccpAccountRepository.findTopByOrderByIdDesc();
	
}


public void save(CcpAccount ccpAccount) {
	ccpAccountRepository.save(ccpAccount);
	
}


public Optional<CcpAccount> findById(Long compteC) {
	return ccpAccountRepository.findById(compteC);

	
}


public Optional<CcpAccount> findByNumIban(String numIban) {
	return ccpAccountRepository.findByNumIban(numIban);
}








	
	
	


	
}
