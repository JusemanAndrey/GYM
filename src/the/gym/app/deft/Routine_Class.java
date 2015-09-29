package the.gym.app.deft;


/* This class is to display routines 
 * has a custom list
 * and a back button to navigate to home screen*/
import java.net.URLEncoder;
import java.util.ArrayList;


import org.json.JSONArray;

import org.json.JSONException;

import org.json.JSONObject;

import android.app.Activity;

import android.app.ProgressDialog;

import android.content.Intent;

import android.os.AsyncTask;

import android.os.Bundle;

import android.text.Html;
import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.view.Window;

import android.widget.AdapterView;
import android.widget.Toast;

import android.widget.ArrayAdapter;

import android.widget.BaseAdapter;

import android.widget.Button;

import android.widget.ListView;

import android.widget.TextView;

public class Routine_Class extends Activity
{

	
	ListView Routine ;
	
	JSONParser parser ;
	
	JSONArray Mon , Tue , Wed , Thr , Fri , Sat , Sun ;
	
	JSONObject jsonObject ,mesagesObj,bicepsDetail;
	
	CustomDialogClass customDialogClass ;
	
	ProgressDialog dialog ;
	
	Button Home ,Back;
	
	TextView GymId ;
	
	String[] Msg;
	
	String Floor_Plan  = "" ;
	
	String FloorNumber = "" ;
	
	String WorkoutName = "" ;
	
	public static ArrayList<String> Routines = new ArrayList<String>();
	public static ArrayList<String> ExcnameSendList = new ArrayList<String>();
	public static ArrayList<String> FloorNumberSendList = new ArrayList<String>();
	public static ArrayList<String> Dir_imageSendList = new ArrayList<String>();
	public static ArrayList<String> WorkouSendList = new ArrayList<String>();
//	public static ArrayList<String> ExcnamearmArray = new ArrayList<String>();
//	public static ArrayList<String> FloorNumberarmArray = new ArrayList<String>();
//	public static ArrayList<String> Dir_imageListarmArray = new ArrayList<String>();
//	public static ArrayList<String> WorkoutNamearmArray = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.routeenscreen);
		
		parser = new JSONParser();
		
		customDialogClass = new CustomDialogClass(this);
		
		Msg = new String[1];
		
		Routine = (ListView)findViewById(R.id.routinelist);
		 
		Home = (Button)findViewById(R.id.home); 
		Back=  (Button) findViewById(R.id.backbutton);
		
		GymId = (TextView)findViewById(R.id.gymid); 
		   
		  
		GymId.setText(Html.fromHtml("<b>GYM MEMBERSHIP ID : </b>") +customDialogClass.pref.getString(
	    		
				AllLink.GymId, ""));
		  
//		Routines.clear();
//		Routines.add("Arm"); 
//		Routines.add("Upper Body"); 
		
