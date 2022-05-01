package com.onlineBanking.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineBanking.model.CcpAccount;
import com.onlineBanking.model.CcpOperation;
import com.onlineBanking.model.LivretA;
import com.onlineBanking.model.LivretAOperation;
import com.onlineBanking.model.UserDetails;
import com.onlineBanking.repository.LivretAOperationRepository;


@Service
public class LivretAOperationService {
	@Autowired
	private LivretAOperationRepository livretAOperationRepository;
	public void save(LivretAOperation livretAOperation) {
		livretAOperationRepository.save(livretAOperation);		
	}
	
	public List<LivretAOperation> findByLivretA(LivretA livretA) {
		// TODO Auto-generated method stub
		return livretAOperationRepository.findBylivretA(livretA);	
	}

	public List<LivretAOperation> findByStatus(String status) {
		// TODO Auto-generated method stub
		return livretAOperationRepository.findByStatus(status);
	}

	public Optional<LivretAOperation> findById(Long id) {
		// TODO Auto-generated method stub
		return livretAOperationRepository.findById(id);
	}
	public List<LivretAOperation> findByLivretAOrderByCreationDateTimeDesc(LivretA livretA) {
		// TODO Auto-generated method stub
		return livretAOperationRepository.findByLivretAOrderByCreationDateTimeDesc(livretA);	
	}

}





	
	
	


	

