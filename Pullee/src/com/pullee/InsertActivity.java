package com.pullee;

import java.io.ByteArrayOutputStream;
import java.io.File;

import com.parse.ParseFile;
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
	private TextView heading;
	private EditText insertName, insertStory;
	private Button saveButton, imageButton;
	private ImageView profileImage;
	private File imageFile;
	private byte[] byteArray;
	private boolean accessed = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_insert);
		
		//link up variables to their positions on the layout using id's in R.java
		
		heading = (TextView) this.findViewById(R.id.Heading);
 
		
		insertName = (EditText) this.findViewById(R.id.InsertName);
		insertStory = (EditText) this.findViewById(R.id.InsertStory);
		
		saveButton = (Button) this.findViewById(R.id.SaveButton);
		imageButton = (Button) this.findViewById(R.id.ImageButton);
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
			}
		});
		
		
		
		
		
		//method that will be executed when the save button is clicked
		saveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				if( insertName.getText().length() != 0 && insertStory.getText().length() != 0 && accessed)
				{
					final String name = insertName.getText().toString();
					final String story = insertStory.getText().toString();
					
					//storing the actual file on parse using parsefile objects
					
					ParseFile file = new ParseFile("image.png", byteArray);
					file.saveInBackground();
				
					ParseObject newGuy = new ParseObject("Person");
					newGuy.put("name", name);
					newGuy.put("story", story);
					newGuy.put("Image", file);
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

	//Getting result of taking the picture
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
	
			if(requestCode == ACTION_CODE)
			{
				//gets bitmap data of whats stored at imageFile
				Bitmap photo = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
				
				//if the photo is valid
				if(photo != null)
				{
					//set the imageview to the image so it can be seen
					profileImage = (ImageView) this.findViewById(R.id.ProfileImage);
					profileImage.setImageBitmap(photo);
					
					//convert the image to a bytearray so it can be stored on parse
					//got this code from stackoverflow
					
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
					byte[] byteA = stream.toByteArray();
					byteArray = byteA;
					accessed = true;			
				}
			}
			else
			{
				heading.setText("Failed to create image");
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
