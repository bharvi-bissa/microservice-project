package com.app.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	Environment env;

	@RequestMapping("/status/check")
	public String check() {
		return env.getProperty("spring.application.name") + " : Active on port : "
				+ env.getProperty("local.server.port");
	}
}
