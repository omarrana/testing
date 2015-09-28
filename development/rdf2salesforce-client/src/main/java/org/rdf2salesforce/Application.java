package org.rdf2salesforce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {



	public static void main(String args[]) {
		ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
		Rdf2SalesforceService service = ctx.getBean(Rdf2SalesforceService.class);
		AccessToken login = service.login();
		System.out.println(login.getAccessToken());
	}

	

}