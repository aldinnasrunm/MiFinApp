package com.quick.myfin.Activity

import android.content.ContentValues
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quick.myfin.Adapter.ListAdapter
import com.quick.myfin.Models.inOutDatabase
import com.quick.myfin.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private lateinit var  helperInOut : inOutDatabase
    lateinit var mAdapter : ListAdapter
    lateinit var rv_list : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()
//        cv_balance = findViewById(R.id.cv_balance)
        helperInOut = inOutDatabase(this)
        rv_list = findViewById(R.id.rv_balanceList)
        updateList()
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



        btn_insert.setOnClickListener {
            var status : String = ""
            if (rb_in.isChecked){
                status = "in"
            }else if(rb_out.isChecked){
                status = "out"
            }

            if (et_title.equals("") || et_total.equals("")|| status.equals("")){
                Toast.makeText(this, "Ups!!, kamu belum mengisi data secara lengkap", Toast.LENGTH_SHORT).show()
            }else{
                val value  = ContentValues()
                value.put("title_balance", et_title.text.toString())
                value.put("status_balance", status)
                value.put("total_balance", Integer.parseInt(et_total.text.toString()))
                Log.d(Companion.TAG, "addBalance: " + value.toString())
                Toast.makeText(this, "data = " +value.toString(), Toast.LENGTH_SHORT).show()
                helperInOut.insertData(value)
            }
//            if(status.equals("not set")){
//                if(rb_in.isChecked){
//                    status = "in"
//                    val value  = ContentValues()
//                    value.put("title_balance", et_title.text.toString())
//                    value.put("status_balance", status)
//                    value.put("total_balance", Integer.parseInt(et_total.text.toString()))
//                    Log.d(Companion.TAG, "addBalance: " + value.toString())
//                    Toast.makeText(this, "data = " +value.toString(), Toast.LENGTH_SHORT).show()
//                    helperInOut.insertData(value)
//                }else if (rb_out.isChecked){
//                    status = "out"
//                    val value  = ContentValues()
//                    value.put("title_balance", et_title.text.toString())
//                    value.put("status_balance", status)
//                    value.put("total_balance", Integer.parseInt(et_total.text.toString()))
//                    Log.d(Companion.TAG, "addBalance: " + value.toString())
//                    helperInOut.insertData(value)
//                    Toast.makeText(this, "data = " +value.toString(), Toast.LENGTH_SHORT).show()
//                }else{
//                    Toast.makeText(this, "Pilih Status", Toast.LENGTH_SHORT).show()
//                }
//
//            }else{
//                val value  = ContentValues()
//                value.put("title_balance", et_title.text.toString())
//                value.put("status_balance", status)
//                value.put("total_balance", Integer.parseInt(et_total.text.toString()))
//                Log.d(Companion.TAG, "addBalance: " + value.toString())
//                helperInOut.insertData(value)
//                Toast.makeText(this, "data = " +value.toString(), Toast.LENGTH_SHORT).show()
//            }
            updateList()
        }
        AlertDialog.Builder(this)
            .setView(privateView)
            .setPositiveButton("yes", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(this, "click", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }).show()
    }

    private fun updateList(){
        mAdapter = com.quick.myfin.Adapter.ListAdapter(helperInOut.select())
        mAdapter.notifyDataSetChanged()
        rv_list.setHasFixedSize(true)
        rv_list.layoutManager = LinearLayoutManager(this)
        rv_list.adapter = mAdapter
    }

    companion object {
        private const val TAG = "HomeActivity"
    }
}