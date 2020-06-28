package com.app.account.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Value("${spring.application.name}")
	private String appName;

	@RequestMapping("/status/check")
	public String check() {
		return appName + " : Active";
	}
}
