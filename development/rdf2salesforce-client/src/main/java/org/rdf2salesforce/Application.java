package org.rdf2salesforce;

import java.util.List;

import org.rdf2salesforce.model.Contact;
import org.rdf2salesforce.model.CreateResponse;
import org.rdf2salesforce.services.ContactService;
import org.rdf2salesforce.services.LoginService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;



@SpringBootApplication
public class Application {

    public static void main(String args[]){
    	ApplicationContext ctx = SpringApplication.run(Application.class, args);
    	LoginService loginService = ctx.getBean(LoginService.class);
    	AccessToken token = loginService.getToken().getBody();
    	System.out.println(token);
    	ContactService contactService = ctx.getBean(ContactService.class);
    	List<Contact> allContacts = contactService.getAll(token);
    	allContacts.forEach(contact -> System.out.println(contact.getName()));
    	Contact newContact = new Contact();
    	newContact.setFirstName("Adam");
    	newContact.setLastName("Jenkins");
    	CreateResponse createResponse = contactService.createContact(newContact, token);
    	String newContactId = createResponse.getId();
    	newContact.setId(newContactId);
    	contactService.deleteContact(newContact, token);
    }

}