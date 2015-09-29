package the.gym.app.deft;

/* This projects uses only json parsing
 * and this class contains the method to parse json content
 * ad is called by every other class which wants json data 
 * it has a method getJSONFromUrl 
 * Which returns a json object to calling objects
 * */
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;

import org.apache.http.HttpEntity;

import org.apache.http.HttpResponse;

import org.apache.http.client.ClientProtocolException;

import org.apache.http.client.methods.HttpPost;

import org.apache.http.impl.client.DefaultHttpClient;

import org.json.JSONException;

import org.json.JSONObject;

import android.util.Log;

public class JSONParser {

	static InputStream is = null;
	
	static JSONObject jObj = null;
	
	static String json = "";

	// constructor
	public JSONParser() {

	
	}              
	
	

	public JSONObject getJSONFromUrl(String url) {

		// Making HTTP request
		try {
			// defaultHttpClient
			
			DefaultHttpClient httpClient = new DefaultHttpClient();
			
			System.out.println("url is   " +url);
			
			String abc = URLEncoder.encode(url,"UTF-8");
		
			
			
			
			HttpPost httpPost = new HttpPost(url) ;
//			httpPost.setHeader("host", "http://www.qrfitnesssolutions.com");
			
			HttpResponse httpResponse = httpClient.execute(httpPost);
			
			HttpEntity httpEntity = httpResponse.getEntity();
			
			is = httpEntity.getContent();			

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			
			
		
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
		
			StringBuilder sb = new StringBuilder();
			
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			
			is.close();
			
			
//			System.out.println("sbsbsb     " +sb);
			
			
			json = sb.toString();
			
			System.out.println("");
		} 
		catch (Exception e) 
		{
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try 
		{
			jObj = new JSONObject(json);
		} 
		catch (JSONException e) 
		{
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		
//		System.out.println("jObj     " +jObj);
		
		return jObj;

	}
}
