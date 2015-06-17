package org.rdf2salesforce;

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
    }

}