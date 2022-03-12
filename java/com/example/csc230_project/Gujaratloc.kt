package com.example.csc230_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Gujaratloc : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gujaratloc)
        done4()
    }

    fun done4() {

        val backbut1 = findViewById<Button>(R.id.button20)
        backbut1.setOnClickListener{
            val Intent = Intent(this, Goaloc::class.java)
            startActivity(Intent)
        }

        val nextbut1 = findViewById<Button>(R.id.button22)
        nextbut1.setOnClickListener{
            val Intent = Intent(this, Hyderabadloc::class.java)
            startActivity(Intent)
        }

    }
}