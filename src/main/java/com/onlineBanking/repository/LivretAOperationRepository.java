package com.onlineBanking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlineBanking.model.LivretA;
import com.onlineBanking.model.LivretAOperation;



@Repository
public interface LivretAOperationRepository extends JpaRepository<LivretAOperation, Long> {

	List<LivretAOperation> findBylivretA(LivretA livretA);

	List<LivretAOperation> findByStatus(String status);

	List<LivretAOperation> findByLivretAOrderByCreationDateTimeDesc(LivretA livretA);	

}
