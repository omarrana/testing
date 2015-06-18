package org.rdf2salesforce;

import org.rdf2salesforce.services.LoginService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;



@SpringBootApplication
public class Application {

    public static void main(String args[]){
    	ApplicationContext ctx = SpringApplication.run(Application.class, args);
    	LoginService loginService = ctx.getBean(LoginService.class);
    	AccessToken token = loginService.getToken().getBody();
    	System.out.println(token);
    	RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(token.getInstanceUrl()+ "/services/data/v34.0/sobjects/Contact/describe");
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", token.getTokenType()+ " " + token.getAccessToken());
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<String> exchange = restTemplate.exchange(builder
				.build().encode().toUri(), HttpMethod.GET, entity,
				String.class);
    	System.out.println(exchange.getBody());
    }

}