//package the.gym.app.deft;
//
//import java.net.URLEncoder;
//
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Random;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.GridView;
//import android.widget.TextView;
//import android.widget.AdapterView.OnItemClickListener;
//
//public class WorkoutlogCalendar extends Activity {
//
//	public Calendar month;
//	public CalendarAdapter adapter;
//	public Handler handler;
//	JSONObject jsonObject;
//	JSONParser jsonParser ;
//	JSONArray  MesageList;
//	
//	public ArrayList<String> items; // container to store some random calendar items
//	
//	CustomDialogClass customDialogClass ;
//	Calendar cal ;
//	Integer YY , MM , DD ;
//	JSONArray jsonArray;
//	
//	ProgressDialog dialog ;
//	
//	Button Home ;
//	public void onCreate(Bundle savedInstanceState) {
//	    super.onCreate(savedInstanceState);
//	    requestWindowFeature(Window.FEATURE_NO_TITLE);
//	    setContentView(R.layout.calendarworklog);
//	    month = Calendar.getInstance();
//	    onNewIntent(getIntent());
//	    
//	    jsonParser = new JSONParser();
//	     
//	    customDialogClass = new CustomDialogClass(this);
//	                items = new ArrayList<String>();
//	              adapter = new CalendarAdapter(this, month);
//	                  cal = Calendar.getInstance();
//	    GridView gridview = (GridView) findViewById(R.id.gridview);
//	    gridview.setAdapter(adapter);
//	    
//	    
//	    final Calendar calendar = Calendar.getInstance();
//
//		YY = calendar.get(Calendar.YEAR);
//		MM = calendar.get(Calendar.MONTH);
//		DD = calendar.get(Calendar.DAY_OF_MONTH);
//
//	    handler = new Handler();
////	    handler.post(calendarUpdater);
//	    
//	       TextView title  = (TextView) findViewById(R.id.title);
//	    TextView previous  = (TextView) findViewById(R.id.previous);
//	        TextView next  = (TextView) findViewById(R.id.next);
//	                  Home = (Button) findViewById(R.id.backbutton);
//	                  
//	                  
//	                  Home.setOnClickListener(new View.OnClickListener() {
//						
//						@Override
//						public void onClick(View v) {
//							// TODO Auto-generated method stub
//							
//							
//							WorkoutlogCalendar.this.finish();
//						}
//					});
//	     title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
//	  
//	     
//	     previous.setOnClickListener(new OnClickListener() 
//	    {
//			
//			@Override
//			public void onClick(View v)
//			{
//				if(month.get(Calendar.MONTH)== 
//				   month.getActualMinimum(Calendar.MONTH)) 
//				{				
//				   month.set((
//				   month.get(Calendar.YEAR)-1),
//				   month.getActualMaximum(Calendar.MONTH),1);
//				} 
//				else 
//				{
//					month.set(Calendar.MONTH,month.get(Calendar.MONTH)-1);
//				}
//				refreshCalendar();
//			}
//		});
//	    
//	    
//	    
//	    next.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				if(month.get(Calendar.MONTH)== 
//				   month.getActualMaximum(Calendar.MONTH)) {				
//				   month.set((
//				   month.get(Calendar.YEAR)+1),
//				   month.getActualMinimum(Calendar.MONTH),1);
//				} 
//				else 
//				{
//					month.set(Calendar.MONTH,month.get
//							 (Calendar.MONTH)+1);
//				}
//				refreshCalendar();
//				
//			}    
//		});
//	    
//		gridview.setOnItemClickListener(new OnItemClickListener() {
//		    
//			public void onItemClick(AdapterView<?> parent, View v, 
//		    		int position, long id) {
//		    	TextView date = (TextView)v.findViewById(R.id.date);
//		    
//		    	if(date instanceof TextView && !date.getText().equals("")) 
//		    	{
//		        	
//		        	Intent intent = new Intent(WorkoutlogCalendar.this ,
//		        			Workoutlog.class);
//		        	   String day = date.getText().toString();
//		        	
//		        	   if(day.length()==1) 
//		        	   
//		        	   {
//		        		
//		        		   day = "0"+day;
//		        	
//		        	   }
//		        	// return chosen date as string format 
//		        
//		        	   intent.putExtra("date", 
//		        			android.text.format.DateFormat.format("yyyy-MM", month)
//		        			+"-"+day);
//		        	
//		        	   setResult(RESULT_OK, intent);
//		        	   finish();
//		        	   startActivity(intent);
//		        	
////		        	   finish();
//		        }
//		        
//		    }
//		});
//		
//		
//		
//		if(ConditionClas.isNetworkAvailable(WorkoutlogCalendar.this))
//		{
//		new RoutineParsing().execute("");
//		}
//		else {
//			ConditionClas.Toastmethod(WorkoutlogCalendar.this, 
//					"There is no internet");
//			
//		}
//	}
//	
//	public void refreshCalendar()
//	{
//		TextView title  = (TextView) findViewById(R.id.title);
//		
//		adapter.refreshDays();
//		adapter.notifyDataSetChanged();				
//		handler.post(calendarUpdater); // generate some random calendar items				
//		
//		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
//	}
//	
//	public void onNewIntent(Intent intent) 
//	{
//		     String date = intent.getStringExtra("date");
//		String[] dateArr = date.split("-"); // date format is yyyy-mm-dd
//		month.set(Integer.parseInt(dateArr[0]), 
//				  Integer.parseInt(dateArr[1]),
//				  Integer.parseInt(dateArr[2]));
//	}
//	
//	public Runnable calendarUpdater = new Runnable() {
//		
//		@Override
//		public void run() {
//			items.clear();
//			// format random values. You can implement a dedicated 
//			// class to provide real values
//			for(int i=0;i<31;i++) {
//				Random r = new Random();
//				
//				if(r.nextInt(10)>6)
//				{
//					items.add(Integer.toString(i));
//				}
//			}
//
//			System.out.println("SiteList.Log_Date       " +SiteList.Log_Date);
//			
//			SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//			ArrayList<String>  date = new ArrayList<String>();
//			ArrayList<String> date2 = new ArrayList<String>();
//			
//			date.clear();
//			
//			for(int i=0;i<SiteList.Log_Date.size();i++)
//			{
//			
////				Random r = new Random();
//				
//				try 
//				{
//					
//                                          
//					if(fromUser.parse(SiteList.Log_Date.get(i)).before(new Date()))
//						
//					{
//			date2.add(myFormat.format(fromUser.parse(SiteList.Log_Date.get(i))));
//		System.out.println(
//				"fromUser.parse(SiteList.Log_Date.get(i))." +
//				"before(new Date())  " +
//								"     "+
//				fromUser.parse(SiteList.Log_Date.get(i)).before(new Date()));
//
//		}
//						
//		else      
//		{
//		
//			date.add(myFormat.format(fromUser.parse(SiteList.Log_Date.get(i))));
//	
//		}
//					
//				
//				} 
//				
//				catch (ParseException e) 
//				{
//				    e.printStackTrace();
//				}
//			}
//				
//				
//				System.out.println("date avi    " +date);
//			
//			adapter.setItems(date);
//			adapter.setItems2(date2);
//			
//			adapter.notifyDataSetChanged();
//		}
//	};
//	
//	
//	class RoutineParsing extends AsyncTask<String, Void, String>
//	
//	{
//		
//		
//
//		@Override
//		protected String doInBackground(String... params) {
//			// TODO Auto-generated method stub
//			
//			
//			String encoded = "" ;
//			
//	
//			encoded = URLEncoder.encode("{"+"\"userid\""+ ":" 
//					+"\""+customDialogClass.pref.getString
//					(AllLink.UserId, "")+"\""+","+
//							"\"auth\""+ ":"+
//							"\""+customDialogClass.pref.getString
//							(AllLink.AuthKey, "")+"\"" +"}");
//						
//		System.out.println("the link to hit         "+
//			AllLink.getLogs+"{"+"\"userid\""+ ":" 
//			+"\""+customDialogClass.pref.getString
//			(AllLink.UserId, "")+"\""+","+
//					"\"auth\""+ ":"+
//					"\""+customDialogClass.pref.getString
//					(AllLink.AuthKey, "")+"\"" +"}");	
//		
//		
//		
//						
//						jsonObject = jsonParser.getJSONFromUrl(AllLink
//								.getLogs+encoded);
//						
//			if(jsonObject != null)
//				
//			{
//			
//			try 
//			{
//				jsonArray = jsonObject.getJSONArray("mesages");
//				
////				for (int i = 0; i < jsonArray.length(); i++) {
////				
////				JSONObject obj = jsonArray.getJSONObject(i);	
////					SiteList.Excname.add(obj.getString("exc_name"));
////					SiteList.Excinfo.add(obj.getString("workout_name"));
////					
////					
////				}
//				
//
//				MesageList = jsonObject.getJSONArray("mesages");
//				
//				
//				for (int i = 0; i < MesageList.length(); i++) 
//				{
//				
//					JSONObject c = MesageList.getJSONObject(i);
////					
//					
//					/* log_reps */
//					
//					
//					if((c.getString("log_reps")).length()>0)
//					{
//					SiteList.Log_reps.add(c.getString("log_reps"));
//					}
//					
//					else
//					{
//						SiteList.Log_reps.add("");
//					}
//					
//					/* excercise Name */
//					
//					
//					if((c.getString("execname")).length()>0)
//					{
//					SiteList.Exc_Name.add(c.getString("execname"));
//					}
//					
//					else 
//					{
//						SiteList.Exc_Name.add("");
//					}
//					
//					
//					/* Log Date */
//					
//					
//					if((c.getString("log_date")).length()>0)
//					{
//					
//						SiteList.Log_Date.add(c.getString("log_date"));
//					}
//					
//					else 
//					{
//					
//						SiteList.Log_Date.add("");
//					
//					}
//					
//					
//					/* log_intensity */
//					
//					if((c.getString("log_intensity")).length()>0)
//					
//					{
//				
//						SiteList.Log_Ints.add(c.getString("log_intensity"));
//				
//					}
//					
//					else
//					{
//			
//						SiteList.Log_Ints.add("");
//				
//					}
//					
//					
//					/* -----------    execid  --------------  */
//					
//					if((c.getString("execid")).length()>0)
//					{
//					
//						SiteList.Exces_Id.add(c.getString("execid"));
//					
//					}
//					
//					else
//					{
//					
//						SiteList.Exces_Id.add("");
//				
//					}
//					
//					
//					/* -----------    execid  --------------  */
//					
//					
//					
//					if((c.getString("log_sets")).length()>0)
//					{
//					SiteList.Log_Sets.add(c.getString("log_sets"));
//					}
//					
//					else 
//					{
//						SiteList.Log_Sets.add("");
//					}
//					
//				}
//				
//			    handler.post(calendarUpdater);
//			
//			}
//			catch (JSONException e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			}
//			
//			else {
//				
//				System.out.println("no json obj");
//			}
//			
//			return null;
//		
//		}
//		
//		
//		@Override
//		protected void onPreExecute() 
//		{
//			// TODO Auto-generated method stub
//			super.onPreExecute();
//			
//			dialog = new ProgressDialog(WorkoutlogCalendar.this);
//			dialog.setCancelable(false);
//			dialog.setMessage("Please wait while processing");
//			dialog.setTitle("Fetching all routines");
//			dialog.show();
//		}
//		
//		@Override
//		protected void onPostExecute(String result) {
//			// TODO Auto-generated method stub
//			super.onPostExecute(result);
//			
//			dialog.dismiss();
//			
////			Routine.setAdapter(new CustomListRoutine());
//			
//			
//		
//		}
//		
//	}
//	
//	
//	class CustomListRoutine extends BaseAdapter
//	{
//
//		@Override
//		public int getCount() 
//		{
//			// TODO Auto-generated method stub
//			return jsonArray.length();
//		}
//
//		@Override
//		public Object getItem(int arg0) 
//		{
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		@Override
//		public long getItemId(int position) 
//		{
//			// TODO Auto-generated method stub
//			return 0;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) 
//		{
//			// TODO Auto-generated method stub
//			
//			LayoutInflater inflater = (LayoutInflater) WorkoutlogCalendar.this.
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
//}
