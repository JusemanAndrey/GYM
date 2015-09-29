package the.gym.app.deft;


/* This activity is called when class schedule button is hit 
 * and it has a list of class content parsed from getclass link
 * and is displayed in custom list*/



import java.net.URLEncoder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Class_List extends Activity{

	ProgressDialog dialog ;
	
	
	JSONObject jsonObject ;
	JSONParser jsonParser ;
	JSONArray class_list;
	
	CustomDialogClass customDialogClass ;
	
	ListView class_List;   
	TextView header , GymId;
	
	boolean hide = true;
	
	Button Backbutton ,Homebutton;
	
	String one , two , three ;
	
	ArrayList<String> onelist   = new ArrayList<String>();
	ArrayList<String> twolist   = new ArrayList<String>();
	ArrayList<String> threelist = new ArrayList<String>();
	ArrayList<String> fourlist = new ArrayList<String>();
	
	SimpleDateFormat myFormat ;
	
	boolean join  = false ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.classlistscreen);
		
		       jsonParser = new JSONParser();
		customDialogClass = new CustomDialogClass(this);
		
		myFormat = new SimpleDateFormat("yyyy-MM-dd");
		one   = getIntent().getStringExtra("id");
		two   = getIntent().getStringExtra("title");
		three = getIntent().getStringExtra("desc");
		
		onelist   = getIntent().getStringArrayListExtra("idlist");
		twolist   = getIntent().getStringArrayListExtra("titlelist");
		threelist = getIntent().getStringArrayListExtra("desclist");
		fourlist  = getIntent().getStringArrayListExtra("datelist");
	
		
		
		
		try {
			
			System.out.println("myFormat.parse(fourlist.get(0))."+
					   "before(new Date())   " 
				+myFormat.parse(fourlist.get(0)).
				after(new Date()));
			
			        
			
			if(myFormat.parse(fourlist.get(0)).after(new Date()) 
		    || myFormat.parse(fourlist.get(0)).equals(new Date()))
				
			{
				join = true;
			}
		} 
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println("one                 " +onelist);
//		System.out.println("two                 " +twolist);
//		System.out.println("three               " +threelist);
//		System.out.println("three               " +fourlist);

		
		class_List = (ListView)findViewById(R.id.routinelist);
	    
		header = (TextView)findViewById(R.id.viewheader);
	   
	    Homebutton = (Button)  findViewById(R.id.home);
	   
	    Backbutton=(Button)findViewById(R.id.backbutton);
	    
	    GymId = (TextView)findViewById(R.id.gymid);
	         
	         
		class_List.setAdapter(new ClassCustomList());
		    header.setText("CLASS SCHEDULE - DAILY VIEW");
		    
		    
		    
		    
		    GymId.setText(Html.fromHtml("<b>GYM MEMBERSHIP ID : </b>") +customDialogClass.pref.getString(
		    		AllLink.GymId, ""));
		    
		    
		    /* This button click of this class shall simply take the user
		     * to home screen */
		    Homebutton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				
//					Class_List.this.finish();
					
					Intent intent = new Intent(Class_List.this ,
							HomeScreen.class);
					finish();
					startActivity(intent);
				}
			});
		    
		    
		    Backbutton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				
					
				Class_List.this.finish();
					
				}
			});
		    
//		    if(ConditionClas.isNetworkAvailable(Class_List.this))
//		    {
////		new ClassDetailsAsyncTask().execute("");
//		    }
//		    
//		    else {
//				
//		    ConditionClas.Toastmethod(Class_List.this,
//		    		"There is no internet");
//			}
		
		    
		    /* this is the list item click of this class 
		     * and shall open the content of the 
		     * item selected in the next class */
		class_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				
				Intent i = new Intent(Class_List.this , ClassDetails.class);
				
				i.putExtra("name",   twolist.get(arg2));
				i.putExtra("desc",   threelist.get(arg2));
				i.putExtra("date",   fourlist.get(arg2));
				i.putExtra("id",     onelist.get(arg2));
				i.putExtra("join",   join);
				
				startActivity(i);
			}
		});
	}
	
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
			
//			System.out.println("all links are  ----           "+
//			
//					AllLink.getClass+"{"+"\"gymid\""+ ":" 
//			+"\""+customDialogClass.pref.getString(AllLink.UserId, 
//					"")+"\""+","+
//					"\"auth\""+ ":"+
//					"\""+customDialogClass.pref.getString(AllLink.AuthKey, 
//							"")+"\""+"}");
		
			
			jsonObject = jsonParser.getJSONFromUrl(AllLink.getClass+encode);
			
			
			System.out.println("jsonObject     "+jsonObject);
			if(jsonObject != null)
			{		
			try {
				   

				
				
				class_list = jsonObject.getJSONArray("0");
				
				System.out.println("class_list    "+class_list);
				
				SiteList.ClassName.clear();
				SiteList.ClassDesc.clear();
				SiteList.ClassId.clear();
				
				for (int i = 0; i < class_list.length(); i++) {
					
					
					JSONObject ob = class_list.getJSONObject(i);
					SiteList.ClassName.add(ob.getString("cls_title"));
					SiteList.ClassDesc.add(ob.getString("cls_description"));
					SiteList.ClassId.add(ob.getString("id"));
					
				}
				
				System.out.println("ClassName       " +SiteList.ClassId);
//				System.out.println("ClassName       " +SiteList.ClassName);
//				System.out.println("ClassName       " +SiteList.ClassDesc);
				
				
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
			
			dialog = new ProgressDialog(Class_List.this);
			
//			dialog.setTitle("Fetching content of Class");
		    dialog.setMessage("Please wait while peocessing");
		    dialog.setCancelable(false);
		    dialog.show();
		    
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		dialog.dismiss();
		
		
		if(hide == false)
		{
			ConditionClas.Toastmethod(Class_List.this, 
					"No associated classes found with this user id");
		}
		
		else if(hide){
			class_List.setAdapter(new ClassCustomList());
		}
		
		}
	}
	
	/* This method is to show custom classes ofr the particualr user */
	                                 
	class ClassCustomList extends BaseAdapter
	{

		@Override
		public int getCount() 
		{
			// TODO Auto-generated method stub
			return onelist.size();
			
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
			
			
			LayoutInflater inflater ;
			
			inflater = (LayoutInflater) Class_List.this.
					getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			
			ViewHolder holder ; 
			if( arg1 == null )
			{
				
				holder = new ViewHolder();
				  arg1 = inflater.inflate(R.layout.classcustomelement, 
						null);
				
				holder.Heading = (TextView)arg1.findViewById(R.id.headingtv);
				
				holder.Date = (TextView)arg1.findViewById(R.id.datetv);
				
				arg1.setTag(holder);
			}
			
			else 
			{  
				                
				holder = (ViewHolder)arg1.getTag();
			
			}
			
			holder.Heading.setText(twolist.get(arg0));
			holder.Date.setText(fourlist.get(arg0));
			
			
			
			return arg1;
		}
		
		
		class ViewHolder 
		{
			TextView Heading , Date ; 
		}
		
	}
}
