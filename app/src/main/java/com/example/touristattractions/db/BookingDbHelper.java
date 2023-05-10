package com.example.touristattractions.db;

import static android.provider.BaseColumns._ID;

import static com.example.touristattractions.db.SQLiteDbHelper.NAME_COLUMN;
import static com.example.touristattractions.db.SQLiteDbHelper.TABLE_BOOKING;
import static com.example.touristattractions.db.SQLiteDbHelper.WIKI_COLUMN;
import static com.example.touristattractions.db.SQLiteDbHelper.XID_COLUMN;
import static com.example.touristattractions.db.SQLiteDbHelper.RATE_COLUMN;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.touristattractions.classes.FeatureClass;

import java.util.ArrayList;

public class BookingDbHelper
{
    private static final String DATABASE_TABLE = TABLE_BOOKING;
    private final Context context;
    private SQLiteDbHelper dataBaseHelper;

    private SQLiteDatabase database;

    public BookingDbHelper(Context context){
        this.context = context;
    }

    public BookingDbHelper open() throws SQLException {
        dataBaseHelper = new SQLiteDbHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }

    public ArrayList<FeatureClass> query(){
        ArrayList<FeatureClass> arrayList = new ArrayList<>();

        Cursor cursor = database.query(DATABASE_TABLE,null,null,null,null,null,_ID +" DESC",null);
        cursor.moveToFirst();
        FeatureClass attractionItem;
        if (cursor.getCount()>0) {
            do {
                attractionItem = new FeatureClass();

                attractionItem.setAnIntId((cursor.getInt(cursor.getColumnIndexOrThrow(_ID))));
                attractionItem.getProperties().setXid(cursor.getString(cursor.getColumnIndexOrThrow(XID_COLUMN)));
                attractionItem.getProperties().setName(cursor.getString(cursor.getColumnIndexOrThrow(NAME_COLUMN)));
                attractionItem.getProperties().setRate(cursor.getInt(cursor.getColumnIndexOrThrow(RATE_COLUMN)));
                attractionItem.getProperties().setWikidata(cursor.getString(cursor.getColumnIndexOrThrow(WIKI_COLUMN)));

                arrayList.add(attractionItem);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(FeatureClass attractionItem){
        ContentValues initialValues =  new ContentValues();
        initialValues.put(XID_COLUMN, attractionItem.getProperties().getXid());
        initialValues.put(NAME_COLUMN, attractionItem.getProperties().getName());
        initialValues.put(RATE_COLUMN, attractionItem.getProperties().getRate());
        initialValues.put(WIKI_COLUMN, attractionItem.getProperties().getWikidata());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int delete(int id){
        return database.delete(TABLE_BOOKING, _ID + " = '"+id+"'", null);
    }
}
