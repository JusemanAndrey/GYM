package the.gym.app.deft;


/* This is the class to show workout log*/
import java.net.URLEncoder;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;




import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

public class Workoutlog extends Activity {

	public  ArrayList<Integer> list = new ArrayList<Integer>();
	public  ArrayList<String> outDate = new ArrayList<String>();
	public  ArrayList<String> listd = new ArrayList<String>();
	public  ArrayList<Integer> list1 = new ArrayList<Integer>();
	public  ArrayList<String> listd1 = new ArrayList<String>();
	public  ArrayList<Integer> listsum = new ArrayList<Integer>();
	ExpandableListView Routine ;
	
	JSONParser parser ;
	
	int temp = 0;
//	 ExpandableListAdapter listAdapter;
	     
//	  List<String> listDataHeader;
//	  
//	  HashMap<String, List<String>> listDataChild;
	    
	  List<String> nowShowing = new ArrayList<String>();
	   
	  List<String> comingSoon = new ArrayList<String>();
       
	private JSONObject jsonObjectWorklog;
    
	List<String> top250 = new ArrayList<String>();
	
    JSONArray MesageList = null ;
	
	JSONArray Mon , Tue , Wed , Thr , Fri , Sat , Sun ;
	
	
	JSONObject jsonObject ;
	
	
	CustomDialogClass customDialogClass ;
	
	Button BackButton ;
	
	ProgressDialog dialog ;
	
	String[] Msg ;
	
	String Date_Rec = "" ;
	String Date_toadd = "" ;
	String Date_temp = "" ;
	
	TextView GymId ;

	int pos= 1;
	private SimpleDateFormat fromUser;

	private SimpleDateFormat myFormat , myFormat1;
	
	int indextoshow = 0;
	
	SimpleDateFormat sdf;
	SimpleDateFormat formatter;
	private HashMap<String, List<String>> listDataChild;
	private ArrayList<String> listDataHeader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.workoutlog);
		

		fromUser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 myFormat = new SimpleDateFormat("yyyy-MM-dd");
		 
		 myFormat1 = new SimpleDateFormat("HH:mm:ss");
		parser = new JSONParser();
		
		Msg = new String[1];
		
		customDialogClass = new CustomDialogClass(this);
		
		
