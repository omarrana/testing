package org.rdf2salesforce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {
	
	@Value("${client.id}")
	public String CLIENT_ID;
	@Value("${client.secret}")
	public String CLIENT_SECRET;
	@Value("${client.username}")
	public String USERNAME;
	@Value("${client.password}")
	public String PASSWORD;
	@Value("${login_url}")
	public String LOGIN_URL;
	
	@Value("${contact.query.all}")
	public String CONTACT_QUERY_ALL;
	
	
	

}
