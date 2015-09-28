package org.rdf2salesforce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class Rdf2SalesforceService {
	
	@Autowired
	AppConfig appConfig;
	
	public AccessToken login() {
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(appConfig.LOGIN_URL)
				.queryParam("client_id", appConfig.CLIENT_ID)
				.queryParam("client_secret", appConfig.CLIENT_SECRET)
				.queryParam("username", appConfig.USERNAME)
				.queryParam("password", appConfig.PASSWORD);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<AccessToken> exchange = restTemplate.exchange(builder
				.build().encode().toUri(), HttpMethod.POST, entity,
				AccessToken.class);
		return exchange.getBody();
	}

	public void revokeToken(String token) {
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
				appConfig.LOGOUT_URL).queryParam("token", token);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<String> exchange = restTemplate.exchange(builder.build()
				.encode().toUri(), HttpMethod.GET, entity, String.class);
		if (exchange.getStatusCode().equals(HttpStatus.OK)) {
			// everything is fine, TODO: handle errors
		}
	}

}
