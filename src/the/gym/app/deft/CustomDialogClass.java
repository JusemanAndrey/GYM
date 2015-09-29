package the.gym.app.deft;


/* This class is to create custom dialog used in the application
 * and has got the dialog of forget password
 * and also has initialization of shared preferences 
 * */
import android.app.Dialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CustomDialogClass {

	Dialog LoginDialog ;
	
	Button LoginButton , CancelButton  , SignUpButton ;
	
	TextView ForgetpasswordButton , BackFp;
	
	EditText UserName , Password ;
	
	
	TextView CountsTv , DateTv , ExcerciseName , Repetition ;
	
	Dialog ForgetPassword ;
	
	Dialog Detail; ;
	
	Button SbmitFp ;
	
	EditText emailIdFp ;
	
	
	Dialog ReviewDialog ;
	EditText ReviewText;
	Button ReviewButton , BackButton;
	
	/* These are the objects of shredprefrences and edit
	 * this objects are used in the application throughout */
	
	SharedPreferences pref;
	
	SharedPreferences.Editor edit;
	
	public CustomDialogClass(Context context) {
		// TODO Auto-generated constructor stub
	
		pref = context.getSharedPreferences("logincrednetial", 
				Context.MODE_PRIVATE);
		edit = pref.edit();
		
	}
	public void LoginDialogInit(Context context) 
	{
		// TODO Auto-generated method stub

		
		LoginDialog = new Dialog(context , android.R.style.Theme_Black_NoTitleBar);
		
		LoginDialog.setContentView(R.layout.loginscreen);
		
		
		 LoginButton =  (Button) LoginDialog.findViewById(R.id.login);
		
		 CancelButton =  (Button) LoginDialog.findViewById(R.id.cancel);
		
		SignUpButton =  (Button) LoginDialog.findViewById(R.id.signup);
		
		
		ForgetpasswordButton =  (TextView) LoginDialog.findViewById(R.id.forgetpassword);
	
	
		UserName =  (EditText) LoginDialog.findViewById(R.id.username);
		
		Password =  (EditText) LoginDialog.findViewById(R.id.password);
		
	}
	
	
	/* This is the method to show the custom dialog of forget password */
	 
	void forgetPasswordInit(Context context) {
		// TODO Auto-generated method stub

		 
		 ForgetPassword = new Dialog(context , 
				
				 android.R.style.Theme_Black_NoTitleBar);
		 
		 
		 ForgetPassword.setContentView(R.layout.forgetpassword);
		 
		 emailIdFp = (EditText)ForgetPassword.findViewById(R.id.emailFp);
		  
		 SbmitFp = (Button)  ForgetPassword.findViewById(R.id.submitbuttonfp);
		    
		 BackFp = (Button)  ForgetPassword.findViewById(R.id.backbutton);
	}
	 
	 /* This is the method to show the custom dialog of forget password */
	 
	 
	 void DateInit(Context context) {
		// TODO Auto-generated method stub

		 Detail = new Dialog(context , 
				
				 android.R.style.Theme_Black_NoTitleBar);
		 
		 
		 Detail.setContentView(R.layout.loglistobject);
		 
		 
		 
		    CountsTv      = (TextView)Detail.findViewById(R.id.counttv);
			
		    DateTv        = (TextView)Detail.findViewById(R.id.datetV);
			
		    ExcerciseName = (TextView)Detail.findViewById(R.id.enametV);
			
		    Repetition    = (TextView)Detail.findViewById(R.id.reptV);
	}
	 
	 void ReviewInit(Context context) {
			// TODO Auto-generated method stub

			 ReviewDialog = new Dialog(context , 
					
					 android.R.style.Theme_Black_NoTitleBar);
			   
			 
			 ReviewDialog.setContentView(R.layout.registrtion);
			 
			 ReviewButton  = (Button)  ReviewDialog.findViewById(R.id.reviewdone);
			    ReviewText = (EditText)ReviewDialog.findViewById(R.id.reviewtext);
			   BackButton  = (Button)  ReviewDialog.findViewById(R.id.backbutton);
			 
			 
			   BackButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				
					ReviewDialog.dismiss();
				}
			});
		}
		 
	 
}
