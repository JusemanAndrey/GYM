package the.gym.app.deft;

/* This is a simple java class which contains the links to hit for parsing
 * and also has string variable which are used as keynames for shared prefrence
 * data*/
public class AllLink {

	static String TAG_VALUEIS = "Check Output";
	/* These three String objects are difrent from
	 * the links
	 * the are they key names of values stored in sharedprefrences
	 * this is done to to avoid any typing mistake */
	public static String Username    = "username";
	public static String AuthKey     = "authkey";
	public static String UserId      = "userid";
	public static String GymMid      = "gymmeberid";
	public static String GymId       = "gymid";
	public static String InMesg      = "in_message";
	public static String MesgCount   = "message_count";
	public static String GymEmail    = "gymmail";
	/*  this link is to hit to get the classes for user*/			

	public static String getClass = "http://www.qrfitnesssolutions.com/" +
			"webservices/index.php?action=getClass&data=";

	/* This link is to get the exercise data */
	public static String Excercise = "http://www.qrfitnesssolutions.com/" +
			"webservices/index.php?action=getExercise&data=";
			
	/* This link is to check the log in of the user*/
	public static String CheckLogin = "http://www.qrfitnesssolutions.com/" +
			"webservices/index.php?action=checklogin&data=";
	
	/* This link is to register a user*/
	public static String Registration = "http://www.qrfitnesssolutions." +
			"com/webservices/index.php?action=registeruser&data=";
	
	/*  This link is need to hit in case of forget password*/
	public static String ForgetPassword = "http://www.qrfitnesssolutions.com/"+
			"webservices/index.php?action=forgetpassword&data=";
			
	/* This link is to hit when user want to logout */		
	public static String LogOut = "http://www.qrfitnesssolutions.com/webservices/"+
			"index.php?action=logout&data=";
	
	/* This link is to hit when need to check whether the exercise exist or not*/
	public static String CheckExcercise = "http://www.qrfitnesssolutions.com/" +
			"webservices/index.php?action=checkExerciseId&data=";
			
	/* This is the link to hit to get the logs*/
	public static String getLogs = "http://www.qrfitnesssolutions.com/" +
			"webservices/index.php?action=getLogs&data=";
			   
	/* This is the link to hit when to set logs*/
	public static String putLogs = "http://www.qrfitnesssolutions.com/" +
			"webservices/index.php?action=setLogs&data=";
			
	/* This is the link to get the messages of user */		 
	public static String getMessage = "http://www.qrfitnesssolutions.com/" +
			"webservices/index.php?action=getMessages&data=";		
		 
	/* This is the link to delete the messages of user */		 
	public static String DeleteMessage ="http://www.qrfitnesssolutions.com/webservices/" +
			"index.php?action=deleteMessage&data=";
	/* This is the link to get the workout details */
	public static String getWorkOut = "http://www.qrfitnesssolutions.com/" +
			"webservices/index.php?action=getWorkOut&data=";	
		 
	
	/* This is the link to get the routines */
	public static String getRoutine = "http://www.qrfitnesssolutions.com/" +
			"webservices/index.php?action=getRoutine&data=";	
	
	/* This is the link to put the content to a class
	 * i.e the user want to join that class  */
	public static String joinClass = "http://www.qrfitnesssolutions.com/" +
			"webservices/index.php?action=joinClass&data=";	
	
	
	 public static String feedBack = "http://www.qrfitnesssolutions.com/" +
			"webservices/index.php?action=sendMail&data=";
}

