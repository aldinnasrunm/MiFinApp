package com.quick.myfin.Activity

import android.content.ContentValues
import android.content.DialogInterface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.quick.myfin.Adapter.CashPlantAdapter
import com.quick.myfin.Adapter.ListItemAdapter
import com.quick.myfin.Models.cashPlantDatabase
import com.quick.myfin.Models.inOutDatabase
import com.quick.myfin.R
import kotlinx.android.synthetic.main.activity_cash_plan.*
import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class CashPlanActivity : AppCompatActivity() {
    lateinit var mAdapter : CashPlantAdapter
    val helperCashPlan : cashPlantDatabase = cashPlantDatabase(this)
    val helperInOut : inOutDatabase = inOutDatabase(this)
    private lateinit var formatRupiah : NumberFormat
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash_plan)
        supportActionBar?.hide()
        val localeID = Locale("in", "ID")
        formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        updateView()

        btn_addCashPlan.setOnClickListener {
            addPlan()
        }
    }

    private fun updateView() {
        mAdapter = CashPlantAdapter(helperCashPlan.select(), this)
        mAdapter.notifyDataSetChanged()
        rv_listCashPlant.setHasFixedSize(true)
        rv_listCashPlant.layoutManager = LinearLayoutManager(this)
        rv_listCashPlant.adapter = mAdapter
        tv_totalCashPlant.setText(formatRupiah.format(helperCashPlan.getBiayaTotal()))
        var kekurangan : Int = helperCashPlan.getBiayaTotal() - helperInOut.getBalance()
        if (kekurangan > 0 ){
            tv_kekuranganCashPlan.setText(formatRupiah.format(kekurangan))
        }else{
            val i : Int = 0
            tv_kekuranganCashPlan.setText(formatRupiah.format(i))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addPlan() {
        val popUpView : View = LayoutInflater.from(this).inflate(R.layout.layout_popup_cash_plan,null, false)
        val btn_input : Button = popUpView.findViewById(R.id.btn_inputCashPlan)
        val et_title : TextInputEditText = popUpView.findViewById(R.id.et_inputTitleCashPlan)
        val et_total : TextInputEditText = popUpView.findViewById(R.id.et_inputTotalCashPlan)
        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy")
        val formatted = current.format(formatter)

        btn_input.setOnClickListener {
            val value  = ContentValues()
            value.put("title_cash_plan", et_title.text.toString())
            value.put("date", formatted)
            value.put("total_cash_plan", (et_total.text.toString()).toLongOrNull())
            helperCashPlan.insertData(value)

        }
        AlertDialog.Builder(this, R.style.CustomAlertDialog)
            .setView(popUpView)
            .setPositiveButton("Done", DialogInterface.OnClickListener { dialog, which ->
                updateView()
                dialog.cancel()
            }).show()
    }
}