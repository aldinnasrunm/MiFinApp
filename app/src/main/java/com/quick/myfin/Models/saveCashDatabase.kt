package com.quick.myfin.Models

import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class saveCashDatabase(var context : Context?) : SQLiteOpenHelper(context, "db_saveCash", null, 1)  {

    private  lateinit var mQuery : String
    override fun onCreate(db: SQLiteDatabase?) {
        mQuery = "CREATE TABLE IF NOT EXISTS tb_saveCash(" +
                "_id INTEGER PRIMARY KEY," +
                "title_save_cash TEXT," +
                "date TEXT,"+
                "total_save_cash INTEGER" +
                ")"
        db?.execSQL(mQuery)
    }
    fun insertData(values: ContentValues) {
        val db = this.writableDatabase
        Log.d("Values", "" + values.toString())
        db.insert("tb_saveCash", null, values)
    }
    fun select(): Cursor? {
        val db = this.writableDatabase
        mQuery = "SELECT * FROM tb_saveCash ORDER BY _id DESC"
        return db.rawQuery(mQuery, null)
    }
    fun getTotalSaved(): Int {
        val db = this.writableDatabase
        mQuery = "SELECT SUM(total_save_cash) FROM  tb_saveCash"
        val i = db.rawQuery(mQuery, null)
        var total = 0
        if (i.moveToFirst()) {
            total = i.getInt(0)
        }
        return total
    }
    fun deleteItem(id: Int) {
        //SQLite Delete ndes
        val db = this.writableDatabase
        mQuery = "DELETE FROM tb_saveCash WHERE _id =$id"
        db.execSQL(mQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}