package com.pullee;

import java.io.File;

import com.parse.ParseObject;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class InsertActivity extends Activity {
	
	private static final int ACTION_CODE = 0;
	//create variables
	private EditText insertName, insertStory;
	private TextView heading, name, story;
	private Button saveButton, imageButton;
	//private ImageView imageView;
	private File imageFile;

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
		imageButton = (Button) this.findViewById(R.id.imageButton);
		//imageView = (ImageView) this.findViewById(R.id.imageView);
		
		imageButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//private void dispatchTakePictureIntent(int actionCode) {
				 //Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				   // startActivityForResult(takePictureIntent, actionCode);
				
				
			}
		});
				
		imageButton.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				File picture = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
				imageFile = new File(picture, "pullee_image");
				

			    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

//			    startActivityForResult(takePictureIntent, actionCode);
			    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
			    startActivityForResult(takePictureIntent, ACTION_CODE);

			    
			   //First attempt to get the image to show up after taking the picture
			   

			}
		});
		
		
		
		
		
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
					
					//code for sending toast
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		System.out.println("Im here now");
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

			
			if(scanResult != null)
			{
				//imageFile = scanResult.getContents();
				ParseObject newGuy = new ParseObject("Person");
				newGuy.put("Image", imageFile);
				newGuy.saveInBackground();				
			}
			
			else
			{
				Toast.makeText(this, "Unable to save file",Toast.LENGTH_LONG).show();		
			}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.insert, menu);
		return true;
	}

}
