package com.pullee;

import com.parse.ParseObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InsertActivity extends Activity {
	
	//create variables
	private EditText insertName, insertStory;
	private TextView heading, name, story;
	private Button saveButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_insert);
		
		//link up variables to their positions on the layout using id's in R.java
		
		heading = (TextView) this.findViewById(R.id.heading);
		name = (TextView) this.findViewById(R.id.name);
		story = (TextView) this.findViewById(R.id.story);
 
		
		insertName = (EditText) this.findViewById(R.id.insertName);
		insertStory = (EditText) this.findViewById(R.id.insertStory);
		
		saveButton = (Button) this.findViewById(R.id.saveButton);
		
		//method that will be executed when the save button is clicked
		saveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				if( insertName.getText().length() != 0 && insertStory.getText().length() != 0)
				{
					final String name = insertName.getText().toString();
					final String story = insertStory.getText().toString();
				
					ParseObject newGuy = new ParseObject("Person");
					newGuy.put("name", name);
					newGuy.put("story", story);
					newGuy.saveInBackground();
					
					Context context = getApplicationContext();
					CharSequence text = "Your information has been saved";
					int duration = Toast.LENGTH_SHORT;
	
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
					
					
					finish();
				}
				
				else
				{
					Context context = getApplicationContext();
					CharSequence text = "Please enter information to all fields";
					int duration = Toast.LENGTH_SHORT;
	
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.insert, menu);
		return true;
	}

}
