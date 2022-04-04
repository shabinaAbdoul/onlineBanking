package com.onlineBanking.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineBanking.model.LivretA;
import com.onlineBanking.model.LivretAOperation;
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

}





	
	
	


	

