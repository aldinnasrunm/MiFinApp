package com.quick.myfin.Models

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class inOutDatabase(var context: Context) : SQLiteOpenHelper(context, "db_inOutCom", null, 1) {
    private lateinit var mQuery: String
    override fun onCreate(db: SQLiteDatabase?) {
        mQuery = "CREATE TABLE IF NOT EXISTS tb_inOutCom(" +
                "_id INTEGER PRIMARY KEY," +
                "title_balance TEXT," +
                "status_balance TEXT," +
                "total_balance INTEGER" +
                ")"
        db?.execSQL(mQuery)
    }

    fun insertData(values: ContentValues) {
        val db = this.writableDatabase
        Log.d("Values", "" + values.toString())
        db.insert("tb_inOutCom", null, values)
    }

    fun select(): Cursor? {
        val db = this.writableDatabase
        mQuery = "SELECT * FROM tb_inOutCom"
        return db.rawQuery(mQuery, null)
    }

    fun getTotal(): Int {
        val db = this.writableDatabase
        mQuery = "SELECT SUM(total_balance) FROM  tb_inOutCom WHERE status_balance = in"
        val i = db.rawQuery(mQuery, null)
        var total = 0
        if (i.moveToFirst()) {
            total = i.getInt(0)
        }
        return total
    }

    fun selectTitle(): ArrayList<CharSequence>{
        val db = this.writableDatabase
        mQuery ="SELECT title_balance FROM tb_inOutCom"
        val i  = db.rawQuery(mQuery, null)
        var list = java.util.ArrayList<CharSequence>()
        if (i.moveToNext()){
           list.add(i.getString(1))
        }
        return list
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}