package the.gym.app.deft;


/* This is the class to go to the logview 
 * where user can the log set and can navigate to
 * the step to do and exercise 
 * and video for that
 * the navigation is done by buttons and touch*/
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;

import org.json.JSONException;

import org.json.JSONObject;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import android.R.string;
import android.app.Activity;

import android.app.ProgressDialog;

import android.content.Intent;
import android.graphics.Bitmap;

import android.os.AsyncTask;
import android.os.Handler;

import android.os.Bundle;

import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;

import android.view.MotionEvent;

import android.view.View;

import android.view.ViewGroup;

import android.view.Window;

import android.view.View.OnTouchListener;

import android.widget.AdapterView;
import android.widget.TableLayout;
import android.widget.TableRow;

import android.widget.ArrayAdapter;

import android.widget.BaseAdapter;

import android.widget.Button;

import android.widget.EditText;

import android.widget.ImageView;

import android.widget.LinearLayout;

import android.widget.ListView;

import android.widget.TextView;

import android.widget.ViewFlipper;

public class LogView extends Activity implements OnTouchListener{
	
	           
	private Date date;
	String Id ;

	JSONParser jsonParser ;
	
	JSONObject jsonObject , jsonObject2 , jsonObject3 ,jsonObject_Put;
	
	JSONArray MesageList ;
	
	CustomDialogClass customDialogClass ;
	
	ProgressDialog dialog ;
	
//	ListView SelectList;
	
	EditText Ed1 , Ed2 , Ed3 , Ed4 ;
	
	String[] oneValue ;
	
	String[] twoValue ;
	
	String[] thrValue ;
	
	
	Button Submit ;
	
	TableLayout dataTable;
	String Methodcall = "show" ;
	
	int count = 0 ;
	
	
	Button Direction , VideoButton , HomeButton ,   backButton;
	

	String MessageDisplay , Status ,StatusLog , MessageDisplay_Submit;
	
	TextView GymId ;
	
	
	/* This the object of view flipper 
	 * to navigate between various screen
	 * on touch event */
	ViewFlipper flipper ;
	
	
	public static ArrayList<String> Log_reps            = new ArrayList<String>();
	public static ArrayList<String> Exc_Namel            = new ArrayList<String>();
	public static ArrayList<String> Log_Date            = new ArrayList<String>();
	public static ArrayList<String> Log_Ints            = new ArrayList<String>();
	public static ArrayList<String> Exces_Id            = new ArrayList<String>();
	public static ArrayList<String> Log_Sets            = new ArrayList<String>();
	public static ArrayList<String> Log_Nots            = new ArrayList<String>();
	
	/* these are various tag names used in various links 
	 * */
	private static final String VideoParentTag = "videos";
	
	private static final String DirectionTag = "directions";
	
	
	private static final String Dir_image = "dir_image";
	
	private static final String Dir_description = "dir_description";
	
	private static final String Vid_img = "vid_img";
	
	private static final String Vid_path = "vid_path";
	
	private static final String Vid_title = "vid_title";
	
	private static final String Vid_description = "vid_description";


	// contacts JSONArray
	
	JSONArray Videos;
	
	JSONArray Desc;
	
	ListView Excercise , VideoList ;
	
	ImageLoader loader ;
	
	private float x = (float) 0.0 ;
	
	private float currentx = (float) 0.0  ;
	
	int[] childId ;
	
	LinearLayout Lout1 , Lout2 , Lout3 ;

	
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
		 * 
		 * 
		 * 
		 * */
	
	String exc_Name = "";
	
	TextView Exc_Name ;
	
	private SimpleDateFormat fromUser;
	private SimpleDateFormat myFormat , myFormat1;
	
	
	Display display  ;
	