		// this button click shall take the user to home page of the 
		   // application
		Home.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Intent i=new Intent(Routine_Class.this,HomeScreen.class);
				
				
				finish();
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				
				
			}
		});
		
		Back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Routine_Class.this.finish();
			
			
			
			}
		});
		
		if(ConditionClas.isNetworkAvailable(Routine_Class.this))
		{
	    	new RoutineParsing().execute("");

		}
		
		else {
			
			
			ConditionClas.Toastmethod(Routine_Class.this, 
			
					
			" There is no internet connection");
		}
		
		
		
		
		
	}
	
	/* This is the async class of RoutineParsing to get the data 
	 * of routine */
	class RoutineParsing extends AsyncTask<String, Void, String>
	{
		
		

		@SuppressWarnings("deprecation")
		@Override
		protected String doInBackground(String... params) {
	
			// TODO Auto-generated method stub
			
			
			String encoded = "" ;
			
	
	// encoding of string before attaching to the main link
			
			encoded = URLEncoder.encode("{"+"\"gymid\""+ ":" 
			
					+"\""+customDialogClass.pref.getString(
					
							AllLink.UserId, 
							
							"")+"\""+","+
							
							"\"auth\""+ ":"+
							
							"\""+customDialogClass.pref.getString(
									
									AllLink.AuthKey, 
								
									"")+"\""+"}");
			
			
			System.out.println("to hit link is       " +
					
					AllLink.getWorkOut + "{"+"\"gymid\""+ ":" 
					
					+"\""+customDialogClass.pref.getString(
							AllLink.UserId, 
						
							"")+"\""+","+
					
							"\"auth\""+ ":"+
			
							"\""+customDialogClass.pref.getString(
							
									AllLink.AuthKey, 
							
									"")+"\""+"}");
			
			

			
			
			jsonObject = parser.getJSONFromUrl(AllLink.getWorkOut+encoded);
			
			System.out.println("jsonObject     "+jsonObject.keys());
			System.out.println("jsonObject     "+jsonObject.names());
			
			System.out.println("jsonObject     "+jsonObject.length());
			return null;
		
		}
		
				@Override
		
		protected void onPreExecute() {
			// TODO Auto-generated method stub
		
					super.onPreExecute();
			
			dialog = new ProgressDialog(Routine_Class.this);
			
			dialog.setCancelable(false);
		
			dialog.setMessage("Please wait while processing");
			
			dialog.setTitle("Fetching all routines");
			
			dialog.show();
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			Routines.clear();
			SiteList.Exc_Name_Routine.clear();
			
			SiteList.Floor_no_Routine.clear();
			
			SiteList.Dir_image_Routine.clear();
			
			SiteList.WorkoutName_Routine.clear();
			if (jsonObject != null) 
			
			{
				

				
				
				try {				
					
				
					if(jsonObject.getString("status").equalsIgnoreCase("success"))
					
					
					{
						System.out.println("jsonObject     " +jsonObject);
						System.out.println("messageList   " +jsonObject.length());
//						Toast.makeText(Routine_Class.this, "Content "+jsonObject.getJSONArray("mesages"),
//								
//								Toast.LENGTH_LONG).show();
						
//						System.out.println("Content			"+jsonObject.getJSONArray("mesages"));
					
						
						Floor_Plan = jsonObject.getString("floor_map");
						
						
						mesagesObj=jsonObject.getJSONObject("mesages");
						
						
				System.out.println("object name    " +mesagesObj.names().getString(0));
				
				for (int i = 0; i < mesagesObj.names().length(); i++) {
					Routines.add(mesagesObj.names().getString(i));
				}
				System.out.println("Routines     " +Routines);
				
//				uperBodyArray= mesagesObj.getJSONArray(Routines.get(0));
//				System.out.println("uperBodyArray            " +uperBodyArray.length());
				
					    if (mesagesObj!=null) {
							
				  	    	
						
//							uperBodyArray= mesagesObj.getJSONArray("Upper Body");
							
							
						
							
	
							
//							ExcnamearmArray.clear();
//							
//							FloorNumberarmArray.clear();
//							
//							Dir_imageListarmArray.clear();
//							
//							WorkoutNamearmArray.clear();
							for (int j = 0; j < Routines.size(); j++) {
								
								JSONArray BodyPrt= mesagesObj.getJSONArray(Routines.get(j));
							
								  SiteList.Index.add(BodyPrt.length());

								for (int i = 0; i < BodyPrt.length(); i++) {
									
									JSONObject BodyDetail=BodyPrt.getJSONObject(i);
								      
								        SiteList.Exc_Name_Routine.add(BodyDetail.getString("exc_name"));
										
										
								        SiteList.Floor_no_Routine.add(BodyDetail.getString("floor_number"));
							
							
								        SiteList.Dir_image_Routine.add(BodyDetail.getString("dir_image"));
								 
							
//								        SiteList.WorkoutName_Routine.add("Upper Body");
									
									
										}
							}
							
}
							
							
					}
						
					
				}	
						
	                    
				
			 catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				
//				
				else {
					
					Toast.makeText(Routine_Class.this,"Thereb is No Element " , 
							
							Toast.LENGTH_LONG).show();
					
					
					
					
					System.out.println("no element");
				}
			
			   if (SiteList.Exc_Name_Routine.size() >0) {
				
            //	Routine.setAdapter(new CustomListRoutines());
				
				Routine.setAdapter(new ArrayAdapter<String>(
						
						Routine_Class.this, 
						
						android.R.layout.simple_list_item_1,
						
						android.R.id.text1 , Routines));
				
				        Routine.setOnItemClickListener(new 
						
				        		AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, 
							View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
				
						Intent intent = new Intent(
								Routine_Class.this ,
						RoutineSub.class);
					
						
											
						try {
							
							System.out.println("mesagesObj.getJSONArray(Routines.get(arg2)) " +
									"                " +mesagesObj.getJSONArray(Routines.get(arg2)));
							
							 ExcnameSendList.clear();
								FloorNumberSendList.clear();
								Dir_imageSendList.clear();
								WorkouSendList.clear();
								JSONArray BodyPrt= mesagesObj.getJSONArray(Routines.get(arg2));				
								
							
							for (int i = 0; i < mesagesObj.getJSONArray(Routines.get(arg2)).length(); i++) {
								
								
								JSONObject BodyDetail=BodyPrt.getJSONObject(i);
								ExcnameSendList.add(BodyDetail.getString("exc_name"));
								
								
								FloorNumberSendList.add(BodyDetail.getString("floor_number"));
					
					
								Dir_imageSendList.add(BodyDetail.getString("dir_image"));
	
//								ExcnameSendList.add(SiteList.Exc_Name_Routine.get(i));
//								FloorNumberSendList.add(SiteList.Floor_no_Routine.get(i));
//								Dir_imageSendList.add(SiteList.Dir_image_Routine.get(i));
								
//								JSONObject BodyDetail=BodyPrt.getJSONObject(i);
//							      
//						        SiteList.Exc_Name_Routine.add(BodyDetail.getString("exc_name"));
//								
//								
//						        SiteList.Floor_no_Routine.add(BodyDetail.getString("floor_number"));
//					
//					
//						        SiteList.Dir_image_Routine.add(BodyDetail.getString("dir_image"));

//								WorkouSendList.add(SiteList.WorkoutName_Routine.get(i));
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						System.out.println("list created 1 " + ExcnameSendList);
						System.out.println("list created 2 " + WorkouSendList);
						System.out.println("list created 3 " + FloorNumberSendList);
						System.out.println("list created 4 " + Dir_imageSendList);
						
						intent.putExtra("floorplan", Floor_Plan);
						
						intent.putExtra("name", Routines.get(arg2));
						
						
						intent.putStringArrayListExtra("dir", 
								Dir_imageSendList);
						
						intent.putStringArrayListExtra("workname", 
								
								ExcnameSendList);
						
						intent.putStringArrayListExtra("floornumber", 
								
								
								FloorNumberSendList);
						
	
						
//						intent.putStringArrayListExtra("index", 
//								
//								
//								SiteList.Index);
						
						
						  startActivity(intent);
						
					}
				});
				                  
			}
			
			else {
			
				System.out.println("no adapter");
			
				 dialog.dismiss();	
			}
			
			   dialog.dismiss();
			 //  dialog.dismiss();	
		
		}
		
	}
	
	/* this list is to display the content to get workout*/
	
	class CustomListRoutines extends BaseAdapter
	{

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
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			LayoutInflater inflater = (LayoutInflater) Routine_Class.this.
					getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			
			ViewHolder holder;
			if(convertView == null)
			{
				
				
				holder = new ViewHolder();
				
				 convertView = inflater.inflate(R.layout.routinecustom, null);
				
				holder.Excname = (TextView)convertView.findViewById(R.id.excname);
				holder.ExcInfo = (TextView)convertView.findViewById(R.id.excinfo);
				
				convertView.setTag(holder);
			}
			
			else {
				
				
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.ExcInfo.setText(SiteList.Excinfo.get(position));
			holder.Excname.setText(SiteList.Exc_Name.get(position));
			
			return convertView;
		}
		
		class ViewHolder
		{
			
			TextView Excname , ExcInfo ;
			
		}
		
	}
	// class to create custom list 
	// this class shall add the custom lit components to
	// the view hierarchy 
	
	class CustomListRoutine extends BaseAdapter
	{

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
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			LayoutInflater inflater = (LayoutInflater) Routine_Class.this.
					getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			
			ViewHolder holder;
			
			if(convertView == null)
			{
				
				
				holder = new ViewHolder();
				
				 convertView = inflater.inflate(R.layout.routinecustom, 
						 null);
				
				holder.Excname = (TextView)convertView.
						findViewById(R.id.excname);
				
				holder.ExcInfo = (TextView)convertView.
						findViewById(R.id.excinfo);
				
				convertView.setTag(holder);
			}
			
			else {
				
				
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.ExcInfo.setText(SiteList.Rout_set.get(position));
		
			
			System.out.println("SiteList.Rout_set.get(position) "
					  +    SiteList.Exc_Name.get(position));
			
			
			return convertView;
		}
		
		class ViewHolder
		{
			
			TextView Excname , ExcInfo ;
			
		}
		
	}

}
