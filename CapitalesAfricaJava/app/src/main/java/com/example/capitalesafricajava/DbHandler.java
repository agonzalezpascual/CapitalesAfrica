package com.example.capitalesafricajava;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DbHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "africadb";
    private static final String TABLE_Respuestas = "repuestas";
    private static final String KEY_ID = "id";
    private static final String KEY_PAIS = "pais";
    private static final String KEY_CAPITAL = "capital";
    private static final String KEY_ESTADO  = "estado";
    public DbHandler(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " + TABLE_Respuestas + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_PAIS + " TEXT,"
                + KEY_CAPITAL + " TEXT,"
                + KEY_ESTADO + " TEXT"+ ")";
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Respuestas);
        // Create tables again
        onCreate(db);
    }
    // **** CRUD (Create, Read, Update, Delete) Operations ***** //
    public void deleteAll(){
        SQLiteDatabase db  = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_Respuestas);

    }
    // Adding new User Details
    void insertUserDetails(String pais, String capital, String estado){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_PAIS, pais);
        cValues.put(KEY_CAPITAL, capital);
        cValues.put(KEY_ESTADO, estado);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_Respuestas,null, cValues);
        db.close();
    }
    // Get User Details
    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> GetUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT pais, capital, estado FROM "+ TABLE_Respuestas;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("pais",cursor.getString(cursor.getColumnIndex(KEY_PAIS)));
            user.put("estado",cursor.getString(cursor.getColumnIndex(KEY_ESTADO)));
            user.put("capital",cursor.getString(cursor.getColumnIndex(KEY_CAPITAL)));
            userList.add(user);
        }
        return  userList;
    }
    // Get User Details based on userid
    public ArrayList<HashMap<String, String>> GetUserByUserId(int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT name, location, designation FROM "+ TABLE_Respuestas;
        Cursor cursor = db.query(TABLE_Respuestas, new String[]{KEY_PAIS, KEY_CAPITAL, KEY_ESTADO}, KEY_ID+ "=?",new String[]{String.valueOf(userid)},null, null, null, null);
        if (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("name",cursor.getString(cursor.getColumnIndex(KEY_PAIS)));
            user.put("designation",cursor.getString(cursor.getColumnIndex(KEY_ESTADO)));
            user.put("location",cursor.getString(cursor.getColumnIndex(KEY_CAPITAL)));
            userList.add(user);
        }
        return  userList;
    }
    // Delete User Details
    public void DeleteUser(int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Respuestas, KEY_ID+" = ?",new String[]{String.valueOf(userid)});
        db.close();
    }
    // Update User Details
    public int UpdateUserDetails(String location, String designation, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put(KEY_CAPITAL, location);
        cVals.put(KEY_ESTADO, designation);
        int count = db.update(TABLE_Respuestas, cVals, KEY_ID+" = ?",new String[]{String.valueOf(id)});
        return  count;
    }
}