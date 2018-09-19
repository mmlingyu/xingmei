package com.cheyipai.corec.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
    public DBOpenHelper(Context context) {
        super(context, "cyp.db", null, 1);
    }

    //数据库第一次创建时候调用，
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user(uid integer primary key autoincrement, uname varchar(20), uaddress varchar(20))");
    }

    //数据库文件版本号发生变化时调用
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
    }
}