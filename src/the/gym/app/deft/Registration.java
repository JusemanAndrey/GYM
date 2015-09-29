package the.gym.app.deft;

/* This is the class forsign up of the user
 * it has fields of
 * user name 
 * password
 * gym id
 * and 
 * email id
 * 
 * clear button to clear all fields
 * and submit button for user sign up
 * */
import java.net.URLEncoder;

import org.json.JSONException;

import org.json.JSONObject;

import android.app.Activity;

import android.app.ProgressDialog;

import android.os.AsyncTask;

import android.os.Bundle;

import android.provider.Settings.Secure;

import android.view.View;

import android.view.Window;

import android.widget.Button;

import android.widget.EditText;

public class Registration extends Activity {

	String query ;
	JSONObject json;
//	JSONArray contacts = null;
	
	JSONParser parser ;
	
	EditText UserName , Password1 ,  Password2 ,EmailId ,GymMembershipId;
	        
	//GymId ; 
	
	
	
	
	String DeviceId ="";
	
	ProgressDialog dialog ;
	
	ConditionClas conditionClas ;
	
	Button Submit , Cancel , Back;
	
	String ErrorMessage ="" ;
	private String GymId = "114";
	
//	private EditText GymId;
	
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.registationscree);
		
		parser = new JSONParser();
		
		DeviceId = Secure.getString(Registration.this.getContentResolver(),
               
		Secure.ANDROID_ID); 
		
		conditionClas = new ConditionClas();
		
		ConditionClas.isNetworkAvailable(this);
		
		setupView();
		
		OnClickListenerMehtod();
	}
	
	
/* This is the method which holds the initializations 
	 * for the view objects used in this class*/

	private void setupView() {
		// TODO Auto-generated method stub
		
		 UserName = (EditText)findViewById(R.id.usernameeditText);
		 
		 EmailId = (EditText)findViewById(R.id.emailideditText);
		
		 Password1 = (EditText)findViewById(R.id.pwdeditText1);
		
		 Password2 = (EditText)findViewById(R.id.pwdeditText2);
		    
		 GymMembershipId = (EditText)findViewById(R.id.gymid);
  
		  //  GymMembershipId = (EditText)findViewById(R.id.gymmid);    
		    
		   Submit = (Button)findViewById(R.id.submitbutton);
		   
		   Cancel = (Button)findViewById(R.id.cancelbutton);
		   
		   Back = (Button)findViewById(R.id.backbutton);
	}


	/* This class is extended by async class and performs the 
	 * action to submit detials of registraion by parsing to the user*/
	public class RegistrtionParsing extends 
	AsyncTask<String, Void, String>
	{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			
			json = parser.getJSONFromUrl(AllLink.Registration + 
			
					EncodedString());
		
			System.out.println("json is   " +json);
			
			return null;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			dialog = new ProgressDialog(Registration.this);
			
			dialog.setCancelable(false);
			
			dialog.setTitle("Please wait while processing");
			
			dialog.show();
			
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			String abc = "";
			
			if(json != null)
			{
			try {
				
				abc = json.getString("status");
				
				ErrorMessage = json.getString("error");
			}
			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(abc.equalsIgnoreCase("success"))
			{
				
				ConditionClas.Toastmethod(Registration.this ,
						
				"Registration succesfull");
				
				UserName.setText("");
				 
				EmailId.setText("");
				
				Password1.setText("");
				
				Password2.setText("");
				    
			 Registration.this.finish();
		
			}
			
			else {
			
				
				
				ConditionClas.Toastmethod(Registration.this ,
					
						"Error   " +ErrorMessage);
				
				
			}
			}
			
			else {
				ConditionClas.Toastmethod(Registration.this ,
						
						"Registration Failed");
			}
			
			dialog.dismiss();
		}
		
		
	}
	
	
	// this methods returns the encoed string of the arguments
	// to be passed with the link for registration 
	@SuppressWarnings("deprecation")
	private String EncodedString() {
		// TODO Auto-generated method stub
		
		String encoded = "";
		
		
		System.out.println("link for parsing is    " +
		
				AllLink.Registration+

		"{"+"\"username\""+ ":" +
		
"\""+ UserName.getText().toString()+ "\"" +
		
" , " + "\"email\""+ ":"+

"\""+ EmailId.getText().toString()+ "\"" +

" , " + "\"pass\""+ ":" +

"\""+ Password1.getText().toString()+ "\"" +

" , " + "\"deviceid\""+ ":"+

"\""+ DeviceId+ "\""+

" , " + "\"gymid\""+ ":"+

"\""+ GymId + "\""+

" , " + "\"gymmemberid\""+ ":"+

"\""+ GymMembershipId.getText().toString()+ "\""+"}");
		
		encoded = URLEncoder.encode("{"+"\"username\""+ ":" +
				
		"\""+ UserName.getText().toString()+ "\"" +
				
		" , " + "\"email\""+ ":"+

		"\""+ EmailId.getText().toString()+ "\"" +

		" , " + "\"pass\""+ ":" +

		"\""+ Password1.getText().toString()+ "\"" +

		" , " + "\"deviceid\""+ ":"+

		"\""+ DeviceId+ "\""+

		" , " + "\"gymid\""+ ":"+

		"\""+ GymId + "\""+

		" , " + "\"gymmemberid\""+ ":"+

		"\""+ GymMembershipId.getText().toString()+ "\""+"}");
		
		return encoded;
	}   
	
	
	/* This method holds all the button click events in this method
	 */
	private void OnClickListenerMehtod() {
		// TODO Auto-generated method stub
		
		
		Back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				
				Registration.this.finish();
				
			}
		});
		
		
		
		
		Submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				
				if(  EmailId.getText().toString().length()>0
						&& Password1.getText().toString().length()>0
						
						&& Password2.getText().toString().length()>0
						
						&&  UserName.getText().toString().length()>0
						
						&&     GymId.length()>0
						
						&&  GymMembershipId.getText().toString().
						
						length()>0)
						
						{
							
							
							if(Password1.getText().toString().equalsIgnoreCase(
									
									Password2.getText().toString()))
							{
								
								
								if(ConditionClas.isEmailValid(EmailId.getText().toString()))
								{
									
									if(ConditionClas.isNetworkAvailable(Registration.this))
										
									{
								new RegistrtionParsing().execute("");
								
									}
									else {
										
										
										ConditionClas.Toastmethod(Registration.this, 
												
												"There is no internet");
									}
								
								}
								
								else {
								
									ConditionClas.Toastmethod(Registration.this,
										
											"Email id incorrect");
								}
								
							}
							
							else {
								
								ConditionClas.Toastmethod(Registration.this,
										
										"Password did not match");
							}
							
						
						
						
						}
						
				
				else {
							
							ConditionClas.Toastmethod
							
							
							(Registration.this, "Please do not leave any field" +
								
									" empty");
						}
					
				
				
				
				
			}
		});
		
		Cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				 UserName.setText("");
				
				 EmailId.setText("");
			
				 Password1.setText("");
				
				 Password2.setText("");
				    
				 //GymId.setText("");
				 
				 GymMembershipId.setText("");
				 
				 
				
				
			}
		});
	}
	
	
}
