package com.app.fitude;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView textView = (TextView) findViewById(R.id.profileDetails);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String userId  = intent.getStringExtra("userId");
        SQLiteDatabase db = this.openOrCreateDatabase("fitudeDB",MODE_PRIVATE,null);
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE userId = "+userId+";",null);

        String userName;
        String name;
        int age;
        String gender;
        String bloodGroup;
        double height;
        double weight;
        String isDiabetic;
        int fitnessScore;
        double totalCaloriesBurnt;

        if(null!=cursor && cursor.getCount() > 0)
        {
            cursor.moveToFirst();
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
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(name+"\n("+userName+")");
            String profileDetails =
                    "User Name : "+userName+"\n\n"+
                            "Name : "+name+"\n\n"+
                            "Age : "+age+" years old\n\n"+
                            "Gender : "+gender+"\n\n"+
                            "Blood Group : "+bloodGroup+"\n\n"+
                            "Height : "+height+" cm\n\n"+
                            "Weight : "+weight+" kg\n\n"+
                            "isDiabetic : "+isDiabetic+"\n\n"+
                            "FITNESS SCORE : "+fitnessScore+"\n\n"+
                            "OVERALL CALORIES BURNT : "+totalCaloriesBurnt+" cals";
            textView.setText(profileDetails);
        }
    }
}