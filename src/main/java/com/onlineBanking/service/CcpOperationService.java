package com.onlineBanking.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineBanking.model.CcpAccount;
import com.onlineBanking.model.CcpOperation;
import com.onlineBanking.repository.CcpOperationRepository;


@Service
public class CcpOperationService {
	@Autowired
	private CcpOperationRepository ccpOperationRepository;
	public void save(CcpOperation ccpOperation) {
		ccpOperationRepository.save(ccpOperation);		
	}
	
	public List<CcpOperation> findByCcpAccount(CcpAccount ccpAccount) {
		// TODO Auto-generated method stub
		return ccpOperationRepository.findByCcpAccount(ccpAccount);	
	}

	
	
}





	
	
	


	

