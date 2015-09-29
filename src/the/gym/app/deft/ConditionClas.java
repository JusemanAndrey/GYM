package the.gym.app.deft;


/* This class contains the condition methods required
 * to validate fields 
 * check Internet and 
 *  display toast */


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.content.Context;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class ConditionClas {

	   
	public ConditionClas() {
		// TODO Auto-generated constructor stub
	}
	 
	
	/* This method is used to check if the mail id is correct or not
	 * this method is accessed only from registration class
	 * */ 
	public static boolean isEmailValid(String email) {
	    boolean isValid = false;

	    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	    CharSequence inputStr = email;

	    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(inputStr);
	    if (matcher.matches()) 
	    {
	        isValid = true;
	    }
	    return isValid;
	}
	
	/* This method is to check if the device is connected with 
	 * Internet or not
	 * the return type of this method is boolean 
	 * and it return true if device is connected to 
	 * a network
	 * */
	
	public static boolean isNetworkAvailable(Context context)
	{
		
		
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) context.getSystemService
	          (Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	
	}
	
	    
	
	/* This method is to display toast and is accessed from 
	 * every activity of the application*/
	 static void  Toastmethod(Context context , String message) {
		// TODO Auto-generated method stub

		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}
	 
//	 static void  DisplayImageOptionsmethod(Context context) {
//	 DisplayImageOptions  options = new DisplayImageOptions.Builder()
//
//	 .showImageOnLoading(R.drawable.icon_grey)
//
//	 .showImageForEmptyUri(R.drawable.icon_grey)
//
//	 .showImageOnFail(R.drawable.icon_grey)
//.cacheInMemory(true)
//.cacheOnDisc(true)
//.bitmapConfig(Bitmap.Config.RGB_565)
//.build();
//	 }
}
