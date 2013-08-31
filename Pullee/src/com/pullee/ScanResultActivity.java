package com.pullee;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ScanResultActivity extends Activity {

	private TextView nameText;
	private TextView storyText;
	//private TextView teamText;
	//private TextView donationText;
	
	private Button backButton;
	private Button donateButton;
	private ImageView profilePic;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan_result);
		
		nameText = (TextView) this.findViewById(R.id.NameText);
		storyText = (TextView) this.findViewById(R.id.StoryText);
		//teamText = (TextView) this.findViewById(R.id.TeamText);
		//donationText = (TextView) this.findViewById(R.id.DonationText);
		
		donateButton = (Button)this.findViewById(R.id.dButton);
		backButton = (Button) this.findViewById(R.id.BackButton);
		profilePic = (ImageView) this.findViewById(R.id.profilePic);
		
		nameText.setText(Global.name);
		storyText.setText(Global.story);
		//teamText.setText(Global.team);
		//donationText.setText(Global.donated);
		
		
		//Code to pull down image and set profile pic
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
		/*ParseQuery<ParseObject> query = ParseQuery.getQuery("Person");
			query.whereEqualTo("name", Global.name);
			query.findInBackground(new FindCallback<ParseObject>() {
			    public void done(List<ParseObject> scoreList, ParseException e) {
			        if (e == null) {
			        	if(scoreList.size() > 0){
			        		ParseObject person = scoreList.get(0);
			        		person.get("Donate", true);
			        		person.saveInBackground();
			        	}
			        }
			    }
			});*/
		
		//actual implementation of the functionality of the button.
		backButton.setOnClickListener(new View.OnClickListener(){
	  		public void onClick(View arg0) {
	  			finish();
	  		}
		});
		
		donateButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				donateButton.setText("Thanks for your donation");
				
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
}
}
		
	



	  
