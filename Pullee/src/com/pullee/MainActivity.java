package com.pullee;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button scanButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Parse.initialize(this, "9F2wROYBUEyqci0je6JBPPP1xmNKJcLfc0IACtce", "Zc95yEL51OX8db1jNEaqJ1suLcD1HmDKoptadApL"); 
		ParseAnalytics.trackAppOpened(getIntent());
		
		setContentView(R.layout.activity_main);
		
		scanButton = (Button) this.findViewById(R.id.ScanButton);
		
		scanButton.setOnClickListener(new View.OnClickListener(){
	  		public void onClick(View arg0) {
	  			//Intent intent = new Intent(MainActivity.this, SelectTypeActivity.class);
	  			//startActivity(intent);
	  			
	  			
	  			
	  		}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		System.out.println("TEST TEXT MESS");
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