//		customDialogClass = new CustomDialogClass(this);
		
		customDialogClass.DateInit(this);
		
		
		Date_Rec = getIntent().getStringExtra("date");
		
		System.out.println("Date_Rec     " +Date_Rec);
		
		   Routine = (ExpandableListView)findViewById(R.id.routinelist);
		   
		   BackButton = (Button)findViewById(R.id.backbutton);
		   
		   GymId = (TextView)findViewById(R.id.gymid);
		     
		     GymId.setText(Html.fromHtml("<b>GYM MEMBERSHIP ID : </b>") +customDialogClass.pref.getString
		    		 
		    		 (AllLink.GymId, ""));
		     
		     
		if (ConditionClas.isNetworkAvailable(Workoutlog.this)) 
		
		{
			
			new WorkoutlogParsing().execute("");			
			
		} 
		
		else 
		{

			ConditionClas.Toastmethod(getApplicationContext(), 
					
					"There is no internet");
	
		}
		
		
		/* this button click is to take the user simply to
		 * home screen */
		BackButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			/*
				Intent i = new Intent(Workoutlog.this , HomeScreen.class);
				
				Workoutlog.this.finish();
				
				startActivity(i);*/
				
				Workoutlog.this.finish();
			
			}
		});
		
		
		
	}
	
	
	/* This class is to parse the content of link of
	 * workout log*/
	class WorkoutlogParsing extends AsyncTask<String, Void, String>
	{
		
		

//		private JSONObject jsonObject2;
		
		private String StatusLog;

		@SuppressWarnings("deprecation")
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			
			String encoded = "" ;
			
	
			System.out.println("in do in background");
			SiteList.Exc_Name.clear();
			SiteList.Excinfo.clear();
			SiteList.Log_reps.clear();
			SiteList.Log_Date.clear();
			SiteList.Log_Ints.clear();
			SiteList.Exces_Id.clear();
			SiteList.Log_Sets.clear();
			SiteList.Log_Nots.clear();
			
			
			// encoding of arguments used in link of 
			// workout log
			encoded = URLEncoder.encode("{"+"\"userid\""+ ":" 
					+"\""+customDialogClass.pref.getString(
							AllLink.UserId, 
							"")+"\""+","+
					"\"auth\""+ ":"+
					"\""+customDialogClass.pref.getString(
							AllLink.AuthKey, 
							"")+
					"\"" +"}");
			                  
			
			System.out.println("to hit link is       " +
					AllLink.getLogs + "{"+"\"userid\""+ ":" 
				
					+"\""+customDialogClass.pref.getString(
					
							AllLink.UserId, 
							
							"")+"\""+","+
					"\"auth\""+ ":"+
					
					"\""+customDialogClass.pref.getString(
							AllLink.AuthKey, 
							"")+
					"\"" +"}");
			
			
			jsonObjectWorklog = parser.getJSONFromUrl(AllLink.getLogs+encoded);
			
			

			
			System.out.println("jsonObject2      " +jsonObjectWorklog);
			
			
			
			if(jsonObjectWorklog != null)
			{

			try
			{
				
				StatusLog = jsonObjectWorklog.getString("status");
				
				System.out.println("StatusLog     " +StatusLog);
				
				if(StatusLog.equalsIgnoreCase("success"))
					
				{
					try {
						MesageList = jsonObjectWorklog.getJSONArray("data");
						
						System.out.println("MesageList    " +MesageList.length());
						if(MesageList.length()>0)
							
						{
							
//							listDataHeader = new ArrayList<String>();
//					         listDataChild = new HashMap<String, List<String>>();
//							
							System.out.println("messge list not   null   "); 
							
						System.out.println("MesageList     " +MesageList);
						for (int i = 0; i < MesageList.length(); i++) {
						
							JSONObject c = MesageList.getJSONObject(i);
//							
							
							/* log_reps */
							
							
							if((c.getString("log_reps")).length()>0)
							{
							SiteList.Log_reps.add(c.getString("log_reps"));
							
//							 comingSoon.add("log_reps");
							}
							
							else
							{
								SiteList.Log_reps.add("");
							}
							
							/* excercise Name */
							
							
							if((c.getString("lognotes")).length()>0)
							{
							SiteList.Log_Nots.add(c.getString("lognotes"));
//							listDataHeader.add(c.getString("execname"));
							}
							
							else 
							{
								SiteList.Log_Nots.add("");
							}
							
					   		
	                        /* excercise Name */
							
							
							if((c.getString("execname")).length()>0)
							{
							SiteList.Exc_Name.add(c.getString("execname"));
//							listDataHeader.add(c.getString("execname"));
							}
							
							else 
							{
								SiteList.Exc_Name.add("");
							}
							
							
							
							/* Log Date */
							
							
							if((c.getString("log_date")).length()>0)
							{
																
						SiteList.Log_Date.add(c.getString("log_date"));
							
//					        top250.add(c.getString("log_date"));
							}
							
							else 
							{
							
								SiteList.Log_Date.add("");
							
							}
							
							
							/* log_intensity */
							
							if((c.getString("log_intensity")).length()>0)
							{
								
								
//						        nowShowing.add(c.getString("log_intensity"));
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
						System.out.println("length of list is   " +
								" " +list);
						
						}
						
						else {
							
							System.out.println("messge list null   ");
							
							SiteList.Error.add(jsonObjectWorklog.getString("error") +" " 
							+" data exist");
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				
				}
				
				else {
					
					System.out.println("status log is    " +StatusLog);
					
//					oneValue[0] = MessageDisplay; 
					
					Msg[0] = Msg[0] = jsonObjectWorklog.getString("msg");
				}
			}
			
			catch (JSONException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
			}
			return null;
		
		}
		
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			dialog = new ProgressDialog(Workoutlog.this);
			dialog.setCancelable(false);
			dialog.setMessage("Please wait while processing");
			dialog.setTitle("Fetching workout");
			dialog.show();
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			dialog.dismiss();
			

			if(jsonObjectWorklog != null)
				
			{
				
			try {
				Date date11 , date22;
				for (int i = 0; i < SiteList.Log_Date.size(); i++) {
					date11 = fromUser.parse(SiteList.Log_Date.get(i));
					String output1 = myFormat.format(date11);
					outDate.add(output1);
				}
				
				System.out.println("outDate   " +outDate);
				if (SiteList.Log_reps.size()>0) {
					
					System.out.println(AllLink.TAG_VALUEIS+"lognote" +SiteList.Log_Nots);
					System.out.println(AllLink.TAG_VALUEIS+"lognote" +listd);     
					System.out.println(AllLink.TAG_VALUEIS+"lognote" +list);  
//					prepareListData();
					Set<String> unique = new HashSet<String>(outDate);
					HashMap hashMap = new HashMap();
					for (String key : unique) {
					    System.out.println(key + ": " + Collections.frequency(outDate, key));
			
					    hashMap.put(key, Collections.frequency(outDate, key));
					    listd.add(key);
//					    list.add(Collections.frequency(outDate, key));
					    
					}
//					Collections.reverse(listd);
//					Collections.reverse(list);
							
					System.out.println("listd  "+hashMap);
					System.out.println("listd  "+listd);
					System.out.println("listd  "+list);
					
					dateedString();
//					System.out.println("listd  "+hashMap.get(listd.get(0)));
//					System.out.println("listd  "+hashMap.get(listd.get(1)));
//					System.out.println("listd  "+hashMap.get(listd.get(2)));
					
					for (int i = 0; i < listd.size(); i++) {
						
					String abc = hashMap.get(listd.get(i)).toString();
					list.add(Integer.parseInt(abc));
					}
					Collections.reverse(listd);
					Collections.reverse(list);
					System.out.println("listd  "+listd);
					System.out.println("listd  "+list);
//					prepareListData();
//					Routine.setAdapter(new CustomList());
					System.out.println("listsumi     " +list);
					for (int i = 0; i < list.size(); i++) {
						
						System.out.println("listsumi     " +list.get(i));
						if(i>0)
						{
							System.out.println("listsumii     " +list.get(i-1));
							System.out.println("listsumii     " +list.get(i));
						listsum.add(listsum.get(i-1)+list.get(i));
						}
						else {
							listsum.add(list.get(i));
						}
					}
					System.out.println("listsum       " +listsum);
					Routine.setAdapter(new ExpandableListAdaptersample());
					
					Routine.setOnItemClickListener(new AdapterView.
							OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							// TODO Auto-generated method stub
							
						
							indextoshow = arg2;
						}
					});
					
					Routine.setOnGroupClickListener(new OnGroupClickListener() {
						
						@Override
						public boolean onGroupClick(ExpandableListView parent, View v,
								int groupPosition, long id) {
							// TODO Auto-generated method stub
//							Toast.makeText(Workoutlog.this, "clicked position is" +
//									"   " +(0+groupPosition-1), Toast.LENGTH_LONG).show();

							return false;
						}
					});
					Routine.setOnChildClickListener(new OnChildClickListener() {
						
						@Override
						public boolean onChildClick(ExpandableListView parent, View v,
								int groupPosition, int childPosition, long id) {
							// TODO Auto-generated method stub
							
						
							
							if(groupPosition>0)
							{
								
						//							Toast.makeText(Workoutlog.this, "clicked position is" +
//									"   " +SiteList.Log_Nots.get(
//											(childPosition+groupPosition-1)), Toast.LENGTH_LONG).show();
						
							showAlert(SiteList.Log_Nots.get(
									(childPosition+listsum.get(groupPosition-1))));
							}
							else {
								
					
//								Toast.makeText(Workoutlog.this, "clicked position is" +
//										"   " +SiteList.Log_Nots.get((childPosition+groupPosition)), Toast.LENGTH_LONG).show();

								showAlert(SiteList.Log_Nots.get(
										(childPosition)));
							}
							
								
							return false;
						}

						
					});
				}
				
				else {
					System.out.println("no adapter");
					System.out.println("no SiteList.Error    " +SiteList.Error);
					
					Routine.setAdapter(new ArrayAdapter<String>(
							Workoutlog.this, R.layout.routinecustom ,
							R.id.excinfo ,  SiteList.Error
							));
				}
			} 
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				System.out.println("e.messgae        " +e.getMessage());
			}
			
			}

		else

			{
	
			System.out.println("no content");

			} 

		}


		
		
	}
	
	
	
	/* this class is to create custom list for 
	 * workout log*/
