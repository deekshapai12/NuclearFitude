package com.app.fitude;

import java.util.Date;

/**
 * Created by Deeksha on 18-Jun-17.
 */

public class Events {
    private int eventId;
    private String eventName;
    private String eventDescription;
    private String eventDate;
    private double eventLatitude;
    private double eventLongitude;

    Events(int eventId,String eventName,String eventDescription,String eventDate,Double eventLatitude,Double eventLongitude){
        this.eventId=eventId;
        this.eventName=eventName;
        this.eventDescription=eventDescription;
        this.eventDate=eventDate;
        this.eventLatitude=eventLatitude;
        this.eventLongitude=eventLongitude;
    }

    public int getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public String getEventDate() {
        return eventDate;
    }

    public double getEventLatitude() {
        return eventLatitude;
    }

    public double getEventLongitude() {
        return eventLongitude;
    }
}
