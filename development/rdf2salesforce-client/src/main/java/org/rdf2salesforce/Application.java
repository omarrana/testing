package org.rdf2salesforce;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

	private static Logger LOGGER = LoggerFactory.getLogger(Application.class);
	private static Rdf2SalesforceService service;
	private static String instance;
	private static AccessToken login;

	public static void main(String args[]) {
		ConfigurableApplicationContext ctx = SpringApplication.run(
				Application.class, args);
		service = ctx.getBean(Rdf2SalesforceService.class);
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		do {
			LOGGER.info("---------------------------------------------------------");
			LOGGER.info("1. Perform login");
			LOGGER.info("2. List all contacts in salesforce");
			LOGGER.info("3. Get contact with given Id");
			LOGGER.info("4. Get contact as RDF with given Id");
			LOGGER.info("5. Update contact");
			LOGGER.info("6. Create contact");
			LOGGER.info("7. Delete contact with given Id");
			LOGGER.info("8. Exit");
			LOGGER.info("---------------------------------------------------------");
			try {
				switch (input.nextInt()) {
				case 1:
					login();
					break;
				case 2:
					listAllContacts();
					break;
				case 3:
					getContactWithId(input.next());
					break;
				case 4:
					getContactAsRdf(input.next());
				case 5:
					updateContact(input.nextLine().substring(1));
					break;
				case 6:
					createContact(input.nextLine().substring(1));
					break;
				case 7:
					deleteContactWithId(input.next());
					break;
				case 8:
					exit();
					break;

				default:
					break;
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		} while (true);
	}

	private static void updateContact(String contactAsJson) {
		LOGGER.info("# Updating contact: " + contactAsJson);
		service.updateContact(contactAsJson, login.getAccessToken(), instance);
		LOGGER.info("# Updated...");
		
	}

	private static void createContact(String contactAsJson) {
		LOGGER.info("# Creating contact:" + contactAsJson);
		String contactId = service.createContact(contactAsJson, login.getAccessToken(), instance);
		LOGGER.info("# Created, new contact id: "+ contactId);
		
	}

	private static void deleteContactWithId(String contactId) {
		LOGGER.info("# Deleting Contact with Id:" + contactId);
		service.deleteContact(contactId, login.getAccessToken(), instance);
		LOGGER.info("# Deleted...");
	}

	private static void getContactAsRdf(String contactId) {
		LOGGER.info("# Getting Contact as RDF with Id:" + contactId);
		String contactAsRdf = service.getContactAsRdf(contactId, login.getAccessToken(), instance);
		LOGGER.info("# Contact as RDF: " + contactAsRdf);
	}

	private static void getContactWithId(String contactId) {
		LOGGER.info("# Getting Contact with Id:" + contactId);
		String contact = service.getContact(contactId, login.getAccessToken(),
				instance);
		LOGGER.info("# Contact: " + contact);
	}

	private static void listAllContacts() {
		String allContacts = service.getAll(login.getAccessToken(), instance);
		LOGGER.info("# All contacts: ");
		LOGGER.info(allContacts);

	}

	private static void login() {
		LOGGER.info("# Login...");
		login = service.login();
		LOGGER.info("# Acces token: " + login.getAccessToken());
		instance = login.getInstanceUrl().replace("https://", "").split("\\.")[0];
		LOGGER.info("# Instance: " + instance);
	}

	private static void exit() {
		LOGGER.info("# Exit...");
		System.exit(0);

	}

}