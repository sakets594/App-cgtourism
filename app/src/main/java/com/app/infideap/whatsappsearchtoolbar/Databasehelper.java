package com.app.infideap.whatsappsearchtoolbar;

/**
 * Created by sks on 10/3/18.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

/**
 * Created by sks on 25/2/18.
 */

public class Databasehelper extends SQLiteOpenHelper {

    public static final String  DATABASE_NAME= "cg.db";

    public static final String  FILE_DIR= "DCIM";
    public static final String  TABLE_NAME= "DATA";
    public static final String  COL1= "id";
    public static final String  COL2= "cat";
    public static final String  COL3= "p_name";
    public static final String  COL4= "lat";
    public static final String  COL5= "long";
    public static final String  COL6= "begin";
    public static final String  COL7= "end";
    public static final String  COL9= "favrt";
    public static final String  COL8= "loc";

    public Databasehelper(final Context context) {

        super(context,  Environment.getExternalStorageDirectory()+File.separator+DATABASE_NAME, null, 1);

        SQLiteDatabase.openOrCreateDatabase(Environment.getExternalStorageDirectory()+File.separator+DATABASE_NAME,null);
        // super(context, DATABASE_NAME, null, 1);
        //SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME,null);

        //SQLiteDatabase.openOrCreateDatabase(Environment.getExternalStorageDirectory()+ File.separator+FILE_DIR+File.separator+DATABASE_NAME,null);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" create table IF NOT EXISTS "+ TABLE_NAME + " (id varchar2(20) PRIMARY KEY NOT NULL,cat varchar2(50), p_name varchar2(50),lat Float(10,7),long,[begin] NUMBER(2),[end] NUMBER(2),loc varchar2(50) ,favrt varchar2(5));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String id,String cat,String p_name,float lat,float long_,int col_6,int end,String favrt){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentvalue = new ContentValues();
        contentvalue.put(COL1,id);
        contentvalue.put(COL2,cat);
        contentvalue.put(COL3,p_name);
        contentvalue.put(COL4,lat);
        contentvalue.put(COL5,long_);
        contentvalue.put(COL6,col_6);
        contentvalue.put(COL7,end);

        contentvalue.put(COL8,favrt);
        contentvalue.put(COL9,favrt);
        long result = db.insert(TABLE_NAME,null,contentvalue);
        if(result==-1)
            return false;
        else return true;
    }




    public Cursor getAllData()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME+" ORDER BY p_name ASC ",null);
        //db.close();
        return res;
    }
    public Cursor getmdata()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME+" where ID=="+"1",null);
        //db.close();
        return res;
    }


    public boolean updateData(String id,String cat,String p_name,float lat,float long_,int col_6,int end,String loc ,String favrt)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentvalue=new ContentValues();
        contentvalue.put(COL1,id);
        contentvalue.put(COL2,cat);
        contentvalue.put(COL3,p_name);
        contentvalue.put(COL4,lat);
        contentvalue.put(COL5,long_);
        contentvalue.put(COL6,col_6);
        contentvalue.put(COL7,end);
        contentvalue.put(COL8,loc);
        contentvalue.put(COL9,favrt);
        db.update(TABLE_NAME,contentvalue,"id = ?",new String[] {String.valueOf(id)} );
        //db.close();
        return true;
    }

    public boolean togglefavrt(String ID,String placename,String placeloc,String favrt)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL1,ID);
        contentValues.put(COL2,placename);
        contentValues.put(COL3,placeloc);
        favrt=favrt=="yes"?"no":"yes";
        contentValues.put(COL4,favrt);
        db.update(TABLE_NAME,contentValues,"ID = ?",new String[] {ID} );
        //db.close();
        return true;
    }

    public Integer deleteData(String id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID=?",new String[]{id});
    }




}
