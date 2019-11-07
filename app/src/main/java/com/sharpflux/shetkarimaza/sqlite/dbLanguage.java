package com.sharpflux.shetkarimaza.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sagar Hatikat on 10 September 2019
 */
public class dbLanguage extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Languages.db";
    public static final String TABLE_NAME = "LanguageUser";
    public static final String CURRENT_LANGUAGE = "LANGUAGE";



    public dbLanguage(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,LANGUAGE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }


    public boolean LanguageInsert(String Language) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CURRENT_LANGUAGE,Language);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }



   public Cursor LanguageGet(String language) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT LANGUAGE FROM " + TABLE_NAME ;
        Cursor cursor =  db.rawQuery(query,null);
        return cursor;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public Integer DeleteRecord (String FilterBy, String CorrespondanceId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "LANGUAGE",new String[] {FilterBy,CorrespondanceId});
    }
    public Integer DeleteRecordAll () {
        SQLiteDatabase db = this.getWritableDatabase();
        return   db.delete(TABLE_NAME, null, null);

    }
    public String GETExist(String itemId){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT ITEMID FROM " + TABLE_NAME + " WHERE ITEMID = ?";
        Cursor cursor =  db.rawQuery(query, new String[] {itemId});
        String Id="0";
        if(cursor.moveToFirst()){
            Id = cursor.getString(0);
        }
        return  Id;
    }
}