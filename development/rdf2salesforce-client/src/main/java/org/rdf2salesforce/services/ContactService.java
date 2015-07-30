package org.rdf2salesforce.services;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.rdf2salesforce.AccessToken;
import org.rdf2salesforce.config.AppConfig;
import org.rdf2salesforce.model.Contact;
import org.rdf2salesforce.model.ContactResponse;
import org.rdf2salesforce.model.CreateResponse;
import org.rdf2salesforce.model.Odette;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;

@Service
public class ContactService {

	@Autowired
	private AppConfig appConfig;

	private static Logger LOGGER = LoggerFactory
			.getLogger(ContactService.class);

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
		ResponseEntity<Contact> exchange = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			UriComponentsBuilder builder = UriComponentsBuilder
					.fromHttpUrl(token.getInstanceUrl()
							+ "/services/data/v34.0/sobjects/Contact/"
							+ contactId);
			HttpHeaders headers = createHeaders(token);
			HttpEntity<?> entity = new HttpEntity<>(headers);
			exchange = restTemplate.exchange(builder.build().encode().toUri(),
					HttpMethod.GET, entity, Contact.class);
		} catch (HttpClientErrorException httpError) {
			LOGGER.error(httpError.getMessage());
			exchange = new ResponseEntity<Contact>(new Contact(),
					httpError.getStatusCode());
		}
		return exchange.getBody();
	}

	public CreateResponse createContact(Contact contact, AccessToken token) {
		ResponseEntity<CreateResponse> exchange = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			UriComponentsBuilder builder = UriComponentsBuilder
					.fromHttpUrl(token.getInstanceUrl()
							+ "/services/data/v34.0/sobjects/Contact/");
			HttpHeaders headers = createHeaders(token);
			HttpEntity<Contact> entity = new HttpEntity<>(contact, headers);
			exchange = restTemplate.exchange(builder.build().encode().toUri(),
					HttpMethod.POST, entity, CreateResponse.class);

		} catch (HttpClientErrorException httpError) {
			LOGGER.error(httpError.getMessage());
			exchange = new ResponseEntity<CreateResponse>(new CreateResponse(),
					httpError.getStatusCode());
		}
		return exchange.getBody();
	}

	public String updateContact(Contact newContact, AccessToken token) {
		ResponseEntity<String> exchange = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
					token.getInstanceUrl()
							+ "/services/data/v34.0/sobjects/Contact/"
							+ newContact.getId()).queryParam("_HttpMethod",
					"PATCH");
			HttpHeaders headers = createHeaders(token);
			Contact oldContact = this.getContact(newContact.getId(), token);
			Contact diffContact = this.getDiffContact(oldContact, newContact);

			HttpEntity<Contact> entity = new HttpEntity<>(diffContact, headers);
			URI uri = builder.build().encode().toUri();
			exchange = restTemplate.exchange(uri, HttpMethod.POST, entity,
					String.class);

		} catch (HttpClientErrorException httpError) {
			LOGGER.error(httpError.getMessage());
			exchange = new ResponseEntity<String>(new String(),
					httpError.getStatusCode());
		}
		return exchange.getBody();
	}

	private Contact getDiffContact(Contact oldContact, Contact newContact) {
		Contact result = new Contact();
		Field[] fields = Contact.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				Object oldProperty = PropertyUtils.getProperty(oldContact,
						field.getName());
				Object newProperty = PropertyUtils.getProperty(newContact,
						field.getName());
				// introduced new field value that was null before
				if (newProperty != null && oldProperty == null) {
					PropertyUtils.setProperty(result, field.getName(),
							newProperty.toString());
					continue;
				}
				// changed old value to new value
				if (newProperty != null && oldProperty != null
						&& !newProperty.equals(oldProperty)) {
					PropertyUtils.setProperty(result, field.getName(),
							newProperty.toString());

				}
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				// TODO Auto-generated catch block
				LOGGER.error(e.getMessage());
			}
		}
		return result;
	}

	public void deleteContact(Contact contact, AccessToken token) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			UriComponentsBuilder builder = UriComponentsBuilder
					.fromHttpUrl(token.getInstanceUrl()
							+ "/services/data/v34.0/sobjects/Contact/"
							+ contact.getId());
			HttpHeaders headers = createHeaders(token);
			HttpEntity<?> entity = new HttpEntity<>(headers);
			restTemplate.exchange(builder.build().encode().toUri(),
					HttpMethod.DELETE, entity, String.class);
		} catch (HttpClientErrorException httpError) {
			LOGGER.error(httpError.getMessage());
		}
	}

	private HttpHeaders createHeaders(AccessToken token) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization",
				token.getTokenType() + " " + token.getAccessToken());
		return headers;
	}
	
	public Contact createFromRdf (Contact newContact){

    	FileManager.get().readModel(Odette.getM_model(), "example-eccenca.ttl");
    	
    	StmtIterator iter=Odette.getM_model().listStatements();
    	while(iter.hasNext()) {
    		Statement stms = iter.nextStatement();
    		Resource subject = stms.getSubject();
    		Property predicate = stms.getPredicate();
    		RDFNode object = stms.getObject();
    		System.out.print(subject.toString());
    		System.out.print(" " + predicate.toString() + " ");
    		if (object instanceof Resource){
    			 System.out.print(object.toString());
            } else {
                // object is a literal
                System.out.print(" \"" + object.toString() + "\"");
            }
    		 System.out.println(" .");
    	}
    	
    	Odette.getM_model().write(System.out); 
    	return newContact;
    	
	}

}
