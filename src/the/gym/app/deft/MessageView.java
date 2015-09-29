package the.gym.app.deft;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MessageView extends Activity {

	TextView msgDetail;
	
	Button HomeButton, BackButton;
	
	String msgText;
	
	TextView GymId ;
	
	CustomDialogClass customDialogClass ;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  
		super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
	    
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.messageview);
	   
	   msgText=getIntent().getStringExtra("messageDetail");
	   
	   intializeWedget();
	    
	   setClickLisner();
	   
	   msgDetail.setText(msgText);
	   
	   GymId.setText(Html.fromHtml("<b>GYM MEMBERSHIP ID : </b>") +customDialogClass.pref.getString(
	    		
				AllLink.GymId, ""));
	    
	    
	   
	}

	

	private void intializeWedget() {
		// TODO Auto-generated method stub
		
	   GymId =(TextView)findViewById(R.id.gymid);
		
		msgDetail=(TextView)findViewById(R.id.msgtextView);
		
		HomeButton=(Button)findViewById(R.id.home);
		
		BackButton=(Button)findViewById(R.id.backbutton);
		
		customDialogClass = new CustomDialogClass(this);
		
	}

	
	
	
	private void setClickLisner() {
		// TODO Auto-generated method stub
		
		
		
		HomeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Intent i=new Intent(MessageView.this,HomeScreen.class);
				
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				
				finish();
				
				startActivity(i);
				
				
			}
		});
		
		
		BackButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
		
				MessageView.this.finish();
			
			}
		});
		
		
		
		
	}
	
}
