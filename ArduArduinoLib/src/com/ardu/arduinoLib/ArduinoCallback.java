package com.ardu.arduinoLib;

public interface ArduinoCallback {
	public void OnConnected(boolean result);
	public void OnReceive(byte[] buffer,int begin, int length);
	public void OnDisconected(boolean result);
		
}
