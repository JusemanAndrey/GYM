package the.gym.app.deft;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.FloatMath;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class FloorPlan extends Activity implements OnTouchListener{

	private static final String TAG = "Touch";
	   // These matrices will be used to move and zoom image
	   Matrix matrix = new Matrix();
	   Matrix savedMatrix = new Matrix();

	   // We can be in one of these 3 states
	   static final int NONE = 0;
	   static final int DRAG = 1;
	   static final int ZOOM = 2;
	   int mode = NONE;

	   // Remember some things for zooming
	   PointF start = new PointF();
	   PointF mid = new PointF();
	   float oldDist = 1f;

	String Url           = "";
	
	String floornumber   = "";
	
	String exerciseName  = "";
	
	String directionname = "";
	
	int indexpos;
	WebView FloorPlanView;
	
	TextView Floornumber ;
	
	TextView ExerciseName ;
	
//	ImageView Icon ;
	
	ImageLoader loader ;
	
	Button BackButton ;
	
	Dialog ImageDialog ;
//	WebView Image ;
	ImageView Imageview ;
	private TextView GymId;

	int postoshow;
	private CustomDialogClass customDialogClass;

	DisplayImageOptions options;
	private ArrayList<String> Dir_imageListA = new ArrayList<String>();
	private ArrayList<Integer> index = new ArrayList<Integer>();
	ListView ImgeIconList;

	private Integer Sizetoshow = 0 ;

	private ArrayList<Integer> listsum= new ArrayList<Integer>();;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.floorplanview);
		

		GymId = (TextView)findViewById(R.id.gymid);
        
		customDialogClass = new CustomDialogClass(this);
		GymId.setText(Html.fromHtml("<b>GYM MEMBERSHIP ID : </b>")
				+customDialogClass.pref.getString(
	    		
				AllLink.GymId, ""));
		ImageDialog = new Dialog(this , android.R.style.Theme_NoTitleBar_Fullscreen);
		
		ImageDialog.setContentView(R.layout.imagedialog );
        
//		Image = (WebView)ImageDialog.findViewById(R.id.imagewebView);
//		Image.se
		Imageview = (ImageView)ImageDialog.findViewById(R.id.imagewebView);

//		Image.getSettings().setSupportZoom(true);  
//		Image.getSettings().setBuiltInZoomControls(true);
		
//		Image.getSettings().setDefaultZoom(ZoomDensity.FAR);
		loader = ImageLoader.getInstance();
         FloorPlan.this.loader.init(ImageLoaderConfiguration.createDefault(FloorPlan.this));
         
		FloorPlanView = (WebView)  findViewById(R.id.floorplanview);
		
		Floornumber   = (TextView) findViewById(R.id.floornumber);
		
		ExerciseName  = (TextView) findViewById(R.id.excname);
		
		System.out.println("ExerciseName  " +ExerciseName);
		
		ImgeIconList          = (ListView)findViewById(R.id.imagelist);
		
//		Icon          = (ImageView)findViewById(R.id.imageView1);
	    
		BackButton    = (Button)   findViewById(R.id.backbutton);
	
	    Url           = getIntent().getStringExtra("floorplan");
		
		floornumber   = getIntent().getStringExtra("floornumber");
		
		exerciseName  = getIntent().getStringExtra("workname");
		
//		directionname = getIntent().getStringExtra("dir");
		indexpos = getIntent().getIntExtra("arg", 0);
		Dir_imageListA.clear();
		index.clear();
		
		Dir_imageListA  = getIntent().getStringArrayListExtra("dir");
		index  = getIntent().getIntegerArrayListExtra("index");
		System.out.println(" index            " +index);
		for (int i = 0; i <= indexpos; i++) {
			
			Sizetoshow += index.get(i);
		}
		
		for (int i = 0; i < index.size(); i++) {
			
			System.out.println("listsumi     " +index.get(i));
			if(i>0)
			{
				System.out.println("listsumii     " +index.get(i-1));
				System.out.println("listsumii     " +index.get(i));
			listsum.add(listsum.get(i-1)+index.get(i));
			}
			else {
				listsum.add(index.get(i));
			}
		}
		System.out.println("floornumber listsum       " +listsum);
		
		System.out.println("floornumber Sizetoshow  " +Sizetoshow);
		System.out.println("floornumber  Dir_imageListA" +Dir_imageListA);
		
		
		System.out.println("floornumber  " +floornumber);
		System.out.println("floornumber  " +exerciseName);
		System.out.println("floornumber  " +Url);
		System.out.println("floornumber  " +directionname);
		
//		Image.loadUrl(directionname);
		
		
		BackButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			
				
				FloorPlan.this.finish();
			}
		});
		
		Floornumber.setText ("Floor Number  " +floornumber);
		
		
		ExerciseName.setText( exerciseName);

		
		Url.replace(":", "://www.");
		
		
		System.out.println("Url      " +Url);
		
		
//		loader.displayImage(directionname, Icon);
		
		
		FloorPlanView.loadUrl(Url);
	
//		Icon.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//				ImageDialog.show();
//			}
//		});
		
		ImgeIconList.setAdapter(new CustomListRoutines());
		
		ImgeIconList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				postoshow = arg2;
				
