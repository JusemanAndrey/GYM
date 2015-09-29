package the.gym.app.deft;


/* this class is the intermediate view of
 * home screen and the log view
 * right */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ScanClass extends Activity {

	
	
	Button Scanit , toSomeView ;
	EditText Idfield ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scanscreen);
		
		
		
		setUpView();
		OnClickListener_Method();
	
	}

	
	private void OnClickListener_Method() {
		// TODO Auto-generated method stub
		
		
		Scanit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				ConditionClas.Toastmethod(ScanClass.this,
					
						"This function is under construction");
			}
		});
		

		toSomeView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
			Intent i = new	Intent(ScanClass.this,
						LogView.class);
			i.putExtra("idval", Idfield.getText().toString());
			
			startActivity(i);
			}
		});
		
	}
	
	
	private void setUpView() {
		// TODO Auto-generated method stub
		
		    Scanit = (Button)  findViewById(R.id.scanbutton);
		
		    toSomeView = (Button)  findViewById(R.id.toexcbutton);
		   
		    Idfield = (EditText)findViewById(R.id.ideditText);
		
	}
}
