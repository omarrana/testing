package org.rdf2salesforce.services;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rdf2salesforce.AccessToken;
import org.rdf2salesforce.Application;
import org.rdf2salesforce.model.Contact;
import org.rdf2salesforce.model.CreateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ContactServiceTest {

	@Autowired
	private ContactService contactService;
	@Autowired
	private LoginService loginService;
	private AccessToken token;

	@Before
	public void init() {
		token = loginService.getToken();

	}

	@Test
	public void testGetAll() {
		List<Contact> allContacts = contactService.getAll(token);
		assertTrue(allContacts.size() > 0);
	}

	@Test
	public void testGetContact() {

	}

	@Test
	public void testCreateContact() {
		Contact contact = new Contact();
		contact.setFamilyName("Nash");
		contact.setGivenName("John");
		CreateResponse createResponse = contactService.createContact(contact, token);
		assertTrue(createResponse.getId() != null);		
	}

	@Test
	public void testUpdateContact() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteContact() {
		Contact contact = new Contact();
		contact.setFamilyName("Nash");
		contact.setGivenName("John");
		CreateResponse createResponse = contactService.createContact(contact, token);
		assertTrue(createResponse.getId() != null);
		contact.setId(createResponse.getId());
		contactService.deleteContact(contact, token);
		Contact deletedContact = contactService.getContact(createResponse.getId(), token);
		assertTrue(deletedContact.getName() == null);
	}

	@Test
	public void testCreateFromRdf() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateRdfContact() {
		fail("Not yet implemented");
	}

}
