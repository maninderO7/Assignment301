package com.example.assignment301.recyclerViewInterface

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment301.R
import com.example.assignment301.roomdb.PersonTable


interface Handler {
    fun delete(position: Int)
    fun update(position: Int)
}


class MyRecyclerAdapter(
    var list: ArrayList<PersonTable>,
    var handler: Handler
) : RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameView = itemView.findViewById<TextView>(R.id.recycName)
        var ageView = itemView.findViewById<TextView>(R.id.recycAge)
        var updateBtnView = itemView.findViewById<Button>(R.id.btnrecycUpdate)
        var deleteBtnView = itemView.findViewById<Button>(R.id.btnrecycDelete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {

            nameView.text = list[position].name
            ageView.text = list[position].age

            updateBtnView.setOnClickListener{
                handler.update(position)
            }
            deleteBtnView.setOnClickListener{
                handler.delete(position)
            }

        }
    }

}