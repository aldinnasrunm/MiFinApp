package com.quick.myfin.Activity

import android.content.ContentValues
import android.content.DialogInterface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.textfield.TextInputEditText
import com.quick.myfin.Adapter.SaveCashAdapter
import com.quick.myfin.Models.saveCashDatabase
import com.quick.myfin.R
import kotlinx.android.synthetic.main.activity_save_cash.*
import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class SaveCashActivity : AppCompatActivity() {
    lateinit var mAdapter : SaveCashAdapter
    val helperSaveCash : saveCashDatabase = saveCashDatabase(this)
    private lateinit var formatRupiah : NumberFormat
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_cash)
        supportActionBar?.hide()
        val localeID = Locale("in", "ID")
        formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        updateView()
        btn_addSaveCash.setOnClickListener {
            addSaveCash()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addSaveCash() {
        val popUpLayout : View = LayoutInflater.from(this).inflate(R.layout.layout_popup_save_cash, null, false)
        val mTitle : TextInputEditText = popUpLayout.findViewById(R.id.et_inputTitleSaveCash)
        val mTotal : TextInputEditText = popUpLayout.findViewById(R.id.et_inputTotalSaveCash)
        val mInsert : Button = popUpLayout.findViewById(R.id.btn_inputSaveCash)

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy")
        val formatted = current.format(formatter)

        mInsert.setOnClickListener {
            if (mTitle.text.toString().equals("") || mTotal.text.toString().equals("")){
                Toast.makeText(this, "Ups, kamu belum mengisi semua datanya", Toast.LENGTH_SHORT).show()
            }else {
                val value = ContentValues()
                value.put("title_save_cash", mTitle.text.toString())
                value.put("date", formatted)
                value.put("total_save_cash", (mTotal.text.toString()).toLongOrNull())
                helperSaveCash.insertData(value)
            }
        }

        AlertDialog.Builder(this, R.style.CustomAlertDialog)
            .setView(popUpLayout)
            .setCancelable(false)
            .setPositiveButton("Done", DialogInterface.OnClickListener { dialog, which ->
                updateView()
                dialog.dismiss()
            }).show()
    }

    private fun updateView() {
        mAdapter = SaveCashAdapter(helperSaveCash.select(), this, this)
        mAdapter.notifyDataSetChanged()
        rv_saveCash.layoutManager = LinearLayoutManager(this)
        rv_saveCash.adapter = mAdapter
        tv_totalSaveCash.setText(formatRupiah.format(helperSaveCash. getTotalSaved()))
    }


   fun deleteItem(pos :Int){
       val deleteView : View = LayoutInflater.from(this).inflate(R.layout.layout_delete_item, null, false)
       val deleteAnimation : LottieAnimationView = deleteView.findViewById(R.id.deleteAnimation)
       deleteAnimation.playAnimation()
       android.app.AlertDialog.Builder(this, R.style.CustomAlertDialog)
           .setView(deleteView)
           .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
               helperSaveCash.deleteItem(pos)
               updateView()
           })
           .setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
               dialog.cancel()
           })
           .setCancelable(false)
           .show()
   }
}