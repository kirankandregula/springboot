package com.solventek.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solventek.entity.User;
import com.solventek.repository.UserRepository;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/get")
	public List<User> getUser(){
		return userRepository.findAll();
	}
	
	@GetMapping("/get/{id}")
	public User getUserById(@PathVariable Integer id) {
		return userRepository.findById(id).orElseThrow(()->new NoSuchElementException("user not found with the id"+id));
	}
	
	@PostMapping("/add")
	public User addUser(@RequestBody User user) {
		return userRepository.save(user);
	}
	
	@PutMapping("/{id}")
	public User updateUser(@RequestBody User user,@PathVariable Integer id) {
		return userRepository.findById(id).map(
				
				newUser->{
					newUser.setUserName(user.getUserName());
					newUser.setPassWord(user.getPassWord());
					newUser.setRole(user.getRole());
					userRepository.save(newUser);
					return newUser;
				}).orElseThrow(()-> new NoSuchElementException("user id not found with the id "+id));
				
	}
	
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable Integer id) {
		 userRepository.deleteById(id);
	}
	
	
	  @PostMapping("/login")
	    public ResponseEntity<?> login(@RequestBody User user) {
	        List<User> users = userRepository.findAll();
	        for (User u : users) {
	            if (u.getUserName().equals(user.getUserName())) {
	                if (verifyPassword(user.getPassWord(), u.getPassWord())) {
	                    // If login is successful, construct a Map containing username and role
	                    Map<String, String> responseObject = new HashMap<>();
	                    responseObject.put("userName", u.getUserName());
	                    responseObject.put("role", u.getRole().toString());
	                    return ResponseEntity.ok(responseObject);
	                } else {
	                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
	                }
	            }
	        }
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	    }

	    // Method to verify password (for demonstration purposes)
	    private boolean verifyPassword(String inputPassword, String storedPassword) {
	        // Implement your logic to verify the password against the stored hashed password
	        // For demonstration, we're assuming a simple comparison
	        return inputPassword.equals(storedPassword);
	    }
}
