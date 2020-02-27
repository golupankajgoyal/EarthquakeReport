package com.example.earthquakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(Context context, List<Earthquake> earthquakes){
        super(context,0,earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Earthquake currentQuake=getItem(position);
        View listItemView=convertView;
        if(listItemView==null){
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.list_items,parent,false);
        }

        TextView magnitude_textView=(TextView)listItemView.findViewById(R.id.magnitude);
        double magnitude=currentQuake.getmMagnitude();
        DecimalFormat magnitudeFormatter=new DecimalFormat("0.0");
        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle=(GradientDrawable)magnitude_textView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getColor(magnitude);

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);
        magnitude_textView.setText(magnitudeFormatter.format(magnitude));

        TextView locationOffset_textView=(TextView)listItemView.findViewById(R.id.location_offset);
        String fullLocation=currentQuake.getmLocation();
        String primaryLocation;

        if(fullLocation.contains("of")){
            String[] location=fullLocation.split("of ");
            String locationOffset=location[0];
            primaryLocation=location[1];
            locationOffset_textView.setText(locationOffset+"of");
        }
        else{
            locationOffset_textView.setText("Nearby Area");
            primaryLocation=fullLocation;
        }

        TextView primaryLocation_textView=(TextView)listItemView.findViewById(R.id.primary_location);
        primaryLocation_textView.setText(primaryLocation);

        TextView date_textView=(TextView)listItemView.findViewById(R.id.date);
        Long time=currentQuake.getmTimeInMilliSecond();
        Date dateObject=new Date(time);
        SimpleDateFormat dateFormatter=new SimpleDateFormat("MMM dd,yyyy");
        String dateToDisplay=dateFormatter.format(dateObject);
        date_textView.setText(dateToDisplay);

        TextView time_textView=(TextView)listItemView.findViewById(R.id.time);
        dateFormatter=new SimpleDateFormat("h:mm a");
        dateToDisplay=dateFormatter.format(dateObject);
        time_textView.setText(dateToDisplay);

        return listItemView;
    }

    public int getColor(double magnitude){
        int ColorValue;
        int magnitudeFloor=(int)Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                ColorValue = R.color.magnitude1;
                break;
            case 2:
                ColorValue = R.color.magnitude2;
                break;
            case 3:
                ColorValue = R.color.magnitude3;
                break;
            case 4:
                ColorValue = R.color.magnitude4;
                break;
            case 5:
                ColorValue = R.color.magnitude5;
                break;
            case 6:
                ColorValue = R.color.magnitude6;
                break;
            case 7:
                ColorValue = R.color.magnitude7;
                break;
            case 8:
                ColorValue = R.color.magnitude8;
                break;
            case 9:
                ColorValue = R.color.magnitude9;
                break;
            default:
                ColorValue = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), ColorValue);
    }
}