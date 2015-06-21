package org.rdf2salesforce.services;

import java.util.List;

import org.rdf2salesforce.AccessToken;
import org.rdf2salesforce.config.AppConfig;
import org.rdf2salesforce.model.Contact;
import org.rdf2salesforce.model.ContactResponse;
import org.rdf2salesforce.model.CreateResponse;
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

	public List<Contact> getAll(AccessToken token) {
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
				token.getInstanceUrl() + "/services/data/v34.0/query/")
				.queryParam("q", appConfig.CONTACT_QUERY_ALL);
		HttpHeaders headers = createHeaders(token);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<ContactResponse> exchange = restTemplate.exchange(
				builder.build().encode().toUri(), HttpMethod.GET, entity,
				ContactResponse.class);
		return exchange.getBody().getRecords();
	}

	public Contact getContact(String contactId, AccessToken token) {
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(token
				.getInstanceUrl()
				+ "/services/data/v34.0/sobjects/Contact/"
				+ contactId);
		HttpHeaders headers = createHeaders(token);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<Contact> exchange = restTemplate.exchange(builder
				.build().encode().toUri(), HttpMethod.GET, entity,
				Contact.class);
		return exchange.getBody();
	}

	public CreateResponse createContact(Contact contact, AccessToken token) {
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(token
				.getInstanceUrl() + "/services/data/v34.0/sobjects/Contact/");
		HttpHeaders headers = createHeaders(token);
		HttpEntity<Contact> entity = new HttpEntity<>(contact, headers);
		ResponseEntity<CreateResponse> exchange = restTemplate.exchange(builder
				.build().encode().toUri(), HttpMethod.POST, entity,
				CreateResponse.class);
		return exchange.getBody();
	}

	public String updateContact(Contact contact, AccessToken token) {
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(token
				.getInstanceUrl()
				+ "/services/data/v34.0/sobjects/Contact/"
				+ contact.getId());
		HttpHeaders headers = createHeaders(token);
		HttpEntity<Contact> entity = new HttpEntity<>(contact, headers);
		ResponseEntity<String> exchange = restTemplate.exchange(builder.build()
				.encode().toUri(), HttpMethod.POST, entity, String.class);
		return exchange.getBody();
	}

	public void deleteContact(Contact contact, AccessToken token) {
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(token
				.getInstanceUrl()
				+ "/services/data/v34.0/sobjects/Contact/"
				+ contact.getId());
		HttpHeaders headers = createHeaders(token);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<String> exchange = restTemplate.exchange(builder.build()
				.encode().toUri(), HttpMethod.DELETE, entity, String.class);
	}

	private HttpHeaders createHeaders(AccessToken token) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization",
				token.getTokenType() + " " + token.getAccessToken());
		return headers;
	}

}
