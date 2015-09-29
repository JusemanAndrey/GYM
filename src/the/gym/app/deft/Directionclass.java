package the.gym.app.deft;

/* Did not use this class any where*/
//import java.util.ArrayList;
//import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
//import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Directionclass extends Activity {

	
	// JSON Node names
	
	/* The node names used in this parsing are */
//	private static final String VideoParentTag = "videos";
//	private static final String DirectionTag = "directions";
//	
//	
//	private static final String Dir_image = "dir_image";
//	private static final String Dir_description = "dir_description";
//	
//	
//	
//	private static final String Vid_img = "vid_img";
//	private static final String Vid_path = "vid_path";
//	private static final String Vid_title = "vid_title";
//	private static final String Vid_description = "vid_description";


	// contacts JSONArray
	JSONArray Videos;
	JSONArray Desc;
	
	String query ;
	JSONObject json;
	                         
	ProgressDialog dialog ;
	
	Button ToVideo ;

	Context context ;
	
	ListView listView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.exercisescreen);
		
		// Hashmap for ListView
//		ArrayList<HashMap<String, String>> contactList = new 
//				ArrayList<HashMap<String, String>>();


		System.out.println("inside direction app       " +
		SiteList.Dir_imageList.size() );
		
//		context = ExcersiceDataClass.this;
		
		 ToVideo = (Button)findViewById(R.id.tovideobutton);
		listView = (ListView)findViewById(R.id.listexc);
		
		
		listView.setAdapter(new CustomList());
		
		ToVideo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
//				Intent i = new Intent(Directionclass.this , 
//						PlayVideo.class);
//				i.putExtra("videourl", SiteList.Vid_pathList.get(0));
//				startActivity(i);
			}
		});

	}

	


	
	
	class CustomList extends BaseAdapter
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
					 Directionclass.
					 this.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			 
		      if (arg1 == null)
		      {
		    	 
		    	     arg1 = mInflater.inflate(R.layout.dirctioncustomview, null);
		           holder = new ViewHolder();
		           
		           holder.txtDesc   = (TextView)  arg1.findViewById(R.id.step);
		           holder.txtTitle  = (TextView)  arg1.findViewById(R.id.stepno);
//		           holder.imageView = (ImageView) arg1.findViewById(R.id.img);
		           
		           arg1.setTag(holder);
		      
		      }
		        
		      else
		      {
		            holder = (ViewHolder) arg1.getTag();
		      
		      }
			 
		      holder.txtTitle.setText("Step No. " +arg0+1);
		      holder.txtDesc.setText(SiteList.Dir_descriptionList.get(arg0));
		      
			 return arg1;
		}
		
		private class ViewHolder {
//	        ImageView imageView;
	        TextView txtTitle;
	        TextView txtDesc;
	    }
		
	}
}