	DisplayImageOptions options;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.logviewtable);
		
		oneValue = new String[1];
		
		twoValue = new String[1];
		
		
		thrValue = new String[1];
		
		
		jsonParser = new JSONParser();
		
		
		customDialogClass = new CustomDialogClass(LogView.this);
	  	               
		
		Id = getIntent().getStringExtra("idval");
		exc_Name = getIntent().getStringExtra("name");
	                         
		  childId = new int[3];
		  display = getWindowManager().getDefaultDisplay();
		 fromUser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 myFormat = new SimpleDateFormat("yyyy-MM-dd");
		loader = ImageLoader.getInstance();
		
		LogView.this.loader.
		
		init(ImageLoaderConfiguration.createDefault(LogView.this));
		 
		 options = new DisplayImageOptions.Builder()
        .showImageOnLoading(R.drawable.noimage) // resource or drawable
        .showImageForEmptyUri(R.drawable.ic_empty) // resource or drawable
        .showImageOnFail(R.drawable.ic_error) // resource or drawable
        .resetViewBeforeLoading(false)  // default
        .delayBeforeLoading(500)
        .cacheInMemory(true) // default
        .cacheOnDisc(true)
        .considerExifParams(false) // default
        .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
        .bitmapConfig(Bitmap.Config.ARGB_8888) // default
        .displayer(new SimpleBitmapDisplayer()) // default
        .handler(new Handler()) // default
        .build();
		// this method is called to
		// Initialize the view used in this class
		
		
		setupView();
		
		// this method holds all the clicks of buttons of 
		// LogView class 
		
	    AllOnClickListener();
	    
		// this method holds all the clicks of lists of 
		// LogView class 
	    
		AllOnItemClickListener();
		
		
		flipper.setOnTouchListener(this);
		
		if(ConditionClas.isNetworkAvailable(LogView.this))
		{
			
		new SomeView_Async().execute("");
		}
		else {
			
			ConditionClas.Toastmethod(LogView.this   , 
					" There is no internet");
		}
		
		
	}
	
	
	
	private void AllOnItemClickListener() {
		// TODO Auto-generated method stub
		
		/* this is the item click of 
		 * video list if any content exist in the list
		 * of video that shall take the user to
		 * play video class where user can watch the video
		 * click in the list */
		VideoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(LogView.this ,
						PlayVideo.class);
				intent.putExtra("videourl", SiteList.Vid_pathList.get(arg2));
				startActivity(intent);
			}
		});
	}



	private void AllOnClickListener() {
		// TODO Auto-generated method stub
		
		
		
		/*  This button click event of the class
		 * is to navigate back to the home screen*/
		HomeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				 VideoButton.setBackgroundResource(R.drawable.video);
				 Direction.setBackgroundResource(R.drawable.direction);
				 HomeButton.setBackgroundResource(R.drawable.homepress);
				 flipper.setDisplayedChild(childId[0]);
//				Intent i = new Intent(LogView.this , HomeScreen.class);
//				
//				finish();
//				
//				startActivity(i);
				
				
//				Direction.setBackgroundResource(R.drawable.b);
				
			
			}
		});		
		
		backButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Intent i = new Intent(LogView.this , HomeScreen.class);
				
				finish();
				
				startActivity(i);
				
				
