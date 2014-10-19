package com.ardu.gorient;
import java.nio.ByteBuffer;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

   public class Directions extends IntentService
    {
	   public Directions() {
			super("Directions");
			// TODO Auto-generated constructor stub
		}
    	int n;
    	int direction=8;
    	boolean mustChange=false;
    	public static byte[] intToByteArray(int value) {
    		/*
    		byte[] b = new byte[4];
    		for (int i = 0; i < 4; i++) 
    		{
    			int offset = (b.length - 1 - i) * 8;
    			b[i] = (byte) ((value >> offset) & 0xFF);
 
    		}
    		return b;*/
    		return ByteBuffer.allocate(4).putInt(value).array();
    		}

		@Override
		protected void onHandleIntent(Intent intent) {
			
			while(Route.GSpotList.size()>0){
				
				//
				GSpot temp = Route.GSpotList.get(0);
				
				Location end=new Location("");
				end.setLatitude(temp.endLatitude);
				end.setLongitude(temp.endLongitude);
				float distanceEnd;
				Location start = new Location("");
				start.setLatitude(Route.lat);
				start.setLongitude(Route.lon);
				distanceEnd=Route.nowPosition.distanceTo(end);
				byte[] b=new byte[1];
				byte[] head = new byte[1];
				head[0]=(int)'8';
				/*
				//real life mode
				if(distanceEnd<100&& mustChange==true)
				{	
					byte directionToGo=DecodeDirection(temp.HTMLInstructions);
					
					b[0]=directionToGo;
					mustChange=false;
					//MainActivity.aw.send(b);
				}
				else
				{
					b[0]=head[0];
					mustChange=false;
					//MainActivity.aw.send(head);
				}
				if(distanceEnd<25)
				{
					mustChange=true;
					//MainActivity.aw.send(b);
					Route.GSpotList.remove(0);
				}
				MainActivity.aw.send(b);
				Log.d("loc", ""+temp.HTMLInstructions+" "+distanceEnd);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				
				//Simulation - for demo purposes only
				byte directionToGo=DecodeDirection(temp.HTMLInstructions);
				b[0]=directionToGo;
				Route.GSpotList.remove(0);
				MainActivity.aw.send(b);
				int dir=directionToGo-48;
				if(dir==2)
					Log.d("Directie","back");
				if(dir==4)
					Log.d("Directie","left");
				if(dir==6)
					Log.d("Directie","right");
				if(dir==8)
					Log.d("Directie","forward");
				Log.d("loc", ""+temp.HTMLInstructions+" "+distanceEnd);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			  Message msgObj = Route.handler.obtainMessage();
	           Bundle b = new Bundle();
	           b.putString("message", "finish");
	           msgObj.setData(b);
	           Route.handler.sendMessage(msgObj);
			Log.d("route","destination");
			
			
		}

		private void correctDirection(int correction)
		{
			//corecteaza directia
			
			if(direction==8)
			{
				direction=correction;
			}
			if (direction==6)
			{
				if(correction==8)
					direction=6;
				if(correction==2)
					direction=4;
				if(correction==4)
					direction=2;
				if(correction==6)
					direction=8;
					
					
			}
			if(direction==4)
			{
				if(correction==8)
					direction=4;
				if(correction==2)
					direction=6;
				if(correction==4)
					direction=2;
				if(correction==6)
					direction=8;
			}
			
			if(direction==2)
			{
				if(correction==8)
					direction=2;
				if(correction==2)
					direction=8;
				if(correction==4)
					direction=6;
				if(correction==6)
					direction=4;
			}		
			Log.d("direction corection",""+direction+" "+correction);
			
		}
		private byte returnDirection(int newDir)
		{
			if(newDir==8)
			{
				if(direction==8)
				{
					direction=newDir;
					return (int)'8';
				}
				if(direction==6)
				{
					direction=newDir;
					return(int) '6';
				}
				if(direction==4)
				{
					direction=newDir;
					return (int)'4';
				}
				if(direction==2)
				{
					direction=newDir;
					return (int)'2';
				}
			}
			
			
			if(newDir==6)
			{
				if(direction==8)
				{
					direction=newDir;
					return(int) '6';
				}
				if(direction==6)
				{
					direction=newDir;
					return (int)'8';
				}
				if(direction==4)
				{
					direction=newDir;
					return (int)'2';
				}
				if(direction==2)
				{
					direction=newDir;
					return (int)'4';
				}
			}
			
			if(newDir==2)
			{
				if(direction==8)
				{
					direction=newDir;
					return (int)'2';
				}
				if(direction==6)
				{
					direction=newDir;
					return (int)'6';
				}
				if(direction==4)
				{
					direction=newDir;
					return (int)'4';
				}
				if(direction==2)
				{
					direction=newDir;
					return (int)'2';
				}
			}
			
			if(newDir==4)
			{
				if(direction==8)
				{
					direction=newDir;
					return (int)'4';
				}
				if(direction==6)
				{
					direction=newDir;
					return (int)'2';
				}
				if(direction==4)
				{
					direction=newDir;
					return (int)'8';
				}
				if(direction==2)
				{
					direction=newDir;
					return(int) '6';
				}
			}
			return (int)'0';
		}
		private byte DecodeDirection(String hTMLInstructions) 
		{
			if(hTMLInstructions.contains("right")||hTMLInstructions.contains("Right"))
			{	
				correctDirection(6);
				return (int) '6';
			}
			
			if(hTMLInstructions.contains("left")||hTMLInstructions.contains("Left"))
			{
				correctDirection(4);
				return (int) '4';
			}
			if(hTMLInstructions.contains("front")||hTMLInstructions.contains("Front")
					||hTMLInstructions.contains("ahead")||hTMLInstructions.contains("2nd</b> exit")
					||hTMLInstructions.contains("through"))
			{
				correctDirection(8);
				return '8';
			}
			if(hTMLInstructions.contains("north")||hTMLInstructions.contains("North"))
			{	
				return returnDirection(8); 
			}
			if(hTMLInstructions.contains("south")||hTMLInstructions.contains("South"))
			{
				return returnDirection(2);
			}
			if(hTMLInstructions.contains("west")||hTMLInstructions.contains("West"))
			{
				return returnDirection(4);
			}
			if(hTMLInstructions.contains("est")||hTMLInstructions.contains("Est"))
			{
				return returnDirection(6);
			}
			
			return (int) '1';
		}
    }