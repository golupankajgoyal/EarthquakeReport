package com.example.earthquakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.earthquakereport.EarthquakeActivity.LOG_TAG;
public class QueryUtils {
    /** Sample JSON response for a USGS query */

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    public static List<Earthquake> fetchEarthquakeData(String url){

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.i(LOG_TAG,"Test: fetchEarthquakeData() called");
        URL url1=createUrl(url);

        String jsonResponse=null;
        List<Earthquake> list=new ArrayList<Earthquake>();
        try{
            jsonResponse=makeHttpRequest(url1);
        }catch (IOException e){
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        list=extractEarthquakes(jsonResponse);
        return list;
    }
    private static URL createUrl(String stringUrl){
        URL url=null;
        try{
            url=new URL(stringUrl);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG,"Error with creating Url",e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url)throws IOException{
        String jsonResponse = "";

        if(url==null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection=null;
        InputStream inputStream=null;
        try{
            urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else {
                Log.e(LOG_TAG,"Error response code: " + urlConnection.getResponseCode());

            }
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream)throws IOException{
        StringBuilder output=new StringBuilder();
        if(inputStream!=null){
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            String line=null;
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            line=bufferedReader.readLine();
            while(line!=null){
                output.append(line);
                line=bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    private static List<Earthquake> extractEarthquakes(String jsonResponce) {

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // build up a list of Earthquake objects with the corresponding data.
            JSONObject baseJsonResponse = new JSONObject(jsonResponce);
            JSONArray earthquakeArray=baseJsonResponse.getJSONArray("features");

            for(int i=0;i<earthquakeArray.length();i++){
                JSONObject currentEarthquake=earthquakeArray.getJSONObject(i);
                JSONObject properties=currentEarthquake.getJSONObject("properties");
                Double magnitude=properties.getDouble("mag");
                String location=properties.getString("place");
                Long time=properties.getLong("time");
                String url=properties.getString("url");
                Earthquake earthquake=new Earthquake(magnitude,location,time,url);
                earthquakes.add(earthquake);
            }


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

}
