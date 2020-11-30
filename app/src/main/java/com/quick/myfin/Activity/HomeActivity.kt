package com.quick.myfin.Activity

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment
import com.quick.myfin.Adapter.CalendarAdapter
import com.quick.myfin.Adapter.ListItemAdapter
import com.quick.myfin.Models.CalendarModel
import com.quick.myfin.Models.inOutDatabase
import com.quick.myfin.R
import kotlinx.android.synthetic.main.activity_home.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class HomeActivity : AppCompatActivity() {
    private val DAYS_COUNT = 42
    private val calendarList = ArrayList<CalendarModel>()
    private val calendar = Calendar.getInstance()
    private var tahun : Int = -1
    private var monthOfYear : Int = -1
    private var adapter : CalendarAdapter = CalendarAdapter(calendarList)
    private lateinit var  helperInOut : inOutDatabase
    lateinit var mAdapter : ListItemAdapter
    lateinit var rv_list : RecyclerView
    private lateinit var formatRupiah : NumberFormat
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(1)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        window.setStatusBarColor(Color.TRANSPARENT)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        supportActionBar?.hide()

        helperInOut = inOutDatabase(this)
        val localeID = Locale("in", "ID")
        formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        rv_list = findViewById(R.id.rv_balanceList)
        updateList()
        loadCalendar()

        calendar_month.setOnClickListener {
            showMonthYearPicker()
        }
        calendar_year.setOnClickListener {
            showMonthYearPicker()
        }

        rv_calendar.layoutManager = GridLayoutManager(applicationContext, 7)
        rv_calendar.adapter = adapter


        cv_balance.setOnClickListener {
            addBalance()
        }
        tv_viewAll.setOnClickListener {
            startActivity(Intent(this, ShowAllActivity::class.java))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showMonthYearPicker() {
        val dialogFragment  = MonthYearPickerDialogFragment.getInstance(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR))
        dialogFragment.show(supportFragmentManager, null)

        dialogFragment.setOnDateSetListener { year, month ->
            tahun = year
            monthOfYear = month
            loadCalendar()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadCalendar() {

        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val formatted = current.format(formatter)

        //ubah val ke var
        val cells = ArrayList<CalendarModel>()         // inisialisasi variabel untuk setiap tanggal kalender
        if (tahun != -1 && monthOfYear != -1 ){     // pengecekan bila varuiabel tahun dan monthOfYear kosong (-1 hanya pengecoh)
            //ubah obyek kalender ke tahun dan bulan yang diterima
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.YEAR, tahun)
        } else {
            // set variabel tahun dan monthOfYear ke tahun dan bulan sekarang
            tahun = calendar.get(Calendar.YEAR)
            monthOfYear = calendar.get(Calendar.MONTH)
        }
        var sdf = SimpleDateFormat("MMMM,yyyy", Locale("in", "ID"))  // obyek untuk parse bulan dan tahun
        val dateToday = sdf.format(calendar.time).split(",") //format obyek calendar lalu split berdasarkan ,
        calendar_month.text = dateToday[0] //settext bulan ke textview month
        calendar_year.text = dateToday[1] //settext bulan ke textview year

        //calendarToday
        val calendarCompare : Calendar = Calendar.getInstance() //instansiasi obyek calendar pembanding

        calendarCompare.set(Calendar.MONTH, monthOfYear) //set bulan pada calendar pembanding ke monthOfYear
        calendarCompare.set(Calendar.YEAR, tahun) //set tahun pada calendar pembanding ke tahun


        // memnentukan kapan tanggal dimulai pada bulan
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1

        // pindah calendar ke awal minggu
        calendar.add(Calendar.DAY_OF_MONTH,-monthBeginningCell)

        //obyek untuk parse tanggal
        sdf = SimpleDateFormat("dd-MM-yyyy", Locale("in", "ID"))

        // isi tanggal
        while (cells.size < DAYS_COUNT) {
            if(sdf.format(calendar.time).equals(formatted)){
                cells.add(CalendarModel(
                    calendar.get(Calendar.DATE),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.YEAR),
                    calendarCompare,
                    "hijau"
                ))
            }else{
                cells.add(CalendarModel(
                    calendar.get(Calendar.DATE),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.YEAR),
                    calendarCompare,
                    null
                ))
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        calendarList.clear()
        calendarList.addAll(cells)
        adapter.notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addBalance() {
        var privateView : View = LayoutInflater.from(this).inflate(R.layout.layout_popup, null, false)
        val et_title : EditText = privateView.findViewById(R.id.et_inputTitle)
        val et_total : EditText = privateView.findViewById(R.id.et_inputTotal)
        val btn_insert :Button = privateView.findViewById(R.id.btn_insert)
        val rb_in : RadioButton = privateView.findViewById(R.id.rb_status_in)
        val rb_out : RadioButton = privateView.findViewById(R.id.rb_status_out)
        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy")
        val formatted = current.format(formatter)


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
                value.put("date", formatted)
                value.put("total_balance", Integer.parseInt(et_total.text.toString()))
                Log.d(Companion.TAG, "addBalance: " + value.toString())
                Toast.makeText(this, "data = " +value.toString(), Toast.LENGTH_SHORT).show()
                helperInOut.insertData(value)
            }
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
        tv_balance.setText(formatRupiah.format(helperInOut.getBalance()))
        tv_outBalance.setText("- "+formatRupiah.format(helperInOut.getOutBalance()))
        mAdapter = com.quick.myfin.Adapter.ListItemAdapter(helperInOut.select())
        mAdapter.notifyDataSetChanged()
        rv_list.setHasFixedSize(true)
        rv_list.layoutManager = LinearLayoutManager(this)
        rv_list.adapter = mAdapter
    }

    companion object {
        private const val TAG = "HomeActivity"
    }
}