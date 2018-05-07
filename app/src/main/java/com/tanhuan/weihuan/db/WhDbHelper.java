package com.tanhuan.weihuan.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chenyaning on 2017/12/28.
 */

public class WhDbHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "weihuan.db";
    public static final String TABLE_NAME = "laters";

    public WhDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static final String CREATE_LATERS = "create table later ("
            + "id integer primary key autoincrement, "
            + "avatar_url text,"
            + "name text,"
            + "content text)";



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_LATERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
