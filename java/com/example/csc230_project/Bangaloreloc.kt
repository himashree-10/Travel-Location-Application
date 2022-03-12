package com.example.csc230_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Bangaloreloc : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bangaloreloc)
        done1()
    }

    fun done1() {

        val backbut1 = findViewById<Button>(R.id.button8)
        backbut1.setOnClickListener{
            val Intent = Intent(this, HomePageActivity::class.java)
            startActivity(Intent)
        }

        val nextbut1 = findViewById<Button>(R.id.button9)
        nextbut1.setOnClickListener{
            val Intent = Intent(this, Delhiloc::class.java)
            startActivity(Intent)
        }

    }
}