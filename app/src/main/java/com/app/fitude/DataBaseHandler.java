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
    public void onCreate(SQLiteDatabase db) {
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

        db.execSQL("INSERT INTO User(userName,password,name,age,gender,bloodGroup,height,weight,isDiabetic,fitnessScore,totalCaloriesBurnt) VALUES ('mrerikson@hotmail.com','12345','Marshall',22,'M','A+',183,75,'T',1234,5.347);");
        db.execSQL("INSERT INTO User(userName,password,name,age,gender,bloodGroup,height,weight,isDiabetic,fitnessScore,totalCaloriesBurnt) VALUES ('sheila122@yahaoo.com','54321','Sheila',34,'F','A-',192,87,'F',2341,100.234);");
        db.execSQL("INSERT INTO User(userName,password,name,age,gender,bloodGroup,height,weight,isDiabetic,fitnessScore,totalCaloriesBurnt) VALUES ('deepika12@gmail.com','dptt1234','Deepika',21,'F','O+',168,41,'F',199,0.253);");
        db.execSQL("INSERT INTO User(userName,password,name,age,gender,bloodGroup,height,weight,isDiabetic,fitnessScore,totalCaloriesBurnt) VALUES ('sharman1512@gmail.com','switch10','Sharman',21,'M','A+',174,68,'F',3214,240.322);");
        db.execSQL("INSERT INTO User(userName,password,name,age,gender,bloodGroup,height,weight,isDiabetic,fitnessScore,totalCaloriesBurnt) VALUES ('raj22@gmail.com','pikachu','Rajesh',15,'M','AB+',156,50,'F',2645,245.544);");

        db.execSQL("INSERT INTO Event(eventName, eventDescription, eventDate, eventLatitude, eventLongitude) VALUES ('10k Marathon','Participate in the 10k Marathon and get fit','19-06-2017',12.9260308,77.6762463)");
        db.execSQL("INSERT INTO Event(eventName, eventDescription, eventDate, eventLatitude, eventLongitude) VALUES ('Walk-a-thon','Heard of Marathon, this Walk-a-thon is for those who want to take it slow','19-06-2017',12.94345157,77.61007103)");
        db.execSQL("INSERT INTO Event(eventName, eventDescription, eventDate, eventLatitude, eventLongitude) VALUES ('Talk-and-Walk','Get into groups, the more the merrier, exercise and have fun','19-06-2017',12.87671028,77.7501675)");
        db.execSQL("INSERT INTO Event(eventName, eventDescription, eventDate, eventLatitude, eventLongitude) VALUES ('Weekend Cycling Ride','Get out your cycles, and join us in the weekly cycle ride for this weekend','19-06-2017',12.93690897,77.68066994)");
        db.execSQL("INSERT INTO Event(eventName, eventDescription, eventDate, eventLatitude, eventLongitude) VALUES ('Yoga Training','Get trained in all the basic asanas, and realize a new feeling to life','19-06-2017',13.14788021,77.61110278)");
        db.execSQL("INSERT INTO Event(eventName, eventDescription, eventDate, eventLatitude, eventLongitude) VALUES ('Zumba Dance Fiesta','Dance.Music.Exercise.Fun.','19-06-2017',12.92267247,77.73961346)");
        db.execSQL("INSERT INTO Event(eventName, eventDescription, eventDate, eventLatitude, eventLongitude) VALUES ('Salsa Class','Sway your way to a fit future','19-06-2017',12.93513202,77.73962587)");
        db.execSQL("INSERT INTO Event(eventName, eventDescription, eventDate, eventLatitude, eventLongitude) VALUES ('Snap Fitness','Attend this event and meet other like-minded fitness people','19-06-2017',13.00287603,77.80151948)");
        db.execSQL("INSERT INTO Event(eventName, eventDescription, eventDate, eventLatitude, eventLongitude) VALUES ('Urban Stampede','What do you get when you mix special discount prizes for winners and a lot of people? An Urban stampede','19-06-2017',13.1390609,77.73688365)");
        db.execSQL("INSERT INTO Event(eventName, eventDescription, eventDate, eventLatitude, eventLongitude) VALUES ('Sports Triumph Cricket Tournament','Gather your team, and show off your team skills in this game of cricket','19-06-2017',13.07467524,77.86097321)");

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
