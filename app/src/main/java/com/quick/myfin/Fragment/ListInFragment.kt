package com.quick.myfin.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
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

class ListInFragment : Fragment() {
    lateinit var rv_listIn: RecyclerView
    lateinit var mAdapter: ListItemAdapter
    lateinit var helperInOut: inOutDatabase
    lateinit var tv_totalIn : TextView
    lateinit var tv_sisaIn : TextView
    private lateinit var formatRupiah : NumberFormat

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v: View = inflater.inflate(R.layout.fragment_list_in, container, false)
        rv_listIn = v.findViewById(R.id.rv_listIn)
        tv_sisaIn = v.findViewById(R.id.tv_sisaIn)
        tv_totalIn = v.findViewById(R.id.tv_totalIn)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        helperInOut = inOutDatabase(context)
        val localeID = Locale("in", "ID")
        formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        mAdapter = ListItemAdapter(helperInOut.selectIn())
        rv_listIn.setHasFixedSize(true)
        rv_listIn.layoutManager = LinearLayoutManager(context)
        rv_listIn.adapter = mAdapter

        updateTotalCard()
    }

    private fun updateTotalCard() {
        tv_totalIn.setText(formatRupiah.format(helperInOut.getTotal()))
        tv_sisaIn.setText(formatRupiah.format(helperInOut.getBalance()))
    }

    companion object {

    }
}