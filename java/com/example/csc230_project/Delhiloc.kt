package com.example.csc230_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Delhiloc : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delhiloc)
        done2()
    }

    fun done2() {

        val backbut1 = findViewById<Button>(R.id.button12)
        backbut1.setOnClickListener{
            val Intent = Intent(this, Bangaloreloc::class.java)
            startActivity(Intent)
        }

        val nextbut1 = findViewById<Button>(R.id.button13)
        nextbut1.setOnClickListener{
            val Intent = Intent(this, Goaloc::class.java)
            startActivity(Intent)
        }

    }
}