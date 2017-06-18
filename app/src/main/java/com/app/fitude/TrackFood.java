package com.app.fitude;
/**
 * Created by Shravan on 6/17/2017.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TrackFood extends Activity {

    Button btpic, btnup;
    String ba1;
    public static String URL = "http://fitude.co.nf/upload.php";
    String mCurrentPhotoPath;
    ImageView mImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        btpic = (Button) findViewById(R.id.cpic);
        mImageView = (ImageView) findViewById(R.id.Imageprev);
        btpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImage();
            }
        });

        btnup = (Button) findViewById(R.id.up);
        btnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });
    }

    private void upload() {
        if(mCurrentPhotoPath == null){
            Toast toast=Toast.makeText(getApplicationContext(),"Take pic of your favourite Food !",Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            Bitmap bm = BitmapFactory.decodeFile(mCurrentPhotoPath);
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG,60, bao);
            byte[] ba = bao.toByteArray();

            // Upload image to server
            ba1 = Base64.encodeToString(ba, Base64.NO_WRAP);
            Log.e("base64", "-----" + ba1);
            new uploadToServer().execute();
        }


    }

    private void captureImage() {

        // Check Camera
        File photoFile = null;
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // Open default camera
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(intent, 100);
            }

        } else {
            Toast.makeText(getApplication(), "Camera not supported", Toast.LENGTH_LONG).show();
        }


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            setPic();
        }
    }
    public class uploadToServer extends AsyncTask<Void, Void, String> {

        private ProgressDialog pd = new ProgressDialog(TrackFood.this);

        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Searching Food , Please Wait !");
            pd.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            String res="";
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("base64", ba1));
            nameValuePairs.add(new BasicNameValuePair("ImageName", System.currentTimeMillis() + ".jpg"));
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(URL);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                res = EntityUtils.toString(response.getEntity());
                Log.v("log_tag", "Output from Server " + res);
            } catch (Exception e) {
                Log.v("log_tag", "Error in http connection " + e.toString());
            }
            return "Success,"+res;

        }

        protected void onPostExecute(String result) {
            String []s = result.split(",");
            super.onPostExecute(s[0]);
            pd.hide();
            pd.dismiss();
            Toast toast=Toast.makeText(getApplicationContext(),s[1],Toast.LENGTH_LONG);
            toast.show();

        }
    }
    private void setPic() {
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File image = null;

        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.e("Getpath", "Cool" + mCurrentPhotoPath);
        System.out.print("Look"+mCurrentPhotoPath);
        return image;

    }
    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d("lifeCycle","Activity Resumed Interacting with the User");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d("lifeCycle","Activity Paused interacting with the user");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d("lifeCycle","Activity taken out from the screen and Stopped");
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        Log.d("lifeCycle","Activity again shown on screen and Restarted");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d("lifeCycle","Activity has been Destroyed");
    }
}
