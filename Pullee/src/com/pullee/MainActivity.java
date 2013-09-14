package com.pullee;

import java.util.List; 

import net.sourceforge.zbar.Symbol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


import com.dm.zbar.android.scanner.ZBarConstants;
import com.dm.zbar.android.scanner.ZBarScannerActivity;

public class MainActivity extends Activity {

	private Button scanButton;
	private Button insertButton;
	
	private String barcode;
	//shoulda come from git
	private RelativeLayout loadingScreen;

	private static final int ZBAR_SCANNER_REQUEST = 0;
    private static final int ZBAR_QR_SCANNER_REQUEST = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Parse.initialize(this, "9F2wROYBUEyqci0je6JBPPP1xmNKJcLfc0IACtce", "Zc95yEL51OX8db1jNEaqJ1suLcD1HmDKoptadApL"); 
		ParseAnalytics.trackAppOpened(getIntent());		 
		setContentView(R.layout.activity_main);
		
		
		scanButton = (Button) this.findViewById(R.id.ScanButton);
		insertButton = (Button) this.findViewById(R.id.InsertButton);
		//shoulda come from git
		loadingScreen = (RelativeLayout) this.findViewById(R.id.LoadingScreen);

		
		insertButton.setOnClickListener(new View.OnClickListener(){
	  		public void onClick(View arg0) {
	  			
	  			Intent myIntent = new Intent(MainActivity.this, InsertActivity.class);
	  			startActivity(myIntent);

	  		}
		});
		
		scanButton.setOnClickListener(new View.OnClickListener(){
	  		public void onClick(View arg0) {
	  			
	  			Intent intent = new Intent(MainActivity.this, ZBarScannerActivity.class);
	  			intent.putExtra(ZBarConstants.SCAN_MODES, new int[]{Symbol.QRCODE, Symbol.ISBN10, Symbol.ISBN13});
	  			startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
	  			
	  			
	  			/**
	  			 * Code commented out below uses ZXing for scanning instead of ZBar
	  			 */
	  			
	  			/*
	  			Intent intent = new Intent(MainActivity.this, CameraTestActivity.class);
	  			startActivity(intent);
	  			
	  			IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
	  			integrator.initiateScan();
	  		    //shoulda come from git
	  			loadingScreen.setVisibility(View.VISIBLE);
	  			*/
	  			
	  		}
		});
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{    
	    if (resultCode == RESULT_OK) 
	    {
	        // Scan result is available by making a call to data.getStringExtra(ZBarConstants.SCAN_RESULT)
	        // Type of the scan result is available by making a call to data.getStringExtra(ZBarConstants.SCAN_RESULT_TYPE)
	        Toast.makeText(this, "Scan Result = " + data.getStringExtra(ZBarConstants.SCAN_RESULT), Toast.LENGTH_SHORT).show();
	        //Toast.makeText(this, "Scan Result Type = " + data.getStringExtra(ZBarConstants.SCAN_RESULT_TYPE), Toast.LENGTH_SHORT).show();
	        // The value of type indicates one of the symbols listed in Advanced Options below.
	    } else if(resultCode == RESULT_CANCELED) {
	        Toast.makeText(this, "Camera unavailable", Toast.LENGTH_SHORT).show();
	    }
	    
	    String scanResult = data.getStringExtra(ZBarConstants.SCAN_RESULT);
	    
	    if (scanResult != null) {

			//handle scan result, string contents holds the results from the scan
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Person");
  			query.whereEqualTo("name", scanResult);
  			query.findInBackground(new FindCallback<ParseObject>() {
  			    public void done(List<ParseObject> scoreList, ParseException e) {
  			        if (e == null) {
  			        	
  			        	if(scoreList.size() > 0){
  			        		ParseObject person = scoreList.get(0);
  	  			        	
  			        		//Not understanding the purpose of this initial print statement
  			        		//it appears to me that these print statements arent even doing any
  			        		//in app work
  			        		
  	  			        	
  	  			        	Global.name = person.getString("name");
  	  			        	Global.story = person.getString("story");
  	  			        	Global.team =  person.getString("team");
  	  		                Global.donated = "Donated: " + person.getBoolean("Donate");
  	  		                Global.id = person.getObjectId();
  			        	} else {
  			        		
  			        		Global.name = "DID NOT FIND";
  	  						Global.story = "DID NOT FIND";
  	  						Global.team = "DID NOT FIND";
  	  						Global.donated= "DID NOT FIND";
  			        		
  			        	}
  			        	
  			        	Intent intentToScan = new Intent(MainActivity.this, ScanResultActivity.class);
  	  			        startActivity(intentToScan);
  			        	
  			        } else {
  			        	
  			        	
  			        	
  			        }
  			        
  			      //shoulda come from git
  			      loadingScreen.setVisibility(View.INVISIBLE);

  			    }
  			});
			
		}
	    
	}
	
	
	/**
	 * Code commented out below uses ZXing for scanning instead of ZBar
	 */
	
	/*
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {

		
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanResult != null) {

			//handle scan result, string contents holds the results from the scan
			barcode = scanResult.getContents();
			
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Person");
  			query.whereEqualTo("name", barcode);
  			query.findInBackground(new FindCallback<ParseObject>() {
  			    public void done(List<ParseObject> scoreList, ParseException e) {
  			        if (e == null) {
  			        	
  			        	if(scoreList.size() > 0){
  			        		ParseObject person = scoreList.get(0);
  	  			        	
  			        		//Not understanding the purpose of this initial print statement
  			        		//it appears to me that these print statements arent even doing any
  			        		//in app work
  			        		
  	  			        	
  	  			        	Global.name = person.getString("name");
  	  			        	Global.story = person.getString("story");
  	  			        	Global.team =  person.getString("team");
  	  		                Global.donated = "Donated: " + person.getBoolean("Donate");
  	  		                Global.id = person.getObjectId();
  			        	} else {
  			        		
  			        		Global.name = "DID NOT FIND";
  	  						Global.story = "DID NOT FIND";
  	  						Global.team = "DID NOT FIND";
  	  						Global.donated= "DID NOT FIND";
  			        		
  			        	}
  			        	
  			        	Intent intentToScan = new Intent(MainActivity.this, ScanResultActivity.class);
  	  			        startActivity(intentToScan);
  			        	
  			        } else {
  			        	
  			        	
  			        	
  			        }
  			     //shoulda come from git
  			      loadingScreen.setVisibility(View.INVISIBLE);

  			    }
  			});
			
		}

		if (resultCode == RESULT_CANCELED) {

			//Handle scan failure, rescan
			IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
			integrator.initiateScan();

		}

	} */

	
}
