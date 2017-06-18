package com.app.fitude;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Deeksha on 18-Jun-17.
 */

public class DataBaseHandler extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "fitudeDB";
    private static final int DATABASE_VERSION = 1;

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("CREATE TABLE IF NOT EXISTS User ("
                +"userId INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"userName TEXT,"
                +"password TEXT,"
                +"name TEXT,"
                +"age INTEGER,"
                +"gender TEXT,"
                +"bloodGroup TEXT,"
                +"height FLOAT,"
                +"weight FLOAT,"
                +"isDiabetic TEXT,"
                +"fitnessScore INTEGER,"
                +"totalCaloriesBurnt FLOAT);");

        db.execSQL("CREATE TABLE IF NOT EXISTS Event ("
                +"eventId INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"eventName TEXT,"
                +"eventDescription TEXT,"
                +"eventDate TEXT,"
                +"eventLatitude DOUBLE,"
                +"eventLongitude DOUBLE);");

        db.execSQL("INSERT INTO User(userName,password,name,age,gender,bloodGroup,height,weight,isDiabetic,fitnessScore,totalCaloriesBurnt) VALUES ('log@app.com','12345','App',22,'M','A+',183,75,'T',1234,5.347);");
        db.execSQL("INSERT INTO User(userName,password,name,age,gender,bloodGroup,height,weight,isDiabetic,fitnessScore,totalCaloriesBurnt) VALUES ('abc@def.com','54321','Abc',34,'F','A-',192,87,'F',2341,100.234);");
        db.execSQL("INSERT INTO User(userName,password,name,age,gender,bloodGroup,height,weight,isDiabetic,fitnessScore,totalCaloriesBurnt) VALUES ('dp@tt.com','dptt1234','Deepika',21,'F','O+',168,41,'F',199,0.253);");
        db.execSQL("INSERT INTO User(userName,password,name,age,gender,bloodGroup,height,weight,isDiabetic,fitnessScore,totalCaloriesBurnt) VALUES ('wii@nin.com','switch10','Wii',21,'M','A+',174,68,'F',3214,240.322);");
        db.execSQL("INSERT INTO User(userName,password,name,age,gender,bloodGroup,height,weight,isDiabetic,fitnessScore,totalCaloriesBurnt) VALUES ('ak@pt.com','pikachu','Ash',15,'M','AB+',156,50,'F',2645,245.544);");

//        db.execSQL("INSERT INTO Event(eventName, eventDescription, eventDate, eventLatitude, eventLongitude) VALUES ()");
//        db.execSQL("INSERT INTO Event(eventName, eventDescription, eventDate, eventLatitude, eventLongitude) VALUES ()");
//        db.execSQL("INSERT INTO Event(eventName, eventDescription, eventDate, eventLatitude, eventLongitude) VALUES ()");
//        db.execSQL("INSERT INTO Event(eventName, eventDescription, eventDate, eventLatitude, eventLongitude) VALUES ()");
//        db.execSQL("INSERT INTO Event(eventName, eventDescription, eventDate, eventLatitude, eventLongitude) VALUES ()");
//        db.execSQL("INSERT INTO Event(eventName, eventDescription, eventDate, eventLatitude, eventLongitude) VALUES ()");
//        db.execSQL("INSERT INTO Event(eventName, eventDescription, eventDate, eventLatitude, eventLongitude) VALUES ()");
//        db.execSQL("INSERT INTO Event(eventName, eventDescription, eventDate, eventLatitude, eventLongitude) VALUES ()");
//        db.execSQL("INSERT INTO Event(eventName, eventDescription, eventDate, eventLatitude, eventLongitude) VALUES ()");
//        db.execSQL("INSERT INTO Event(eventName, eventDescription, eventDate, eventLatitude, eventLongitude) VALUES ()");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        onCreate(db);
    }

    public void insertData(String tableName, String fields, String values){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("INSERT INTO "+tableName+"("+fields+") VALUES ("+values+");");
    }

    public ArrayList<User> getQueriedUserList(String query){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<User> userList = new ArrayList<User>();
        Cursor cursor = db.rawQuery(query,null);
        String userId;
        String userName;
        String name;
        int age;
        String gender;
        String bloodGroup;
        Double height;
        Double weight;
        String isDiabetic;
        int fitnessScore;
        Double totalCaloriesBurnt;

        if(null!=cursor && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                userId = cursor.getString(cursor.getColumnIndex("userId"));
                userName = cursor.getString(cursor.getColumnIndex("userName"));
                name = cursor.getString(cursor.getColumnIndex("name"));
                age = cursor.getInt(cursor.getColumnIndex("age"));
                gender = cursor.getString(cursor.getColumnIndex("gender"));
                bloodGroup = cursor.getString(cursor.getColumnIndex("bloodGroup"));
                height = cursor.getDouble(cursor.getColumnIndex("height"));
                weight = cursor.getDouble(cursor.getColumnIndex("weight"));
                isDiabetic = cursor.getString(cursor.getColumnIndex("isDiabetic"));
                fitnessScore = cursor.getInt(cursor.getColumnIndex("fitnessScore"));
                totalCaloriesBurnt = cursor.getDouble(cursor.getColumnIndex("totalCaloriesBurnt"));
                User u = new User(userId,userName,name,age,gender,bloodGroup,height,weight,isDiabetic,fitnessScore,totalCaloriesBurnt);
                userList.add(u);
                cursor.moveToNext();
            }
        }
        return userList;
    }

    public ArrayList<Events> getNearbyEventsList(Double currLatitude,Double currLongitude){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Events> eventList = new ArrayList<Events>();
        int eventId;
        String eventName;
        String eventDescription;
        String eventDate;
        double eventLatitude;
        double eventLongitude;
        float results[] = new float[3];
        Cursor cursor = db.rawQuery("SELECT * FROM Event;",null);

        if(null!=cursor && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                eventId = cursor.getInt(cursor.getColumnIndex("eventId"));
                eventName = cursor.getString(cursor.getColumnIndex("eventName"));
                eventDescription = cursor.getString(cursor.getColumnIndex("eventDescription"));
                eventDate = cursor.getString(cursor.getColumnIndex("eventDate"));
                eventLatitude = cursor.getDouble(cursor.getColumnIndex("eventLatitude"));
                eventLongitude = cursor.getDouble(cursor.getColumnIndex("eventLongitude"));
                Location.distanceBetween(currLatitude, currLongitude, eventLatitude, eventLongitude,results);
                if(results[0] < 10000){
                    Events e = new Events(eventId,eventName,eventDescription,eventDate,eventLatitude,eventLongitude);
                    eventList.add(e);
                }
                cursor.moveToNext();
            }
        }
        return eventList;
    }
}
