package org.rdf2salesforce;

import org.json.JSONException;
import org.json.JSONObject;

public class OAuth2Response {
	String id;
	String issued_at;
	String instance_url;
	String signature;
	String access_token;

	public OAuth2Response() {
	}
	public OAuth2Response(JSONObject json) {
		try {
			id =json.getString("id");
			issued_at = json.getString("issued_at");
			instance_url = json.getString("instance_url");
			signature = json.getString("signature");
			access_token = json.getString("access_token");

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
