package com.onlineBanking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlineBanking.model.CcpAccount;
import com.onlineBanking.model.User;



@Repository
public interface CcpAccountRepository extends JpaRepository<CcpAccount, Long> {

	CcpAccount findTopByOrderByIdDesc();

}
