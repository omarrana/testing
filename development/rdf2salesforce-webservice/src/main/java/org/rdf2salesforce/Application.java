package org.rdf2salesforce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class Application {
	
	private static Logger LOGGER = LoggerFactory
			.getLogger(Application.class);

    public static void main(String args[]){
    	SpringApplication.run(Application.class, args);
//    	ApplicationContext ctx = SpringApplication.run(Application.class, args);
//    	LoginService loginService = ctx.getBean(LoginService.class);
//    	String token = loginService.getToken().getAccessToken();
//    	System.out.println(token);
//    	ContactService contactService = ctx.getBean(ContactService.class);
//    	List<Contact> allContacts = contactService.getAll(token);
//    	allContacts.forEach(contact -> System.out.println(contact.getName()));
//    	Contact newContact = new Contact();
//    	newContact.setGivenName("Adam");
//    	newContact.setFamilyName("Jenkins");
//    	CreateResponse createResponse = contactService.createContact(newContact, token);
//    	String newContactId = createResponse.getId();
//    	newContact.setId(newContactId);
//    	newContact.setGivenName("Jack");
//    	newContact.setFamilyName("Prod");
//    	newContact.setEmailAdress("jack@gmail.com");
//    	contactService.updateContact(newContact, token);
//    	contactService.deleteContact(newContact, token);
//    	ArrayList<Contact> contactsFromRdf = contactService.createFromRdf();
//    	contactsFromRdf.forEach(contact -> LOGGER.info(contact.toString()));
//    	contactService.createRdfContact(newContact);
    	
    }

}