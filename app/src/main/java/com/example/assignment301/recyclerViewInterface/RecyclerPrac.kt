package com.example.assignment301.recyclerViewInterface

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.assignment301.R
import com.example.assignment301.baseAdapterPrac.Person
import com.example.assignment301.databinding.ActivityRecyclerPracBinding

typealias Operation = (String, String) -> Unit

class RecyclerPrac : AppCompatActivity(), Handler {
    val list = arrayListOf<Person>()
    lateinit var recyclerAdapter: MyRecyclerAdapter
    lateinit var binding: ActivityRecyclerPracBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRecyclerPracBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerAdapter = MyRecyclerAdapter(list, this)
        binding.recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerview.adapter = recyclerAdapter


        binding.fabAdd.setOnClickListener {
            addItem()
        }


    }

    override fun delete(position: Int) {
        list.removeAt(position)
        recyclerAdapter.notifyDataSetChanged()
    }

    override fun update(position: Int) {
        openNameAgeDialog(this, "Update") { name, age ->

            if (name.length > 0 && age.length > 0) {
                list[position].name = name
                list[position].age = age
                recyclerAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Enter Valid data", Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun addItem() {
        openNameAgeDialog(this, "Add Item") { name, age ->
            if (name.length > 0 && age.length > 0) {
                list.add(Person(name, age))
                recyclerAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Enter Valid data", Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun openNameAgeDialog(
        context: Context, action: String, operation: Operation
    ) {

        val dialog = Dialog(context)
        dialog.setContentView(R.layout.value_dialog)


        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )

        val cancelBtn = dialog.findViewById<Button>(R.id.btnDialogCancel)
        val okayBtn = dialog.findViewById<Button>(R.id.btnDialogOk)
        val nameEdt = dialog.findViewById<EditText>(R.id.edtDialogName)
        val ageEdt = dialog.findViewById<EditText>(R.id.edtDialogAge)
        val actionTv = dialog.findViewById<TextView>(R.id.tvActionHeader)



        actionTv.text = action


        okayBtn.setOnClickListener {
            val name = nameEdt.text.toString()
            val age = ageEdt.text.toString()
            operation(name, age)
            dialog.dismiss()
        }

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

}
