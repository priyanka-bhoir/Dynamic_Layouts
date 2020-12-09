package com.priyanka.dynamiclayouts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String Databse_name = "dynamic.db";
    public static final String Table_name = "details";
    public static final String ID = "id";
    public static final String name = "name";
    public static final String phone = "phone";
    public static final String email = "email";
    public static final String web = "web";
    public static final String password="password";
    private static final String TAG = "Main";

    public DatabaseHelper(@Nullable Context context) {
        super(context, Databse_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table "+ Table_name +
                        "(id integer primary key, fname text,email text, phone text,web text,password text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS details");
        onCreate(db);
    }

    void insert(Data data){
        ContentValues contentValues = new ContentValues();
        contentValues.put(name, data.getName());
        contentValues.put(email, data.getEmail());
        contentValues.put(phone, data.getNumber());
        contentValues.put(web, data.getWeb());
        contentValues.put(password,data.getPass());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(Table_name, null, contentValues);
    }

    public ArrayList<Data> listData(){
        String query="select * FROM " + Table_name;
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<Data> storeData= new ArrayList<>();

        Log.e("listdata", "listData: 1" +storeData);
        Cursor cursor=db.rawQuery(query,null);

        Log.e("TAG", "cursor count===: "+cursor.getCount() );

//        while (cursor.moveToNext()) {
        if(cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
//                Log.e("id", "listData" + Fname);
                String Lname = cursor.getString(2);
                String email = cursor.getString(3);
                String phone = cursor.getString(4);
                String web = cursor.getString(5);
                String pass=cursor.getString(6);
//                Log.e(TAG, "listData: "+"id"+id+""+ Fname+"f "+ Lname+"l"+phone+"p"+web+"w"+email+"e"+password);
                storeData.add(new Data(name,phone,web,email,pass,id));
                Log.e(TAG, "listData: " + id);
            }
//        }
        }
        cursor.close();
        return storeData;
    }
    void Update(Data data){
        ContentValues contentValues =new ContentValues();

        contentValues.put(name,data.getName());
//        contentValues.put(lname,data.getLname());
        contentValues.put(email,data.getEmail());
        contentValues.put(phone,data.getNumber());
        contentValues.put(web,data.getWeb());
        SQLiteDatabase db = this.getReadableDatabase();
        db.update(Table_name,contentValues,ID +" =   ? ",null);
    }

    void Delete(String data){

        SQLiteDatabase db =this.getWritableDatabase();
        db.delete(Table_name, ID+"=? ", new String[]{data});
    }
    boolean search(String Email, String Pass){
//        String query="Select "+Email + "From " + Table_name+ "where "+ pass +"="+ Pass;
        String[] columns={ID};

        Log.e(TAG, "search:SQLiteDatabase " );
        SQLiteDatabase db=getReadableDatabase();

        Log.e(TAG, "search:SQLiteDatabase " );
        String selection = email+"= ?";

        String[] selectionArgs = {Email};

        Cursor cursor= db.query(Table_name,columns,selection,selectionArgs,null,null,null);
        int count=cursor.getCount();
        cursor.close();
        if(count>0){
            return true;
        }
        return false;

    }
}
