package the.gym.app.deft;


/* this is the main class which navigates to all the 

 * Functionality of the application
 * it navigate to scan class which has the bar code scanner 
 * on click on the button with scan image
 * navigates to 
 * routine class 
 * worklog
 * message class
 * class schedule
 * and change gym user which navigates back to  
 * */

import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HomeScreen extends Activity {

	
	String status = "";
	 String error = "";
	JSONParser jParser;
	private JSONArray messageList;
	JSONObject jsonObjectMsg;
	// contacts JSONArray
	JSONArray contacts = null;
	String query ;
	JSONObject json;
	                           
	ProgressDialog dialog ;
	
	Button NextClass , LoginDialogButton , ToRegistrtionButton,
	FpButton, LogOut , ToScan    ,
	ToCalendar, ToClass;
	 
	Button ToMessageList;
	CustomDialogClass customDialogClass;
	
	
	  TextView Toworkout;
	  String MethodCall = "" ;
	  String emailidFp  = "" ;
	  
	  TextView GymId;
	  
	/* ************************************************************
	 * ************************************************************
	 * ****************** These Methods has been used*******************
	 * ******************  Throughout the application ****************  */ 
	  
		
		/* Methods used from other classes
		 * are 
		 * isNetworkAvailable is a method of customDialogClass 
		 * is used to check the Internet connection 
		 * Its return type is boolean 
		 * 
		 * 
		 * ***********************************************
		 * 
		 * 
		 * Toastmethod is a method of customDialogClass 
		 * is used to show toast 
		 * This method has no return type
		 * 
		 * 
		 * ***********************************************
		 * 
		 * 
		 * getJSONFromUrl is method of JSONParser class
		 * user to parse a link
		 * Its return type is json
		 * 
		 * 
		 * The Jar files used in this application are
		 * 
		 * myjar.jar is the jar file to use the qr code scanner
		 * need to build path with this 
		 * 
		 * 
		 * 
		 * */
	  
	  
	  TextView MessageNumber , Review;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.homescreen);
		
		customDialogClass = new CustomDialogClass(this);

		jParser = new JSONParser();
		
		customDialogClass.ReviewInit(HomeScreen.this);
		 // method contains initialization of widgets used in HomeScreen class 
		 setUpView();
		 
		 // method contains click event of buttons of HomeScreen class 
		allclickListeners();
	   
	    
	    GymId.setText(Html.fromHtml("<b>GYM MEMBERSHIP ID : </b>")+customDialogClass.pref.getString(
	    		AllLink.GymId, ""));
	    
	    final Handler handler = new Handler();
		
		  final Runnable mUpdateResults = new Runnable() {
	            public void run() {
	            	
	            	if(ConditionClas.isNetworkAvailable(HomeScreen.this))
	        		{
	        		new MessageAsynctask().execute("");
	        		}
	        		else {
	        			System.out.println("there is no internet");
	        		}
	            	
	            }
	        };
			

	        int period = 15000; // repeat every 4 sec.

	        Timer timer = new Timer();

	        timer.scheduleAtFixedRate(new TimerTask() {

     
	        
	        public void run() {

	        	handler.post(mUpdateResults);

	        }

	        }, 0, period);
	    		
	}
	
	private void setUpView() {
		// TODO Auto-generated method stub
		

               LogOut = (Button)  findViewById(R.id.logoutbutton);
               ToScan = (Button)  findViewById(R.id.toscanfield);
        ToMessageList = (Button)  findViewById(R.id.tomessgelist);
            Toworkout = (TextView)findViewById(R.id.workout);
           ToCalendar = (Button)  findViewById(R.id.toroutine);
              ToClass = (Button)  findViewById(R.id.toclass);
		        GymId = (TextView)findViewById(R.id.gymid);
		MessageNumber = (TextView)findViewById(R.id.number);
		       Review = (TextView)findViewById(R.id.review);
		
}


	/* Start of method allclickListeners whixh olds all the button clicks 
	 * of this current activity
	 * which is HomeScreen*/

	private void allclickListeners() {
		// TODO Auto-generated method stub

		
		
		customDialogClass.ReviewButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MethodCall = "review";
				if(ConditionClas.isNetworkAvailable(HomeScreen.this))
				{
				new myAsyncTask().execute("");
				}
				else {
					ConditionClas.Toastmethod(HomeScreen.this,
							"There is no internet connection");
				}
			}
		});
		Review.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

