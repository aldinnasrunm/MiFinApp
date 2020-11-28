package com.quick.myfin.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quick.myfin.Adapter.ListItemAdapter
import com.quick.myfin.Models.inOutDatabase
import com.quick.myfin.R
import java.text.NumberFormat
import java.util.*


class ListOutFragment : Fragment() {

    lateinit var rv_listOut : RecyclerView
    lateinit var tv_totalOut : TextView
    lateinit var mAdapter: ListItemAdapter
    lateinit var helperInOut: inOutDatabase
    private lateinit var formatRupiah : NumberFormat

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v : View = inflater.inflate(R.layout.fragment_list_out, container, false)
        rv_listOut = v.findViewById(R.id.rv_listOut)
        tv_totalOut = v.findViewById(R.id.tv_totalOut)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        helperInOut = inOutDatabase(context)
        val localeID = Locale("in", "ID")
        formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        mAdapter = ListItemAdapter(helperInOut.selectOut())
        rv_listOut.setHasFixedSize(true)
        rv_listOut.layoutManager = LinearLayoutManager(context)
        rv_listOut.adapter = mAdapter

        updateTotalOutCard()
    }

    private fun updateTotalOutCard() {
        tv_totalOut.setText(formatRupiah.format(helperInOut.getOutBalance()))
    }
}