//	class CustomListRoutine extends BaseAdapter
//	{
//
//		@Override
//		public int getCount() {
//			// TODO Auto-generated method stub
//			return SiteList.Excname.size();
//		}
//
//		@Override
//		public Object getItem(int arg0) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		@Override
//		public long getItemId(int position) {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			// TODO Auto-generated method stub
//			
//			LayoutInflater inflater = (LayoutInflater) Workoutlog.this.
//					getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//			
//			ViewHolder holder;
//			if(convertView == null)
//			{
//				
//				
//				holder = new ViewHolder();
//				
//				 convertView = inflater.inflate(R.layout.routinecustom, null);
//				
//				holder.Excname = (TextView)convertView.findViewById(R.id.excname);
//				holder.ExcInfo = (TextView)convertView.findViewById(R.id.excinfo);
//				
//				convertView.setTag(holder);
//			}
//			
//			else {
//				
//				
//				holder = (ViewHolder) convertView.getTag();
//			}
//			
//			holder.ExcInfo.setText(SiteList.Excinfo.get(position));
//			holder.Excname.setText(SiteList.Excname.get(position));
//			
//			return convertView;
//		}
//		
//		class ViewHolder
//		{
//			
//			TextView Excname , ExcInfo ;
//			
//		}
//		
//	}
	
	class CustomList extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return MesageList.length();
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
					Workoutlog.this.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			
			ViewHolder holder = null ;
			
			if(arg1 == null)
			{
			
				
				arg1 = inflater.inflate(R.layout.classcustomelement, null);
				
				holder = new ViewHolder();
				
//				holder.CountsTv      = (TextView)arg1.findViewById(R.id.counttv);
//				holder.DateTv        = (TextView)arg1.findViewById(R.id.datetV);
//				holder.ExcerciseName = (TextView)arg1.findViewById(R.id.enametV);
//				holder.Repetition    = (TextView)arg1.findViewById(R.id.reptV);
				
				holder.Heading = (TextView)arg1.findViewById(R.id.headingtv);
				   holder.Date = (TextView)arg1.findViewById(R.id.datetv);
				
				arg1.setTag(holder);
			}
			
			else 
			{
				
				holder = (ViewHolder) arg1.getTag();	
		
			}
		
			
