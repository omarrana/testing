package org.rdf2salesforce.controller;

import org.rdf2salesforce.AccessToken;
import org.rdf2salesforce.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	@Autowired
	LoginService loginService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public AccessToken login(@RequestParam(value = "clientId") String clientId,
			@RequestParam(value = "clientSecret") String clientSecret,
			@RequestParam(value = "clientUsername") String clientUsername,
			@RequestParam(value = "clientPassword") String clientPassword) {
		AccessToken token = loginService.login(clientId, clientSecret,
				clientUsername, clientPassword);
		return token;
	}
	
	@RequestMapping(value = "/logut", method = RequestMethod.GET)
	public void revokeToken(@RequestParam(value = "token") String token) {
		loginService.revokeToken(token);
	}
	
}
