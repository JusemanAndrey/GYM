package the.gym.app.deft;


/* This is the first class called after splash screen 
 * has got options to 
 * sign in
 * forget password
 * and sign up
 * if login successful it navigates to home screen
 * if not shows ans error message
 * if forgetpassword text is clicked 
 * it popsup a forget password dialog
 * and click of signin utton it navigates to
 * registration class
 * */
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
 
public class LogInClass extends Activity {

	Button LoginButton , CancelButton  , SignUpButton ;
	
	TextView ForgetpasswordButton ;
	
	EditText UserName , Password , GymId ;
	
	private CustomDialogClass customDialogClass;
	
	// this is a string type of object to recognize 
   //  which link to hit and consequently which action to hit 
	
	private String MethodCall = "";
	
	String checksString = "" ;
	
	JSONParser jsonParser ;
	
	JSONArray  array ;
	
	JSONObject jsonObject ;
	          
	ProgressDialog dialogis ; 
	
	private String emailidFp;
	
	
	/* Methods used from other classes
	 * are 
	 * isNetworkAvailable is a method of customDialogClass 
	 * is used to check the Internet connection 
	 * Its return type is boolean 
	 * 
	 * Toastmethod is a method of customDialogClass 
	 * is used to show toast 
	 * This method has no return type
	 * 
	 * 
	 * getJSONFromUrl is method of JSONParser class
	 * user to parse a link
	 * Its return type is json
	 * */
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		String languageToLoad  = "de";
		Locale locale = new Locale(languageToLoad);
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		getBaseContext().getResources().updateConfiguration(config, null);
		setContentView(R.layout.loginscreen);
		final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		final List pkgAppsList = this.getPackageManager().queryIntentActivities( mainIntent, 0);
		
		
		System.out.println("pkgAppsList      " +pkgAppsList);
		customDialogClass = new CustomDialogClass(this);
		  
		jsonParser = new JSONParser();
		
		       
		       customDialogClass.forgetPasswordInit(this);
		
		
		
		/* this method is called to initialize all the widgets used in this 
		 * login activity*/
		       
		setupView();
		
		
		/* This method holds the button click events of this class */
		All_OnClickListener();
	}

	
	// this method holds the initialization for the view objects  
	// log in class 
	private void setupView() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		
		LoginButton  =  (Button) findViewById(R.id.login);
		
		 CancelButton =  (Button) findViewById(R.id.cancel);
		
		 SignUpButton =  (Button) findViewById(R.id.signup);
		
		
		 ForgetpasswordButton =  (TextView) findViewById(R.id.forgetpassword);
	
		 UserName =  (EditText) findViewById(R.id.username);
		 
		 Password =  (EditText) findViewById(R.id.password);
		   
		 GymId =  (EditText) findViewById(R.id.gidput);
	
	}
	
	/* this methd holds click events of all buttons used in Log in class
	 * 
	 * 
	 */
	
	private void All_OnClickListener() {
		// TODO Auto-generated method stub
	
		

		/* This is the button click of the dialog forget password
		 * if user has filled the email id
		 * it shall parse the link of forget password with
		 * that email id */
		customDialogClass.SbmitFp.setOnClickListener(new View.OnClickListener() {
			
			

		

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				MethodCall = "fp";
				
				
				 emailidFp = customDialogClass.emailIdFp.getText().toString();
				 
				 
				 if(ConditionClas.isNetworkAvailable(LogInClass.this))
					{
					 if(emailidFp.length()>0)
						{
						 if (ConditionClas.isEmailValid(emailidFp)) {
							
							 new LoginAsyncTask().execute("");
						}
				
						 else {
							
							 ConditionClas.Toastmethod(LogInClass.this, 
										"Wrong email id");
							 
							 
						}
						}
				else {
					ConditionClas.Toastmethod(LogInClass.this, 
							
							"Please do not leave any field empty");
					}
						
					}
					else 
					{
						ConditionClas.Toastmethod(LogInClass.this, 
								
								"There is no internet");
					}
			}
		});

		
		/* This is the click of textview to open the forget password dialog */

		ForgetpasswordButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				customDialogClass.ForgetPassword.show();
			}
		});


		/* This button click shall take the user to registration class
		 * to register as a new user*/
	
		SignUpButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Intent reg = new Intent(LogInClass.this ,
						Registration.class);
				
				startActivity(reg);
			}
		});
	
	
		/* This button click of the user is to perform the action of 
		 * parsing for log in task and shall check if user has filled 
		 * the user name and password*/

		LoginButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		
		
				
