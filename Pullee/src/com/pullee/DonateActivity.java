package com.pullee;

import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DonateActivity extends Activity {

	private Button fiftyCentsButton;
	private Button dollarButton;
	private Button fiveDollarButton;
	
	private Button backButton;
	
	private TextView directionsText;
	
	private double donateAmount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_donate);
		
		fiftyCentsButton = (Button) this.findViewById(R.id.FiftyCentsButton);
		dollarButton = (Button) this.findViewById(R.id.DollarButton);
		fiveDollarButton = (Button) this.findViewById(R.id.FiveDollarButton);
		
		directionsText = (TextView) this.findViewById(R.id.DirectionsText);
		
		backButton = (Button) this.findViewById(R.id.BackButton);
		
		fiftyCentsButton.setOnClickListener(new View.OnClickListener(){
	  		public void onClick(View arg0) {
	  			donateAmount = 0.50;

	  			donate();
	  		}
		});
		
		dollarButton.setOnClickListener(new View.OnClickListener(){
	  		public void onClick(View arg0) {
	  			donateAmount = 1.00;

	  			donate();
	  		}
		});
		
		fiveDollarButton.setOnClickListener(new View.OnClickListener(){
	  		public void onClick(View arg0) {
	  			donateAmount = 5.00;
	  			
	  			donate();
	  		}
		});
		
		backButton.setOnClickListener(new View.OnClickListener(){
	  		public void onClick(View arg0) {
	  			finish();
	  		}
		});
		
	}
	
	public void donate(){
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Person");
		query.whereEqualTo("name", Global.name);
		
		//Run the query in a background thread, good practice to handle network connections on a seperate thread
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> scoreList, ParseException e) {
		        if (e == null) {
		        	
	        		//if the size of the list of Person's returned from the query has elements get the first one
	        		//  (there should only be 1 person in the list)
	        		ParseObject person = scoreList.get(0);
	        		System.out.println(person.getString("name"));
		        	System.out.println("ABOUT TO INCREMENT CREDITS");
		        	
		        	Number number = donateAmount;
		        	
	        		person.increment("credits", number);
	        		person.saveInBackground();
	        		System.out.println("INCREMENTED CREDITS");
	        		
	        		System.out.println("CREATING DONATION OBJECT");
	        		ParseObject donation = new ParseObject("Donation");
	        		donation.put("donationAmount", number);
	        		donation.put("donor", ParseUser.getCurrentUser());
  		            donation.put("receiver", Global.receiver);
  		            System.out.println("DONATION OBJECT CREATED");
  		          
  		            donation.saveInBackground();
  		            System.out.println("DONATION OBJECT SAVED");
  		            
  		          //Adding a toast
  		            Context context = getApplicationContext();
					CharSequence text = "Thank you so much for your donation!!";
					int duration = Toast.LENGTH_SHORT;
	
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
	
  		          
		        }
		        
		    }
		    
		});	
		System.out.println("DONE");
		finish();
	
	}

}
