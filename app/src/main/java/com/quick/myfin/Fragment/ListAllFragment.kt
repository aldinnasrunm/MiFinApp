package com.quick.myfin.Fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.quick.myfin.Adapter.ListItemAllAdapter
import com.quick.myfin.Models.inOutDatabase
import com.quick.myfin.R


class ListAllFragment : Fragment() {

    lateinit var rv_listAll : RecyclerView
    lateinit var mAdapter : ListItemAllAdapter
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
        updateList()

    }

    private fun updateList() {
        mAdapter = ListItemAllAdapter(helperInOut.select(), context, this)
        mAdapter.notifyDataSetChanged()
        rv_listAll.setHasFixedSize(true)
        rv_listAll.layoutManager = LinearLayoutManager(context)
        rv_listAll.adapter = mAdapter
        rv_listAll.getAdapter()?.notifyDataSetChanged()
    }

    fun deleteItem(pos : Int){
        val deleteView : View = LayoutInflater.from(context).inflate(R.layout.layout_delete_item, null, false)
        val deleteAnimation : LottieAnimationView = deleteView.findViewById(R.id.deleteAnimation)
        deleteAnimation.playAnimation()
        AlertDialog.Builder(context, R.style.CustomAlertDialog)
            .setView(deleteView)
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                helperInOut.deleteItem(pos)
                updateList()
            })
            .setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
            .setCancelable(false)
            .show()
    }
}


