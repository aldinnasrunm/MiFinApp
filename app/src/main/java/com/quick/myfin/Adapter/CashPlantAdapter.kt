package com.quick.myfin.Adapter

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.database.getIntOrNull
import androidx.recyclerview.widget.RecyclerView
import com.quick.myfin.Activity.CashPlanActivity
import com.quick.myfin.R
import java.text.NumberFormat
import java.util.*

class CashPlantAdapter(
    c: Cursor?,
    mContext: Context?,
    cashPlanActivity: CashPlanActivity
): RecyclerView.Adapter<CashPlantAdapter.ViewHolder>() {
    val context = mContext
    val mParent = cashPlanActivity
    private val cursor: Cursor
    private lateinit var formatRupiah: NumberFormat

    inner class ViewHolder(v : View) : RecyclerView.ViewHolder(v) {
        val nTitle: TextView = v.findViewById(R.id.tv_titleCashPlan)
        val nTotal: TextView = v.findViewById(R.id.tv_taksiran)
        val nRoot : ConstraintLayout = v.findViewById(R.id.cl_cashPlanRoot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(com.quick.myfin.R.layout.layout_cash_plan, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val localeID = Locale("in", "ID")
        formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        cursor.moveToPosition(position)
        val itemId: Int =
            cursor.getInt(cursor.getColumnIndex("_id"))
        val textTitle: String =
            cursor.getString(cursor.getColumnIndex("title_cash_plan"))
        val textTotal: Int =
            cursor.getInt(cursor.getColumnIndex("total_cash_plan"))

        holder.nTitle.text = textTitle
        holder.nTotal.text = formatRupiah.format(textTotal)
        holder.nRoot.setOnClickListener {
            mParent.deleteItem(itemId)
        }
    }

    override fun getItemCount(): Int {
        return cursor.getCount()
    }



    init {
        cursor = c!!
    }
}