//				new UploadContent().execute("");
				 if(indexpos==0)
					{
						
					try {
//						Image.loadUrl(Dir_imageListA.get(postoshow));
						loader.displayImage(Dir_imageListA.get(postoshow),
								Imageview , options);
					} catch (Exception e) {
//						Image.loadUrl(Dir_imageListA.get(postoshow));
						
						loader.displayImage(Dir_imageListA.get(postoshow),
								Imageview , options);
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
					else {
						try {
							loader.displayImage(Dir_imageListA.get(postoshow+listsum.get(indexpos-1)),
									Imageview , options);
//							Image.loadUrl(Dir_imageListA.get(postoshow+listsum.get(indexpos-1)));
						} catch (Exception e) {
							// TODO Auto-generated catch block
//							Image.loadUrl(Dir_imageListA.get(postoshow+listsum.get(indexpos-1)));
//							e.printStackTrace();
						}
					}
				 ImageDialog.show();
				//				if(Image.)
			
			}
		});
		
		Imageview.setOnTouchListener(this);
	}
	
	class CustomListRoutines extends BaseAdapter
	{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return index.get(indexpos);
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
			
			LayoutInflater inflater = (LayoutInflater) FloorPlan.this.
					getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			
			ViewHolder holder;
			if(convertView == null)
			{
				
				
				holder = new ViewHolder();
				
				 convertView = inflater.inflate(R.layout.exc_img, null);
				
				holder.Excname = (ImageView)convertView.findViewById(R.id.imageView1);
				
				convertView.setTag(holder);
			}
			
			else {
				
				
				holder = (ViewHolder) convertView.getTag();
			}
			
			if(indexpos==0)
			{
			 try {
		    	  
		    	  System.out.println("link to disply inside view exc  " 
		    	  +Dir_imageListA.get(position)
						  );
				loader.displayImage(Dir_imageListA.get(position),
						  holder.Excname , options);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			else {
				 try {
			    	  
			    	  System.out.println("link to disply inside view exc  " 
			    	  +Dir_imageListA.get(position+listsum.get(indexpos-1))
							  );
					loader.displayImage(Dir_imageListA.get(position+listsum.get(indexpos-1)),
							  holder.Excname , options);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			
			return convertView;
		}
		
		class ViewHolder
		{
			
			ImageView Excname  ;
			
		}
		
	}
	
	 class UploadContent extends AsyncTask<String, Void, String>
	    {

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
			     

				return null;
			}
	    	
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
			}
			
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				
			}  
			}
	 
	 @Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		if(ImageDialog.isShowing())
		{
			
		}
	}
	 public boolean onTouch(View v, MotionEvent event) {
	      ImageView view = (ImageView) v;

	      // Dump touch event to log
	      dumpEvent(event);

	      // Handle touch events here...
	      switch (event.getAction() & MotionEvent.ACTION_MASK) {
	      case MotionEvent.ACTION_DOWN:
	         savedMatrix.set(matrix);
	         start.set(event.getX(), event.getY());
	         Log.d(TAG, "mode=DRAG");
	         mode = DRAG;
	         break;
	      case MotionEvent.ACTION_POINTER_DOWN:
	         oldDist = spacing(event);
	         Log.d(TAG, "oldDist=" + oldDist);
	         if (oldDist > 10f) {
	            savedMatrix.set(matrix);
	            midPoint(mid, event);
	            mode = ZOOM;
	            Log.d(TAG, "mode=ZOOM");
	         }
	         break;
	      case MotionEvent.ACTION_UP:
	      case MotionEvent.ACTION_POINTER_UP:
	         mode = NONE;
	         Log.d(TAG, "mode=NONE");
	         break;
	      case MotionEvent.ACTION_MOVE:
	         if (mode == DRAG) {
	            // ...
	            matrix.set(savedMatrix);
	            matrix.postTranslate(event.getX() - start.x,
	                  event.getY() - start.y);
	         }
	         else if (mode == ZOOM) {
	            float newDist = spacing(event);
	            Log.d(TAG, "newDist=" + newDist);
	            if (newDist > 10f) {
	               matrix.set(savedMatrix);
	               float scale = newDist / oldDist;
	               matrix.postScale(scale, scale, mid.x, mid.y);
	            }
	         }
	         break;
	      }

	      view.setImageMatrix(matrix);
	      return true; // indicate event was handled
	   }

	   /** Show an event in the LogCat view, for debugging */
	   private void dumpEvent(MotionEvent event) {
	      String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
	            "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
	      StringBuilder sb = new StringBuilder();
	      int action = event.getAction();
	      int actionCode = action & MotionEvent.ACTION_MASK;
	      sb.append("event ACTION_").append(names[actionCode]);
	      if (actionCode == MotionEvent.ACTION_POINTER_DOWN
	            || actionCode == MotionEvent.ACTION_POINTER_UP) {
	         sb.append("(pid ").append(
	               action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
	         sb.append(")");
	      }
	      sb.append("[");
	      for (int i = 0; i < event.getPointerCount(); i++) {
	         sb.append("#").append(i);
	         sb.append("(pid ").append(event.getPointerId(i));
	         sb.append(")=").append((int) event.getX(i));
	         sb.append(",").append((int) event.getY(i));
	         if (i + 1 < event.getPointerCount())
	            sb.append(";");
	      }
	      sb.append("]");
	      Log.d(TAG, sb.toString());
	   }

	   /** Determine the space between the first two fingers */
	   private float spacing(MotionEvent event) {
	      float x = event.getX(0) - event.getX(1);
	      float y = event.getY(0) - event.getY(1);
	      return FloatMath.sqrt(x * x + y * y);
	   }

	   /** Calculate the mid point of the first two fingers */
	   private void midPoint(PointF point, MotionEvent event) {
	      float x = event.getX(0) + event.getX(1);
	      float y = event.getY(0) + event.getY(1);
	      point.set(x / 2, y / 2);
	   }

}
