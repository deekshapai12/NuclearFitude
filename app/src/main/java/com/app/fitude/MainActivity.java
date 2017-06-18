package com.app.fitude;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    private ListView mRssListView;
    private FeedParser mNewsFeeder;
    private List<Feed> mRssFeedList;
    private RssAdapter mRssAdap;

    private static final String FITNESS_FEED = "http://feeds.feedburner.com/AceFitFacts?format=xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRssListView = (ListView) findViewById(R.id.rss_list_view);
        mRssFeedList = new ArrayList<Feed>();
        new DoRssFeedTask().execute(FITNESS_FEED);
        mRssListView.setOnItemClickListener(this);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    // FEED FETCHING PART //
    private class RssAdapter extends ArrayAdapter<Feed> {
        private List<Feed> rssFeedLst;

        public RssAdapter(Context context, int textViewResourceId, List<Feed> rssFeedLst) {
            super(context, textViewResourceId, rssFeedLst);
            this.rssFeedLst = rssFeedLst;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = convertView;
            RssHolder rssHolder = null;
            if (convertView == null) {
                view = View.inflate(MainActivity.this, R.layout.rss_list_item, null);
                rssHolder = new RssHolder();
                rssHolder.rssTitleView = (TextView) view.findViewById(R.id.rss_title_view);
                view.setTag(rssHolder);
            } else {
                rssHolder = (RssHolder) view.getTag();
            }
            Feed rssFeed = rssFeedLst.get(position);
            rssHolder.rssTitleView.setText(rssFeed.getTitle());
            return view;
        }
    }

    static class RssHolder {
        public TextView rssTitleView;
    }

    class DoRssFeedTask extends AsyncTask<String, Void, List<Feed>> {
        ProgressDialog prog;
        String jsonStr = null;
        Handler innerHandler;
        private FeedParser mNewsFeeder;
        private List<Feed> mRssFeedList;
        private RssAdapter mRssAdap;
        @Override
        protected void onPreExecute() {
            prog = new ProgressDialog(MainActivity.this);
            prog.setMessage("Loading Fitness Tips ...");
            prog.show();
        }

        @Override
        protected List<Feed> doInBackground(String... params) {
            for (String urlVal : params) {
                mNewsFeeder = new FeedParser(urlVal);
            }
            mRssFeedList = mNewsFeeder.parse();
            return mRssFeedList;
        }

        @Override
        protected void onPostExecute(List<Feed> result) {
            prog.dismiss();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mRssAdap = new RssAdapter(MainActivity.this, R.layout.rss_list_item,
                            mRssFeedList);
                    int count = mRssAdap.getCount();
                    if (count != 0 && mRssAdap != null) {
                        mRssListView.setAdapter(mRssAdap);
                    }
                }
            });
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent intent = new Intent(this, TrackFood.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            // Handle the gallery action
            Intent intent = new Intent(this, Pedometer.class);
            startActivity(intent);


        }  else if (id == R.id.nav_send) {
            // Handle the logout action
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }  else if (id == R.id.nav_leaderboard) {
            // Handle the search action
            Intent intent = new Intent(this,Leaderboard.class);
            startActivity(intent);
        }  else if (id == R.id.nav_profile) {
            //Handle the profile viewing action
            Intent intent = new Intent(this, UserProfile.class);
            intent.putExtra("userId","3");
            startActivity(intent);
        }
        else if (id == R.id.nav_events) {
            //Handle the profile viewing action
            Intent intent = new Intent(this, EventLocator.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MAIN_ACTIVITY", "Activity shown on screen");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MAIN_ACTIVITY", "Activity Resumed on screen");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MAIN_ACTIVITY", "Activity Paused");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MAIN_ACTIVITY", "Activity Stopped");



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MAIN_ACTIVITY", "Activity Destroyed");
        finish();

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Feed f = (Feed)parent.getItemAtPosition(position);
        String RSS_URL = f.getLink();
        Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(RSS_URL));
        startActivity(intent);
    }
}
