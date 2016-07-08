package ru.startandroid.mybrowser;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLData;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private WebView mWebView;
    EditText editText;
    String LOG_TAG = "tanya";
    WebAppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("http://www.google.ru");
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient());
        editText = (EditText) findViewById(R.id.editText);
        db = new WebAppDatabase(MainActivity.this.getApplicationContext());
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    public void onClick(View v) {

        String s = editText.getText().toString();
        Calendar c = Calendar.getInstance();//объявление календапря
        Log.d("WebTag", "Calendar c " + c.toString());

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Log.d("WebTag", "Calendar c c.getTime()" + c.getTime().toString());
        String formatedDate = df.format(c.getTime());

        Log.d("formatedDate", formatedDate);
        if (s.equals("")) {
            Toast t = Toast.makeText(MainActivity.this, "Введите данные", Toast.LENGTH_SHORT);
            t.show();
        } else {


            if (s.startsWith("http://") || s.startsWith("https://")) {
                Log.d("WebTag", "onForward Текст с http или https");
                mWebView.getSettings().setJavaScriptEnabled(true);
                mWebView.setWebViewClient(new MyWebViewClient());
                mWebView.setWebChromeClient(new WebChromeClient());
                mWebView.loadUrl(s);

                db.addrecord(s, formatedDate);
                db.getAllPagesURL();
                db.getAllPagesDate();
            } else {
                s = "http://" + s;
                Log.d(LOG_TAG, s);

                mWebView.getSettings().setJavaScriptEnabled(true);

                mWebView.setWebViewClient(new MyWebViewClient());
                mWebView.setWebChromeClient(new WebChromeClient());
                mWebView.loadUrl(s);


                db.addrecord(s, formatedDate);
                db.getAllPagesURL();
                db.getAllPagesDate();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.dev) {
            Intent intent = new Intent(this, CallActivity.class);

            startActivity(intent);
        }
        if (id == R.id.history) {
            Intent intent = new Intent(this, HistoryActivity.class);

            startActivity(intent);
        }
        if (id == R.id.delete) {
            db.deleteAllPages();
        }


        return super.onOptionsItemSelected(item);
    }
}
