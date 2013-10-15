package com.pullee;

import java.util.List;

import net.sourceforge.zbar.Symbol;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dm.zbar.android.scanner.ZBarConstants;
import com.dm.zbar.android.scanner.ZBarScannerActivity;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MainActivity extends Activity {

	/** Buttons are actually RelativeLayouts to fit the UI design style */
	private RelativeLayout scanButton;
	private RelativeLayout historyButton;
	private RelativeLayout aboutButton;
	
	/** Layout to bring the loading page to and off the screen */
	private RelativeLayout loadingScreen;

	/** Necessary variables for ZBar scanning */
	private static final int ZBAR_SCANNER_REQUEST = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Get rid of the title bar for the app
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_main);
		
		/** Match UI Views to corresponding elements in the XML Layout */
		scanButton = (RelativeLayout) this.findViewById(R.id.ScanButton);
		historyButton = (RelativeLayout) this.findViewById(R.id.HistoryButton);
		aboutButton = (RelativeLayout) this.findViewById(R.id.AboutButton);
		loadingScreen = (RelativeLayout) this.findViewById(R.id.LoadingScreen);

		/** Set the click listeners for the screens buttons */
		scanButton.setOnClickListener(new View.OnClickListener(){
	  		public void onClick(View arg0) {
	  			
	  			//On click of the Scan Button we will use the ZBar scanning library to handle QR Code scanning
	  			Intent intent = new Intent(MainActivity.this, ZBarScannerActivity.class);
	  			//Necessary code from ZBar documentation, tells the ZBar Activity what type of scanning to perform
	  			intent.putExtra(ZBarConstants.SCAN_MODES, new int[]{Symbol.QRCODE, Symbol.ISBN10, Symbol.ISBN13});
	  			//Getting a result from this Activity, the contents of the barcode
	  			startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
	  			
	  		}
		});
		
		historyButton.setOnClickListener(new View.OnClickListener(){
	  		public void onClick(View arg0) {
		  	//		Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
	  		//	startActivity(intent);
	  			//On click of the history button we want to show the donor's donation history
	  			
	  		}
		});
		
		aboutButton.setOnClickListener(new View.OnClickListener(){
	  		public void onClick(View arg0) {
	  			
	  			//On click of the about button we want to bring a About Pullee page up
	  			/** For now this is the substitute for the Insert Button */
	  			Intent intent = new Intent(MainActivity.this, InsertActivity.class);
	  			startActivity(intent);
	  		}
		});
		
	}

	/** Below method is what is called when the scan is performed, on result of the ZBar Activity */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{    
		//String to hold the actual content of the barcode
		String scanResult = null;
		
	    if (resultCode == RESULT_OK) 
	    {
	    	//if scan was performed okay, get the content of the scan
	    	scanResult = data.getStringExtra(ZBarConstants.SCAN_RESULT);  
	    	
	    } else if(resultCode == RESULT_CANCELED) {
	    	//else if the scan was not performed okay pop up a toast message
	        Toast.makeText(this, "Scan Cancelled", Toast.LENGTH_SHORT).show();
	        
	    }
	    
	    if (scanResult != null) {

			/** if scanResult (the value returned from the barcode scan) is not null then 
	    	  we have a successful scan and need to get the data for this person from 
	    	  Parse. QR-Code's hold the person's name */
	    	
	    	//Bring up the loading screen 
	    	loadingScreen.setVisibility(View.VISIBLE);
	    	
	    	//Set up a ParseQuery for the Person class and find the Person who's name matches
	    	//  what was encoded in the QR-Code. 
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Person");
  			query.whereEqualTo("objectId", scanResult);
  			//Run the query in a background thread, good practice to handle network connections on a seperate thread
  			query.findInBackground(new FindCallback<ParseObject>() {
  			    public void done(List<ParseObject> scoreList, ParseException e) {
  			        if (e == null) {
  			        	//if there was no ParseException continue here
  			        	if(scoreList.size() > 0){
  			        		//if the size of the list of Person's returned from the query has elements get the first one
  			        		//  (there should only be 1 person in the list)
  			        		ParseObject person = scoreList.get(0);
  	  			        	
  			        		Global.receiver = person;
  			        		
  			        		//Set global variables equal to the person's information so they can be accessed and used 
  			        		//  for display in the ScanResultActivity
  	  			        	Global.name = person.getString("name");
  	  			        	Global.story = person.getString("story");
  	  			        	Global.team =  person.getString("team");
  	  		                Global.donated = "Donated: " + person.getBoolean("Donate");
  	  		                Global.id = person.getObjectId();
  	  		                
  	  		                Global.accountBalance = person.getNumber("credits").doubleValue();
  	  		                
  			        	} else {
  			        		//else if no Person's were returned and no exception, it means no such person is in our DB
  			        		Global.name = "DID NOT FIND";
  	  						Global.story = "DID NOT FIND";
  	  						Global.team = "DID NOT FIND";
  	  						Global.donated= "DID NOT FIND";
  			        	}
  			        	
  			        	//Start the ScanResultActivity to show the results of a successful scan
  			        	Intent intentToScan = new Intent(MainActivity.this, ScanResultActivity.class);
  	  			        startActivity(intentToScan);
  			        	
  			        } else {
  			        	
  			        	//else if there was a exception handle it
  			        	
  			        }
  			        
  			      //Get rid of the loading screen
  			      loadingScreen.setVisibility(View.INVISIBLE);

  			    }
  			});	
		}
	}
	
}
