package com.pullee;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDoneException;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignInActivity extends Activity {

	private TextView directionsText;
	
	private EditText usernameEditText;
	private EditText passwordEditText;
	
	private Button loginButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/** Required Parse code, first line is to initialize and use Parse, 2nd is to stard App statistic recording */
		Parse.initialize(this, "9F2wROYBUEyqci0je6JBPPP1xmNKJcLfc0IACtce", "Zc95yEL51OX8db1jNEaqJ1suLcD1HmDKoptadApL"); 
		ParseAnalytics.trackAppOpened(getIntent());		 
		
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
		
		directionsText = (TextView) this.findViewById(R.id.DirectionsText);
		
		usernameEditText = (EditText) this.findViewById(R.id.Username);
		passwordEditText = (EditText) this.findViewById(R.id.Password);
		
		loginButton = (Button) this.findViewById(R.id.LogInButton);
		
		loginButton.setOnClickListener(new View.OnClickListener(){
	  		public void onClick(View arg0) {
	  			
	  			try {
					ParseUser user = ParseUser.logIn(usernameEditText.getText().toString(), passwordEditText.getText().toString());
					
					if(user != null){
						
						Intent intent = new Intent(SignInActivity.this, MainActivity.class);
						startActivity(intent);
						
					}
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	  			
  			
  		}
	});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_in, menu);
		return true;
	}

}
