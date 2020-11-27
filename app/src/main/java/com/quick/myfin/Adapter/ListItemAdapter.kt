package com.quick.myfin.Adapter

import android.annotation.SuppressLint
import android.database.Cursor
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.quick.myfin.R
import java.text.NumberFormat
import java.util.*


class ListItemAdapter (c: Cursor?): RecyclerView.Adapter<ListItemAdapter.ViewHolder>() {
    private val cursor: Cursor
    private lateinit var formatRupiah : NumberFormat
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(com.quick.myfin.R.layout.layout_list_item, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        cursor.moveToPosition(position)
        val localeID = Locale("in", "ID")
        formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        val textTitle: String =
            cursor.getString(cursor.getColumnIndex("title_balance"))
        val textTotal : Int =
            cursor.getInt(cursor.getColumnIndex("total_balance"))
        val textItemDate : String =
            cursor.getString(cursor.getColumnIndex("date"))
        val textStatus : String =
            cursor.getString(cursor.getColumnIndex("status_balance"))
        val part: Array<String> = textItemDate.split("-".toRegex()).toTypedArray()
        val statusSign : String
        if (textStatus.equals("in")){
            statusSign = "+"
        }else{
            statusSign = "-"
            holder.root.setBackgroundColor(Color.parseColor("#294870"))
        }
        holder.title.text = textTitle
        holder.total.text = statusSign +" "+ formatRupiah.format(textTotal)
        holder.date.text = part[0]
        holder.month.text = part[1]
    }


    override fun getItemCount(): Int {
        return cursor.getCount()
    }

    inner class ViewHolder(v : View) : RecyclerView.ViewHolder(v) {
        var title : TextView
        var total :TextView
        var date : TextView
        var month : TextView
        var root : ConstraintLayout
        init {
            month = v.findViewById(R.id.tv_dateMonth)
            date = v.findViewById(R.id.tv_date)
            title = v.findViewById(R.id.tv_displayTitle)
            total = v.findViewById(R.id.tv_displaTotal)
            root = v.findViewById(R.id.cl_root)
        }
    }

    init {
        cursor = c!!
    }
}
