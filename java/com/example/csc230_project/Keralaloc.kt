package com.example.csc230_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Keralaloc : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keralaloc)
        done7()
    }

    fun done7() {

        val backbut1 = findViewById<Button>(R.id.button8)
        backbut1.setOnClickListener{
            val Intent = Intent(this, Jaipurloc::class.java)
            startActivity(Intent)
        }

        val nextbut1 = findViewById<Button>(R.id.button9)
        nextbut1.setOnClickListener{
            val Intent = Intent(this, Mumbailoc::class.java)
            startActivity(Intent)
        }

    }
}