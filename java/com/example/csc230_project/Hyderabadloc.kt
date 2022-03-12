package com.example.csc230_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Hyderabadloc : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hyderabadloc)
        done5()
    }

    fun done5() {

        val backbut1 = findViewById<Button>(R.id.button18)
        backbut1.setOnClickListener{
            val Intent = Intent(this, Gujaratloc::class.java)
            startActivity(Intent)
        }

        val nextbut1 = findViewById<Button>(R.id.button19)
        nextbut1.setOnClickListener{
            val Intent = Intent(this, Jaipurloc::class.java)
            startActivity(Intent)
        }

    }
}