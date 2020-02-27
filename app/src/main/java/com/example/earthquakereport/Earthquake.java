package com.example.earthquakereport;

public class Earthquake {
    private Double mMagnitude;
    private String mLocation;
    private Long mTimeInMilliSecond;
    private String mUrl;

    public Earthquake(Double magnitude,String location,Long TimeInMilliSecond,String url){
        mMagnitude=magnitude;
        mLocation=location;
        mTimeInMilliSecond=TimeInMilliSecond;
        mUrl=url;
    }

    public Double getmMagnitude() {
        return mMagnitude;
    }

    public String getmLocation(){
        return mLocation;
    }

    public Long getmTimeInMilliSecond() {
        return mTimeInMilliSecond;
    }

    public String getmUrl() {
        return mUrl;
    }
}