//				message" : "Hello every one this is testing mail" , "gymid" : "66"}
			
				customDialogClass.ReviewDialog.show();
				
				
			
				
//				  Intent email = new Intent(Intent.ACTION_SEND);
//					email.putExtra(Intent.EXTRA_EMAIL, 
//							customDialogClass.pref.getString(
//							AllLink.GymEmail,
//							"email-id"));
//					email.putExtra(Intent.EXTRA_EMAIL, 
//							"dcazes@qrFitnessSolutions.com");
//					email.putExtra(Intent.EXTRA_SUBJECT, "subject");
//
//					
//					email.setType("message/rfc822");
//					startActivity(Intent.createChooser(email,
//							"Choose an Email client :"));
			}
		});
	
		/* this button click is to go to the 
		 * class schedule view which is  calendarview class*/
		ToClass.setOnClickListener(new View.OnClickListener() {
			
			private int YY;
			private int MM;
			private int DD;

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 final Calendar calendar = Calendar.getInstance();

					YY = calendar.get(Calendar.YEAR);
					
					MM = calendar.get(Calendar.MONTH);
					
					DD = calendar.get(Calendar.DAY_OF_MONTH);
				
					Intent i = new Intent(HomeScreen.this ,
						
							Calendar_View
					.class);
				i.putExtra("date",  YY+"-"
				
						+MM+"-"
					
						+DD);
				
				startActivity(i);
			}
		});
		
		
		/* this button click is to call the parsing action
		 * of the logout by calling myasynctask class */

		LogOut.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
//				customDialogClass.log.show();
				
				MethodCall = "logout";
				
				if(ConditionClas.isNetworkAvailable(HomeScreen.this))
				{
				new myAsyncTask().execute("");
				}
				else {
					Toast.makeText(HomeScreen.this, "There is no internet",
							Toast.LENGTH_SHORT).show();
				}
				
			}
		});



		/*  This button click takes the user to the message list class
		 * which has all messages received by the user */
		ToMessageList.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Intent i = new Intent(HomeScreen.this , 
					
						MessageListclass.class);
				
				startActivity(i);
			}
		});
		
		
		/* this button click takes the user to the 
		 * routine class */

		ToCalendar.setOnClickListener(new View.OnClickListener() {
			
//			private int YY;
//			private int MM;
//			private int DD;

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				 final Calendar calendar = Calendar.getInstance();


				Intent i = new Intent(HomeScreen.this ,
					Routine_Class
					.class);
//				

				startActivity(i);
			}
		});
		
		
		
		/* This button will call the method of zxing library 
		 * for qr code scanning and shall open camera to to start scanning
		 * once data is found it shall navigate to the next class
		 * that is log view */
		
	ToScan.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
//				customDialogClass.log.show();
				
				Intent intent = new Intent("com.google.zxing.client.android.SCAN");
				
				intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
				
				startActivityForResult(intent, 0);
