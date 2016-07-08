package ru.startandroid.mybrowser;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    WebAdapter adapter;
    ListView list;
    private ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hisory);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new WebAdapter(HistoryActivity.this, getData(),getPageData(), HistoryActivity.this.getApplicationContext());
        ListView lvNotes = (ListView) findViewById(R.id.listView2);
        lvNotes.setAdapter(adapter);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public ArrayList<String> getData() {

        WebAppDatabase db = new WebAppDatabase(HistoryActivity.this.getApplicationContext());
                ArrayList<String> pr = (ArrayList<String>) db.getAllPagesURL();

        return pr;

    }
    public ArrayList<String> getPageData() {

        WebAppDatabase db = new WebAppDatabase(HistoryActivity.this.getApplicationContext());
        ArrayList<String> pr = (ArrayList<String>) db.getAllPagesDate();

        return pr;

    }
}
