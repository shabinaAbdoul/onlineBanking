package com.onlineBanking.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.onlineBanking.model.CcpAccount;
import com.onlineBanking.model.LivretA;
import com.onlineBanking.model.User;
import com.onlineBanking.model.UserDetails;
import com.onlineBanking.repository.CcpAccountRepository;
import com.onlineBanking.repository.LivretARepository;
import com.onlineBanking.repository.UserDetailsRepository;
import com.onlineBanking.repository.UserRepository;


@Service
public class LivretAService {

	@Autowired
	private LivretARepository livretARepository;
	

public LivretA findTopByOrderByIdDesc() {
	return livretARepository.findTopByOrderByIdDesc();
	
}
public void save(LivretA livretA) {
	livretARepository.save(livretA);
	
}
public Optional<LivretA> findById(Long compteC) {
	
	return livretARepository.findById(compteC);
}
	
	
	


	
}
