package org.rdf2salesforce.services;

import org.rdf2salesforce.AccessToken;
import org.rdf2salesforce.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ContactService {
	
	@Autowired
	private AppConfig appConfig;
	
	public String getAll(AccessToken token){
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(token.getInstanceUrl()+ "/services/data/v34.0/query/")
				.queryParam("q", appConfig.CONTACT_QUERY_ALL);
		System.out.println(builder.build().encode().toString());
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", token.getTokenType()+ " " + token.getAccessToken());
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<String> exchange = restTemplate.exchange(builder
				.build().encode().toUri(), HttpMethod.GET, entity,
				String.class);
		return exchange.getBody();
	}

}
