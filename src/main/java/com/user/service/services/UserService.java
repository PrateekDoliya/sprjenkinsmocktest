package com.user.service.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.user.service.entities.User;

public interface UserService <T> {

	// create
	T createUser(User user);
	
	// update
	T updateUser(User user, String userId);
	
	// delete
	T deleteUser(String userId);
	
	// get all
	T getAllUsers();
	
	// get by id
	T getUserById(String userId);
	
	// upload profile picture
	T updloadProfile(String userId, MultipartFile file) ;
}
