package com.ardu.gorient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class Navigate extends Activity implements OnClickListener  {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_navigate);
		Button buttonGenerateRoute= (Button) findViewById(R.id.button_generate_route);
		buttonGenerateRoute.setOnClickListener(this);
		AutoCompleteTextView destination = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView_destination);
		destination.setAdapter(new PlacesAutoCompleteAdapter(this, android.R.layout.simple_dropdown_item_1line));
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.button_generate_route)
		{
			startActivity(new Intent(Navigate.this,Route.class));
		}
		
	}
	

}
