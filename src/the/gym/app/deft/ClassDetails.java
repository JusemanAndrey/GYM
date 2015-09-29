package the.gym.app.deft;


/*This activity is called from classlist and it displays the
 * content of a selected class and has a backward navigation 
 * to classlist activity*/



import java.net.URLEncoder;



import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class ClassDetails extends Activity{

	

	 
	TextView ClassName , ClassDate , ClassDesc ;
	String className , classDate , classDesc , classId;
	Button HomeButton , JoinClass,BackButton;
	
	
	JSONParser parser ;
	JSONObject jsonObject ;
	
	CustomDialogClass customDialogClass ;
	
	ProgressDialog dialog ;
	
	boolean join  ;
	
	String status = "" ;
	String msg    = "" ;
	private TextView GymId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.classcreen);
		
		
		customDialogClass = new CustomDialogClass(this);
		
		parser = new JSONParser();
		
		className = getIntent().getStringExtra("name");
		
		classDesc = getIntent().getStringExtra("desc");
		
		classDate = getIntent().getStringExtra("date");
		
		  classId = getIntent().getStringExtra("id");
		
		join = getIntent().getBooleanExtra("join", false);
		
		
		setupView();
		
		
		ClassName.setText(className);
		
		ClassDesc.setText(classDesc);
		
		ClassDate.setText(classDate);
		
		
		
		
		BackButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
		
			ClassDetails.this.finish();
			
			
			}
		});
		
		
		
		
		
		
		
		
		
		/* this is the button click to navigate back to the home screen */
		HomeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Intent i = new Intent(ClassDetails.this ,
						HomeScreen.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				finish();

				
				startActivity(i);
			}
		});
		
		
		
		/* This button click of the class is to hit the 
		 * link to join class and perform the parsing for tis arguments */

		
		JoinClass.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				new JOinClass().execute("");
			}
		});
		
		
	}
	
	
	/* this method of this class holds the initializations 
	 * of the wdigets used in this  */
	
	private void setupView() {
		// TODO Auto-generated method stub
		
		
		 ClassName = (TextView)findViewById(R.id.classname);
		
		 ClassDate = (TextView)findViewById(R.id.classtime);
		 
		 ClassDesc = (TextView)findViewById(R.id.classdesc);
		     
		 GymId = (TextView)findViewById(R.id.gymid);
		     
		     
	    HomeButton = (Button)  findViewById(R.id.home);
	    
	    BackButton=(Button)findViewById(R.id.backbutton);
	    
	    JoinClass = (Button)  findViewById(R.id.joinclass);
	         
	     GymId.setText(Html.fromHtml("<b>GYM MEMBERSHIP ID : </b>") +customDialogClass.pref.getString(
		    		AllLink.GymId, ""));
	     
	     System.out.println("join    " +join);
	     if(join ==  false )
	     {
	    	 JoinClass.setVisibility(View.GONE);
	     }
	}


	class JOinClass extends AsyncTask<String, Void, String>
	{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			
			@SuppressWarnings("deprecation")
			String encode = URLEncoder.encode("{"+"\"clsid\""+ ":" 
					+"\""+classId+"\""+","+
							"\"userid\""+ ":"+
							"\""+customDialogClass.pref.getString(AllLink.UserId, 
									"")+"\""+","+
									
									"\"auth\""+ ":"+
									
									"\""+customDialogClass.pref.getString(AllLink.AuthKey, 
											"")+"\""+"}");
			
	   
			System.out.println("link to hit  " +
					AllLink.joinClass+"{"+"\"clsid\""+ ":" 
					+"\""+classId+"\""+","+
					"\"userid\""+ ":"+
					"\""+customDialogClass.pref.getString(AllLink.UserId, 
							"")+"\""+","+
							
							"\"auth\""+ ":"+
							
							"\""+customDialogClass.pref.getString(AllLink.AuthKey, 
									"")+"\""+"}");
			
			jsonObject = parser.getJSONFromUrl(AllLink.joinClass +encode);
			
			System.out.println("jsonObject   "+jsonObject);
			
			if(jsonObject != null) 
			{
				
				try {
					status = jsonObject.getString("status");
					   msg = jsonObject.getString("msg");
				} catch (JSONException e) {
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
			
			dialog  = new ProgressDialog(ClassDetails.this);
			dialog.setMessage("Please wait while processing");
			dialog.setCancelable(false);
			dialog.show();
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(status.equals("success"))
			{
				
				ConditionClas.Toastmethod(ClassDetails.this,
						msg);
				
			}
			dialog.dismiss();
		}
	}
	
}
