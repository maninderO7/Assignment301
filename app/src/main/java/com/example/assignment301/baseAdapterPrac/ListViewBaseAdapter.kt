package com.example.assignment301.baseAdapterPrac

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.assignment301.R

class ListViewBaseAdapter(
    var list: ArrayList<Person>

): BaseAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(index: Int): Any {
        return list[index]
    }

    override fun getItemId(index: Int): Long {

        return list[index].hashCode().toLong()
    }

    override fun getView(index: Int, counterView: View?, parent: ViewGroup?): View {
//        val view = LayoutInflater.from(parent?.context)
//            .inflate(R.layout.adapter_list_item, parent, false)

        val view = if(counterView == null){
            LayoutInflater.from(parent?.context).inflate(R.layout.adapter_list_item, parent, false)
        }else{
            counterView
        }

        val name = view.findViewById<TextView>(R.id.tvName)
        val age = view.findViewById<TextView>(R.id.tvAge)

        name.text = list[index].name.toString()
        age.text = list[index].age.toString()

        return view
    }
}