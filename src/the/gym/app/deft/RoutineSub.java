package the.gym.app.deft;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class RoutineSub extends Activity{

	
	ListView Routine ;
	
	JSONParser parser ;
	

	public static ArrayList<String> ExcnameA = new ArrayList<String>();
	public static ArrayList<String> FloorNumberA = new ArrayList<String>();
	public static ArrayList<String> Dir_imageListA = new ArrayList<String>();
	public static ArrayList<String> ExcnametoShow = new ArrayList<String>();
	public static ArrayList<Integer> index = new ArrayList<Integer>();
	
	CustomDialogClass customDialogClass ;
	
	String name;
	
	Button Home ,Back;
	
	TextView GymId ;
	
//	String[] Msg;
	
	String Floor_Plan  = "" ;
	
	String FloorNumber = "" ;
	
	String WorkoutName = "" ;
	
	TextView Header;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.routeenscreen);
		
		Floor_Plan = getIntent().getStringExtra("floorplan");
		name = getIntent().getStringExtra("name");
		ExcnameA.clear();
		FloorNumberA.clear();
		Dir_imageListA.clear();
		ExcnametoShow.clear();
		index.clear();
		
		      ExcnameA = getIntent().getStringArrayListExtra("workname");
		  FloorNumberA = getIntent().getStringArrayListExtra("floornumber");
		Dir_imageListA = getIntent().getStringArrayListExtra("dir");
		
//		Map<String, List<String>> map = new HashMap<String, List<String>>();
		Set<String> unique = new HashSet<String>(ExcnameA);
		for (String key : unique) {
		    System.out.println(key + ": " + Collections.frequency(ExcnameA, key));

//		    hashMap.put(key, Collections.frequency(ExcnameA, key));
//		    listd.add(key);
		    
		    System.out.println("output is  "+key + ": " + 
		    Collections.frequency(ExcnameA, key));
		    
		    
//		    list.add(Collections.frequency(outDate, key));
		    index.add(Collections.frequency(ExcnameA, key));
		    ExcnametoShow.add(key);
		    
		    
		}
		
		
		System.out.println("ExcnametoShow  " +ExcnametoShow);
		System.out.println("ExcnametoShow  " +ExcnameA);
		System.out.println("ExcnametoShow  " +index);
		
		
		Collections.reverse(ExcnametoShow);
		System.out.println("ExcnametoShow  " +ExcnametoShow);
		
		Header = (TextView)findViewById(R.id.viewheader);
		Header.setText(name);
		
		parser = new JSONParser();
		
		customDialogClass = new CustomDialogClass(this);
		
//		Msg = new String[1];
		
		Routine = (ListView)findViewById(R.id.routinelist);
		 
		Home = (Button)findViewById(R.id.home); 
		Back=  (Button) findViewById(R.id.backbutton);
		
		GymId = (TextView)findViewById(R.id.gymid); 
		   
		  
		GymId.setText(Html.fromHtml("<b>GYM MEMBERSHIP ID : </b>") +customDialogClass.pref.getString(
	    		
				AllLink.GymId, ""));
		  
		  
		
		// this button click shall take the user to home page of the 
		   // application
		Home.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Intent i=new Intent(RoutineSub.this,HomeScreen.class);
				
				
				finish();
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				
				
			}
		});
		
		Back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				RoutineSub.this.finish();
			
			
			
			}
		});
		
		
		System.out.println("ExcnameA      " +ExcnameA);
		Routine.setAdapter(new ArrayAdapter<String>(
				
				RoutineSub.this, 
				
				android.R.layout.simple_list_item_1,
				
				android.R.id.text1 , ExcnametoShow));
		
		
		 Routine.setOnItemClickListener(new 
					
	        		AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, 
				View arg1,
				int arg2, long arg3) {
			// TODO Auto-generated method stub
	
			Intent intent = new Intent(
					RoutineSub.this ,
			FloorPlan.class);
		
			System.out.println("Floor_Plan  " +
					"   " +Floor_Plan);
			
			intent.putExtra("floorplan", Floor_Plan);
			
			intent.putStringArrayListExtra("dir", 
					Dir_imageListA);
			
			intent.putIntegerArrayListExtra("index", 
					index);
			
			intent.putExtra("arg", arg2);
			
			intent.putExtra("workname", 
					
					ExcnametoShow.get(arg2));
			
			intent.putExtra("floornumber", 
					
					
				FloorNumberA.get(arg2));
			
			
			  startActivity(intent);
			
		}
	});
		
		
		
		
	}
	
	/* This is the async class of RoutineParsing to get the data 
	 * of routine */

	
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
			
			LayoutInflater inflater = (LayoutInflater) RoutineSub.this.
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
			holder.Excname.setText(ExcnametoShow.get(position));
			
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
			
			LayoutInflater inflater = (LayoutInflater) RoutineSub.this.
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
