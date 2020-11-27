package com.quick.myfin.Adapter

import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.quick.myfin.R
import java.text.NumberFormat
import java.util.*


class ListAdapter (c: Cursor?): RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    private val cursor: Cursor
    private lateinit var formatRupiah : NumberFormat
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(com.quick.myfin.R.layout.layout_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        cursor.moveToPosition(position)
        val localeID = Locale("in", "ID")
        formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        val textTitle: String =
            cursor.getString(cursor.getColumnIndex("title_balance"))
        val textTotal : Int =
            cursor.getInt(cursor.getColumnIndex("total_balance"))
        holder.title.text = textTitle
        holder.total.text = formatRupiah.format(textTotal)
    }


    override fun getItemCount(): Int {
        return cursor.getCount()
    }

    inner class ViewHolder(v : View) : RecyclerView.ViewHolder(v) {
        var title : TextView
        var total :TextView
        init {
            title = v.findViewById(R.id.tv_displayTitle)
            total = v.findViewById(R.id.tv_displaTotal)
        }
    }

    init {
        cursor = c!!
    }
}
