package com.user.service.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.user.service.entities.User;
import com.user.service.responsedto.ApiResponse;
import com.user.service.services.UserService;

@RestController
@RequestMapping("/api/v1/users")
@SuppressWarnings("unchecked")
public class UserController<T> {

	@Autowired
	private UserService<T> userService;

	// create
	@PostMapping("/create")
	public ResponseEntity<T> createUser(@RequestBody User user) {
		T createUser = this.userService.createUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(createUser);
	}

	// update
	@PutMapping("/edit/{userId}")
	public ResponseEntity<T> updateUser(@RequestBody User user, @PathVariable String userId) {
		T updateUser = this.userService.updateUser(user, userId);
		return ResponseEntity.ok(updateUser);
	}

	// delete
	@DeleteMapping("/remove/{userId}")
	public ResponseEntity<T> deleteUser(@PathVariable String userId) {
		T deleteUser = this.userService.deleteUser(userId);
		return ResponseEntity.ok(deleteUser);
	}

	// get all
	@GetMapping("/get-all")
	public ResponseEntity<T> getAllUsers() {
		T allUsers = this.userService.getAllUsers();
		return ResponseEntity.ok(allUsers);
	}

	// get by id
	@GetMapping("/get/{userId}")
	public ResponseEntity<T> getUserById(@PathVariable String userId) {
		T userById = this.userService.getUserById(userId);
		return ResponseEntity.ok(userById);
	}

	// upload profile image
	@PostMapping("/upload/{userId}")
	public ResponseEntity<T> uploadProfile(@RequestParam("profileImage") MultipartFile file,
			@PathVariable String userId) {
		if (file.isEmpty()) {
			return (ResponseEntity<T>) ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse("File Cant not be empty !!", false, HttpStatus.BAD_REQUEST));
		}

		String fileContentType = file.getContentType();
		
		if ((!file.isEmpty())
				&& (!fileContentType.equals("image/jpg") && !file.getContentType().equals("image/jpeg"))) {
			return (ResponseEntity<T>) ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse("Only JPG or JPEG File Allowed !!", false, HttpStatus.BAD_REQUEST));
		}

		// upload file to folder and add file name to user
		T updloadProfile = this.userService.updloadProfile(userId, file);
		return ResponseEntity.status(HttpStatus.OK).body(updloadProfile);
	}

	// getProfile pic
	@GetMapping("/get-profile/{userId}")
	public ResponseEntity<T> getProfile(@PathVariable String userId) {

		return (ResponseEntity<T>) ResponseEntity.ok(ServletUriComponentsBuilder.fromCurrentContextPath().path("/image")
				.path("userRepo.getProfilePath()").toUriString());

	}
}
