package com.onlineBanking.service;


import java.util.List;
import java.util.Optional;

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
		return ccpOperationRepository.findByCcpAccount(ccpAccount);	
	}
	public List<CcpOperation> findByStatus(String status) {
		return ccpOperationRepository.findByStatus(status);	
	}

	public Optional<CcpOperation> findById(Long id) {
		return ccpOperationRepository.findById(id);	
	}

	public List<CcpOperation> findByCcpAccountOrderByCreationDateTimeDesc(CcpAccount ccpAccount) {
		// TODO Auto-generated method stub
		return ccpOperationRepository.findByCcpAccountOrderByCreationDateTimeDesc(ccpAccount);	
	}

	
}





	
	
	


	

