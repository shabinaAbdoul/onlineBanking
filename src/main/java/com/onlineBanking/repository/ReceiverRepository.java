package com.onlineBanking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlineBanking.model.Receiver;
import com.onlineBanking.model.UserDetails;



@Repository
public interface ReceiverRepository extends JpaRepository<Receiver, Long> {

	List<Receiver> findByUserDetails(UserDetails userDetails);

		

}
