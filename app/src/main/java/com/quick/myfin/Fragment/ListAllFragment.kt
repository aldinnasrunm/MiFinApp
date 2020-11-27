package com.quick.myfin.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quick.myfin.Adapter.ListItemAdapter
import com.quick.myfin.Models.inOutDatabase
import com.quick.myfin.R


class ListAllFragment : Fragment() {

    lateinit var rv_listAll : RecyclerView
    lateinit var mAdapter : ListItemAdapter
    lateinit var helperInOut : inOutDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v : View =  inflater.inflate(R.layout.fragment_list_all, container, false)
        rv_listAll = v.findViewById(R.id.rv_listAll)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        helperInOut = inOutDatabase(context)
        mAdapter = ListItemAdapter(helperInOut.select())
        rv_listAll.setHasFixedSize(true)
        rv_listAll.layoutManager = LinearLayoutManager(context)
        rv_listAll.adapter = mAdapter

    }
}