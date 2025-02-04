package com.example.assignment301

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.assignment301.databinding.ActivityMainBinding

import com.example.assignment301.baseAdapterPrac.ListView
import com.example.assignment301.recyclerViewInterface.MyRecyclerAdapter
import com.example.assignment301.recyclerViewInterface.RecyclerPrac

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.ListViewCrud.setOnClickListener{
            val indent = Intent(this@MainActivity, ListView::class.java)
            startActivity(indent)
        }
        binding.RecyclerViewCrud.setOnClickListener{
            val indent = Intent(this@MainActivity, RecyclerPrac::class.java)
            startActivity(indent)
        }

        binding.btnPractice.setOnClickListener{
            val indent = Intent(this@MainActivity, Practice::class.java)
            startActivity(indent)
        }

        binding.btnRoomDBPrac.setOnClickListener{
            val indent = Intent(this@MainActivity, Practice::class.java)
            startActivity(indent)
        }


    }
}