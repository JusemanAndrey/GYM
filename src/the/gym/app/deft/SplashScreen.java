package the.gym.app.deft;


/* This is the simple java class which extends with activity

 * and has one screen which stays for given specific time
 * and then navigates to the log in class */


import android.os.Bundle;

import android.os.Handler;
import android.view.Window;
import android.app.Activity;
import android.content.Intent;

public class SplashScreen extends Activity {
	
	
	// Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
	@Override
	
	protected void onCreate(Bundle savedInstanceState) 
	
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	   
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
	    setContentView(R.layout.splashscreen);
		
		Handler handler = new Handler();
		
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() 
			{
			
				
				
				// TODO Auto-generated method stub
				
				Intent i = new Intent(SplashScreen.this , 
					
						LogInClass.class);
				
				startActivity(i);
				
				
				finish();
			}
		}, 
		
		SPLASH_TIME_OUT);
		
	
    }
	
	}
