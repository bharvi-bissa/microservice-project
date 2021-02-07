package com.app.users.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.app.users.service.UsersService;
import com.app.users.shared.UserDto;
import com.app.users.ui.model.CreateUserResponseModel;
import com.app.users.ui.model.UserRequestModel;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired Environment env;
	@Autowired RestTemplate restTemplate;
	@Autowired UsersService usersService;
	
	@GetMapping("/status/check")
	public String check() {
		return env.getProperty("spring.application.name") + " : Active on port : "
				+ env.getProperty("local.server.port") + ", With Secret :: "+env.getProperty("token.secret")+" with exp time "+env.getProperty("token.exp_time");
	}

	@PostMapping(
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE },
			produces = {  MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE }
			)
	public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody UserRequestModel userRequestModel) {

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		UserDto userDto = modelMapper.map(userRequestModel, UserDto.class);
		UserDto createdUser = usersService.createUser(userDto);

		CreateUserResponseModel response = modelMapper.map(createdUser, CreateUserResponseModel.class);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
