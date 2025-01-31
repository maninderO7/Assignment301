package com.example.assignment301.BaseAdapter

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.assignment301.R
import com.example.assignment301.databinding.ActivityListViewBinding

typealias Operation = (Int, String, String) -> Unit

class ListView : AppCompatActivity() {
    private lateinit var adapter: ListViewBaseAdapter
    private val list = arrayListOf<Person>()
    private lateinit var binding: ActivityListViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityListViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        list.add(Person("Mani", "23"))
        list.add(Person("Maninder", "21"))


        adapter = ListViewBaseAdapter(list)

        binding.listview.adapter = this.adapter



        binding.listview.setOnItemLongClickListener{parent, view, index, id ->

            Toast.makeText(this,"$index", Toast.LENGTH_LONG ).show()
            val ops = arrayOf("Delete", "Update")

            AlertDialog.Builder(this@ListView).apply {
                setTitle("Select Action")

                setSingleChoiceItems(ops, -1, { dialog, which ->

                    if(which == 0){
                        list.removeAt(index)
                        adapter.notifyDataSetChanged()
                    }else if(which == 1){
                        openDialog(this@ListView,
                            "Update",
                            hidePositionEdt = true,
                            { _, name, age ->
                            list[index].name = name
                                list[index].age = age
                            adapter.notifyDataSetChanged()
                        })
                    }

                    dialog.dismiss()
                })

                show()

            }


            return@setOnItemLongClickListener true
        }


        binding.fabAdd.setOnClickListener {

            openDialog(
                this,
                "Add New Person",
                true
            ){ _, name, age ->
                run {
                    if(name.length > 0 && age.length > 0){
                        list.add( Person(name, age))
                    }else{
                        Toast.makeText(this@ListView, "failed : Insufficient Data", Toast.LENGTH_SHORT).show()
                    }

                }

            }

        }


    }


    private fun openDialog(context: Context,
                           action: String,
                           hidePositionEdt: Boolean = false,
                           operation: Operation){

        val dialog = Dialog(context)
        dialog.setContentView(R.layout.value_dialog)


        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )

        val cancelBtn = dialog.findViewById<Button>(R.id.btnDialogCancel)
        val okayBtn = dialog.findViewById<Button>(R.id.btnDialogOk)
        val positionEdt = dialog.findViewById<EditText>(R.id.edtDialogPosition)
        val nameEdt = dialog.findViewById<EditText>(R.id.edtDialogName)
        val ageEdt = dialog.findViewById<EditText>(R.id.edtDialogAge)
        val actionTv = dialog.findViewById<TextView>(R.id.tvActionHeader)

        if(hidePositionEdt){
            positionEdt.visibility = View.GONE
        }



        actionTv.text = action


        okayBtn.setOnClickListener{
            val name = nameEdt.text.toString()
            val age = ageEdt.text.toString()
            val position = positionEdt.text.toString().toIntOrNull() ?: 0
            operation(position, name, age)
            dialog.dismiss()
        }

        cancelBtn.setOnClickListener{
            dialog.dismiss()
        }

        dialog.show()
    }

}