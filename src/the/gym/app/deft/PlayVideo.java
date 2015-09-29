package the.gym.app.deft;


/* This class is to play the video item clicked in the log view class*/
import android.app.Activity;
import android.content.Intent;

import android.net.Uri;

import android.os.Bundle;

import android.text.Html;
import android.view.View;

import android.view.Window;

import android.widget.Button;
import android.widget.TextView;

import android.widget.MediaController;

import android.widget.VideoView;

public class PlayVideo extends Activity{

	
	String PlayUrl ;
	TextView GymId ;
	Button backButton , HomeBUtton;
	private CustomDialogClass customDialogClass;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    
		setContentView(R.layout.videourl);
		GymId = (TextView)findViewById(R.id.gymid);
        
		customDialogClass = new CustomDialogClass(this);
		GymId.setText(Html.fromHtml("<b>GYM MEMBERSHIP ID : </b>")
				+customDialogClass.pref.getString(
	    		
				AllLink.GymId, ""));
	    PlayUrl = getIntent().getStringExtra("videourl");
	    
	    System.out.println("PlayUrl   " +PlayUrl);
	    
	    VideoView videoView = (VideoView) findViewById(R.id.videoplay);
	    
	    backButton = (Button)findViewById(R.id.backbutton);
	    HomeBUtton = (Button)findViewById(R.id.home);
	    
	    /* The back button of this class shall simply take the user to
	     * the previous class */
	    backButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				PlayVideo.this.finish();
			}
		});
	    
	    HomeBUtton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
//				PlayVideo.this.finish();
				Intent i = new Intent(PlayVideo.this , HomeScreen.class);
				finish();
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});
	    
	    
	    if(ConditionClas.isNetworkAvailable(PlayVideo.this))
	    {
	    	
        try {
			MediaController mediaController = new MediaController(this);
			mediaController.setAnchorView(videoView);
    // Set video link (mp4 format )
			Uri video = Uri.parse(PlayUrl);
			videoView.setMediaController(mediaController);
			videoView.setVideoURI(video);
			videoView.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			ConditionClas.Toastmethod(PlayVideo.this, 
					"Error  " +e.getMessage());
		}
	    
	    }
	    
	    else 
	    {
			
	    	ConditionClas.Toastmethod(PlayVideo.this,
	    			"There is no internet");
		}
		
	}
}
