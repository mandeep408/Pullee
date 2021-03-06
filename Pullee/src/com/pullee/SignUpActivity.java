package com.pullee;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends Activity
{
	private TextView smallText;
	private EditText username, password, email;
    private Button signupButton, smallButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
				
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		
		username = (EditText) this.findViewById(R.id.username);
		password = (EditText) this.findViewById(R.id.password);
		email = (EditText) this.findViewById(R.id.email);
		signupButton = (Button) this.findViewById(R.id.signup);
		smallButton = (Button) this.findViewById(R.id.smallButton);
		smallText = (TextView) this.findViewById(R.id.smallText);
		
		signupButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//parse automatically checks the uniqueness of username/email
				
				if(username.getText().length() != 0 && password.getText().length() >= 5 && email.getText().length() != 0)
				{
					final String user = username.getText().toString();
					final String pswd = password.getText().toString();
					final String eml = email.getText().toString();
					
					ParseUser newuser = new ParseUser();
					newuser.setUsername(user);
					newuser.setPassword(pswd);
					newuser.setEmail(eml);					
					
					newuser.signUpInBackground(new SignUpCallback() {
						  public void done(ParseException e) {
						    if (e == null) {
						      // Hooray! Let them use the app now.
					  			Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
					  			startActivity(intent);
						    } else {
						      // Sign up didn't succeed. Look at the ParseException
						      // to figure out what went wrong
						    }
						  }
						});
				}
				
				else
				{
					Context context = getApplicationContext();
					CharSequence text = "Please enter a username and a password of length above 5 chars";
					int duration = Toast.LENGTH_SHORT;
	
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();	
				}
			}

		});

		smallButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
	  			startActivity(intent);
			}
		});
		
	}
}