//				Direction.setBackgroundResource(R.drawable.b);
				
			
			}
		});		
		
		/* This button is to navigate to the screen 
		 * of list of videos for the excercise */
	VideoButton.setOnClickListener(new View.OnClickListener() {
			
			@SuppressWarnings({ "deprecation" })
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				
//				Intent i = new Intent(LogView.this , Directionclass.class);
//				startActivity(i);
				
				count = 2 ;
				
				flipper.setDisplayedChild(childId[2]);
				
				 VideoButton.setBackgroundResource(R.drawable.videopress);
//				Direction.setBackgroundResource(R.drawable.b);
				
				 Direction.setBackgroundResource(R.drawable.direction);
				 HomeButton.setBackgroundResource(R.drawable.home_l);

				
			}
		});		
	
	
	/* This button click is to go to the list of exercise
	 * which has the images also how to perform the 
	 * exercise */

		Direction.setOnClickListener(new View.OnClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				
//				Intent i = new Intent(LogView.this , Directionclass.class);
//				startActivity(i);
				count = 1 ;
				     flipper.setDisplayedChild(childId[1]);
				   Direction.setBackgroundResource(R.drawable.directionpress);
				   
       			 VideoButton.setBackgroundResource(R.drawable.video);
       			HomeButton.setBackgroundResource(R.drawable.home_l);

			
//				flipper.showNext();
			}
		});		
		
		
		
		
		/* This button is to set the logs
		 * it calls SomeView_Async class to perform
		 * parsing on a separate thread */
		
		Submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			  boolean f= isNotEmpty();
			  
			  if (f==true) {
				
				  Methodcall = "submit";
					
				  new SomeView_Async().execute("");
				  
			} 
			  
			  
			  else {

				  
				
				  
				  ConditionClas.Toastmethod(LogView.this,"Do not leave any field empty");
			
			  
			  }
			  
				
				
				
			}

			
		});		
		
	}

	
	private boolean isNotEmpty() {
		// TODO Auto-generated method stub
	
		int s1,s2,s3,s4;
		s1=Ed1.getText().toString().length();
		
		s2=Ed2.getText().toString().length();
		
		s3=Ed3.getText().toString().length();
		
		s4=Ed4.getText().toString().length();
		
		if (s1>0&&s2>0&&s3>0) 
		
		{
			
			return true;
			
		} 
		
		else
		 
		{

			return false;
		}
		
		
		
		
		
		
	}


	/*This method of LogView class is used to initialize all
	 * the view used in this class
	 * */
	private void setupView() {
		// TODO Auto-generated method  stub
		
//			SelectList =(ListView)findViewById(R.id.shedulelist);
				 
		   Ed1 = (EditText)findViewById(R.id.editText1);
			  
			Ed2 = (EditText)findViewById(R.id.editText2);
			
			Ed3 = (EditText)findViewById(R.id.editText3);
			
			Ed4 = (EditText)findViewById(R.id.editText4);
		
			Direction = (Button)findViewById(R.id.direction);
			
			HomeButton = (Button)findViewById(R.id.home);
			backButton = (Button)findViewById(R.id.backbutton);
		
			
			VideoButton = (Button)findViewById(R.id.video);
		
			flipper = (ViewFlipper)findViewById(R.id.flipperid);
			
			Excercise = (ListView)   findViewById(R.id.listexc);
		
			VideoList = (ListView)   findViewById(R.id.videolist);
				
			
			Lout1 = (LinearLayout)findViewById(R.id.childone);
				
			Lout2 = (LinearLayout)findViewById(R.id.childtwo);
				
			Lout3 = (LinearLayout)findViewById(R.id.childthree);
			Submit = (Button)  findViewById(R.id.button1);
			GymId = (TextView)findViewById(R.id.gymid);

			dataTable = (TableLayout)findViewById(R.id.shedulelist);	
				  GymId.setText("Gym Membership Id: " +
				  
						  customDialogClass.pref.getString(
				    		
								  AllLink.GymId, ""));
				 
				  Exc_Name = (TextView)findViewById(R.id.heading);	
				  Exc_Name.setText(exc_Name);
				// get the positions of the child view 
				// to set them on click events and 
				// touch events 
			childId[0] = flipper.indexOfChild(Lout1);
			childId[1] = flipper.indexOfChild(Lout2);
			childId[2] = flipper.indexOfChild(Lout3);
			

			
//			 LayoutInflater inflater = LayoutInflater.from(this);
//			    View Header    = inflater.inflate(R.layout.header, null);
//
//			    
//			    Ed1 = (EditText)Header.findViewById(R.id.editText1);
//				  
//				Ed2 = (EditText)Header.findViewById(R.id.editText2);
//				
//				Ed3 = (EditText)Header.findViewById(R.id.editText3);
//				
//				Ed4 = (EditText)Header.findViewById(R.id.editText4);
//				
//				Submit = (Button)  Header.findViewById(R.id.button1);
//				
//				 Exc_Name = (TextView)Header.findViewById(R.id.heading);
//				Exc_Name.setText(exc_Name);
//			    SelectList.addHeaderView(Header);
					
	}

	
	/* This is the class to hit the links in asynctask
	 * and has following hits
	 * getlog
	 * setlog
	 * check exercise
	 * ad get exercises*/

  
	public class SomeView_Async extends AsyncTask<String, Void, String>
	
	{

		private SimpleDateFormat fromUser;
		private SimpleDateFormat myFormat;
		private boolean done_is;


		@SuppressWarnings("deprecation")
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			
			SiteList.Log_reps.clear() ;
			
			SiteList.Exc_Name.clear() ;  
			                                    
			SiteList.Exces_Id.clear() ;  
			
			SiteList.Log_Date.clear() ;
			
			SiteList.Log_Ints.clear() ;
			
			SiteList.Log_Sets.clear() ;
			
			SiteList.Vid_imgList.clear() ;
			
			SiteList.Vid_descriptionList.clear() ;
			
			SiteList.Vid_pathList.clear() ;
			
			SiteList.Vid_titleList.clear() ;
			
			SiteList.Dir_imageList.clear() ;
			
			SiteList.Dir_descriptionList.clear() ;
			
			SiteList.Error.clear() ;
			
			
			if(Methodcall.equalsIgnoreCase("submit"))
				
			{
				Date dt = new Date();
				 fromUser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				 myFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				 String dat = "";
				dat = myFormat.format(new Date());
				  
				System.out.println("dat   " +dat);
				
			
				System.out.println("comlete string is              " +
						
						"     "+AllLink.putLogs+
						
						"{"+"\"userid\""+ ":" 
						+"\""+customDialogClass.pref.getString(AllLink.UserId, 
								"")+"\""+","+
								"\"auth\""+ ":"+
								"\""+customDialogClass.pref.getString(AllLink.AuthKey, 
										"")+"\"" +" , " + "\"execid\""+":"+"\""+Id+"\""
										+" , "+
										"\"logintensity\""+ ":"+
										"\""+Ed3.getText().toString()+"\""
										+" , "+
										"\"logset\""+ ":"+
										"\""+Ed1.getText().toString()+"\""
										+" , "+
										"\"logrep\""+ ":"+
										"\""+Ed2.getText().toString()+"\""
										+" , "+
										"\"lognotes\""+ ":"+
										"\""+Ed4.getText().toString()+"\""
										+" , "+
										"\"logdate\""+ ":"+
										"\""+dat+"\""+"}");
		
			
				
				
			
				
				
				String	encoded = URLEncoder.encode("{"+"\"userid\""+ ":" 
			+"\""+customDialogClass.pref.getString(AllLink.UserId, 
					"")+"\""+","+
					"\"auth\""+ ":"+
					"\""+customDialogClass.pref.getString(AllLink.AuthKey, 
							"")+"\"" +" , " + "\"execid\""+":"+"\""+Id+"\""
							+" , "+
							"\"logintensity\""+ ":"+
							"\""+Ed3.getText().toString()+"\""
							+" , "+
							"\"logrep\""+ ":"+
							"\""+Ed1.getText().toString()+"\""
							+" , "+
							"\"logset\""+ ":"+
							"\""+Ed2.getText().toString()+"\""
								+" , "+
										"\"lognotes\""+ ":"+
										"\""+Ed4.getText().toString()+"\""
							+" , "+
							"\"logdate\""+ ":"+
							"\""+dat+"\""+"}");
				
				
				
				jsonObject_Put = jsonParser.getJSONFromUrl(AllLink
						.putLogs+encoded);
				
				System.out.println("jsonObject_Put    " +jsonObject_Put);
			
				try 
				{
					if(jsonObject_Put !=null)
					{
						System.out.println("status of put is  inside1   " );
						if (jsonObject_Put.getString("status").equals("success"))
						{
							System.out.println("status of put is   inside2  " );
					if (jsonObject_Put.getString("message")!=null) {
						
						System.out.println("status of put is   inside3  " );
						done_is = true ;
						MessageDisplay_Submit = jsonObject_Put.getString("message");
						
						System.out.println("status of put is     " +MessageDisplay);
					}
						}
						else {
							System.out.println("status of put is   inside4  " );
							MessageDisplay_Submit = jsonObject_Put.getString("error");
						}
					
					}
					
				
			
				} 
				
				catch (JSONException e)
				
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					try {
						System.out.println("status of put is   inside5  " );
						MessageDisplay = jsonObject_Put.getString("error");
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	
				}
				
			
				Methodcall="show";
				
			}
			
			
			/* here "show" tells which action to be performed */
			
			if(Methodcall.equalsIgnoreCase("show"))
			
			{
				System.out.println("inside show");
				
				System.out.println("comlete string is              " +
				
						"     "+AllLink.CheckExcercise+
						
						"{"+"\"userid\""+ ":" 
						
						+"\""+customDialogClass.pref.getString(AllLink.UserId, 
								
								"")+"\""+","+
								
								"\"auth\""+ ":"+
								
								"\""+customDialogClass.pref.getString(
										
										AllLink.AuthKey, 
								
										"")+"\"" +" , " 
										
										+ "\"execid\""+" : " +
												
										""+"\""+Id+"\""+"}");
		
			
				
			
				
				String	encoded = URLEncoder.encode("{"+"\"userid\""+ ":" 
			+"\""+customDialogClass.pref.getString(AllLink.UserId, 
					"")+"\""+","+
					"\"auth\""+ ":"+
					"\""+customDialogClass.pref.getString(AllLink.AuthKey, 
							"")+"\"" +" , " + "\"execid\""+":"+"\""+Id+"\""+"}");
				
				
				
				jsonObject = jsonParser.getJSONFromUrl(AllLink
						.CheckExcercise+encoded);
				
				if(jsonObject != null )
				{
				       try {
						
				    	   Status = jsonObject.getString("status");
						
				    	   if(Status.equalsIgnoreCase("success"))
				    	   {
			    
				    		   MessageDisplay = jsonObject.getString("msg");
				    	
				    	   }
				    	   
				    	   else {
				    		   MessageDisplay = "Error";
				    	   }
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						
						e1.printStackTrace();
					}
				}
				
				
				
				                                                       
				encoded = URLEncoder.encode("{"+"\"userid\""+ ":" 
						+"\""+ customDialogClass.pref.getString(AllLink.UserId, 
								"")+"\""+","+
								"\"auth\""+ ":"+
								"\""+customDialogClass.pref.getString(AllLink.AuthKey, 
										"")+
								"\"" +" , " +
										"" + "\"execid\""+":"+"\""+Id+"\""+"}");

				
				
				System.out.println("complete link is     "+
						AllLink
						.getLogs+
						"{"+"\"userid\""+ ":" 
						+"\""+ customDialogClass.pref.getString(AllLink.UserId, 
								"")+"\""+","+
								"\"auth\""+ ":"+
								"\""+customDialogClass.pref.getString(AllLink.AuthKey, 
										"")+"\"" +" , " +
										"" + "\"execid\""+":"+"\""+Id+"\""+"}");		
						
				
				jsonObject2 = jsonParser.getJSONFromUrl(AllLink
								.getLogs+encoded);
				
				
				System.out.println("jsonObject2     " +jsonObject2);
				if(jsonObject2 != null)
				{

				try
				{
					
					StatusLog = jsonObject2.getString("status");
					
					System.out.println("StatusLog     " +StatusLog);
					
					if(StatusLog.equalsIgnoreCase("success"))
						
					{
						try {
							MesageList = jsonObject2.getJSONArray("data");
							
							if(MesageList.length()>0)
							{
							for (int i = 0; i < MesageList.length(); i++) {
							
								JSONObject c = MesageList.getJSONObject(i);
//								
								
								/* log_reps */
								
								
								if((c.getString("log_reps")).length()>0)
								{
								  SiteList.Log_reps.add(c.getString("log_reps"));
								}
								
								else
								{
									SiteList.Log_reps.add("");
								}
								
								/* excercise Name */
								
								
								if((c.getString("execname")).length()>0)
								{
									
								SiteList.Exc_Name.add(c.getString("execname"));
								}
								
								else 
								{
									SiteList.Exc_Name.add("");
								}
								
						   		
								/* Log Date */
								
								
								if((c.getString("log_date")).length()>0)
								{
									
								SiteList.Log_Date.add(c.getString("log_date"));
								}
								
								else 
								{
								
									SiteList.Log_Date.add("");
								
								}
								
								
								/* log_intensity */
								
								if((c.getString("log_intensity")).length()>0)
								{
									
									
									SiteList.Log_Ints.add(c.getString("log_intensity"));
								}
								
								else
								{
									SiteList.Log_Ints.add("");
								}
								
								
								/* -----------    execid  --------------  */
								
								if((c.getString("execid")).length()>0)
								{
									
									
									
								SiteList.Exces_Id.add(c.getString("execid"));
								}
								
								else
								{
									SiteList.Exces_Id.add("");
								}
								
								
								/* -----------    execid  --------------  */
								
								
								
								if((c.getString("log_sets")).length()>0)
								{
									
									
									
								SiteList.Log_Sets.add(c.getString("log_sets"));
								}
								
								else 
								{
									SiteList.Log_Sets.add("");
								}
								
							}
							
							}
							
							else {
								SiteList.Error.add(jsonObject2.getString("error") +" " 
										+" data exist");
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					
					}
					
					else {
						
						System.out.println("status log is    " +StatusLog);
						
						oneValue[0] = MessageDisplay; 
					}
				}
				catch (JSONException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}		
				
				}
				System.out.println("Status Status    " +Status);
						
//						if(Status.equalsIgnoreCase("success"))
//						{
							try {
							String	query = URLEncoder.encode("{"+
							"\"execid\""+":" 
									+ "\""+Id+"\"" + "," + 
							"\"auth\"" +":"+
							"\""+customDialogClass.pref.getString(AllLink.AuthKey, 
									"")+"\""+"}", 
										"utf-8");
						
				
							String url = AllLink.Excercise + query;
						   
							jsonObject3 = jsonParser.getJSONFromUrl(url);
						   
						    Videos =  jsonObject3.getJSONArray(VideoParentTag);
							
						    Desc = jsonObject3.getJSONArray(DirectionTag);
							
							
//							System.out.println("contacts length is    " +contacts);
						
							  
//		String Exc_name        = jsonObject3.getString("exc_name");
//		String Exc_description = jsonObject3.getString("exc_description");
							
							
							
		
		
							
		// looping through All Contacts
							
		for(int i = 0; i < Desc.length(); i++){
								
		System.out.println("i   " +i);
		JSONObject c = Desc.getJSONObject(i);
							
		if(c.getString(Dir_image) != null)
		{
		SiteList.Dir_imageList.add(c.getString(Dir_image));
		}
		else {
			
			SiteList.Dir_imageList.add("");
		}
		if(c.getString(Dir_description) != null)
		{
		SiteList.Dir_descriptionList.add(c.getString(Dir_description));
		}
		else {
			
			SiteList.Dir_descriptionList.add("");
		}					
								
							
		}
							

		for(int i = 0; i < Videos.length(); i++){
								
		System.out.println("i   " +i);
		JSONObject c = Videos.getJSONObject(i);
		
		if(c.getString(Vid_img) != null)
		{
		SiteList.Vid_imgList.add(c.getString(Vid_img));
		}

		else {
			
			SiteList.Vid_imgList.add("");
		}	
		
		if(c.getString(Vid_description) != null)
		{
		SiteList.Vid_descriptionList.add(c.getString(Vid_description));
		}

		else {
			
			SiteList.Vid_descriptionList.add("");
		}
		
		if(c.getString(Vid_path) != null)
		{
		
		SiteList.Vid_pathList.add(c.getString(Vid_path));
		}

		else {
			
			SiteList.Vid_pathList.add("");
		}
		
		if(c.getString(Vid_title) != null)
		{
		SiteList.Vid_titleList.add(c.getString(Vid_title));
		}

		else {
			
			SiteList.Vid_titleList.add("");
		}
								
								
							
		}
							
							
		System.out.println("Dir_imageList is         "
		+SiteList.Dir_imageList);
		System.out.println("Dir_descriptionList is   "
		+SiteList.Dir_descriptionList);
		System.out.println("Vid_imgList is           "
		+SiteList.Vid_imgList);
		System.out.println("Vid_descriptionList is   "
		+SiteList.Vid_descriptionList);
 
		} 
		catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
							
		}
//						}
//						else {
//							
//							
//							System.out.println("error in fetching " +
//									"exercise data");
//						}
			}
			
			
			return null;
		}
		
	
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			dialog = new ProgressDialog(LogView.this);
			dialog.setCancelable(false);
			dialog.setMessage("Please wait while processing");
			dialog.setTitle("Fetching Data");
			dialog.show();
			
		}
		
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			

			Ed1.setText("");
			
			Ed2.setText("");
			
			Ed3.setText("");
			
			Ed4.setText("");
			
			if(done_is==true)
			{
				
				ConditionClas.Toastmethod(LogView.this, " " +MessageDisplay_Submit);
				
			
			}
			if(Methodcall.equalsIgnoreCase("submit"))
			{
				
				ConditionClas.Toastmethod(LogView.this, " " +MessageDisplay);
				
			
			}
			
			else if(Methodcall.equalsIgnoreCase("show")) 
			
			
			{
				
				
				System.out.println("inside showLog_reps " +SiteList.Log_reps);
				System.out.println("inside show Exc_Namel "+SiteList.Exc_Name);
				System.out.println("inside showLog_Date " +SiteList.Log_Date);
				System.out.println("inside show Log_Ints " +SiteList.Log_Ints);
				System.out.println("inside show Log_Sets " +SiteList.Log_Sets);
				System.out.println("inside show exc_Name " +method(exc_Name));
				Log_reps.clear() ;
				
				Exc_Namel.clear() ;  
				
				Log_Date.clear() ;
				
				Log_Ints.clear() ;
				
				Log_Sets.clear() ;
				
				for (int i = 0; i < SiteList.Exc_Name.size(); i++) {
					
//					if(SiteList.Exc_Name.get(i).toLowerCase().
//							contains(method(exc_Name).toLowerCase()))
					if(SiteList.Exces_Id.get(i).toLowerCase().
							contains(Id.toLowerCase()))
					{
					
						Log_reps.add(SiteList.Log_reps.get(i)) ;
						
						Exc_Namel.add(SiteList.Exc_Name.get(i)) ;  
						
						Log_Date.add(SiteList.Log_Date.get(i)) ;
						
						Log_Ints.add(SiteList.Log_Ints.get(i)) ;
						
						Log_Sets.add(SiteList.Log_Sets.get(i)) ;
					}
				}
				System.out.println("inside show");
				
				System.out.println("inside showLog_reps " +Log_reps);
				System.out.println("inside show Exc_Namel "+Exc_Namel);
				System.out.println("inside showLog_Date " +Log_Date);
				System.out.println("inside show Log_Ints " +Log_Ints);
				System.out.println("inside show Log_Sets " +Log_Sets);
				
				System.out.println("StatusStatusStatus " +Status);
				System.out.println("MessageDisplay    "+MessageDisplay);
//				if (Status.equalsIgnoreCase("success"))
//					
//				{
					System.out.println("inside if  ");
					System.out.println("MessageDisplay    "+MessageDisplay);
					
					
					Excercise.setAdapter(new CustomListExcercise());
					
//					VideoList.setAdapter()
					VideoList.setAdapter(new CustomListVideo());
					
					
//				}
//				
//				else 
//				
//				{
//					System.out.println(" in side elese " +
//							"MessageDisplay    "+MessageDisplay);
//					
//					twoValue[0] = " Error";
//					VideoList.setAdapter(new ArrayAdapter<String>(LogView.this,
//							android.R.layout.simple_list_item_1 ,
//							android.R.id.text1 ,twoValue ));
//					ConditionClas.Toastmethod(LogView.this, MessageDisplay);
//					
//					Excercise.setAdapter(new ArrayAdapter<String>(LogView.this,
//							android.R.layout.simple_list_item_1 ,
//							android.R.id.text1 ,twoValue ));
//					ConditionClas.Toastmethod(LogView.this, MessageDisplay);
//					
//					
//				}
				
				
			/* This part checks is the log link hit was successful
			 *  so on basis of that it shall set an adapter */
					
					
				if(StatusLog.equalsIgnoreCase("success"))
				{
					System.out.println("inside if StatusLog   ");
					if(MesageList.length()>0)
				{
				
//						SelectList.setAdapter(new CustomList());
						updateTable();
				
				}
				
				
					else 
					{
						
//						SelectList.setAdapter(new ArrayAdapter<String>(LogView.this,
//								android.R.layout.simple_list_item_1 ,
//								android.R.id.text1 ,SiteList.Error ));
					}
				}
				
				
				
			}
			dialog.dismiss();
			
		}
	}
	
	
	/* This is the class which  extends BaseAdapter
	 * adds the custom view to view hierarchy to group view
	 * of the activity 
     */
	class CustomList extends BaseAdapter{

		


		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return SiteList.Exc_Name.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			
			LayoutInflater inflater = (LayoutInflater)
					LogView.this.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			
			ViewHolder holder = null ;
			
			if(arg1 == null)
			{
			
				
				arg1 = inflater.inflate(R.layout.loglistobject, null);
				
				holder = new ViewHolder();
				
				holder.CountsTv      = (TextView)arg1.findViewById(R.id.counttv);
				
				holder.DateTv        = (TextView)arg1.findViewById(R.id.datetV);
				
				holder.ExcerciseName = (TextView)arg1.findViewById(R.id.enametV);
				
				holder.Repetition    = (TextView)arg1.findViewById(R.id.reptV);
				
				arg1.setTag(holder);
			}
			
			else 
			{
				
				holder = (ViewHolder) arg1.getTag();	
		
			}
			try {
				date = fromUser.parse(Log_Date.get(arg0));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String outputDateStr = myFormat.format(date);
			
			holder.CountsTv.setText(String.valueOf(arg0+1));
			
			holder.ExcerciseName.setText(Exc_Namel.get(arg0));
			
			holder.DateTv.setText(outputDateStr);
			
			holder.Repetition.setText(Log_reps.get(arg0)+" Reps");
			
			
			return arg1;
		}
		
		
		private class ViewHolder
		{
	        TextView CountsTv , DateTv , ExcerciseName , Repetition ;
	    }
	}
	
	/* CustomListExcercise extends baseadapter class to make cutom view for 
	 * list of 
	 * exercise 
	 * */
	
	class CustomListExcercise extends BaseAdapter
	{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return SiteList.Dir_imageList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			                     
			ViewHolder holder ;
			 LayoutInflater mInflater = (LayoutInflater)
					 LogView.
					 this.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			 
		      if (arg1 == null)
		      {
		    	 
		    	     arg1 = mInflater.inflate(R.layout.dirctioncustomview, null);
		           holder = new ViewHolder();
		           
		           holder.txtDesc   = (TextView)  arg1.findViewById(R.id.step);
		           holder.txtTitle  = (TextView)  arg1.findViewById(R.id.stepno);
		           holder.imageView = (ImageView) arg1.findViewById(R.id.img);
		           
		           arg1.setTag(holder);
		      
		      }
		        
		      else
		      {
		            holder = (ViewHolder) arg1.getTag();
		      
		      }
			 
		      holder.txtTitle.setText("Step No. " +(arg0+1));
		      holder.txtDesc.setText(SiteList.Dir_descriptionList.get(arg0));
		      try {
		    	  
		    	  System.out.println("link to disply inside view exc  " 
		    	  +SiteList.Dir_imageList.get(arg0)
						  );
				loader.displayImage(SiteList.Dir_imageList.get(arg0),
						  holder.imageView , options);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 return arg1;
		}
		
		private class ViewHolder {
	        ImageView imageView;
	        TextView txtTitle;
	        TextView txtDesc;
	    }
		
	}
	
	
	/* This class is to display the custom list images of all the video 
	 * available for the exercise
	 * Click on the item of this list takes user to play the video for that item
	 * */
	class CustomListVideo extends BaseAdapter
	{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return SiteList.Vid_pathList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			                     
			ViewHolder holder ;
			 LayoutInflater mInflater = (LayoutInflater)
					 LogView.
					 this.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			 
		      if (arg1 == null)
		      {
		    	 
		    	     arg1 = mInflater.inflate(R.layout.dirctioncustomview, null);
		           holder = new ViewHolder();
		           
		           
		           holder.txtDesc   = (TextView)  arg1.findViewById(R.id.step);
		           holder.txtTitle  = (TextView)  arg1.findViewById(R.id.stepno);
		           holder.imageView = (ImageView) arg1.findViewById(R.id.img);
		           
		           arg1.setTag(holder);
		                  
		      }
		        
		      else
		      {
		            holder = (ViewHolder) arg1.getTag();
		      
		      }
			 
		     
		      try {
		    	  
		    	  holder.txtTitle.setText("Video " +(arg0+1));
		    	  System.out.println("link to disply inside view vid  " 
		    	  +SiteList.Vid_imgList.get(arg0)
						  );
				loader.displayImage(SiteList.Vid_imgList.get(arg0),
						  holder.imageView  , options);
				
				holder.txtDesc.setText(SiteList.Vid_titleList.get(arg0));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 return arg1;
		}
		
		private class ViewHolder {
			    ImageView imageView;
		        TextView  txtTitle;
		        TextView  txtDesc;
	    }
		
	}
  
    @SuppressWarnings("deprecation")
    
    /* This method is navigate between screens of exercise step
     * and videos and logview on basis of finger gesture */
	public boolean onTouch(View v, MotionEvent event) 
 {
     switch (event.getAction())
     {
         case MotionEvent.ACTION_DOWN:
         {       
               // Here u can write code which is executed after the user 
        	  // touch on the screen 
                
        	 x = event.getX();
        	 break; 
         }
         case MotionEvent.ACTION_UP:
         {             
        	 
        	 currentx = event.getX();
        	 
        	 
        	 if(currentx<x)
        	 {
        		 if(count<2)
        		 {
        		
        		 count++;
        		 if(count == 1)
        		 {
        			 
        			 // setting default background for buttons
        		
        		  
        		   
        		   VideoButton.setBackgroundResource(R.drawable.video);
//   				Direction.setBackgroundResource(R.drawable.b);
   				
   				 Direction.setBackgroundResource(R.drawable.directionpress);
   				 HomeButton.setBackgroundResource(R.drawable.home_l);
        		   
        		   flipper.setDisplayedChild(childId[1]);
        		 }
        		 else if (count == 2)
        		 {
        			
        			
//        			 flipper.setDisplayedChild(2);
        			 flipper.setDisplayedChild(childId[2]);
        			 
        			 VideoButton.setBackgroundResource(R.drawable.videopress);
//    				Direction.setBackgroundResource(R.drawable.b);
    				
    				 Direction.setBackgroundResource(R.drawable.direction);
    				 HomeButton.setBackgroundResource(R.drawable.home_l);
        			 
        		 }
        		 else if(count == 0) 
        		 
        		 {
//        			 Direction.setBackgroundDrawable(getResources().
//        					 getDrawable(android.R.drawable.btn_default));
//        		   VideoButton.setBackgroundDrawable(getResources().
//        				   getDrawable(android.R.drawable.btn_default));
//        		   flipper.setDisplayedChild(0);
        		   
        			 VideoButton.setBackgroundResource(R.drawable.video);
//     				Direction.setBackgroundResource(R.drawable.b);
     				
     				 Direction.setBackgroundResource(R.drawable.direction);
     				 HomeButton.setBackgroundResource(R.drawable.homepress);
     				 
     				 
        		   flipper.setDisplayedChild(childId[0]);
				}
        		 
        		 }
        		 else 
        		 {
//					Toast.makeText(LogView.this, 
//							"There is no further view", 
//							Toast.LENGTH_LONG).show();
					
					ConditionClas.Toastmethod(LogView.this, 
							"There is no further view");
				}
        	 }
        	 
        	 else if(currentx>x) {
        		 
        		 if(count>=0)
        			 
        		 { 
//        		 flipper.showPrevious();
        		 count--;
        		 if(count == 1)
        		 {
        			 VideoButton.setBackgroundResource(R.drawable.video);
//    				Direction.setBackgroundResource(R.drawable.b);
    				
    				 Direction.setBackgroundResource(R.drawable.directionpress);
    				 HomeButton.setBackgroundResource(R.drawable.home_l);
        		   flipper.setDisplayedChild(childId[1]);
        		 }
        		 else if (count == 2)
        		 {
        			
        			 VideoButton.setBackgroundResource(R.drawable.videopress);
//     				Direction.setBackgroundResource(R.drawable.b);
     				
     				 Direction.setBackgroundResource(R.drawable.direction);
     				 HomeButton.setBackgroundResource(R.drawable.home_l);
        			 flipper.setDisplayedChild(childId[2]);
        		 }
        		 else if(count == 0) 
        		 
        		 {
        			 VideoButton.setBackgroundResource(R.drawable.video);
//      				Direction.setBackgroundResource(R.drawable.b);
      				
      				 Direction.setBackgroundResource(R.drawable.direction);
      				 HomeButton.setBackgroundResource(R.drawable.homepress);
        		   flipper.setDisplayedChild(childId[0]);
				}
        		 
        		 }
        		 else 
        		 {
//					Toast.makeText(LogView.this, 
//							"There is no view remaining", 
//							Toast.LENGTH_LONG).show();
					
					ConditionClas.Toastmethod(LogView.this, 
							"There is no view remaining");
				}
			}
        	 
                // Here u can write code which is executed after the user release 
        	 //the touch on the screen    
              break;
         }
         case MotionEvent.ACTION_MOVE:
         {  
              // Here u can write code which is executed when user move the 
        	 // finger on the screen   
             break;
         }
     }
     return true;
 }
    /**
     * updates the table from the database.
     */
    private void updateTable()
    {
    	// delete all but the first row.  remember that the count 
    	// starts at one and the index starts at zero
    	while (dataTable.getChildCount() > 1)
    	{
    		// while there are at least two rows in the table widget, delete
    		// the second row.
    		dataTable.removeViewAt(1);
    	}
 
    	// collect the current row information from the database and
    	// store it in a two dimensional ArrayList
//    	ArrayList<ArrayList<Object>> data = db.getAllRowsAsArrays();
 
    	// iterate the ArrayList, create new rows each time and add them
    	// to the table widget.
    	
		
//		holder.CountsTv.setText(String.valueOf(arg0+1));
//		
//		holder.ExcerciseName.setText(SiteList.Exc_Name.get(arg0));
//		
//		holder.DateTv.setText(outputDateStr);
//		
//		holder.Repetition.setText(SiteList.Log_reps.get(arg0)+" Reps");

    	for (int position=0; position < Log_Date.size(); position++)
    	{
    		
    		try {
    			date = fromUser.parse(Log_Date.get(position));
    		} catch (ParseException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		String outputDateStr = myFormat.format(date);
    		TableRow tableRow= new TableRow(this);
 
//    		ArrayList<Object> row = data.get(position);
 
    		TextView idText = new TextView(this);
    		idText.setText(String.valueOf(position+1));
//    		idText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
    		idText.setWidth((Math.round(display.getWidth()/6)));
    		idText.setPadding(5, 5, 5, 0);
    		tableRow.addView(idText);
 
    		TextView textOne = new TextView(this);
    		textOne.setText(Exc_Namel.get(position));
    		textOne.setWidth((display.getWidth()/4));
//    		textOne.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
    		textOne.setPadding(3, 5, 5, 0);
    		tableRow.addView(textOne);
 
    		TextView textTwo = new TextView(this);
    		textTwo.setText(outputDateStr);
    		textTwo.setWidth((display.getWidth()/4));
//    		textTwo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
    		textTwo.setPadding(3, 5, 5, 0);
    		tableRow.addView(textTwo);
 
    		TextView textThree = new TextView(this);
//    		textThree.set
    		textThree.setText(Log_reps.get(position)+" Reps");
//    		textThree.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
    		textThree.setWidth((display.getWidth()/6));
    		textThree.setPadding(3, 5, 5, 3);
    		tableRow.addView(textThree);
    		
    		dataTable.addView(tableRow);
    	}
    	
    	
    }
    public String method(String str) {

    	  if (str.length() > 0 && str.charAt(str.length()-1)=='s') {
    	    str = str.substring(0, str.length()-1);
    	  }
    	  return str;
    	}
}
