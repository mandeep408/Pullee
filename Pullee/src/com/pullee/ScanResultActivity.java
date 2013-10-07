package com.pullee;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

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
	
	/** Bitmap that holds the downloaded image */
	private Bitmap loadedBitmap;
	
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
		//  efficiently without freezing the UI
		new LoadImage().execute();
		
	}
	
	class LoadImage extends AsyncTask<String, String, String> {
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Before loading the image want to make the profilePic invisible 
            //  and bring up the spinner to show it is loading
    		spinner.setVisibility(View.VISIBLE);
    		profilePic.setVisibility(View.INVISIBLE);
    		
        }
 
        protected String doInBackground(String... args) {
        	
        	//Code to pull down image from Parse
    		ParseQuery<ParseObject> query = ParseQuery.getQuery("Person");
			query.whereEqualTo("name", Global.name);
			
			List<ParseObject> scoreList;
			
			try {
				//use query.find() because we want to run the query on the same Thread, 
				//  doing in the background would make the code go to onPostExecute() 
				//  before the image is loaded without waiting for the Bitmap
				scoreList = query.find();
				
				if(scoreList.size() > 0){
	        		ParseObject person = scoreList.get(0);
	        		//Get the ParseFile which is the Image of the person
	                ParseFile bum = (ParseFile) person.get("Image");
	                //Not everyone has a picture so do a null check
	                if(bum != null){
	                	//try catch for the ParseException 
		                try {
		                	//get the data in byte form and decode into a Bitmap
							byte[] file = bum.getData();
						    loadedBitmap = BitmapFactory.decodeByteArray(file,0,file.length);
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	                
	                }

	        	}
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
    		return null;
    		
        }
 
       
        protected void onPostExecute(String file_url) {

        	//Set the profilePic to be the laodedBitmap with a safety null check
        	if(loadedBitmap != null){
        		profilePic.setImageBitmap(loadedBitmap);
        		
        		//Get rid of the spinner and show the prerson's picture
        		spinner.setVisibility(View.INVISIBLE);
        		profilePic.setVisibility(View.VISIBLE);
        	} else{
        		//else if the bitmap is null make both the ImageView and Spinner 
        		//  invisible
        		spinner.setVisibility(View.INVISIBLE);
        		profilePic.setVisibility(View.INVISIBLE);
        	}
        	
        	
    		
        }
 
    }
	
}
		
	



	  
