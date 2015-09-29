/*
* Copyright 2011 Lauri Nevala.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


/* This class is a used to display calendar for workout log
 * and is displayed on click of workoutlog text
 * */
package the.gym.app.deft;

import java.net.URLEncoder;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Calendar_View extends Activity {

	public Calendar month;
	public CalendarAdapter adapter;
	public Handler handler;
	JSONObject jsonObject;
	JSONParser jsonParser ;
	JSONArray  MesageList;
	

	
	
	String[] Months = new String[]{"January" ,"February" , "March"
			,"April" ,"May" ,"June" ,"July" ,"August" ,"September",
			"October","November","December"};
	
	
	TextView GymId ;
	

	int year ;
	
	public ArrayList<String> items; 

	
	CustomDialogClass customDialogClass ;
	Calendar cal ;
	Integer YY , MM , DD ;
	JSONArray jsonArray;
	
	ProgressDialog dialog ;
	
	Button Home,Back ;
	
	JSONArray class_list_0 , class_list_1 , class_list_2 ,
	class_list_3 , class_list_4 , class_list_5 , class_list_6 ,class_list_7; 
	
	ArrayList<JSONArray> class_list = new ArrayList<JSONArray>();
	// container to store items in date1 array list which shall contain the dates
	   //  including the current date and after the current date 
	ArrayList<String>  date1 = new ArrayList<String>();
	
	ArrayList<String> id1     = new ArrayList<String>();
	ArrayList<String> Title1  = new ArrayList<String>();
	ArrayList<String> Descr1  = new ArrayList<String>();
	
	
	// container to store items in date1 array list which shall contain the dates
   // and before the current date 
	
	ArrayList<String>  date2 = new ArrayList<String>();
	
	ArrayList<String> id2     = new ArrayList<String>();
	ArrayList<String> Title2  = new ArrayList<String>();
	ArrayList<String> Descr2  = new ArrayList<String>();
	
	
	ArrayList<String> id_tosend     = new ArrayList<String>();
	ArrayList<String> Title_tosend  = new ArrayList<String>();
	ArrayList<String> Descr_tosend  = new ArrayList<String>();
	ArrayList<String> date_tosend   = new ArrayList<String>();
	SimpleDateFormat fromUser ;
	SimpleDateFormat myFormat ;
	
	boolean check = false ;
	
	private boolean hide =  true;
	private Calendar calendar;
	
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    
	    setContentView(R.layout.calendar);
	    
	    month = Calendar.getInstance();
	    
	    onNewIntent(getIntent());
	    
	    jsonParser = new JSONParser();
	     
	    customDialogClass = new CustomDialogClass(this);
	                items = new ArrayList<String>();
	              adapter = new CalendarAdapter(this, month);
	                  cal = Calendar.getInstance();
	    GridView gridview = (GridView) findViewById(R.id.gridview);
	                GymId = (TextView) findViewById(R.id.gymid);
	    gridview.setAdapter(adapter);
	    calendar = Calendar.getInstance();
	    year = calendar.get(Calendar.YEAR);
//	    GymId.setText(gymid)
	    
	      GymId.setText(Html.fromHtml("<b>GYM MEMBERSHIP ID : </b>") +customDialogClass.pref.getString(
	    		AllLink.GymId, ""));
	    
	    
	    final Calendar calendar = Calendar.getInstance();

		YY = calendar.get(Calendar.YEAR);
		MM = calendar.get(Calendar.MONTH);
		DD = calendar.get(Calendar.DAY_OF_MONTH);

	    handler = new Handler();
	    handler.post(calendarUpdater);
	    
	       TextView  title     = (TextView) findViewById(R.id.title);
	       TextView  previous  = (TextView) findViewById(R.id.previous);
	        TextView next      = (TextView) findViewById(R.id.next);
	                      Home = (Button)   findViewById(R.id.home);
	                      Back =  (Button)findViewById(R.id.backbutton);
	                  
	              
	                      
	                      /* this button click of this class
	                       * shall simply take the user to
	                       * homescreen*/
	                  Home.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							
							  //Calendar_View.this.finish();
							Intent intent=new Intent(Calendar_View.this,HomeScreen.class);
							 finish();
							 intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							 startActivity(intent);
						
						}
					});
	                  
	                  
	                  
	                  Back.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
						
							
							Calendar_View.this.finish();	
							
						}
					});
	                  
	                  
	                  
	                  
	                  
	                  
	                  
	                  
	                  
	                  
	                  
	                  
	                  
	                  
	                  
	                  
	     title.setText(android.text.format.DateFormat.format("MMMM yyyy", 
	    		 month));
	  
	     
	     /* this the click of textview in 
	      * the calendar and shall navigate backward to previous months
	      * */
	     previous.setOnClickListener(new OnClickListener() 
	    {
			
			@Override
			public void onClick(View v)
			{
				
				
			
				if(month.get(Calendar.MONTH)== 
				   month.getActualMinimum(Calendar.MONTH)) 
				{		
					year = year-1;;
					
				    month.set((
				    month.get(Calendar.YEAR)-1),
				    month.getActualMaximum(Calendar.MONTH),1);
				   System.out.println("month. in if in calendar");
			
				} 
				
				
				else 
				{
//					year = calendar.get(Calendar.YEAR);
					month.set(Calendar.MONTH,month.get(Calendar.MONTH)-1);
					
					 System.out.println("month. in else in calendar");
				}
				
				
				
				System.out.println("month.get(Calendar.MONTH)  " +
						"   " +month.get(Calendar.MONTH));
				
				System.out.println("month.getActualMaximum(Calendar.MONTH)    " +
						"   " +month.getActualMaximum(Calendar.MONTH));
				refreshCalendar();
				
				
//				Toast.makeText(Calendar_View.this,
//						"Pre is clicked", Toast.LENGTH_LONG).show();
			}
		});
	    
	    
	    
	     /* this the click of textview in 
	      * the calendar and shall navigate forward to coming months
	      * */
	     
	     
	    next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				if(month.get(Calendar.MONTH)== 
				   month.getActualMaximum(Calendar.MONTH)) {	
					
					year = year+1;
					
					
				   month.set((
						   
				   month.get(Calendar.YEAR)+1),
				   month.getActualMinimum(Calendar.MONTH),1);
				   System.out.println("month. in if in calendar");
				} 
				else 
				{
//					year = calendar.get(Calendar.YEAR);
					
					
					month.set(Calendar.MONTH,month.get
							 (Calendar.MONTH)+1);
					 System.out.println("month. in else in calendar");
				}
				
				
				System.out.println("month.get(Calendar.MONTH)  " +
						"   " +month.get(Calendar.MONTH));
				
				System.out.println("month.getActualMaximum(Calendar.MONTH)    " +
						"   " +month.getActualMaximum(Calendar.MONTH));
				refreshCalendar();
				
				
