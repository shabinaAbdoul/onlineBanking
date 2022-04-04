package com.onlineBanking.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.onlineBanking.model.User;
import com.onlineBanking.model.UserDetails;
import com.onlineBanking.repository.UserDetailsRepository;
import com.onlineBanking.repository.UserRepository;


@Service
public class UserServices {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private UserDetailsRepository userDetRepo;
	
	public User findByUserName(String userName) {
        return  userRepo.findByUserName(userName);
    }
	
	
	
	public UserDetails findByEmail(String email) {
        return  userDetRepo.findByEmail(email);
    }
	
	//Je crée une methode C du CRUD
public void createUserDetails(UserDetails userDetails) {
	userDetRepo.save(userDetails);
	}
	
	
	public void createUser(User user) {
		
		BCryptPasswordEncoder crypt = new BCryptPasswordEncoder();
		user.setPassword(crypt.encode(user.getPassword()));		
		userRepo.save(user);
	}



	public Optional<UserDetails> findByUserDetailsId(Long id) {
		// TODO Auto-generated method stub
		return userDetRepo.findById(id);
	}


	
}
