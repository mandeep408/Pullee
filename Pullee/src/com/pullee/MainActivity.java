package com.pullee;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MainActivity extends Activity {

	private Button scanButton;
	private Button insertButton;
	
	private String barcode;
	
	/**
	 * Example of pushing data into Parse Database:
	 * 
	 * 		ParseObject testObject = new ParseObject("Person");
	 * 		testObject.put("name", "Chirag Singh Toor");
	 * 		testObject.put("story", "Born in Canada, Moved to the US, going to UCSD for a B.S. in Comp Sci.");
	 * 		testObject.put("team", "Vancouver Canucks");
	 * 		testObject.saveInBackground();
	 * 
	 * 
	 * Example of pulling data from Parse Database:
	 * 
	 * 		ParseQuery<ParseObject> query = ParseQuery.getQuery("Person");
  	 *		query.whereEqualTo("name", "Sudjeev Singh");
  	 *		query.findInBackground(new FindCallback<ParseObject>() {
  	 *		    public void done(List<ParseObject> scoreList, ParseException e) {
  	 *		        if (e == null) {
  	 *
  	 *		        	//PULL DATA WAS A SUCCESS, ACCESS FIELDS LIKE BELOW
  	 *		        	ParseObject person = scoreList.get(0);
  	 *		        	Global.name = person.getString("name");
  	 *		        	Global.story = person.getString("story");
  	 *		        	Global.team = person.getString("team");
  	 *		        	
  	 *		        } else {
  	 *		        	//PULL DATA WAS A FAILURE
  	 *		        }
  	 *		    }
  	 *		});
	 * 
	 */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Parse.initialize(this, "9F2wROYBUEyqci0je6JBPPP1xmNKJcLfc0IACtce", "Zc95yEL51OX8db1jNEaqJ1suLcD1HmDKoptadApL"); 
		ParseAnalytics.trackAppOpened(getIntent());
		
		setContentView(R.layout.activity_main);
		
		
		scanButton = (Button) this.findViewById(R.id.ScanButton);
		insertButton = (Button) this.findViewById(R.id.InsertButton);
		insertButton.setOnClickListener(new View.OnClickListener(){
	  		public void onClick(View arg0) {
	  			
	  		}
		});
		
		scanButton.setOnClickListener(new View.OnClickListener(){
	  		public void onClick(View arg0) {
	  			
	  			IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
	  			integrator.initiateScan();
	  			
	  		}
		});
		
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {

		/*
		 * Barcode scanning result here
		 */
		
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
  			        		/*
  	  			        	System.out.println("BARCODE = " + barcode);
  	  			        	System.out.println("NAME = " + person.getString("name"));
  	  			        	System.out.println("STORY = " + person.getString("story"));
  	  			        	System.out.println("TEAM = " + person.getString("team"));
  	  			        	*/
  	  			        	
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
  			        
  			    }
  			});
			
		}

		if (resultCode == RESULT_CANCELED) {

			//Handle scan failure, rescan
			IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
			integrator.initiateScan();

		}

	}
	
}
