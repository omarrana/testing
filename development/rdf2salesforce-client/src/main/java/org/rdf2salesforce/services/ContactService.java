package org.rdf2salesforce.services;

import java.util.List;

import org.rdf2salesforce.AccessToken;
import org.rdf2salesforce.config.AppConfig;
import org.rdf2salesforce.model.Contact;
import org.rdf2salesforce.model.ContactResponse;
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
	
	public List<Contact> getAll(AccessToken token){
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(token.getInstanceUrl()+ "/services/data/v34.0/query/")
				.queryParam("q", appConfig.CONTACT_QUERY_ALL);
		System.out.println(builder.build().encode().toString());
		HttpHeaders headers = createHeaders(token);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<ContactResponse> exchange = restTemplate.exchange(builder
				.build().encode().toUri(), HttpMethod.GET, entity,
				ContactResponse.class);
		return exchange.getBody().getRecords();
	}
	
	public Contact getContactById(String contactId, AccessToken token){
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(token.getInstanceUrl()+ "/services/data/v34.0/sobjects/Contact/"+contactId);
		System.out.println(builder.build().encode().toString());
		HttpHeaders headers = createHeaders(token);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<Contact> exchange = restTemplate.exchange(builder
				.build().encode().toUri(), HttpMethod.GET, entity,
				Contact.class);
		return exchange.getBody();
	}

	private HttpHeaders createHeaders(AccessToken token) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", token.getTokenType()+ " " + token.getAccessToken());
		return headers;
	}

}
