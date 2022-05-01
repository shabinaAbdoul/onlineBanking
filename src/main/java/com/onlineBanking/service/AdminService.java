package com.onlineBanking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineBanking.model.UserDetails;
import com.onlineBanking.repository.UserDetailsRepository;

@Service

public class AdminService {
	@Autowired
	private UserDetailsRepository userDetailsRepository;
	
	public List<UserDetails> findAllUsers() {
		return userDetailsRepository.findAll();
		
	}

}
