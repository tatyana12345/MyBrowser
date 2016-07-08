package ru.startandroid.mybrowser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 13.05.2016.
 */




public class WebAppDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "webApp";
    final String LOG_TAG = "WebDatabase";
    private static final int DATABASE_VERSION = 2;

    public WebAppDatabase(Context context) /*, String name, SQLiteDatabase.CursorFactory factory, int version)*/ {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(LOG_TAG, "Создание БД");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sQuery = "CREATE TABLE webHistory1 (id integer primary key autoincrement, history_step text, dateHistory text);";
        db.execSQL(sQuery);
    }

    public void deleteAllPages (){
        SQLiteDatabase db = this.getWritableDatabase();
        String dd = "DROP TABLE webHistory1;";
        db.execSQL(dd);
        Log.d(LOG_TAG, "База данных очищена");
        onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addrecord (String s_db, String date){
        Log.d(LOG_TAG, "Переход в класс базы данных");

        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(LOG_TAG, "подключение к базе");

        ContentValues historyS = new ContentValues();
        historyS.put ("history_step", s_db);
        historyS.put ("dateHistory", date);
        Log.d(LOG_TAG, "s -  " + s_db);
        Log.d(LOG_TAG, "Дата - " + date);
        db.insert("webHistory1", null,historyS);
        Log.d(LOG_TAG, " данные введены " + historyS);

        db.close();
        Log.d(LOG_TAG, "База закрыта ");
    }

    public List<String> getAllPagesURL() {
        Log.d(LOG_TAG, "вход в функцию getAllPagesURL - история ");
        List<String> noteList = new ArrayList<String>();

        String selectQuery = "SELECT history_step  FROM webHistory1 ORDER BY id DESC";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.d(LOG_TAG, "cursor " + cursor.toString());//121231dsad

        if (cursor.moveToFirst()) {

            do {

                String s = cursor.getString(0);


                Log.d(LOG_TAG, " String s " + s);

                noteList.add(s);
            } while (cursor.moveToNext());

        }
        db.close();
        Log.d(LOG_TAG, " noteList " + noteList.toString());
        return noteList;

    }
    public List<String> getAllPagesDate() {
        Log.d(LOG_TAG, "вход в функцию getAllPagesDate - история ");
        List<String> noteList = new ArrayList<String>();

        String selectQuery = "SELECT dateHistory  FROM webHistory1 ORDER BY id DESC";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.d(LOG_TAG, "cursor " + cursor.toString());//121231dsad

        if (cursor.moveToFirst()) {

            do {

                String date = cursor.getString(0);

                Log.d(LOG_TAG, " String date " + date);
                noteList.add(date);

            } while (cursor.moveToNext());

        }
        db.close();
        Log.d(LOG_TAG, " noteList " + noteList.toString());
        return noteList;

    }

}