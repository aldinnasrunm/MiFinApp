package com.quick.myfin.Models

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class inOutDatabase(var context: Context?) : SQLiteOpenHelper(context, "db_inOutCom", null, 1) {
    private lateinit var mQuery: String
    private lateinit var mQuery2 : String
    override fun onCreate(db: SQLiteDatabase?) {
        mQuery = "CREATE TABLE IF NOT EXISTS tb_inOutCom(" +
                "_id INTEGER PRIMARY KEY," +
                "title_balance TEXT," +
                "status_balance TEXT," +
                "date TEXT,"+
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
        mQuery = "SELECT * FROM tb_inOutCom ORDER BY _id DESC"
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
    fun getOutBalance(): Int {
        val db = this.writableDatabase
        mQuery = "SELECT SUM(total_balance) FROM  tb_inOutCom WHERE status_balance = 'out'"
        val i = db.rawQuery(mQuery, null)
        var total = 0
        if (i.moveToFirst()) {
            total = i.getInt(0)
        }
        return total
    }

    fun getBalance(): Int {
        val db = this.writableDatabase
        mQuery = "SELECT SUM(total_balance) FROM  tb_inOutCom WHERE status_balance = 'in'"
        mQuery2 = "SELECT SUM(total_balance) FROM  tb_inOutCom WHERE status_balance = 'out'"
        val i = db.rawQuery(mQuery, null)
        val a = db.rawQuery(mQuery2, null)
        var totalIn = 0
        var totalOut = 0
        if (i.moveToFirst()) {
            totalIn = i.getInt(0)
        }
        if (a.moveToFirst()) {
            totalOut = a.getInt(0)
        }
        return totalIn-totalOut
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