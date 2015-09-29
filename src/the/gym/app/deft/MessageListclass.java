package the.gym.app.deft;


/* Navigation to this class is from 
 * home screen and this activity has the 
 * messages parsed for the user
 * */
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MessageListclass extends Activity {

	
	private static final int MENU_NEW_GAME = Menu.FIRST;
	private static final int MENU_QUIT = Menu.FIRST + 1;
	private static final int MENU_EDIT = Menu.FIRST + 2;
	
	JSONParser parser ;
	
	JSONArray messageList ;
	
	JSONObject jsonObject , jsonObjectDelete;
	
	CustomDialogClass customDialogClass ;
	
	ProgressDialog dialog ;
	
	ListView MessageListView ;
	
	Button HomeButton,BackButton;
	                    
	TextView GymId ;
	
	String messageDetail; 
	
	String Action = "";
	
	int Index = 0 ;
	
	String Mesg ;
	
	int count = 0 ;
	
	  private boolean choice = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.messagelist);
		
		
		parser = new JSONParser();
		
		customDialogClass = new CustomDialogClass(this);

		
		MessageListView = (ListView)findViewById(R.id.messagelistView);
		registerForContextMenu(MessageListView);
		HomeButton = (Button)findViewById(R.id.home);
		
		BackButton = (Button)findViewById(R.id.backbutton);
		         
		GymId = (TextView)findViewById(R.id.gymid);
		          
	  
		GymId.setText(Html.fromHtml("<b>GYM MEMBERSHIP ID : </b>") +customDialogClass.pref.getString(
	    		
				AllLink.GymId, ""));
		
		     
		BackButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		
				
				MessageListclass.this.finish();
				
				
				}
		});
		          
		
		
		HomeButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				
					Intent intent=new Intent(MessageListclass.this,HomeScreen.class);
				
					finish();
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					
					startActivity(intent);
					
					
					//MessageListclass.this.finish();
				}
			});
		     
		     
		if(ConditionClas.isNetworkAvailable(MessageListclass.this))
		{
	    	
			new MessageAsynctask().execute("");
	
		}
		
		else {
			
			
			ConditionClas.Toastmethod(MessageListclass.this, 
				
					" There is no internet connection");
		}
	}
	
	
	/*  this MessageAsynctask class performs the 
	 * parsing of the message list link on  seprate thread*/
	
	class MessageAsynctask extends AsyncTask<String, Void , String>
	{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			
			if(Action.equals("delete"))
			{
				System.out.println("the link to hit is           "
						
						
						
					+  AllLink.DeleteMessage 
				
					+  "{"+"\"messageid\""+ ":" 
				
					+  "\""+SiteList.Msg_Id.get(Index)
					
					+"\""+","+"\"auth\""+ ":"+"\""
					
					+customDialogClass.pref.getString(AllLink.AuthKey,"")
					
					+"\""+"}");
			
			
			
			
			
			@SuppressWarnings("deprecation")
			
			String  encoded = URLEncoder.encode("{"+"\"messageid\""+ ":" 
				
					+  "\""+SiteList.Msg_Id.get(Index)
					
					+"\""+","+"\"auth\""+ ":"+"\""
					
					+customDialogClass.pref.getString(AllLink.AuthKey,"")
					
					+"\""+"}") ;
			
			
		
			jsonObjectDelete = parser.getJSONFromUrl(AllLink.DeleteMessage 
					
					+encoded);	
			}
			else {
				
			
			System.out.println("the link to hit is           "
				
					
					
					+  AllLink.getMessage 
				
					+  "{"+"\"userid\""+ ":" 
				
					+  "\""+customDialogClass.pref.getString(AllLink.UserId,"")
	 				
					+"\""+","+"\"auth\""+ ":"+"\""
					
					+customDialogClass.pref.getString(AllLink.AuthKey,"")
					
					+"\""+"}");
			
			
			SiteList.Msg_Description.clear();
			SiteList.Msg_Subject.clear();
			SiteList.Msg_Id.clear();
			
			@SuppressWarnings("deprecation")
			
			String  encoded = URLEncoder.encode("{"+"\"userid\""+ ":" 
					
					+"\""+customDialogClass.pref.getString(AllLink.UserId, 
							
							"")+"\""+","+
							
							"\"auth\""+ ":"+
							
							"\""+customDialogClass.pref.getString(AllLink.AuthKey, 
									"")+"\""+"}") ;
			
			
		
			jsonObject = parser.getJSONFromUrl(AllLink.getMessage 
					
					+encoded);
			}
			return null;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			
			super.onPreExecute();
			
			
			dialog = new ProgressDialog(MessageListclass.this);
			
			dialog.setCancelable(false);
			
			dialog.setMessage("Please wait while processing");
			
			if(Action.equals("delete"))
			{
				dialog.setTitle("Deleteing Messages");
			}
			else {
				dialog.setTitle("Fetching All Messages");
			}
			
			
			dialog.show();
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			
			super.onPostExecute(result);
			
			if(Action.equals("delete"))
			{
				
				System.out.println("Index   " +Index);
				System.out.println("SiteList.Msg_Description . " +
						"   " +SiteList.Msg_Description.size());
				
				System.out.println("SiteList.Msg_Description . " +
						"   " +SiteList.Msg_Subject.size());
				
				System.out.println("SiteList.Msg_Description . " +
						"   " +SiteList.Msg_Id.size());
				
				SiteList.Msg_Description .remove(Index);
				SiteList.Msg_Id .remove(Index);
				SiteList.Msg_Subject .remove(Index);
				
				ArrayAdapter<String> adpt = 
						new ArrayAdapter<String>(MessageListclass.this ,
						
						R.layout.messageitem ,
						
						R.id.msgtextView ,
					
						SiteList.Msg_Subject ); 
				MessageListView.setAdapter(adpt);
				adpt.notifyDataSetChanged();
				
				if(jsonObjectDelete != null)
				{
					try {
						 Mesg = jsonObjectDelete.getString("msg");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					
					Toast.makeText(MessageListclass.this, 
							Mesg, Toast.LENGTH_LONG).show();
				}
			}
			else {
				
			
			if(jsonObject != null)
			{
		
				try {
				
				
			
				String Status = jsonObject.getString("status");
				
			
				
				System.out.println("messageList   " +jsonObject.length());
		
				System.out.println("jsonObject.getString        " +
					
						jsonObject.getString("msg"));	

				
				
				
				
//			    ConditionClas.Toastmethod(MessageListclass.this,
//			    		"the status is   " +
//								" " +Status);

				if(jsonObject.getString("msg").equalsIgnoreCase("No message found."))
				
				{
					
//					messageList = jsonObject.getJSONArray("messages");
					System.out.println("inside if  ");
					
					SiteList.Msg_Description.clear();
					
					

					ConditionClas.Toastmethod(MessageListclass.this,
							"There are no messages");
					
				
					
					SiteList.Msg_Description.add("There are no messages");
					
					try {
						MessageListView.setAdapter(new ArrayAdapter<String>(MessageListclass.this ,
								
								R.layout.messageitem ,
								
								R.id.msgtextView ,
							
								SiteList.Msg_Description ));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					

				}
				
				else if(jsonObject.getString("msg").equalsIgnoreCase("message found.")) {
					
					System.out.println("inside if  ");
					
					messageList = jsonObject.getJSONArray("messages");
					
					System.out.println("inside else  " + messageList.length());
					
					System.out.println("inside else  " + messageList);
					
					for (int i = 0; i < messageList.length(); i++) {
						
					
						
						JSONObject c = messageList.getJSONObject(i);
						
						SiteList.Msg_Subject.add(c.getString("subject"));
						
						SiteList.Msg_Description.add(c.getString("msg_description"));
						
						SiteList.Msg_Id.add(c.getString("id"));
						
						messageDetail=c.getString("msg_description");
						
					
					}
					
					System.out.println("SiteList.Msg_Description   " +
					
					SiteList.Msg_Description);
					
					System.out.println("SiteList.Msg_Description   " +
							
					SiteList.Msg_Subject);
					
					System.out.println("SiteList.Msg_Description   " +
							
					SiteList.Msg_Id);
					MessageListView.setAdapter(new ArrayAdapter<String>
					
					(MessageListclass.this ,
							
							R.layout.messageitem ,
							
							R.id.msgtextView ,
							
							SiteList.Msg_Subject));
					


					
					MessageListView.setOnItemClickListener(new 
							
							AdapterView.OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									// TODO Auto-generated method stub
												
						
									
									Intent mesgDetail=new Intent(MessageListclass.this,MessageView.class );
									
									mesgDetail.putExtra("messageDetail", SiteList.Msg_Description.get(arg2));
									startActivity(mesgDetail);
									
									
									
									
									
									/*ConditionClas.Toastmethod(MessageListclass.this,
											"List item clicked at index   " 
							+arg2);*/
								}
					});
					
					MessageListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

						

						@Override
						public boolean onItemLongClick(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
							// TODO Auto-generated method stub
						
							Index  = arg2 ;
//							arg1.showContextMenu();
							return false;
							
						}
					});
						
					}
				
				
			} catch (JSONException e) {
		 		// TODO Auto-generated catch block
				
				e.printStackTrace();
			}
			
				customDialogClass.edit.putBoolean(
						AllLink.InMesg, true);
				customDialogClass.edit.putInt(
						AllLink.MesgCount, SiteList.Msg_Subject.size());
				customDialogClass.edit.commit();
		}
		
		
			else {
				
				System.out.println("no jo");
				
				MessageListclass.this.finish();
				
				
				ConditionClas.Toastmethod(MessageListclass.this,
						
				"There are no messages");
			}
			}
			dialog.dismiss();
		
			if(count==0)
			{
			showMessage();
			}
			count++;
		}
	
	}
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// TODO Auto-generated method stub
//		getMenuInflater().inflate(R.menu.splashscreen, menu);
//		return super.onCreateOptionsMenu(menu);
//	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) { 
		
		if (v.getId()==R.id.messagelistView) {  
				 AdapterView.AdapterContextMenuInfo info 
			      = (AdapterView.AdapterContextMenuInfo)menuInfo;  
			 menu.setHeaderTitle("Delete Selected Message");  
			 menu.add(0, MENU_EDIT, 0, "Delete");  
			 
	 	}  }
	
	public boolean onContextItemSelected(MenuItem item) {switch (item.getItemId()) {  
	case MENU_EDIT:    
//		text.setText("Edit selected");    
//    	Toast.makeText(MessageListclass.this, "delete clicked", Toast.LENGTH_SHORT).show();

    	Action = "delete";
    	new MessageAsynctask().execute("");
		return true;  
//	case MENU_DELETE:    
//		text.setText("Delete selected");  
//		return true;  
	default:    
		return super.onContextItemSelected(item);  
}}
	
	void showMessage()
	{
		Button OkButton;
		TextView Message ;
		final CheckBox check;
		final Dialog mydialog = new Dialog(MessageListclass.this);
		mydialog.setContentView(R.layout.alertdialogback);
		
		OkButton = (Button)mydialog.findViewById(R.id.button1);
		Message  = (TextView)mydialog.findViewById(R.id.textView1);
		check    = (CheckBox)mydialog.findViewById(R.id.checkBox1);
		
		mydialog.setTitle("How to delete a message");
		Message.setText(getResources().getString(R.string.deletemessage));
		OkButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				System.out.println("check.isChecked()      " +check.isChecked());
				if(check.isChecked())
				{
				customDialogClass.edit.putBoolean("choice", check.isChecked());
        		customDialogClass.edit.commit();
        		mydialog.dismiss();
				}
				else {
					mydialog.dismiss();
				}
			}
		});
		check.setText(getResources().getString(R.string.warning));
		check.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (check.isChecked()) {
                    // If the user checked the item, add it to the selected items
                   
               	 choice = true;
                } else  {
                    // Else, if the item is already in the array, remove it 
               	 choice = false;
                }
			}   
		});
		if(customDialogClass.pref.getBoolean("choice", check.isChecked())==false)
		{
		mydialog.show();
		mydialog.setCancelable(false);
		}
		else {
			System.out.println("no message");
		}
		
	
		System.out.println("customDialogClass.pref.getBoolean(choice, false)" +
				"        " +customDialogClass.pref.getBoolean("choice", false));
		
//		;
		
		
		
	}
}
