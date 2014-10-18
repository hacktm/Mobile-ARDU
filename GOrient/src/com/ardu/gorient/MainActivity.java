package com.ardu.gorient;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener{

	public static SharedPreferences bluetoothNamePref;
	public static String bluetoothName;
	public static String bluetoothPairKey;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
/*
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
		/*
		 * Adding listeners for the buttons
		 * 
		 */
		
		Button buttonNavigate = (Button) findViewById(R.id.button_navigate);
		buttonNavigate.setOnClickListener(this);
		Button buttonSettings = (Button) findViewById(R.id.button_settings);
		buttonSettings.setOnClickListener(this);
		Button buttonAbout = (Button) findViewById(R.id.button_about);
		buttonAbout.setOnClickListener(this);
		Button buttonExit = (Button) findViewById(R.id.button_iesire);
		buttonExit.setOnClickListener(this);
		
		bluetoothNamePref = this.getSharedPreferences("bluetoothPrefs", Context.MODE_PRIVATE);
		bluetoothName= bluetoothNamePref.getString("bluetoothName", "-NotSet");
		bluetoothPairKey = bluetoothNamePref.getString("bluetoothPairKey", "1234");
		if(bluetoothName=="-NotSet")
		{
			final Dialog d = new Dialog(MainActivity.this);
			d.setContentView(R.layout.fragment_settings);
			d.setCanceledOnTouchOutside(false);
			d.setCancelable(false);
			d.show();
			final Button b1;
			b1=(Button) d.findViewById(R.id.button_ok);
			b1.setOnClickListener(new OnClickListener () 
			{
			    public void onClick(View v) 

			    {
			    	
			    	EditText editTextBluetoothName= (EditText) d.findViewById(R.id.editText_bluetooth_name);
			    	EditText editTextBluetoothPairKey= (EditText) d.findViewById(R.id.editText_pair_key);
			    	if(editTextBluetoothName.getText().toString().length()>2 && editTextBluetoothPairKey.getText().toString().length()>2)
			    	{
			    	bluetoothName = editTextBluetoothName.getText().toString();
			    	bluetoothPairKey = editTextBluetoothPairKey.getText().toString();
			    	d.dismiss();
			    	}
			    	else
			    	{
			    		Toast.makeText(MainActivity.this, "Make sure you entered the right information", Toast.LENGTH_SHORT).show();
			    	}
					
			    }
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	/*
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}*/ 
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if( v.getId()==R.id.button_navigate)
		{
			/*
			 * Navigate the world
			 */
			startActivity(new Intent(MainActivity.this,Navigate.class));
		}
		if( v.getId()==R.id.button_settings)
		{
			/*
			 * Settings
			 */
			startActivity(new Intent(MainActivity.this, Settings.class ));
		}
		if( v.getId()==R.id.button_about)
		{
			/*
			 * About us and the app
			 */
			Toast.makeText(this, "Made for hackTM by the best!", Toast.LENGTH_LONG).show();
		}
		if( v.getId()==R.id.button_iesire)
		{
			/*
			 * Leave the app :(
			 */
			finish();
			
		}
		
	}
	protected  void onStop() {
		SharedPreferences prefs = this.getSharedPreferences("bluetoothPrefs", Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putString("bluetoothName", bluetoothName);
		editor.putString("bluetoothPairKey", bluetoothPairKey);
		editor.commit();
		super.onStop();
		
	}

}
