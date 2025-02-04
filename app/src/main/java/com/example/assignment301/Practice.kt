package com.example.assignment301

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.assignment301.databinding.ActivityPracticeBinding

data class Person(
    var name: String,
    var gender: String, // m-male f-female o-other
) {
    constructor() : this("", "")

    override fun toString(): String {
        return "$name"
    }
}

class Practice : AppCompatActivity() {
    lateinit var binding: ActivityPracticeBinding
    val list = arrayListOf<Person>()
    lateinit var adapter: ArrayAdapter<Person>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityPracticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list )
        binding.namesList.adapter = adapter
        binding.spinnerNames.adapter = adapter

        binding.btnGo.setOnClickListener{

            val gender = findViewById<RadioButton>(binding.radiogroup.checkedRadioButtonId).text.toString()
            val name = binding.edtName.text.toString()

            if(name.length > 0){
                val titleName =  when(gender) {
                    "M" -> "Mr "
                    "F" -> "Ms "
                    else -> ""
                } + name
                list.add(Person(titleName, gender))
                adapter.notifyDataSetChanged()
            }else{
                Toast.makeText(this, "Enter valid details", Toast.LENGTH_SHORT).show()
            }

            binding.edtName.setText("")

        }

        binding.btnShowHide.setOnClickListener {
            if(binding.namesList.visibility == View.GONE){
                binding.namesList.visibility = View.VISIBLE
                binding.btnShowHide.text = "Hide"
            }else{
                binding.namesList.visibility = View.GONE
                binding.btnShowHide.text = "Show"
            }
        }

    }
}