//				Toast.makeText(LogInClass.this, "The credentials user hit are  "
//						+" uername =  " +UserName.getText().toString() 
//						+" password="  +
//								" " +Password.getText().toString(), 
//								Toast.LENGTH_SHORT).show();
//				
				MethodCall = "login";
				
				if(ConditionClas.isNetworkAvailable(LogInClass.this))
				{
					if(UserName.getText().toString().length()>0 
					&& Password.getText().toString().length()>0
					&& GymId.getText().toString().length()>0
					)
					{
						
				new LoginAsyncTask().execute("");
					
					}
					
					else 
					{
						ConditionClas.Toastmethod(LogInClass.this, 
								"Please do not leave any field empty");
					}
				}
				else 
				{
					ConditionClas.Toastmethod(LogInClass.this, 
							"There is no internet");
				}
				
			}
		});


		/* This button click is to simply clear the
		 * edit text fields used for log in 
		 * in case user feels te entries are wrong */
		
		
		CancelButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				UserName.setText("");
				
				GymId.setText("");
				
				Password.setText("");
				
				
			}
		});

		
		/* This is the*/

//		ForgetpasswordButton.setOnClickListener(new 
//				View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//			
//				customDialogClass.ForgetPassword.show();
//			}
//		});
		
		
		/* This button click is to simply dismiss the 
		 * forget password dialog */
		
		
		customDialogClass.BackFp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				customDialogClass.ForgetPassword.dismiss();
			}
		});
	}
	
	
	/* This LoginAsyncTask class holds all the methods and method call
	 * which we need to get the data from server
	 * */
	
	
	class LoginAsyncTask extends AsyncTask<String, Void, String>
	{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			

			if(MethodCall.equalsIgnoreCase("fp"))
			{
			
				fogetPasswordParsing();

			}
	        else if(MethodCall.equals("login"))
			{
			 
			try {
				   
				String nme = UserName.getText().toString();
				String pwd = Password.getText().toString();
				
				String userName = "\""+ nme + "\""; 
				String userPwd  = "\""+ pwd + "\""; 
				
				System.out.println("Log in is   is               " +
						AllLink.CheckLogin+"{"+"\"username\""+":" + 
						userName + "," + 
						"\"pass\"" +":"+userPwd
						+"\"gymmemberid\""+":"
						+"\""+GymId.getText().toString()+"\""+"}"
									);
				    
				   
		/* Encoding the string to hit the link of log in 
		 * without encoding the link throws error */
				
//			String query = URLEncoder.encode("{"+"\"username\""+":" + 
//			userName + "," + 
//			"\"pass\"" +":"+userPwd
//			+"\"gymmemberid\""+":"
//			+"\""+GymId.getText().toString()+"\""+"}", 
//						"utf-8");
			
			String query = 	URLEncoder.encode("{"+"\"username\""+":" + 
					userName + "," + 
					"\"pass\"" +":"+userPwd+ ","
					+"\"gymmemberid\""+":"
					+"\""+GymId.getText().toString()+"\""+"}" ,"utf-8");
			
			
			
			String url = AllLink.CheckLogin + query;
				
			jsonObject = jsonParser.getJSONFromUrl(url);
		
			
			System.out.println("jsonObject             " +jsonObject);
			}
			
			catch (UnsupportedEncodingException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
	 
			return null;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			dialogis = new ProgressDialog(LogInClass.this);
			dialogis.setMessage("Please wait while processing");
			dialogis.setCancelable(false);
			dialogis.show();
			
		}
		 
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			dialogis.dismiss();

			if(MethodCall.equals("fp"))
			{
				String status = "";
				 String error = "";
				
				try 
				{
					 
					status = jsonObject.getString("status");
					  
					 error = jsonObject.getString("error");
					 
				}
				catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (status.equalsIgnoreCase("success")) 
				{
					
//					Toast.makeText(LogInClass.this, 
//							"Password Changed",
//							Toast.LENGTH_LONG).show();
					
					ConditionClas.Toastmethod(LogInClass.this, 
							"Password Changed");
				}
				
				else 
				{
//					Toast.makeText(LogInClass.this, 
//							"Error   " +error,
//							Toast.LENGTH_LONG).show();
					
					ConditionClas.Toastmethod(LogInClass.this, 
							"Error   " +error);
				}
				
			}
			
			else if(MethodCall.equals("login"))
			{
			
			try {
				// Getting Array of Contacts
//				System.out.println("contacts             " +json.getJSONArray(TAG_CONTACTS).length());
//				contacts = json.getJSONArray(TAG_CONTACTS);status
				
				if(jsonObject != null)
				{
				 checksString = jsonObject.getString("status");
				 
				 if(checksString.equalsIgnoreCase("success"))
					{
						
						
					System.out.println("authkey is               " +
					jsonObject.getString("auth"));

//					Toast.makeText(LogInClass.this,
//								"Log in successfull", 
//								Toast.LENGTH_LONG).show();
					
					
					ConditionClas.Toastmethod(LogInClass.this,
								"Log in successfull");
						
						
						customDialogClass.edit.putString(AllLink.Username, 
						UserName.getText().toString());
						
						customDialogClass.edit.putString(AllLink.UserId, 
								jsonObject.getString("gymid"));
						
						customDialogClass.edit.putString(AllLink.AuthKey, 
								jsonObject.getString("auth"));
						
//						customDialogClass.edit.putString(AllLink.GymMid, 
//								jsonObject.getString("gymmeberid"));
						
						customDialogClass.edit.putString(AllLink.GymId, 
								jsonObject.getString("gymmemberid"));
						
						customDialogClass.edit.putString(AllLink.GymEmail, 
								jsonObject.getString("owneremail"));
System.out.println("the value is    "+
		jsonObject.getString("gymid"));
						
						customDialogClass.edit.commit();
						
			System.out.println("name1 " +
			customDialogClass.pref.getString(AllLink.GymId, ""));
			
			System.out.println("name2 " +
			customDialogClass.pref.getString(AllLink.UserId, ""));
			
//			System.out.println("name3 " +
//			customDialogClass.pref.getString(AllLink.GymId, ""));
					
						Intent i = new Intent(LogInClass.this ,
								HomeScreen.class);
						finish();
						startActivity(i);
					}
					
					else {
//						ConditionClas.Toastmethod(LogInClass.this, 
//								jsonObject.getString("msg"));
						
						/*ConditionClas.Toastmethod(LogInClass.this,
								"Log in failed " +
										jsonObject.getString("msg"));*/
						
						Toast.makeText(getApplicationContext(), "Username not found", Toast.LENGTH_LONG)
						.show();
						
					}
				}
				
				
				
			} 
			catch (JSONException e)
			{
				e.printStackTrace();
			}
			
			}
		}
	}
	
	/* method to call the parsing method*/
	private void fogetPasswordParsing() {
		// TODO Auto-generated method stub
		
		
		System.out.println("link hit is              " +
		AllLink.ForgetPassword+"{"+"\"email\""+ ":" +"\""+emailidFp+"\""+"}");
		
		
		jsonObject = jsonParser.getJSONFromUrl(AllLink.ForgetPassword +encodeFpLink());

	}
	            
	/* this is the method to encode the link before htting for 
	 * forget password*/
	@SuppressWarnings("deprecation")
	String encodeFpLink()
	{
	
		String encoded = "";
		
		encoded = URLEncoder.encode("{"+"\"email\""+ ":" +"\""+emailidFp+"\""+"}");
		
		return encoded;
		
	}
}
