package com.example.touristattractions.db;

import static android.provider.BaseColumns._ID;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDbHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "datadb";
    private static final int DATABASE_VERSION = 1;
    static String TABLE_BOOKING = "bookingTable";
    static String XID_COLUMN = "xid";
    static String NAME_COLUMN = "name";
    static String RATE_COLUMN = "rate";
    static String WIKI_COLUMN = "wiki";

    private static final String SQL_CREATE_TABLE_BOOKING = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s INTEGER NOT NULL," +
                    " %s TEXT NOT NULL)", TABLE_BOOKING,
                                                                         _ID, XID_COLUMN, NAME_COLUMN, RATE_COLUMN, WIKI_COLUMN
                                                                        );
    public SQLiteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_BOOKING);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKING);
        onCreate(db);
    }
}