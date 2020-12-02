package com.quick.myfin.Adapter

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.quick.myfin.Activity.SaveCashActivity
import com.quick.myfin.R
import java.text.NumberFormat
import java.util.*

class SaveCashAdapter(
    c: Cursor?,
    mContext: Context?,
    saveCashActivity: SaveCashActivity
): RecyclerView.Adapter<SaveCashAdapter.ViewHolder>() {
    val context = mContext
    val mParent = saveCashActivity
    private val cursor: Cursor
    private lateinit var formatRupiah: NumberFormat

    inner class ViewHolder(v : View) : RecyclerView.ViewHolder(v) {
        val nTitle:TextView = v.findViewById(R.id.tv_titleSaveCash)
        val nTotal:TextView = v.findViewById(R.id.tv_totalSaveCash)
        val nDate : TextView = v.findViewById(R.id.tv_dateSave)
        val nMonth : TextView = v.findViewById(R.id.tv_mothSaveCash)
        val nRoot : ConstraintLayout = v.findViewById(R.id.cl_rootSaveCash)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(com.quick.myfin.R.layout.layout_save_cash_list, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return cursor.getCount()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        cursor.moveToPosition(position)
        val localeID = Locale("in", "ID")
        formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        val itemId:Int =
            cursor.getInt(cursor.getColumnIndex("_id"))
        val textTitle: String =
            cursor.getString(cursor.getColumnIndex("title_save_cash"))
        val textTotal: Int =
            cursor.getInt(cursor.getColumnIndex("total_save_cash"))
        val textItemDate: String =
            cursor.getString(cursor.getColumnIndex("date"))
        val part: Array<String> = textItemDate.split("-".toRegex()).toTypedArray()

        holder.nDate.setText(part[0])
        holder.nMonth.setText(part[1])
        holder.nTitle.setText(textTitle)
        holder.nTotal.setText(formatRupiah.format(textTotal))
        holder.nRoot.setOnClickListener{
            mParent.deleteItem(itemId)
        }
    }

    init {
        cursor = c!!
    }
}