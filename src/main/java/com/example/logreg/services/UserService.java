package com.example.logreg.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.example.logreg.models.LoginUser;
import com.example.logreg.models.User;
import com.example.logreg.repositories.UserRepository;





@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;
	
    public User register(User newUser, BindingResult result) {
        // TO-DO - Reject values: 
        // Reject if email is taken (present in database)
    	Optional<User> optionalUser = userRepo.findByEmail(newUser.getEmail());
        // 1. Find user in the DB by email    
        // 2. if the email is present , reject
    	if(optionalUser.isPresent()) {
    		result.rejectValue("email", "unique", "Email is already registered");
    	}
        // Reject if password doesn't match confirmation
    	if(!newUser.getPassword().equals(newUser.getConfirm())) {
    		result.rejectValue("confirm", "matches", "Passwords do not match");
    	}
        // if result has errors, return 
    	if(result.hasErrors()) {
    		return null;
    	}
        // Hash and set password, save user to database  
    	String hashed = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
    	newUser.setPassword(hashed);
    	return userRepo.save(newUser);
    }
        
    public User login(LoginUser newLogin, BindingResult result) {
    // TO-DO - Reject values: 
        // Find user in the DB by email
        // 1. Find user in the DB by email   
    	Optional<User> optionalUser = userRepo.findByEmail(newLogin.getEmail());
        // 2. if the email is not present , reject  
    	if(!optionalUser.isPresent()) {
    		result.rejectValue("email", "unique", "Please Register");
    	}
    	if(result.hasErrors()) {
    		return null;
    	}
        // 3.1 grab the user from potential user 
    	User loginUser = optionalUser.get();
        // 3.2 if BCrypt password match fails
    	if(!BCrypt.checkpw(newLogin.getPassword(), loginUser.getPassword())) {
    	    result.rejectValue("password", "Matches", "Invalid Password!");
    	}

        // 4 if result has errors,return
    	if(result.hasErrors()) {
    		return null;
    	}
        // Otherwise, return the user object
    	return loginUser;
    }
        

}