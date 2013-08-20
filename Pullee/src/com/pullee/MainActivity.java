package com.pullee;

import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button scanButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Parse.initialize(this, "9F2wROYBUEyqci0je6JBPPP1xmNKJcLfc0IACtce", "Zc95yEL51OX8db1jNEaqJ1suLcD1HmDKoptadApL"); 
		ParseAnalytics.trackAppOpened(getIntent());
		
		setContentView(R.layout.activity_main);
		/*
		ParseObject testObject = new ParseObject("Person");
		testObject.put("name", "Gurs");
		testObject.put("story", "Fresno");
		testObject.put("weight", "built as fuck");
		testObject.saveInBackground();
		*/
		
		
		
		scanButton = (Button) this.findViewById(R.id.ScanButton);
		
		scanButton.setOnClickListener(new View.OnClickListener(){
	  		public void onClick(View arg0) {
	  			//Intent intent = new Intent(MainActivity.this, SelectTypeActivity.class);
	  			//startActivity(intent);
	  			
	  			ParseQuery<ParseObject> query = ParseQuery.getQuery("Person");
	  			query.whereEqualTo("name", "Sudjeev");
	  			query.findInBackground(new FindCallback<ParseObject>() {
	  			    public void done(List<ParseObject> scoreList, ParseException e) {
	  			        if (e == null) {
	  			            Log.d("score", "Retrieved " + scoreList.size() + " scores");
	  			            
	  			            String text = "";
	  			            int count = 0;
	  			            
	  			            for(ParseObject sTest: scoreList){
	  			            	
	  			            	text += sTest.getString("story") + " ";
	  			            	count++;
	  			            	
	  			            }
	  			            
	  			            scanButton.setText(count + " " + text);
	  			        } else {
	  			            Log.d("score", "Error: " + e.getMessage());
	  			        }
	  			    }
	  			});
	  			
	  			/*
	  			ParseObject testObject = new ParseObject("Person");
	  			testObject.put("name", "Sudjeev");
	  			testObject.put("story", "From Mexico");
	  			testObject.put("height", "tall");
	  			testObject.saveInBackground();
	  			*/
	  			
	  		}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		System.out.println("TEST TEXT MESS");
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
