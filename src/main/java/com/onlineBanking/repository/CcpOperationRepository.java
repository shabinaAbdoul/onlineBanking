package com.onlineBanking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlineBanking.model.CcpAccount;
import com.onlineBanking.model.CcpOperation;
import com.onlineBanking.model.User;
import com.onlineBanking.model.UserDetails;



@Repository
public interface CcpOperationRepository extends JpaRepository<CcpOperation, Long> {

	//List<CcpOperation> findCcpOperationList(CcpAccount ccpAccount);

	List<CcpOperation> findByCcpAccount(CcpAccount ccpAccount);

	

}
