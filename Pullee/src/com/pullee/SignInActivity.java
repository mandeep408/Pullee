package com.pullee;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDoneException;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends Activity {

	private TextView directionsText;
	
	private EditText usernameEditText;
	private EditText passwordEditText;
	
	//need to rename vars to be more clear on their function
	private Button loginButton;
	
	/**
	 * STICKING OUT COMMENT 
	 */
	
	private RelativeLayout loadingScreen;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/** Required Parse code, first line is to initialize and use Parse, 2nd is to stard App statistic recording */
		Parse.initialize(this, "9F2wROYBUEyqci0je6JBPPP1xmNKJcLfc0IACtce", "Zc95yEL51OX8db1jNEaqJ1suLcD1HmDKoptadApL"); 
		ParseAnalytics.trackAppOpened(getIntent());		 
		
		PushService.setDefaultPushCallback(this, MainActivity.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
		
		//Get rid of the title bar for the app
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_sign_in);
		
		Data d = new Data(this);
		
		//Do a basic query, catches does not exist exception and
		//  loads DB with very first tuples
		String query = "Select * from Pullee;";
	  	try {
	  		d.select(query);
	  	}
	  	catch(SQLiteDoneException e) {
	  		
	  		d.insert("INSERT INTO Pullee VALUES ('Entered');");
	  		
	  		Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
	  		startActivity(intent);
	  		
	  	}
		
	  	//App stopped crashing once i removed smalltext and button
	  	
		directionsText = (TextView) this.findViewById(R.id.DirectionsText);
		//smallText2 = (TextView) this.findViewById(R.id.smallText2);
		
		usernameEditText = (EditText) this.findViewById(R.id.Username);
		passwordEditText = (EditText) this.findViewById(R.id.Password);
		
		loginButton = (Button) this.findViewById(R.id.LogInButton);
		//smallButton2 = (Button) this.findViewById(R.id.smallButton2);
		
		loadingScreen = (RelativeLayout) this.findViewById(R.id.LoadingScreen);
		
		loadingScreen.setVisibility(View.INVISIBLE);
		
		loginButton.setOnClickListener(new View.OnClickListener(){
	  		public void onClick(View arg0) {
	  			
	  			loadingScreen.setVisibility(View.VISIBLE);
	  			
  				ParseUser.logInInBackground(usernameEditText.getText().toString(), 
  						passwordEditText.getText().toString(), new LogInCallback() {
  				   public void done(ParseUser user, ParseException e) {
  				     if (e == null && user != null) {
  				       
  				    	loadingScreen.setVisibility(View.INVISIBLE);
  				    	 
  				    	Intent intent = new Intent(SignInActivity.this, MainActivity.class);
						startActivity(intent);
  				    	
  				     } else if (user == null) {
  				       
  				    	loadingScreen.setVisibility(View.INVISIBLE); 
  				    	 
  				    	Toast toast = Toast.makeText(SignInActivity.this, "Username or Password invalid", Toast.LENGTH_LONG);
  				    	toast.show();
  				    	 
  				     } else {
  				       
  				    	loadingScreen.setVisibility(View.INVISIBLE); 
  				    	 
  				    	Toast toast = Toast.makeText(SignInActivity.this, "Login Error, please try again", Toast.LENGTH_LONG);
  				    	toast.show();
  				    	 
  				     }
  				   }
  				 });
	  				
	  		}
		});
		
      /* smallButton2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
	  			startActivity(intent);
			}
		});*/
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_in, menu);
		return true;
	}

}