//				
//				Intent i = new	Intent(HomeScreen.this,
//						LogviewWithheader.class);
//
//						i.putExtra("idval", "17");
//						 i.putExtra("name", "biscpes");
//						startActivity(i);
//				
			}
		});
		
		
		
	/* This button click will take the user to the workoutlog log class 
	 * */
	Toworkout.setOnClickListener(new View.OnClickListener() {
		
		private int YY;
		private int MM;
		private int DD;
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		
//			customDialogClass.log.show();
			final Calendar calendar = Calendar.getInstance();
			 
			 YY = calendar.get(Calendar.YEAR);
			
			 MM = calendar.get(Calendar.MONTH);
			
			 DD = calendar.get(Calendar.DAY_OF_MONTH);
			
			             
			Intent intent = new Intent(HomeScreen.this , Workoutlog.class);
			
			intent.putExtra("date",  YY+"-"
					
					+MM+"-"
						
					+DD);
			
			startActivity(intent);
			
		}
	});
	

	}
	
	/* This on activity for result method is to start the qr code scanner class*/
	
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		   if (requestCode == 0) {
		      if (resultCode == RESULT_OK) {
		         String contents = intent.getStringExtra("SCAN_RESULT");
//		         String format   = intent.getStringExtra("SCAN_RESULT_FORMAT");
		         System.out.println("content     " +contents);
		        
		         parsejson(contents);

				
		         
		         // Handle successful sc
		      } 
		      else if (resultCode == RESULT_CANCELED) {
		         // Handle cancel
		      }
		   }
		}
	
	
	/* on the scanning is done , the content received from
	 * from the qr code is parsed and then send to
	 * the next class logview */
	private void parsejson(String contents) {
		// TODO Auto-generated method stub
	
		try {  
			System.out.println("content     " +contents);
			json = new JSONObject(contents);
			 System.out.println("contents         " +contents);
			System.out.println("json id is  " +json.getString("id"));
			
			String excerciseId = json.getString("id");
			       String name = json.getString("name");
			
			System.out.println("json id is  " +excerciseId);
			if(excerciseId.length()>0)
			{
			 
				Intent i = new	Intent(HomeScreen.this,
			
						LogviewWithheader.class);

			  i.putExtra("idval", excerciseId);
			  i.putExtra("name", name);

			   startActivity(i);
			
			}
			
			else 
			{
				System.out.println("no such excercise");
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
//			Toast.makeText(HomeScreen.this, "This QR code is not for this application", 
//					Toast.LENGTH_LONG).show();
			showAlert();
		}
		
	}
	
	
	/* this method is to hit the link of of logout
	 */
	@SuppressWarnings("deprecation")
	private void logOut() {
		// TODO Auto-generated method stub

		
		String encode = "";
		
		// this is encoding of the argument part of a link 
		// else the method will trow error
		encode = URLEncoder.encode("{"+"\"userid\""+ ":" 
		
				+"\""+customDialogClass.pref.getString(AllLink.UserId, 
				"")+"\""+","+
				"\"auth\""+ ":"+
				"\""+customDialogClass.pref.getString(AllLink.AuthKey, 
						"")+"\""+"}");
		
	
		
	
		System.out.println("link hit is              " +
				AllLink.LogOut+"{"+"\"userid\""+ ":" 
				+"\""+customDialogClass.pref.getString(AllLink.UserId, 
						"")+"\""+","+
						"\"auth\""+ ":"+
						"\""+customDialogClass.pref.getString(AllLink.AuthKey, 
								"")+"\""+"}");
				
				
				json = jParser.getJSONFromUrl(AllLink.LogOut +
						encode);

	}
	
	/* class to perform the network task on ther than main thread*/
	class myAsyncTask extends AsyncTask<String, Void, String>
	{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			

			// getting JSON string from URL
			
//			System.out.println("AllLink.Link2   " +AllLink.Link4+EncodingUtils.);
			
			
			if(MethodCall.equalsIgnoreCase("logout"))
			{
			
				logOut();
			
			}
			
			else if(MethodCall.equalsIgnoreCase("review")) {
				
//				AllLink.feedBack
				
				String encode = "";
				
				// this is encoding of the argument part of a link 
				// else the method will trow error
				encode = URLEncoder.encode("{"+"\"message\""+ ":" 
						+"\""+customDialogClass.ReviewText.getText().toString()+"\""+","+
						"\"gymid\""+ ":"+
						"\""+customDialogClass.pref.getString(AllLink.UserId, 
								"")+"\""+"}");
				
				System.out.println("link hit is              " +
						AllLink.feedBack+"{"+"\"message\""+ ":" 
						+"\""+customDialogClass.ReviewText.getText().toString()+"\""+","+
								"\"gymid\""+ ":"+
								"\""+customDialogClass.pref.getString(AllLink.UserId, 
										"")+"\""+"}");
						
						
						json = jParser.getJSONFromUrl(AllLink.feedBack +
								encode);
			}
		
	
			return null;
		}
		
		

		



		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			dialog = new ProgressDialog(HomeScreen.this);
			
			dialog.show();
			
			dialog.setCancelable(false);
			
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			dialog.dismiss();
			
			if(MethodCall.equalsIgnoreCase("logout"))
			{
				
				
				try
				{
					 status = json.getString("status");
					 
					 error = json.getString("error");
					  
					
					
					 
//					  Toast.makeText(HomeScreen.this,
//							  "Status is       " +
//							  " " +status, Toast.LENGTH_LONG).show();
				} 
				catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (status.equalsIgnoreCase("success")) 
				{
					
//					Toast.makeText(HomeScreen.this, 
//							"Logged Out",
//							Toast.LENGTH_LONG).show();
					
					
					
					ConditionClas.Toastmethod(HomeScreen.this, 
							"Logged Out");
					
					 customDialogClass.edit.putString(AllLink.UserId, "");
					 customDialogClass.edit.putString(AllLink.AuthKey, "");
					 customDialogClass.edit.putString(AllLink.Username, "");
					 customDialogClass.edit.putString(AllLink.GymMid, "");
					 customDialogClass.edit.putString(AllLink.GymId, "");
					 
					 
					  customDialogClass.edit.commit();
					  
					  Intent i = new Intent(HomeScreen.this ,
							  LogInClass.class);
					  finish();
					  i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					  startActivity(i);
				}
				
				else
				{
					
//					Toast.makeText(HomeScreen.this, 
//							"Error   " +error,
//							Toast.LENGTH_LONG).show();
					
					
					ConditionClas.Toastmethod(HomeScreen.this, 
							"Error   " +error);
					
					
				}
			}
			
			else if(MethodCall.equalsIgnoreCase("review")) {
				
				System.out.println("json  " +json);
				
				
				if(json!=null)
				{
					    
					 try {
						status = json.getString("status");
						
						if(status.equalsIgnoreCase("success"))
						{
							
							Toast.makeText(HomeScreen.this, json.getString("msg")	,
									Toast.LENGTH_LONG).show();
						
						}
						else if(status.equalsIgnoreCase("error"))
						{
						
							Toast.makeText(HomeScreen.this, json.getString("error"),
									Toast.LENGTH_LONG).show();
						
						}
					} 
					 
					 catch (JSONException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 customDialogClass.ReviewText.setText("");
						customDialogClass.ReviewDialog.dismiss();;
				}
				else {
					
					Toast.makeText(HomeScreen.this, "Message seding failed",
							Toast.LENGTH_LONG).show();
				}
				
				
			}
			
			
		
			
			
			
		}
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
//		super.onBackPressed();
		
		
		MethodCall = "logout";
		
		if(ConditionClas.isNetworkAvailable(HomeScreen.this))
		{
		new myAsyncTask().execute("");
		}
		else {
			Toast.makeText(HomeScreen.this, "There is no internet",
					Toast.LENGTH_SHORT).show();
		}
	}
	
	class MessageAsynctask extends AsyncTask<String, Void , String>
	{

		

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			
		
				
			
			System.out.println("the link to hit is           "
				
					
					
					+  AllLink.getMessage 
				
					+  "{"+"\"userid\""+ ":" 
				
					+  "\""+customDialogClass.pref.getString(AllLink.UserId,"")
	 				
					+"\""+","+"\"auth\""+ ":"+"\""
					
					+customDialogClass.pref.getString(AllLink.AuthKey,"")
					
					+"\""+"}");
			
			
			SiteList.Msg_Description1.clear();
			    SiteList.Msg_Subject1.clear();
			         SiteList.Msg_Id1.clear();
			
			@SuppressWarnings("deprecation")
			
			String  encoded = URLEncoder.encode("{"+"\"userid\""+ ":" 
					
					+"\""+customDialogClass.pref.getString(AllLink.UserId, 
							
							"")+"\""+","+
							
							"\"auth\""+ ":"+
							
							"\""+customDialogClass.pref.getString(AllLink.AuthKey, 
									"")+"\""+"}") ;
			
			
		
			jsonObjectMsg = jParser.getJSONFromUrl(AllLink.getMessage 
					
					+encoded);
			
			return null;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			
			super.onPreExecute();
			
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			
			super.onPostExecute(result);
			
		
			
			if(jsonObjectMsg != null)
			{
		
				try {
				
				
			
				String Status = jsonObjectMsg.getString("status");
				
			
				
//				System.out.println("messageList   " +jsonObjectMsg.length());
//		
//				System.out.println("jsonObject.getString        " +
//					
//
//
// 
//                 jsonObjectMsg.getString("msg"));	

				
				
				
				
//			    ConditionClas.Toastmethod(MessageListclass.this,
//			    		"the status is   " +
//								" " +Status);

				if(jsonObjectMsg.getString("msg").equalsIgnoreCase("No message found."))
				
				{
					
//					messageList = jsonObject.getJSONArray("messages");
					System.out.println("inside if  ");
					
					SiteList.Msg_Description1.clear();
					SiteList.Msg_Description1.add("There are no messages");
					
				}
				
				else if(jsonObjectMsg.getString("msg").equalsIgnoreCase("message found.")) {
					
					System.out.println("inside if  ");
					
					messageList = jsonObjectMsg.getJSONArray("messages");
					
				
					
					System.out.println("inside else  " + messageList.length());
					
			
					
					for (int i = 0; i < messageList.length(); i++) {
						
					
						
						JSONObject c = messageList.getJSONObject(i);
						
						SiteList.Msg_Subject1.add(c.getString("subject"));
						
						
					
					}
					
					
					String my = String.valueOf(SiteList.Msg_Subject1.size());
		
					
					if(SiteList.Msg_Subject1.size()>
					customDialogClass.pref.getInt(AllLink.MesgCount, 
							0))
					{
						
						 my = String.valueOf((SiteList.Msg_Subject1.size()-
								 customDialogClass.pref.getInt(AllLink.MesgCount, 
											0)));
						MessageNumber.setVisibility(View.VISIBLE);
					    MessageNumber.setText(" "+my+" ");
					
					}
					else
					{
						
						MessageNumber.setVisibility(View.GONE);
					
					}
					
					}
				
				
			} catch (JSONException e) {
		 		// TODO Auto-generated catch block
				
				e.printStackTrace();
			}
			
		}
		
		
			else {
				
				System.out.println("no jo");
				
			
			}
			
//			dialog.dismiss();
		
		}
	
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(ConditionClas.isNetworkAvailable(HomeScreen.this))
		{
		new MessageAsynctask().execute("");
		}
		else {
			System.out.println("there is no internet");
		}
	}
	
	public void showAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				HomeScreen.this );

		// Setting Dialog Title
//		alertDialog.setTitle("Viva Fitness");

		// Setting Dialog Message
		alertDialog.setMessage("This QR code is not for this application");

		// Setting Icon to Dialog

		// Setting Positive "Yes" Button
		alertDialog.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						// Write your code here to invoke YES event
						// Toast.makeText(getApplicationContext(),
						// "You clicked on YES", Toast.LENGTH_SHORT).show();
						dialog.dismiss();
					}
				});

		alertDialog.show();
//		alertDialog.
		// Setting Negative "NO" Button
	}
}
