package com.app.fitude;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.view.Window;
        import android.view.WindowManager;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
/**
 * Created by Shravan on 6/17/2017.
 */
public class LoginActivity extends Activity {

    Button b;
    EditText u;
    EditText p;
    TextView e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Set Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        b = (Button)findViewById(R.id.login);
        u= (EditText) findViewById(R.id.editText6);
        p = (EditText) findViewById(R.id.editText7);
        e = (TextView) findViewById(R.id.textView7);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = u.getText().toString();
                String password = p.getText().toString();

                if(emailAddress.equals("app@log.com") && password.equals("12345") )
                {
                    String msg ="";
                    e.setText(msg);
                    Intent intent = new Intent(LoginActivity.this,LoadingScreen.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(intent);
                }
                else {
                    String msg = "Wrong Email or Password !";
                    e.setText(msg);
                }

            }
        });



    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Login LifeCycle", "Activity shown on screen");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Login LifeCycle", "Activity Resumed on screen");

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
        Log.d("Login LifeCycle", "Activity Paused");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Login LifeCycle", "Activity Stopped");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Login LifeCycle", "Activity Destroyed");
        finish();

    }








}

