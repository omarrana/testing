package org.rdf2salesforce.controller;

import java.util.List;

import org.rdf2salesforce.model.Contact;
import org.rdf2salesforce.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

	@Autowired
	ContactService contactService;

	@RequestMapping(value = "/contact/{id}", method = RequestMethod.GET)
	public Contact getContact(@RequestParam(value = "id") String id,
							  @RequestParam(value = "token") String token,
							  @RequestParam(value = "instance") String instance) {
		return contactService.getContact(id, token, instance);
	}
	
	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public List<Contact> getAll(@RequestParam(value = "token") String token,
								@RequestParam(value = "instance") String instance) {
		return contactService.getAll(token, instance);
	}
	
	
	

}