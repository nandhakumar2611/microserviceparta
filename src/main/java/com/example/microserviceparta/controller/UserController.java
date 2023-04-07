package com.example.microserviceparta.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.example.microserviceparta.model.User;
import com.example.microserviceparta.repository.UserRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;

	@PostMapping("/user")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		return new ResponseEntity<User>(userRepository.save(user),HttpStatus.OK);
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<User>> getAllUser() {
		
		List<User> user = new ArrayList<User>();
		userRepository.findAll().forEach(user::add);
		
		if(user.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(user,HttpStatus.OK);
	}
	
	
	@PutMapping("/user/{id}")
	public ResponseEntity<User> getuserById(@PathVariable Long id,@RequestBody User userdetails) {
		
		Optional<User> userDate = userRepository.findById(id);
		
		if(userDate.isPresent()) {
			User user = userDate.get();
			user.setUsername(userdetails.getUsername());
			user.setEmail(userdetails.getEmail());
			return new ResponseEntity<User>(userRepository.save(user),HttpStatus.OK); 
		}
		else {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable Long id) {
		userRepository.deleteById(id);
		return new ResponseEntity<User>(HttpStatus.OK);
	}
}
