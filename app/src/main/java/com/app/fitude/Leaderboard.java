package com.app.fitude;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;


public class Leaderboard extends AppCompatActivity {
    private MaterialSearchView searchView;
    //    private ListView lstView;
    private ArrayList<User> userList;
    private  TableLayout tl;

    public void cleanTable(TableLayout tl){
        int childCount = tl.getChildCount();
        if(childCount > 1){
            tl.removeViews(1,childCount-1);
        }
    }

    public void fillTable(TableLayout tl, ArrayList<User> userList){
        TextView[] textArray = new TextView[userList.size()*3];
        TableRow[] tr_list = new TableRow[userList.size()];

        for(int j=0; j < userList.size()*3;j+=3){
            int i = j/3;
            User user = userList.get(i);
            String name = user.getName();
            String userId = user.getUserId();
            int fitnessScore = user.getFitnessScore();
            tr_list[i] = new TableRow(this);
            tr_list[i].setId(i+1);
            tr_list[i].setBackgroundColor(Color.GRAY);
            tr_list[i].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT));
            textArray[j] = new TextView(this);
            textArray[j].setId(View.generateViewId());
            textArray[j].setText(userId);
            textArray[j].setTextSize(20);
            textArray[j].setTextColor(Color.WHITE);
            textArray[j].setPadding(45, 45, 45, 45);
            textArray[j+1] = new TextView(this);
            textArray[j+1].setId(View.generateViewId());
            textArray[j+1].setText(name);
            textArray[j+1].setTextSize(20);
            textArray[j+1].setTextColor(Color.WHITE);
            textArray[j+1].setPadding(45, 45, 45, 45);
            textArray[j+2] = new TextView(this);
            textArray[j+2].setId(View.generateViewId());
            textArray[j+2].setText(Integer.toString(fitnessScore));
            textArray[j+2].setTextSize(20);
            textArray[j+2].setTextColor(Color.WHITE);
            textArray[j+2].setPadding(45, 45, 45, 45);
            tr_list[i].addView(textArray[j]);
            tr_list[i].addView(textArray[j+1]);
            tr_list[i].addView(textArray[j+2]);
            tr_list[i].setClickable(true);
            tr_list[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),UserProfile.class);
                    TableRow tr = (TableRow) v;
                    TextView tv = (TextView)tr.getChildAt(0);
                    String userId = Integer.toString(Integer.parseInt(tv.getText().toString()));
                    intent.putExtra("userId",userId);
                    startActivity(intent);
                }
            });
            tl.addView(tr_list[i], new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        DataBaseHandler dh = new DataBaseHandler(this);
        tl = (TableLayout) findViewById(R.id.main_table);
        Log.d("t1",Boolean.toString(tl==null));
        TableRow tr_head = new TableRow(this);
        tr_head.setId(View.generateViewId());
        tr_head.setBackgroundColor(Color.GRAY);
        tr_head.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT));

        TextView label_id = new TextView(this);
        label_id.setId(View.generateViewId());
        label_id.setText("ID");
        label_id.setTextSize(20);
        label_id.setTextColor(Color.BLACK);
        label_id.setPadding(45, 45, 45, 45);
        tr_head.addView(label_id);// add the column to the table row here

        TextView label_name = new TextView(this);    // part3
        label_name.setId(View.generateViewId());// define id that must be unique
        label_name.setText("Name"); // set the text for the header
        label_name.setTextSize(20);
        label_name.setTextColor(Color.BLACK); // set the color
        label_name.setPadding(45, 45, 45, 45); // set the padding (if required)
        tr_head.addView(label_name); // add the column to the table row here

        TextView label_score = new TextView(this);    // part4
        label_score.setId(View.generateViewId());// define id that must be unique
        label_score.setText("Fitness Score"); // set the text for the header
        label_score.setTextSize(20);
        label_score.setTextColor(Color.BLACK); // set the color
        label_score.setPadding(45, 45, 45, 45); // set the padding (if required)
        tr_head.addView(label_score); // add the column to the table row here

        tl.addView(tr_head, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

        userList = dh.getQueriedUserList("SELECT * FROM User ORDER BY fitnessScore DESC;");

        fillTable(tl,userList);

        Toolbar toolbar = (Toolbar)findViewById(R.id.searchToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Fitness Leaderboard");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));


        searchView = (MaterialSearchView)findViewById(R.id.search_view);

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

                //If closed Leaderboard View , listView will return default
                TableLayout tl = (TableLayout) findViewById(R.id.main_table);
                fillTable(tl,userList);

            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                TableLayout tl = (TableLayout) findViewById(R.id.main_table);
                if(newText != null && !newText.isEmpty()){
                    Log.d("LeaderBoard",newText);
                    ArrayList<User> lstFound = new ArrayList<User>();
                    for(User user:userList){
                        if(user.toString().toLowerCase().contains(newText.toLowerCase()))
                            lstFound.add(user);
                    }
                    cleanTable(tl);
                    fillTable(tl,lstFound);
                }
                else{
                    cleanTable(tl);
                    fillTable(tl,userList);
                }
                return true;
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }
}
