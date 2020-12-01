package com.quick.myfin.Models

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class cashPlantDatabase(var context : Context?) : SQLiteOpenHelper(context, "db_cashPlant", null, 1) {

    private lateinit var mQuery: String
    private lateinit var mQuery2 : String
    override fun onCreate(db: SQLiteDatabase?) {
        mQuery = "CREATE TABLE IF NOT EXISTS tb_cashPlan(" +
                "_id INTEGER PRIMARY KEY," +
                "title_cash_plan TEXT," +
                "date TEXT,"+
                "total_cash_plan INTEGER" +
                ")"
        db?.execSQL(mQuery)
    }

    fun insertData(values: ContentValues) {
        val db = this.writableDatabase
        Log.d("Values", "" + values.toString())
        db.insert("tb_cashPlan", null, values)
    }

    fun select(): Cursor? {
        val db = this.writableDatabase
        mQuery = "SELECT * FROM tb_cashPlan ORDER BY _id DESC"
        return db.rawQuery(mQuery, null)
    }

    fun getBiayaTotal(): Int {
        val db = this.writableDatabase
        mQuery = "SELECT SUM(total_cash_plan) FROM  tb_cashPlan"
        val i = db.rawQuery(mQuery, null)
        var total = 0
        if (i.moveToFirst()) {
            total = i.getInt(0)
        }
        return total
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}