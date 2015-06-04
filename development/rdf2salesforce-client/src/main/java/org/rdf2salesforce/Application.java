package org.rdf2salesforce;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;


public class Application {

    public static void main(String args[]) throws IOException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        ClientHttpRequest request = restTemplate.getRequestFactory().createRequest(new URI("http://na1.salesforce.com/services/data/"), HttpMethod.GET);
        ClientHttpResponse response = request.execute();
    }

}