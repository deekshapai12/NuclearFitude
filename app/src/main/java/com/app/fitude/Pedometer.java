package com.app.fitude;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by Deeksha on 6/17/2017.
 */
public class Pedometer extends Activity implements SensorEventListener {

    private TextView textView;
    private TextView distanceText;
    private TextView speedText;
    private TextView caloriesText;
    private SensorManager sManager;
    private Sensor stepSensor;
    boolean isSensing = false;
    private long steps = 0;

    // Fill with user data


    private double weight = 67.0; // kg
    private double height = 178.0; // cm

    private double walkingFactor = 0.57;
    private double CaloriesBurnedPerMile;
    private double strip;
    private double stepCountMile; // step/mile
    private double conversionFactor;
    private double CaloriesBurned;
    private double distance;
    private double speed;

    private long startTime;
    private long currTime;

    DecimalFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer);
        textView = (TextView) findViewById(R.id.textView);
        distanceText = (TextView) findViewById(R.id.distanceValue);
        speedText = (TextView) findViewById(R.id.speedValue);
        caloriesText = (TextView) findViewById(R.id.caloriesValue);

        sManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepSensor = sManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        CaloriesBurnedPerMile = walkingFactor * (weight * 2.2);
        strip = height * 0.415;
        stepCountMile = 160934.4 / strip;
        conversionFactor = CaloriesBurnedPerMile / stepCountMile;

        startTime = 0;
    }

    public void togglePedometer(View v) {
        Button toggle = (Button) findViewById(R.id.toggle);
        if(!isSensing){
            isSensing = true;
            if(startTime == 0) {
                startTime = System.currentTimeMillis();
            }
            toggle.setText("STOP");
            sManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_FASTEST);
        }
        else {
            toggle.setText("START");
            isSensing = false;
            sManager.unregisterListener(this,stepSensor);
        }
    }

    public void resetPedometer(View v){
        steps = 0;
        distance = 0;
        speed=0;
        CaloriesBurned = 0;
        distanceText.setText(distance+" km");
        speedText.setText(speed+" m/s");
        caloriesText.setText(CaloriesBurned+" cals");
        textView.setText(Long.toString(steps));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        float[] values = event.values;
        int value = -1;

        if (values.length > 0) {
            value = (int) values[0];
        }


        if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            steps++;
            textView.setText(Long.toString(steps));
            CaloriesBurned = steps * conversionFactor; //in cals
            distance = (steps * strip) / 100000; // in km
            currTime = System.currentTimeMillis();
            speed = distance*1000000/(currTime - startTime);
            distanceText.setText(Math.round(distance*1000d)/1000d+" km");
            speedText.setText(Math.round(speed*1000d)/1000d+" m/s");
            caloriesText.setText(Math.round(CaloriesBurned*1000d)/1000d+" cals");
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Pedometer Activity", "Activity shown on screen");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Pedometer Activity", "Activity Resumed on screen");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Pedometer Activity", "Activity Paused");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Pedometer Activity", "Activity Stopped");



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Pedometer Activity", "Activity Destroyed");
        finish();

    }


}