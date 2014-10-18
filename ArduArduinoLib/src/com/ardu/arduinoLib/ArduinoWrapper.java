package com.ardu.arduinoLib;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

public class ArduinoWrapper {
	private String name;
	private String key;
	private Context mContext;
	private BluetoothAdapter mBluetoothAdapter;
	public BluetoothSocket mSocket;
	private BluetoothDevice mDevice;
	public ConnectedThread mConnectedThread;
	public boolean paired = false;
	private ArduinoCallback callback = null;

	public ArduinoWrapper(String name, String key, Context ctx, ArduinoCallback callback) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.callback = callback;
		this.key = key;
		mContext = ctx;
	}

	public boolean pair() {
		boolean found = false;

		BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
		mBluetoothAdapter = bluetooth;
		// if(!bluetooth.isEnabled()){
		// Intent enableBtIntent = new
		// Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		// startActivityForResult(enableBtIntent, 100);
		// return;
		// }
		Set<BluetoothDevice> devices = bluetooth.getBondedDevices();
		for (BluetoothDevice device : devices) {
			if (device.getName().equals(name)) {
				mDevice = device;
				found = true;
				Log.d("device", "" + found);
			}
		}
		if (!found)
			return false;
		ConnectThread connect = new ConnectThread(mDevice);
		connect.start();
		return found;
	}

	public void send(byte[] data) {
		if(mConnectedThread!=null)
			mConnectedThread.write(data);
		else
			Log.d("connection", "threadnull");
	}
	
	public void disconect(){
		mConnectedThread.cancel();
	}
	
	public boolean isConnected(){
		return paired;
	}
	private class ConnectedThread extends Thread {
		private final BluetoothSocket mmSocket;
		private final InputStream mmInStream;
		private final OutputStream mmOutStream;

		public ConnectedThread(BluetoothSocket socket) {
			mmSocket = socket;
			InputStream tmpIn = null;
			OutputStream tmpOut = null;
			try {
				tmpIn = socket.getInputStream();
				tmpOut = socket.getOutputStream();
			} catch (IOException e) {
			}
			mmInStream = tmpIn;
			mmOutStream = tmpOut;
			paired = true;
			try{
				callback.OnConnected(true);
			} catch (Exception e){
				e.printStackTrace();
			}
			Log.d("connection", "connected");
		}

		public void run() {
			byte[] buffer = new byte[1024];
			int begin = 0;
			int bytes = 0;
			while (true) {
				try {
					bytes += mmInStream.read(buffer, bytes, buffer.length
							- bytes);
					for (int i = begin; i < bytes; i++) {
						if (buffer[i] == "#".getBytes()[0]) {
							try{
								callback.OnReceive(buffer, begin, i);
							} catch (Exception e){
								e.printStackTrace();
							}
							begin = i + 1;
							if (i == bytes - 1) {
								bytes = 0;
								begin = 0;
							}
						}
					}
				} catch (IOException e) {
					break;
				}
			}
		}

		public void write(byte[] bytes) {
			try {
				Log.d("connection", "send");
				mmOutStream.write(bytes);
			} catch (IOException e) {
			}
		}

		public void cancel() {
			try {
				mmSocket.close();
				try{
					callback.OnDisconected(true);
				} catch (Exception e){
					e.printStackTrace();
				}
			} catch (IOException e) {
				try{
					callback.OnDisconected(false);
				} catch (Exception ex){
					ex.printStackTrace();
				}
			}
		}
	}

	private class ConnectThread extends Thread {
		private final BluetoothSocket mmSocket;
		private final BluetoothDevice mmDevice;
		private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

		public ConnectThread(BluetoothDevice device) {
			Log.d("connection", "try to connect");
			BluetoothSocket tmp = null;
			mmDevice = device;
			try {
				tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
			} catch (IOException e) {
			}
			mmSocket = tmp;
			Log.d("connection", "connected");
			
		}

		public void run() {
			mBluetoothAdapter.cancelDiscovery();
			try {
				mmSocket.connect();
			} catch (Exception connectException) {
				try {
					mSocket.close();
				} catch (Exception closeException) {
				}
				return;
			}
			mConnectedThread = new ConnectedThread(mmSocket);
			mConnectedThread.start();

		}

		public void cancel() {
			try {
				mmSocket.close();
			} catch (IOException e) {
			}
		}
	}


}
