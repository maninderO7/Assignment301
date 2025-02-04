package com.example.assignment301.recyclerViewInterface

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Looper
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
import com.example.assignment301.R
import com.example.assignment301.databinding.ActivityRecyclerPracBinding
import com.example.assignment301.roomdb.MyDatabase
import com.example.assignment301.roomdb.PersonTable
import com.example.assignment301.roomdb.PersonTableInterface
import java.util.concurrent.Executors

typealias Operation = (String, String) -> Unit
typealias dataSyncOperation = () -> Unit

class RecyclerPrac : AppCompatActivity(), Handler {
    val list = arrayListOf<PersonTable>()
    lateinit var recyclerAdapter: MyRecyclerAdapter
    lateinit var binding: ActivityRecyclerPracBinding
    lateinit var persontableinterface: PersonTableInterface

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

        persontableinterface = MyDatabase.getPersonTableInstance(this)

        recyclerAdapter = MyRecyclerAdapter(list, this)
        binding.recyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerview.adapter = recyclerAdapter


        binding.fabAdd.setOnClickListener {
            addItem()
        }

        dataSyncExecutor{
            list.addAll(persontableinterface.getAll())
        }

    }


    fun dataSyncExecutor(operation: dataSyncOperation) {
        val executor = Executors.newSingleThreadScheduledExecutor()
        val handler = android.os.Handler(Looper.getMainLooper())

        executor.execute {
                operation()
            handler.post {
                recyclerAdapter.notifyDataSetChanged()
            }

        }


    }

    override fun delete(position: Int) {
        val item = list[position]
        dataSyncExecutor {
            persontableinterface.deleteById(item.id)
        }

        list.removeAt(position)
        recyclerAdapter.notifyDataSetChanged()
    }

    override fun update(position: Int) {
        openNameAgeDialog(this, "Update") { name, age ->

            if (name.length > 0 && age.length > 0) {

                dataSyncExecutor {
                    persontableinterface.setById(list[position].id, name, age)
                }

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
                var id = -1

                dataSyncExecutor{
                    id = persontableinterface.addPerson(PersonTable(name, age)) as Int
                }

                list.add(PersonTable(id, name, age))
                recyclerAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Enter Valid data", Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun openNameAgeDialog(
        context: Context, action: String, operation: Operation,
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
