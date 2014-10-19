package com.ardu.gorient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

public class Navigate extends Activity implements OnClickListener  {
	TextView tv2;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_navigate);
		Button buttonGenerateRoute= (Button) findViewById(R.id.button_generate_route);
		buttonGenerateRoute.setOnClickListener(this);
		AutoCompleteTextView destination = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView_destination);
		tv2= (TextView) findViewById(R.id.textView2);
		destination.setAdapter(new PlacesAutoCompleteAdapter(this,R.layout.list_item ));
	//android.R.layout.simple_dropdown_item_1line
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.button_generate_route)
		{
			Intent i = new Intent( this, Route.class );

			i.putExtra( "destination", tv2.getText().toString() );
			 
			startActivity( i );
			//startActivity(new Intent(Navigate.this,Route.class));
		}
		
	}
	

}
