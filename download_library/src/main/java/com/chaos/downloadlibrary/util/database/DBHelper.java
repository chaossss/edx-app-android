package com.chaos.downloadlibrary.util.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * A helper class for operating database
 * Created by chaos on 2015/10/24.
 */
public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "download_library455.db", null, 1);
    }

    /**
     * Creates a download_info table in download.db database to storage relating info
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table download_info(_id integer PRIMARY KEY AUTOINCREMENT, thread_id integer, "
                + "start_pos integer, end_pos integer, compelete_size integer,url char)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
