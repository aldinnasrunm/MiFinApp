package com.quick.myfin.Activity

import android.content.ContentValues
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.quick.myfin.Models.inOutDatabase
import com.quick.myfin.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private lateinit var  helperInOut : inOutDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()
//        cv_balance = findViewById(R.id.cv_balance)
        helperInOut = inOutDatabase(this)
        cv_balance.setOnClickListener {
            addBalance()
        }
    }

    private fun addBalance() {
        var privateView : View = LayoutInflater.from(this).inflate(R.layout.layout_popup, null, false)
        val et_title : EditText = privateView.findViewById(R.id.et_inputTitle)
        val et_total : EditText = privateView.findViewById(R.id.et_inputTotal)
        val btn_insert :Button = privateView.findViewById(R.id.btn_insert)
        val rb_in : RadioButton = privateView.findViewById(R.id.rb_status_in)
        val rb_out : RadioButton = privateView.findViewById(R.id.rb_status_out)
        val status : String?
        if (rb_in.isChecked){
            status = "in"
        }else if(rb_out.isChecked){
            status = "out"
        }else{
            status = "not set"
        }

        btn_insert.setOnClickListener {
            val value  = ContentValues()
            value.put("title_balance", et_title.text.toString())
            value.put("status_balance", status)
            value.put("total_balance", Integer.parseInt(et_total.text.toString()))
            Log.d(Companion.TAG, "addBalance: " + value.toString())
            helperInOut.insertData(value)
            Toast.makeText(this, "data = " +value.toString(), Toast.LENGTH_SHORT).show()
        }
        AlertDialog.Builder(this)
            .setView(privateView)
            .setPositiveButton("yes", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(this, "click", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }).show()
    }

    companion object {
        //    private lateinit var cv_balance : CardView
        private const val TAG = "HomeActivity"
    }
}