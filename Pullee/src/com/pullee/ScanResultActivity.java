package com.pullee;

import java.util.List; 

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ScanResultActivity extends Activity {

	/** All TextViews for the screen, some are part of RelativeLayout buttons */
	private TextView nameText;
	private TextView storyText;
	private TextView donateButtonText;
	private TextView backButtonText;
	
	/** Buttons are actually RelativeLayout ot fit UI design style */
	private RelativeLayout backButton;
	private RelativeLayout donateButton;
	
	/** ImageView to hold the persons image if it exists */
	private ImageView profilePic;
	
	/** Image loading spinner */
	private ProgressBar spinner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Get rid of the title bar for the app
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_scan_result);
		
		/** Match UI Views to corresponding elements in the XML Layout */
		nameText = (TextView) this.findViewById(R.id.NameText);
		storyText = (TextView) this.findViewById(R.id.StoryText);
		
		donateButtonText = (TextView) this.findViewById(R.id.DonateButtonText);
		backButtonText = (TextView) this.findViewById(R.id.BackButtonText);
		
		donateButton = (RelativeLayout)this.findViewById(R.id.DonateButton);
		backButton = (RelativeLayout) this.findViewById(R.id.BackButton);
		
		profilePic = (ImageView) this.findViewById(R.id.profilePic);
		
		spinner = (ProgressBar) this.findViewById(R.id.ImageSpinner);
		
		spinner.setVisibility(View.VISIBLE);
		profilePic.setVisibility(View.INVISIBLE);
		
		//Set the name and story to be what was pulled from the DB after 
		//  a successful scan
		nameText.setText(Global.name);
		storyText.setText(Global.story);
		
		/** Set click listeners for the screen's buttons */
		
		
		backButton.setOnClickListener(new View.OnClickListener(){
	  		public void onClick(View arg0) {
	  		//Back button just finishes the current activity
	  			finish();
	  		}
		});
		
		donateButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//Donate button updates buttons' text
				donateButtonText.setText("Thanks for your donation");
				//And updates DB to say this person donated to the scanned homeless person
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Person");
	  			query.whereEqualTo("name", Global.name);
	  			query.findInBackground(new FindCallback<ParseObject>() {
	  			    public void done(List<ParseObject> scoreList, ParseException e) {
	  			        if (e == null) {
	  			        	if(scoreList.size() > 0){
	  			        		ParseObject person = scoreList.get(0);
	  			        		person.put("Donate", true);
	  			        		person.saveInBackground();
	  			        	}
	  			        }
	  			    }
	  			});
			}
		});
		
		//Launch the AsyncTask for loading the person's image
		new LoadImage().execute();
		
	}
	
	class LoadImage extends AsyncTask<String, String, String> {
 
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

    		spinner.setVisibility(View.VISIBLE);
    		profilePic.setVisibility(View.INVISIBLE);
    		
        }
 
        
        protected String doInBackground(String... args) {
        	
        	//Code to pull down image from Parse
    		ParseQuery<ParseObject> query = ParseQuery.getQuery("Person");
			query.whereEqualTo("name", Global.name);
			query.findInBackground(new FindCallback<ParseObject>() {
			    public void done(List<ParseObject> scoreList, ParseException e) {
			        if (e == null) {
			        	if(scoreList.size() > 0){
			        		ParseObject person = scoreList.get(0);
			                ParseFile bum = (ParseFile) person.get("Image");
			                try {
								byte[] file = bum.getData();
							    profilePic.setImageBitmap(BitmapFactory.decodeByteArray(file,0,file.length));
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

			        	}
			        }
			    }
			});
            
    		return null;
    		
        }
 
       
        protected void onPostExecute(String file_url) {

        	nameText.setText("done");
    		spinner.setVisibility(View.INVISIBLE);
    		profilePic.setVisibility(View.VISIBLE);
    		
        }
 
    }
	
}
		
	



	  
