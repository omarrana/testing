package org.rdf2salesforce;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class Rest {
	
	private static final String userName = "omarrana@salesforce.com";
	private static final String password = "kiran123QUrkrSGHulSs6sVWltIvS1LaG"; 
	private static String OAUTH_ENDPOINT = "/services/oauth2/token";
	private static String REST_ENDPOINT = "/services/data";

	Header prettyPrintHeader = new BasicHeader("X-PrettyPrint", "1");
	
    String loginInstanceDomain = "eu5.salesforce.com";
    String apiVersion = "34"; 
    String consumerKey = "3MVG9Rd3qC6oMalUWRNxc3ElCYV7udOJuTtH6uTqLlxUNI3d89vn2xYaiGMakS2GU6BPyE9VclN86yyQEPNUd"; // COPY YOUR CONSUMER KEY		
    String consumerSecret = "7316524453111316092"; 
    String grantType = "password";
	private String baseUri;
	private Header oauthHeader;


    public HttpResponse oauth2Login() {
		System.out.println("_______________ Login _______________");
		OAuth2Response oauth2Response = null;
		HttpResponse response = null;
		String loginHostUri = "https://" + loginInstanceDomain + OAUTH_ENDPOINT;
		
		try {
			
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(loginHostUri);
			StringBuffer requestBodyText = new StringBuffer("grant_type=password");
			requestBodyText.append("&username=");
			requestBodyText.append(userName);
			requestBodyText.append("&password=");
			requestBodyText.append(password);
			requestBodyText.append("&client_id=");
			requestBodyText.append(consumerKey);
			requestBodyText.append("&client_secret=");
			requestBodyText.append(consumerSecret);
			System.out.println("Sequence: "+requestBodyText.toString());
			StringEntity requestBody = new StringEntity(requestBodyText.toString());
			requestBody.setContentType("application/x-www-form-urlencoded");
			httpPost.setEntity(requestBody);
			httpPost.addHeader(prettyPrintHeader);
			
			//Make the request and store the result
			response = httpClient.execute(httpPost);
			
			//Parse the result if we were able to connect. 
			if (  response.getStatusLine().getStatusCode() == 200 ) {
				String response_string = EntityUtils.toString(response.getEntity());
				try {
					JSONObject json = new JSONObject(response_string);
					oauth2Response = new OAuth2Response(json);
					System.out.println("JSON returned by response: +\n" + json.toString(1));
				} catch (JSONException je) {
					je.printStackTrace();
				}  
				baseUri = oauth2Response.instance_url + REST_ENDPOINT + "/v" + apiVersion +".0";
				oauthHeader = new BasicHeader("Authorization", "OAuth " + oauth2Response.access_token);
				System.out.println("\nSuccessfully logged in to instance: " + baseUri);
			} else {
				System.out.println("An error has occured. Http status: " + response.getStatusLine().getStatusCode());
				System.exit(-1);
			}
		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (NullPointerException npe) {
			npe.printStackTrace();
		}
		return response;
	}

	
    
public static void main(String arg[]){
	new Rest().oauth2Login();
	
}	
	
	

}