//			holder.CountsTv.setText(String.valueOf(arg0+1));
//			holder.ExcerciseName.setText(SiteList.Exc_Name.get(arg0));
//			holder.DateTv.setText(SiteList.Log_Date.get(arg0));
//			holder.Repetition.setText(SiteList.Log_reps.get(arg0)+" Repetition");
			
			holder.Heading.setText(SiteList.Exc_Name.get(arg0));
			holder.Date.setText(SiteList.Log_Date.get(arg0));	
			
			
			return arg1;
		}
		
		
		private class ViewHolder
		{
//	        TextView CountsTv , DateTv , ExcerciseName , Repetition ;
	        
	        TextView Heading , Date ; 
	    }
	}
	private void prepareListData() {}
	
	
	
	
	

	
	
	/* this is the class to make an expandable list 
	 * which can show the content of the
	 * logview in expandable list */
	class ExpandableListAdaptersample extends  BaseExpandableListAdapter
	{

		private Date date;

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			
			
			
			LayoutInflater inflater = (LayoutInflater)
					Workoutlog.this.getSystemService(Activity.
							LAYOUT_INFLATER_SERVICE);
			
			ViewHolder holder = null ;
			
			if(convertView == null)
			{
			
				
				convertView = inflater.inflate(R.layout.workoutloglistobject, null);
				
				holder = new ViewHolder();
				
				holder.Sets      = (TextView)convertView.findViewById(R.id.sets	);
				holder.DateTv        = (TextView)convertView.findViewById(R.id.time);
				holder.ExcerciseName = (TextView)convertView.findViewById(R.id.excname);
				holder.Repetition    = (TextView)convertView.findViewById(R.id.reps);
				holder.Intensity    = (TextView)convertView.findViewById(R.id.intensity);
//				holder.Repetition    = (TextView)convertView.findViewById(R.id.reps);
				
				
//				holder.Heading = (TextView)convertView.findViewById(R.id.headingtv);
//				   holder.Date = (TextView)convertView.findViewById(R.id.datetv);
				
				convertView.setTag(holder);
			}
			
			else 
			{
				
				holder = (ViewHolder) convertView.getTag();	
		
			}
		
			System.out.println(AllLink.TAG_VALUEIS +"childvala " +childPosition);
			
			if(groupPosition > 0)
			{
				System.out.println(AllLink.TAG_VALUEIS +"childvala " +childPosition);
				
			holder.ExcerciseName.setText(String.valueOf(SiteList.Exc_Name.get(childPosition+
					listsum.get(groupPosition-1))));
			
			
			
			holder.Sets.setText("Weight:   "+SiteList.Log_reps.get(childPosition+
					listsum.get(groupPosition-1)));
			
			
			try {
				date = fromUser.parse(SiteList.Log_Date.get(childPosition+
						listsum.get(groupPosition-1)));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				                      
			}
			String outputDateStr = myFormat1.format(date);
//			holder.DateTv.setText(outputDateStr);
			
//			holder.DateTv.setTextColor(Color.BLUE);
		 
			holder.Intensity.setText("Intensity:   " 
			+SiteList.Log_Ints.get(childPosition+listsum.get(groupPosition-1)));
			
			
			holder.Repetition.setText("Reps: " 
			+SiteList.Log_Sets.get(childPosition+listsum.get(groupPosition-1)));
			
			
		
			
			}
			
			else {
				

				if (childPosition<9) {
					
					
					
				
				holder.ExcerciseName.setText(String.valueOf(SiteList.
						Exc_Name.get(childPosition)));
				
				
				holder.Sets.setText("Weight:   "
						+SiteList.Log_reps.get(childPosition));
				
				
				try {
					date = fromUser.parse(SiteList.Log_Date.get(childPosition));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String outputDateStr = myFormat1.format(date);
//				holder.DateTv.setText(outputDateStr);
				
//				holder.DateTv.setTextColor(Color.BLUE);
			 
				holder.Intensity.setText("Intensity:   " 
				+SiteList.Log_Ints.get(childPosition));
				
				
				holder.Repetition.setText("Reps:   " 
				+SiteList.Log_Sets.get(childPosition));
				
			
				
				}
			
				
				
			}
			
			
			
			
			holder.Sets.setTextColor(Color.BLACK);
			holder.Intensity.setTextColor(Color.BLACK);
			holder.Repetition.setTextColor(Color.BLACK);
			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			
			return list.get(groupPosition);
		}
		
		

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			
			return SiteList.Exc_Name.get(groupPosition);
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			
			return listd.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			
			
			
			LayoutInflater inflater = (LayoutInflater)
					Workoutlog.this.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			
			ViewHolder holder = null ;
			
			if(convertView == null)
			{
			
				
				convertView = inflater.inflate(R.layout.classcustomelement2, null);
				
				holder = new ViewHolder();
				
				
				holder.Heading = (TextView)convertView.findViewById(R.id.headingtv);
//				   holder.Date = (TextView)convertView.findViewById(R.id.datetv);
				
				convertView.setTag(holder);
			}
			
			else 
			{
				
				holder = (ViewHolder) convertView.getTag();	
		
			}
//			try {
//				
//				date = fromUser.parse(listd.get(groupPosition));
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			String outputDateStr = myFormat.format(date);
			
			holder.Heading.setText(listd.get(groupPosition));
			
//			holder.Heading.setText(SiteList.Log_Date.get(groupPosition));
			
			holder.Heading.setTextColor(Color.BLACK);
			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}
		                 
	}
	
	class ViewHolder
	{
		 TextView Sets , DateTv , ExcerciseName , Repetition , Intensity ;
	        TextView Heading , Date ; 
	}
	
	private void showAlert(String string) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				Workoutlog.this);

		// Setting Dialog Title
		alertDialog.setTitle("Notes");

		// Setting Dialog Message
		if(string.length()>0)
		{
		alertDialog.setMessage(string);
		}
		else {
			alertDialog.setMessage("No Added notes");
		}

		// Setting Icon to Dialog

		// Setting Positive "Yes" Button
		alertDialog.setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						// Write your code here to invoke YES event
						// Toast.makeText(getApplicationContext(),
						// "You clicked on YES", Toast.LENGTH_SHORT).show();
						
//						final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
						dialog.dismiss();
					}
				});

		

		// Showing Alert Message
		alertDialog.show();
	}
	
	void dateedString()
	{
		 Collections.sort(listd, new Comparator<String>() {
		        DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		        @Override
		        public int compare(String o1, String o2) {
		            try {
		                return f.parse(o1).compareTo(f.parse(o2));
		            } catch (ParseException e) {
		                throw new IllegalArgumentException(e);
		            }
		        }
		    });
		 
		 System.out.println("listd    " +listd);
	}
}
