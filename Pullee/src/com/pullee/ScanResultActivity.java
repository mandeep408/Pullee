package com.pullee;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScanResultActivity extends Activity {

	private TextView nameText;
	private TextView storyText;
	private TextView teamText;
	private TextView donationText;
	
	private Button backButton;
	private Button donateButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan_result);
		
		nameText = (TextView) this.findViewById(R.id.NameText);
		storyText = (TextView) this.findViewById(R.id.StoryText);
		teamText = (TextView) this.findViewById(R.id.TeamText);
		donationText = (TextView) this.findViewById(R.id.DonationText);
		
		donateButton = (Button)this.findViewById(R.id.dButton);
		backButton = (Button) this.findViewById(R.id.BackButton);
		
		nameText.setText(Global.name);
		storyText.setText(Global.story);
		teamText.setText(Global.team);
		donationText.setText(Global.donated);
		
		//actual implementation of the functionality of the button.
		backButton.setOnClickListener(new View.OnClickListener(){
	  		public void onClick(View arg0) {
	  			finish();
	  		}
		});
		
		donateButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		}); 
				


}}
