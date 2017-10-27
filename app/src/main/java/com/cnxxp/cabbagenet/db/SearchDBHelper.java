package com.cnxxp.cabbagenet.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/5/22 0022.
 */

public class SearchDBHelper extends SQLiteOpenHelper {
    public static SearchDBHelper mInstance = null;

    private SearchDBHelper(Context context) {
        super(context, "search.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS search_tab (id INTEGER primary key autoincrement, history varchar(100))");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS zan_tab (id INTEGER primary key autoincrement, uid varchar(100),shopid varchar(40))");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS comment_zan_tab (id INTEGER primary key autoincrement, uid varchar(100),commentid varchar(40))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public static synchronized SearchDBHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SearchDBHelper(context);
        }
        return mInstance;

    }

    //插入一条
    public void insertHistory(String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("history", value);
        db.insert("search_tab", null, cv);
    }

    public boolean HasHistory(String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "select * from   search_tab  where   history=?",
                new String[]{value});
        while (cursor.moveToNext()) {
            db.close();
            return true;// //有城市在数据库已存在，返回true  
        }
        db.close();
        return false;// //在数据库以前存在 false  

    }

    //插入一条
    public void insertZan(String uid, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("uid", uid);
        cv.put("shopid", value);
        db.insert("zan_tab", null, cv);
    }
    //插入一条
    public void insertCommentZan(String uid, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("uid", uid);
        cv.put("commentid", value);
        db.insert("comment_zan_tab", null, cv);
    }

    //删除一条
    public void deleteHistory(String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "history = ?";
        String[] whereValue = {value};
        db.delete("search_tab", where, whereValue);
    }

    //搜索所有
    public List<String> searchAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<String> list = new ArrayList<String>();
        Cursor cursor = db.query("search_tab", new String[]{"history"},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            list.add(cursor.getString(cursor.getColumnIndex("history")));

        }
        cursor.close();
        return list;
    }

    public boolean HasZanData(String uid, String shopid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "select * from   zan_tab  where   uid=? and shopid=? ",
                new String[]{uid, shopid});
        while (cursor.moveToNext()) {
            db.close();
            return true;// //有城市在数据库已存在，返回true  
        }
        db.close();
        return false;// //在数据库以前存在 false  

    }

    public boolean HasCommentZanData(String uid, String commentid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "select * from   comment_zan_tab  where   uid=? and commentid=? ",
                new String[]{uid, commentid});
        while (cursor.moveToNext()) {
            db.close();
            return true;// //有城市在数据库已存在，返回true  
        }
        db.close();
        return false;// //在数据库以前存在 false  

    }

    //删除所有
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("truncate table search_tab");
    }
}
