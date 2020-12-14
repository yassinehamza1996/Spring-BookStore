package com.vermeg.ams.services;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.vermeg.ams.entities.Client;
import com.vermeg.ams.entities.Role;

import com.vermeg.ams.repositories.ClientRepository;
import com.vermeg.ams.repositories.RoleRepository;




@Service("userService")
public class UserService {
	private ClientRepository userRepository;
	private RoleRepository roleRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UserService(ClientRepository userRepository, RoleRepository roleRepository,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public Client findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public void saveUser(Client user) {

		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(0);
		Role userRole = roleRepository.findByRole("USER");
		user.setRole(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}

}
