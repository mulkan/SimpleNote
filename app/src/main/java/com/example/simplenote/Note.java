package com.example.simplenote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Note extends SQLiteOpenHelper {
    private static final String  database_name = "dbnote2";
    private static final String table_name = "tbnote";
    public static final String clm_id = "_id"; //wajib _id
    public static final String clm_tanggal = "tanggal";
    public final static String clm_title = "title";
    public final static String clm_content = "content";
    private SQLiteDatabase db;
    public Note(Context context) {
        super(context, database_name, null, Setting.VERSI_DB);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //https://stackoverflow.com/questions/47848445/sqlite-database-error-during-execsql
        db = sqLiteDatabase;
        String query = "CREATE TABLE " + table_name + "(" + clm_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + clm_tanggal + " TEXT, " + clm_title + " TEXT, " + clm_content + " TEXT)";
        System.out.println(query);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
    }
    //Get All SQLite Data
    public Cursor allData(){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name , null);
        return cur;
    }

    //Get 1 Data By ID
    public Cursor oneData(Long id){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " WHERE " + clm_id + "=" + id, null);
        return cur;
    }

    //Insert Data to Database
    public void insertData(ContentValues values){
        db.insert(table_name, null, values);
    }

    //Update Data
    public void updateData(ContentValues values, long id){
        db.update(table_name, values, clm_id + "=" + id, null);
    }

    //Delete Data
    public void deleteData(long id){
        db.delete(table_name, clm_id + "=" + id, null);
    }
}
