package com.ardu.gorient;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends Activity implements OnClickListener {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_settings);
		Button buttonOK;
		 buttonOK=(Button) findViewById(R.id.button_ok);
		 buttonOK.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.button_ok)
		{
			EditText editTextBluetoothName= (EditText) findViewById(R.id.editText_bluetooth_name);
	    	EditText editTextBluetoothPairKey= (EditText) findViewById(R.id.editText_pair_key);
	    	if(editTextBluetoothName.getText().toString().length()>2 && editTextBluetoothPairKey.getText().toString().length()>2)
	    	{
	    	MainActivity.bluetoothName = editTextBluetoothName.getText().toString();
	    	MainActivity.bluetoothPairKey = editTextBluetoothPairKey.getText().toString();
	    	finish();
	    	}
	    	else
	    	{
	    		Toast.makeText(Settings.this, "Make sure you entered the right information", Toast.LENGTH_SHORT).show();
	    	}
		}
		
	}

}
