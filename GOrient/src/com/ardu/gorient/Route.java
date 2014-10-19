package com.ardu.gorient;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Route extends Activity implements OnClickListener {
	public static GoogleMap googleMap;
    private ArrayList<String> resultList;
    private static final String LOG_TAG = "ExampleApp";
    public static List<GSpot> GSpotList=new ArrayList<GSpot>();
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/directions";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    public static float lon;
    public static float lat;
    public static float lone;
    public static float late;
    String destination="45.7471893,21.2415749";
    int n;
    boolean markerPlaced=false;
    public static Location nowPosition ;
    private static final String API_KEY = "AIzaSyA9cvaN4DZy4xmtRM5ONrLIiJeP6_ksMeo";
    private static Context mContext;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_route);
		try {
            // Loading map
            initilizeMap();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
		mContext = this;
		googleMap.setMyLocationEnabled(true);
		Button buttonConfigureRoute = (Button) findViewById(R.id.button_confirm_route);
		buttonConfigureRoute.setOnClickListener(this);
		googleMap.setOnMapLongClickListener(new OnMapLongClickListener() {
			
			@Override
			public void onMapLongClick(LatLng point) {
				if(!markerPlaced){
					lone=(float) point.longitude;
					late=(float) point.latitude;
					Marker marker = googleMap.addMarker(new MarkerOptions()
	                .position(point)
	                .title("Destination")
	                );
					marker.showInfoWindow();
					markerPlaced=true;
					
				}
				
				
			}
		});
		//destination = i.getStringExtra("destination");
	}
	/**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
 
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
        googleMap.setOnMyLocationChangeListener(new OnMyLocationChangeListener() {
			
			@Override
			public void onMyLocationChange(Location location) {
				// TODO Auto-generated method stub
				lon = (float) location.getLongitude();
				lat = (float) location.getLatitude();
				nowPosition = location;
			}
		});
        
    }
 
    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }
	@Override
	public void onClick(View v) 
	{
		if(v.getId()==R.id.button_confirm_route)
		{
			//Toast.makeText(Route.this, "X:"+googleMap.getMyLocation().getLongitude()+" Y:"+googleMap.getMyLocation().getLatitude(), Toast.LENGTH_LONG).show();
			Location l=new Location("");
			l.setLatitude(45.7471893);
			l.setLongitude(21.2415749);
			lon = (float) googleMap.getMyLocation().getLongitude();
			lat = (float) googleMap.getMyLocation().getLatitude();
			//Toast.makeText(Route.this, "Distance "+googleMap.getMyLocation().distanceTo(l), Toast.LENGTH_LONG).show();
			destination = late+","+lone;
			Log.d("poz",lone+","+late);
			Toast.makeText(Route.this, "GOOOOO!!!", Toast.LENGTH_SHORT).show();
			Thread th = new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					request();
//					Log.d("json",GSpotList.get(0).HTMLInstructions.toString());
					n = GSpotList.size();
					Intent mServiceIntent = new Intent(Route.this, Directions.class);
					Route.this.startService(mServiceIntent);
				}
			});
			th.start();
			
		}
		
		
	
	}
	public List<Route> parse(String routesJSONString) throws Exception 
	{
	    try {
	        List<Route> routeList = new ArrayList<Route>();
	        final JSONObject jSONObject = new JSONObject(routesJSONString);
	        JSONArray routeJSONArray = jSONObject.getJSONArray("routes");
	       // Route route;
	        JSONObject routesJSONObject;
	        for (int m = 0; m < 1; m++) {
	           // route = new Route(context);
	            routesJSONObject = routeJSONArray.getJSONObject(m);
	            JSONArray legsJSONArray;
	            //route.setSummary(routesJSONObject.getString("SUMMARY"));
	            legsJSONArray = routesJSONObject.getJSONArray("legs");
	            JSONObject legJSONObject=new JSONObject();
	           // Leg leg;
	            JSONArray stepsJSONArray;
	            for (int b = 0; b < legsJSONArray.length(); b++) {

	            	JSONObject stepJSONObject=new JSONObject(), stepDurationJSONObject=new JSONObject(), 
	            			legPolyLineJSONObject=new JSONObject(), stepStartLocationJSONObject=new JSONObject(), stepEndLocationJSONObject=new JSONObject();
	                legJSONObject = legsJSONArray.getJSONObject(b);
	            	stepsJSONArray = legJSONObject.getJSONArray("steps");
	                for (int i = 0; i < stepsJSONArray.length(); i++) {
	                	GSpot g=new GSpot();
	                	stepJSONObject = stepsJSONArray.getJSONObject(i);
	                	JSONObject stepDistanceJSONObject = stepJSONObject.getJSONObject("distance");
	                    g.distanceText=stepDistanceJSONObject.getString("text");
	                    g.distance=stepDistanceJSONObject.getLong("value");
	                    
	                    stepDurationJSONObject = stepJSONObject.getJSONObject("duration");
	                    g.durationText=stepDurationJSONObject.getString("text");
	                    g.duration=stepDurationJSONObject.getLong("value");
	                    stepEndLocationJSONObject = stepJSONObject.getJSONObject("end_location");
	                    g.endLatitude=stepEndLocationJSONObject.getDouble("lat");
	                    g.endLongitude=stepEndLocationJSONObject.getDouble("lng");
	                   
	                    g.HTMLInstructions=stepJSONObject.getString("html_instructions");
	                	
	                	GSpotList.add(g);
	                }
	
	            }
	        }
	        return routeList;
	    } catch (Exception e) {
	        throw e;
	    }
}
	private void request(){
		 HttpURLConnection conn = null;
	        StringBuilder jsonResults = new StringBuilder();
	        try {
	            StringBuilder sb = new StringBuilder(PLACES_API_BASE +  OUT_JSON);
	            sb.append("?key=" + API_KEY);
	            sb.append("&origin="+lat+","+lon);
	            sb.append("&destination="+destination);
	            sb.append("&mode=walking");
	            Log.d("request",sb.toString());
	            URL url = new URL(sb.toString());
	            conn = (HttpURLConnection) url.openConnection();
	            InputStreamReader in = new InputStreamReader(conn.getInputStream());
	            
	            // Load the results into a StringBuilder
	            int read;
	            char[] buff = new char[1024];
	            while ((read = in.read(buff)) != -1) {
	                jsonResults.append(buff, 0, read);
	            }
	            try {
					parse(jsonResults.toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            Log.d("google", jsonResults.toString());
	            
	        } catch (MalformedURLException e) {
	            Log.e(LOG_TAG, "Error processing Places API URL", e);
//	            return resultList;
	        } catch (IOException e) {
	            Log.e(LOG_TAG, "Error connecting to Places API", e);
//	            return resultList;
	        } finally {
	            if (conn != null) {
	                conn.disconnect();
	            }
	        }

	}
	
    public static Handler handler = new Handler() {
    	 
        public void handleMessage(Message msg) {
             
            String aResponse = msg.getData().getString("message");

            if ((null != aResponse)) {

                // ALERT MESSAGE
                Toast.makeText(
                        mContext,
                        "Server Response: "+aResponse,
                        Toast.LENGTH_SHORT).show();
            }
            else
            {

                    // ALERT MESSAGE
                    Toast.makeText(
                            mContext,
                            "Not Got Response From Server.",
                            Toast.LENGTH_SHORT).show();
            }    

        }
    };
}


