package com.saranya.contents;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;

public class DBHandler extends SQLiteOpenHelper {

    private static final String tableName = "CONTENTS";
    private static final String date = "DATE";
    private static final String title = "TITLE";
    private static final String pg_start = "PG_START";
    private static final String pg_end = "PG_END";
    private static final String tableNameCopy= "COPIES";
    private static final String copyName= "NAME";
    private static final String teach= "TEACHER";

    public DBHandler(@Nullable Context context) {
        super(context, tableName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + tableName + " ("
                + "ID" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + teach + " VARCHAR(255),"
                + title + " VARCHAR(255),"
                + pg_start + " VARCHAR(255),"
                + pg_end + " VARCHAR(255),"
                + copyName + " VARCHAR(255))";

        String query2 = "CREATE TABLE " + tableNameCopy + " ("
                + "ID" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + copyName + " VARCHAR(255))";

        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tableName);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tableNameCopy);
        onCreate(sqLiteDatabase);
    }

    public void add_content(String teach, String title, String pg_start, String pg_end, String copy){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHandler.teach,teach);
        values.put(DBHandler.copyName,copy);
        values.put(DBHandler.title,title);
        values.put(DBHandler.pg_start,pg_start);
        values.put(DBHandler.pg_end,pg_end);

        db.insert(tableName, null, values);
        db.close();
    }

    public int get_max_pg(String name){
        int max=0;
        SQLiteDatabase db = this.getReadableDatabase();

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + " WHERE " + copyName + " = '" + name + "'", null);
        if (cursor.moveToFirst()) {
            do {
                int pg_end= Integer.parseInt(cursor.getString(4));
                if(pg_end > max) {max=pg_end;}
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return max;
    }

    public void drop(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE "+tableName);
        String query = "CREATE TABLE " + tableName + " ("
                + "ID" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + teach + " VARCHAR(255),"
                + title + " VARCHAR(255),"
                + pg_start + " VARCHAR(255),"
                + pg_end + " VARCHAR(255),"
                + copyName + " VARCHAR(255))";

        String query2 = "CREATE TABLE " + tableNameCopy + " ("
                + "ID" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + copyName + " VARCHAR(255))";

        db.execSQL(query);
    }

    public int get_copy_id(String name){
        int id=-1;
        SQLiteDatabase db = this.getReadableDatabase();

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM " + tableNameCopy + " WHERE " + copyName + " = '" + name + "'", null);
        if (cursor.moveToFirst()) {
            do {
                id=cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return id;
    }

    public void add_copy(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHandler.copyName,name);
        db.insert(tableNameCopy, null, values);
        db.close();
    }

    public void delete_all_copies(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ tableNameCopy);
        db.close();
    }

    public void delete_all_contents(String copy){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ tableName +" WHERE " + copyName + " = '" + copy + "'" );
        db.close();
    }

    public void delete_copy(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableNameCopy,"name=?", new String[]{name});
    }

    public ArrayList<String> get_copies(){
        ArrayList<String> list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM " + tableNameCopy, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1));
                Log.d("database",cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public ArrayList<Content> get_contents(String copy){
        ArrayList<Content> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + " WHERE " + copyName + " = '" + copy + "'", null);
        if (cursor.moveToFirst()) {
            do {
                Content content=new Content();
                content.setTeacher(cursor.getString(1));
                content.setTopic(cursor.getString(2));
                content.setPg_start(cursor.getString(3));
                content.setPg_end(cursor.getString(4));
                content.setCopy(cursor.getString(5));
                list.add(content);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

}
