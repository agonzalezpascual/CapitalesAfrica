package com.example.capitalesafricajava

import android.database.sqlite.SQLiteOpenHelper
import com.example.capitalesafricajava.DbHandler
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.annotation.SuppressLint
import android.content.Context
import java.util.ArrayList
import java.util.HashMap

class DbHandler(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = ("CREATE TABLE " + TABLE_Respuestas + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_PAIS + " TEXT,"
                + KEY_CAPITAL + " TEXT,"
                + KEY_ESTADO + " TEXT" + ")")
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Respuestas)
        // Create tables again
        onCreate(db)
    }

    // **** CRUD (Create, Read, Update, Delete) Operations ***** //
    fun deleteAll() {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM " + TABLE_Respuestas)
    }

    // Adding new User Details
    fun insertUserDetails(pais: String?, capital: String?, estado: String?) {
        //Get the Data Repository in write mode
        val db = this.writableDatabase
        //Create a new map of values, where column names are the keys
        val cValues = ContentValues()
        cValues.put(KEY_PAIS, pais)
        cValues.put(KEY_CAPITAL, capital)
        cValues.put(KEY_ESTADO, estado)
        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert(TABLE_Respuestas, null, cValues)
        db.close()
    }

    // Get User Details
    @SuppressLint("Range")
    fun GetUsers(): ArrayList<HashMap<String, String>> {
        val db = this.writableDatabase
        val userList = ArrayList<HashMap<String, String>>()
        val query = "SELECT pais, capital, estado FROM " + TABLE_Respuestas
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val user = HashMap<String, String>()
            user["pais"] = cursor.getString(cursor.getColumnIndex(KEY_PAIS))
            user["estado"] = cursor.getString(cursor.getColumnIndex(KEY_ESTADO))
            user["capital"] = cursor.getString(cursor.getColumnIndex(KEY_CAPITAL))
            userList.add(user)
        }
        return userList
    }

    // Get User Details based on userid
    fun GetUserByUserId(userid: Int): ArrayList<HashMap<String, String>> {
        val db = this.writableDatabase
        val userList = ArrayList<HashMap<String, String>>()
        val query = "SELECT name, location, designation FROM " + TABLE_Respuestas
        val cursor = db.query(TABLE_Respuestas,
            arrayOf(KEY_PAIS, KEY_CAPITAL, KEY_ESTADO),
            KEY_ID + "=?",
            arrayOf(userid.toString()),
            null,
            null,
            null,
            null)
        if (cursor.moveToNext()) {
            val user = HashMap<String, String>()
            user["name"] = cursor.getString(cursor.getColumnIndex(KEY_PAIS))
            user["designation"] = cursor.getString(cursor.getColumnIndex(KEY_ESTADO))
            user["location"] = cursor.getString(cursor.getColumnIndex(KEY_CAPITAL))
            userList.add(user)
        }
        return userList
    }

    // Delete User Details
    fun DeleteUser(userid: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_Respuestas, KEY_ID + " = ?", arrayOf(userid.toString()))
        db.close()
    }

    // Update User Details
    fun UpdateUserDetails(
        location: String?,
        designation: String?,
        id: Int
    ): Int {
        val db = this.writableDatabase
        val cVals = ContentValues()
        cVals.put(KEY_CAPITAL, location)
        cVals.put(KEY_ESTADO, designation)
        return db.update(TABLE_Respuestas, cVals, "$KEY_ID = ?", arrayOf(id.toString()))
    }

    companion object {
        private const val DB_VERSION = 1
        private const val DB_NAME = "africadb"
        private const val TABLE_Respuestas = "repuestas"
        private const val KEY_ID = "id"
        private const val KEY_PAIS = "pais"
        private const val KEY_CAPITAL = "capital"
        private const val KEY_ESTADO = "estado"
    }
}