//				Toast.makeText(Calendar_View.this,
//						"Pre is clicked", Toast.LENGTH_LONG).show();
				refreshCalendar();
				
			}    
		});
	    
	    
	    /*  This is the click on date of calendar which 
	     * are in grid and shall take you to next class
	     * if it matches the dates in the array lists */
		gridview.setOnItemClickListener(new OnItemClickListener() {
		    
			public void onItemClick(AdapterView<?> parent, View v, 
		    		int position, long id) {
		    	TextView date = (TextView)v.findViewById(R.id.date);
		    
		    	   id_tosend.clear();
				Title_tosend.clear();
				Descr_tosend.clear();
				 date_tosend.clear();
				
				
		    	if(date instanceof TextView && !date.getText().equals("")) 
		    	{
		        	
		        	Intent intent = new Intent(Calendar_View.this ,
		        			Class_List.class);
		        	   String day = date.getText().toString();
		        	
		        	   if(day.length()==1) 
		        	   
		        	   {
		        		
		        		   day = "0"+day;
		        	
		        	   }
		        	   
		        	// return chosen date as string format 
		        
		        	   intent.putExtra("date", 
		        			android.text.format.DateFormat.format("yyyy-MM", month)
		        			+"-"+day);
		        	   try {
						if(myFormat.parse(android.text.format.DateFormat.format
								   ("yyyy-MM", month)
									+"-"+day).
								   before(new Date()))
								
							{

							   for (int i = 0; i < date2.size(); i++) {
									
								   
				
				
				
	try {
		
		
	System.out.println("myFormat.parse(date2.get(i)) 1     " +
			myFormat.parse(date2.get(i)));	
	
	System.out.println("myFormat.parse(date2.get(i)) 2     " +
			myFormat.parse(android.text.format.
					DateFormat.format("yyyy-MM", month)+"-"+day));	
	
	System.out.println("result is       "+
			myFormat.parse(date2.get(i)).
			compareTo(myFormat.parse(android.text.format.
					DateFormat.format("yyyy-MM", month)+"-"+day)));
	
	
		if(myFormat.parse(date2.get(i)).
				compareTo(myFormat.parse(android.text.format.
					DateFormat.format("yyyy-MM", month)+"-"+day))==0)
		{
			check = true ;

				
				id_tosend.add(id2.get(i));
				Title_tosend.add(Title2.get(i));
				Descr_tosend.add(Descr2.get(i));
				date_tosend.add(date2.get(i));

										}
									
									else if(check == false 
											&& (i+1)==date2.size() ) {
										
//								
										

							ConditionClas.Toastmethod(Calendar_View.this ,
									"no class on this day");
							
									}
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							   
							   
							}
						   
						   else {
							
						
							   
							   for (int i = 0; i < date1.size(); i++) {
									
								   System.out.println("status" +
									   		"      "
									   		+check 
										+ "  "+i +"   " + date1.size() );
								   try {
									if(myFormat.parse(date1.get(i)).
											equals(myFormat.parse(
													android.text.format.
													DateFormat.format("yyyy-MM", month)
								        			+"-"+day)))
											
										{
										   
										check = true ;
										
										
										id_tosend.add(id1.get(i));
										Title_tosend.add(Title1.get(i));
										Descr_tosend.add(Descr1.get(i));
										date_tosend.add(date1.get(i));
										}
									
									else if(check == false 
											&& (i+1)==date1.size() ) {
										

									}
									
	
		} 
		catch (ParseException e) 
		{
		// TODO Auto-generated catch block
		e.printStackTrace();
								
		}
		}
							   
							   
							   

						   }
						
						
if(check==true)
{
//intent.putExtra("id", id1.get(i));
//intent.putExtra("title", Title1.get(i));
//intent.putExtra("desc", Descr1.get(i));

//	System.out.println("id_tosend         " +id_tosend);
//	System.out.println("Title_tosend      " +Title_tosend);
//	System.out.println("Descr_tosend      " +Descr_tosend);
//	System.out.println("Descr_tosend      " +date_tosend);
	
	
intent.putStringArrayListExtra("idlist", id_tosend);
intent.putStringArrayListExtra("titlelist", Title_tosend);
intent.putStringArrayListExtra("desclist", Descr_tosend);
intent.putStringArrayListExtra("datelist", date_tosend);

startActivity(intent);
							
}

					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	   setResult(RESULT_OK, intent);
		        	   
		        	
//		        	   finish();
		        }
		        
		    }
		});
		
		if(ConditionClas.isNetworkAvailable(Calendar_View.this))
		{
		
		new ClassDetailsAsyncTask().execute("");
		}
		else {
			ConditionClas.Toastmethod(Calendar_View.this, 
					"There is no internet");
		}
	}
	
	public void refreshCalendar()
	{
		TextView title  = (TextView) findViewById(R.id.title);
		
		adapter.refreshDays();
		adapter.notifyDataSetChanged();				
		handler.post(calendarUpdater); // generate some random calendar items				
		System.out.println("MONTH.  " +month.get(Calendar.MONTH));
		
		if(month.get(Calendar.MONTH) == 0)
		{
			title.setText(Months[0]+ " " +String.valueOf(year));
		}
		else if (month.get(Calendar.MONTH) == 1) {
			title.setText(Months[1]+ " " +String.valueOf(year));
		}
else if (month.get(Calendar.MONTH) == 2) {
	title.setText(Months[2]+ " " +String.valueOf(year));
		}
else if (month.get(Calendar.MONTH) == 3) {
	title.setText(Months[3]+ " " +String.valueOf(year));
}
else if (month.get(Calendar.MONTH) == 4) {
	title.setText(Months[4]+ " " +String.valueOf(year));
}
else if (month.get(Calendar.MONTH) == 5) {
	title.setText(Months[5]+ " " +String.valueOf(year));
}
else if (month.get(Calendar.MONTH) == 6) {
	title.setText(Months[6]+ " " +String.valueOf(year));
}
else if (month.get(Calendar.MONTH) == 7) {
	title.setText(Months[7]+ " " +String.valueOf(year));
}
else if (month.get(Calendar.MONTH) == 8) {
	title.setText(Months[8]+ " " +String.valueOf(year));
}
else if (month.get(Calendar.MONTH) == 9) {
	title.setText(Months[9]+ " " +String.valueOf(year));
}
else if (month.get(Calendar.MONTH) == 10) {
	title.setText(Months[10]+ " " +String.valueOf(year));
}
else if (month.get(Calendar.MONTH) == 11) {
	title.setText(Months[11]+ " " +String.valueOf(year));
}
//		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
	}
	
	public void onNewIntent(Intent intent) 
	{
		     String date = intent.getStringExtra("date");
		String[] dateArr = date.split("-"); // date format is yyyy-mm-dd
		month.set(Integer.parseInt(dateArr[0]), 
				  Integer.parseInt(dateArr[1]),
				  Integer.parseInt(dateArr[2]));
	}
	
	public Runnable calendarUpdater = new Runnable() {
		
		@Override
		public void run() {
			items.clear();
			// format random values. You can implement a dedicated class to provide real values
//			for(int i=0;i<31;i++) {
//				Random r = new Random();
//				
//				if(r.nextInt(10)>6)
//				{
//					items.add(Integer.toString(i));
//				}
//			}

			
			
			date1.clear();
			date2.clear();
			  id1.clear();
			  id2.clear();
		   Descr1.clear();
		   Descr2.clear();
		   Title1.clear();
		   Title2.clear();
			
			System.out.println("SiteList.Log_Date       " +SiteList.ClassDate);
			
			 fromUser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 myFormat = new SimpleDateFormat("yyyy-MM-dd");

			
			
//			date1.clear();
			 
			 /* This for loop is to create two seprate lists 
			  * of dates 
			  * date1 contains all the dates which shall come after the 
			  * current date
			  * 
			  * and make lists of class data related to those dates
			  * 
			  * and date2 list contains the date which were before
			  * the current date
			  * 
			  * and make lists of class data related to those dates
			  * */
			 
			System.out.println("fromUser out of for "+
					SiteList.ClassDate);
			for(int i=0;i<SiteList.ClassDate.size();i++)
			{
			
//				Random r = new Random();
				
				try 
				{ 
					System.out.println("fromUser inside for" +
							"            "+
							fromUser.parse(SiteList.ClassDate.get(i)).equals(new Date()));

					System.out.println("fromUser give date " +
							"            "+
							fromUser.parse(SiteList.ClassDate.get(i)));
				
					System.out.println("fromUser current date" +
							"            "+
							new Date());
					
					if(fromUser.parse(SiteList.ClassDate.get(i)).before(new Date())||
					   fromUser.parse(SiteList.ClassDate.get(i)).equals(new Date()))
						
					{
						
						
			date2.add(myFormat.format(fromUser.parse(SiteList.ClassDate.get(i))));
			
			   id2.add(SiteList.ClassId.get(i));
			Title2.add(SiteList.ClassName.get(i));
			Descr2.add(SiteList.ClassDesc.get(i));
			
//		System.out.println
//		(
//				"fromUser.parse(SiteList.Log_Date.get(i))." +
//				"before(new Date())  " +
//								"     "+
//				fromUser.parse(SiteList.ClassDate.get(i)).before(new Date()));

		}
			 			
		else 
		{
		
			date1.add(myFormat.format(fromUser.parse(SiteList.ClassDate.get(i))));
			  id1.add(SiteList.ClassId.get(i));
		   Title1.add(SiteList.ClassName.get(i));
		   Descr1.add(SiteList.ClassDesc.get(i));
		}
					
				
				} 
				
				catch (ParseException e) 
				{
				    e.printStackTrace();
				}
			}
				
				
				System.out.println("date1 avi1    " +date1);
				System.out.println("date2 avi1    " +date2);
			
			adapter.setItems(date1);
			adapter.setItems2(date2);
			
			adapter.notifyDataSetChanged();
		}
	};
	
	
	/* This is the parsing class which performs the call 
	 * the link of get content of classes for the user*/
	class ClassDetailsAsyncTask extends AsyncTask<String, Void, String>
	{

		 
		

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			
			
			 @SuppressWarnings("deprecation")
		
			                                                  
			 String encode = URLEncoder.encode("{"+"\"gymid\""+ ":" 
			+"\""+customDialogClass.pref.getString(AllLink.UserId, 
					
					"")+"\""+","+
					"\"auth\""+ ":"+
					"\""+customDialogClass.pref.getString(AllLink.AuthKey, 
							"")+"\""+"}");
			 
				SiteList.ClassName.clear();
				SiteList.ClassDesc.clear();
				SiteList.ClassDate.clear();
				SiteList.ClassId.clear();
			 
			 
			 
			 System.out.println("all links are  ----           "+
			AllLink.getClass+"{"+"\"gymid\""+ ":" 
			+"\""+customDialogClass.pref.getString(AllLink.UserId, 
					
					"")+"\""+","+
					"\"auth\""+ ":"+
					"\""+
					customDialogClass.pref.getString(AllLink.AuthKey, 
							"")+"\""+"}");
			
			
			jsonObject = jsonParser.getJSONFromUrl(AllLink.getClass+encode);
			
			  
//			System.out.println("jsonObject     "+jsonObject.keys());
//			System.out.println("jsonObject     "+jsonObject.names());
//			
//			System.out.println("jsonObject     "+jsonObject.length());
			
			
			if(jsonObject != null)
			{		
			
				try {
				   

				System.out.println("  inside if  ");
				
//				class_list = jsonObject.getJSONArray("0");
				
//				System.out.println("class_list    "+class_list);
				
				for (int i = 0; i < jsonObject.length()-2; i++) {
					
					class_list_0 = jsonObject.getJSONArray(String.valueOf(i));
					createarralist(class_list_0);
					
					System.out.println("ClassName 0      " +class_list_0);
					
				}
				
//				if (jsonObject.getJSONArray("0") != null) {
//					class_list_0 = jsonObject.getJSONArray("0");
//					createarralist(class_list_0);
//				}
//				if (jsonObject.has("1") &&jsonObject.getJSONArray("1")!= null) 
//				{
//					class_list_1 = jsonObject.getJSONArray("1");;
//					createarralist(class_list_1);
//				}
//				
//				
//				if(jsonObject.has("2") &&jsonObject.getJSONArray("2")!= null){
//				
//					class_list_2 = jsonObject.getJSONArray("2");
//					createarralist(class_list_2);
//				}
//				
//				
//				
//				if(jsonObject.has("3") &&jsonObject.getJSONArray("3")!= null){
//				
//					class_list_3 = jsonObject.getJSONArray("3");
//					createarralist(class_list_3);
//				}
//				
//				if(jsonObject.has("4") &&jsonObject.getJSONArray("4")!= null){
//					
//					class_list_4 = jsonObject.getJSONArray("4");
//					createarralist(class_list_4);
//				}
//				
//				
//                if(jsonObject.has("5") &&jsonObject.getJSONArray("5")!= null){
//					
//					class_list_5 = jsonObject.getJSONArray("5");
//					createarralist(class_list_5);
//				}
//				
//				
//                
//
//                if(jsonObject.has("6") &&jsonObject.getJSONArray("6")!= null){
//					
//					class_list_6 = jsonObject.getJSONArray("6");
//					createarralist(class_list_6);
//				}
//                
//                if(jsonObject.has("6") &&jsonObject.getJSONArray("7")!= null){
//					
//         					class_list_7 = jsonObject.getJSONArray("7");
//         					createarralist(class_list_7);
//         				}
				
				System.out.println("ClassName        " +SiteList.ClassName);
				System.out.println("ClassName        " +SiteList.ClassDate);
//				System.out.println("ClassName 0      " +class_list_0);
//				System.out.println("ClassName 1      " +class_list_1);
//				System.out.println("ClassName 2      " +class_list_2);
//				System.out.println("ClassName 3      " +class_list_3);
//				System.out.println("ClassName 4      " +class_list_4);
//				System.out.println("ClassName 5      " +class_list_5);
//				System.out.println("ClassName 6      " +class_list_6);
//				System.out.println("ClassName 7      " +class_list_7);
			} 
				
				catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("e.getmesssage    "+e.getMessage());
			}
			
			
			}
			else {
				
				hide = false ;
				
			}
			return null;
		}
		
		@Override
		protected void onPreExecute()
		{
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			dialog = new ProgressDialog(Calendar_View.this);
			
//			dialog.setTitle("Fetching content of Class");
		    dialog.setMessage("Please wait while processing");
		    dialog.setCancelable(false);
		    dialog.show();
		    
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
		dialog.dismiss();
		refreshCalendar();
		
		if(hide == false)
		{
			ConditionClas.Toastmethod(Calendar_View.this, 
					"No associated classes found with this user id");
		}
		
		else if(hide){
//			class_List.setAdapter(new ClassCustomList());
		}
		
		
		
		}
	}
	
	
	class CustomListRoutine extends BaseAdapter
	{

		@Override
		public int getCount() 
		{
			// TODO Auto-generated method stub
			return jsonArray.length();
		}

		@Override
		public Object getItem(int arg0) 
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) 
		{
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) 
		{
			// TODO Auto-generated method stub
			
			LayoutInflater inflater = (LayoutInflater) Calendar_View.this.
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
	
	private void createarralist(JSONArray which) {
		// TODO Auto-generated method stub
		
		
		try {
			for (int i = 0; i < which.length(); i++) {
				
				
				JSONObject ob = which.getJSONObject(i);
				
				SiteList.ClassName.add(ob.getString("cls_title"));
				SiteList.ClassDesc.add(ob.getString("cls_description"));
				SiteList.ClassDate.add(ob.getString("cls_start"));
				SiteList.ClassId.add(ob.getString("id"));
				
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public class CalendarAdapter extends BaseAdapter {
		  
		static final int FIRST_DAY_OF_WEEK =0; // Sunday = 0, Monday = 1
		
		                                                      
		private Context mContext;
	                                
	    private java.util.Calendar month;
	    private Calendar selectedDate;
	    private ArrayList<String> items , item2;
	    Calendar calendar ;
	    Integer count ;
	    public CalendarAdapter(Context c, Calendar monthCalendar) {
	    
	    	       month = monthCalendar;
	    	    calendar = Calendar.getInstance();
	    	selectedDate = (Calendar)monthCalendar.clone();
	    	    mContext = c;
	        
	    	    month.set(Calendar.DAY_OF_MONTH, 1);
	        
	    	    this.items = new ArrayList<String>();
	            this.item2 = new ArrayList<String>();
	        
	        refreshDays();
	    }
	    
	    public void setItems(ArrayList<String> items) 
	    {
	    	for(int i = 0;i != items.size();i++)
	    	{
	    		if(items.get(i).length()==1) 
	    		{
	    		items.set(i, "0" + items.get(i));
	    		}
	    	}
	    	this.items = items;
	    }        
	    
	    public void setItems2(ArrayList<String> items) 
	    {
	    	
	    	for(int i = 0;i != items.size();i++)
	    	{
	    		if(items.get(i).length()==1) 
	    		
	    		{
	    	
	    			items.set(i, "0" + items.get(i));
	    		
	    		}
	    	}
	    	
	    	this.item2 = items;
	    }
	    
	 
	    public int getCount() {
	        return days.length;
	    }
	   
	    public Object getItem(int position) 
	    {
	        return null;
	    }

	    public long getItemId(int position) 
	    {
	        return 0;
	    }

	    // create a new view for each item referenced by the Adapter
	    public View getView(int position, View convertView, ViewGroup parent) 
	    {
	        View v = convertView;
	    	TextView dayView;
	        if (convertView == null)
	        {  // if it's not recycled, initialize some attributes
	       LayoutInflater vi 
	        	  = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            v = vi.inflate(R.layout.calendar_item, null);
	        	
	        }
	        dayView = (TextView)v.findViewById(R.id.date);
	        
	        // disable empty days from the beginning
	        if(days[position].equals("")) 
	        {
	        	dayView.setClickable(false);
	        	dayView.setFocusable(false);
	        }
	        else 
	        {
	        	// mark current day as focused
	        	if(month.get(Calendar.YEAR)== selectedDate.get(Calendar.YEAR) 
	        			&& month.get(Calendar.MONTH)== 
	        			selectedDate.get(Calendar.MONTH) 
	        			&& days[position].equals(""
	        			+selectedDate.get(Calendar.DAY_OF_MONTH)))
	        	{
	        		v.setBackgroundResource(R.drawable.item_background_focused);
	        	}
	        	else 
	        	{
	        		v.setBackgroundResource(R.drawable.list_item_background);
	        	}
	        }
	        dayView.setText(days[position]);
	        dayView.setTextColor(Color.BLACK);
	        // create date string for comparison
	        
	        String monthStr = ""+(month.get(Calendar.MONTH)+1);
	    	if(monthStr.length()==1) 
	    	{
	    		monthStr = "0"+monthStr;
	    	}
	    	
	    	System.out.println("item year is  " +year);
	        String date = year+"-"+monthStr+"-"+ days[position];
//	        String date = days[position];
	        if(date.length()==1) 
	        {
	    		date = "0"+date;
	    	}
//	    	String monthStr = ""+(month.get(Calendar.MONTH)+1);
//	    	if(monthStr.length()==1) {
//	    		monthStr = "0"+monthStr;
//	    	}
	       
	        // show icon if date is not empty and it exists in the items array
	        
	        
	        ImageView iw = (ImageView)v.findViewById(R.id.date_icon);
	        ImageView iw2 = (ImageView)v.findViewById(R.id.date_icon2);
	        System.out.println("items is         " +items);
	        System.out.println("items is         " +item2);
	        System.out.println("items is          " +date );
	      
	        System.out.println("items.contains(date.trim()     "
	        		+items.contains(date.trim()));
	        
	        
	        /* 
	         * 
	         * in case if date is after the current date ,
	         * a gray dot shall display on calendar
	         * and if the date is same or after the current date
	         * the imageview shall display the green dot on those dates
	         * 
	         * 
	         */
	        
	        
	        if(date.length()>0 && items != null && items.contains(date.trim())) 
	        {        	
	        	iw2.setVisibility(View.VISIBLE);
	        	System.out.println("inside green");
	        	iw2.setBackgroundResource(R.drawable.icongreen);
	        }
	        
	        else 
	        {
	        	iw2.setVisibility(View.INVISIBLE);
	        }
	        
	        
	        if(date.length()>0 && item2!=null && item2.contains(date.trim())) 
	        {        
	        	System.out.println("inside gray ");
	        	iw.setVisibility(View.VISIBLE);
	        	iw.setBackgroundResource(R.drawable.icon_grey);
	        }
	        
	        else 
	        {
	        	iw.setVisibility(View.INVISIBLE);
	        }
	        
	        
	        
	        return v;
	    }
	    
	    public void refreshDays()
	    {
	    	// clear items
	    	
	    	items.clear();
	    	
	    	 int lastDay = month.getActualMaximum(Calendar.DAY_OF_MONTH);
	        int firstDay = (int)month.get(Calendar.DAY_OF_WEEK);
	        
	        // figure size of the array
	        if(firstDay==1)
	        {
	        	days = new String[lastDay+(FIRST_DAY_OF_WEEK*6)];
	        }
	        else
	        {
	        	days = new String[lastDay+firstDay-(FIRST_DAY_OF_WEEK+1)];
	        }
	        
	        int  j = FIRST_DAY_OF_WEEK;
	        
	        // populate empty days before first real day
	        if(firstDay>1)
	        {
		        for(j=0;j<firstDay-FIRST_DAY_OF_WEEK;j++) 
		        {
		        	days[j] = "";
		        }
	        }
		    else 
		    {
		    	for(j=0;j<FIRST_DAY_OF_WEEK*6;j++) 
		    	{
		        	days[j] = "";
		        }
		    	j=FIRST_DAY_OF_WEEK*6+1; // sunday => 1, monday => 7
		    }
	        
	        // populate days
	        int dayNumber = 1;
	        for(int i=j-1;i<days.length;i++) 
	        {
	        	
	        	if(dayNumber<10)
	        	{
	        		days[i] = "0"+dayNumber;
	        	}
	        	else
	        	{
	        		days[i] = ""+dayNumber;
				}
	        	
	        	dayNumber++;
	        }
	    }

	    // references to our items
	    public String[] days;
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		check = false ;
	}
}
