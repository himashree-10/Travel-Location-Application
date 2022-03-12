package com.example.csc230_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Punjabloc : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_punjabloc)
        done9()
    }

    fun done9() {

        val backbut1 = findViewById<Button>(R.id.button8)
        backbut1.setOnClickListener{
            val Intent = Intent(this, Mumbailoc::class.java)
            startActivity(Intent)
        }

        val nextbut1 = findViewById<Button>(R.id.button9)
        nextbut1.setOnClickListener{
            val Intent = Intent(this, TamilNaduloc::class.java)
            startActivity(Intent)
        }

    }
}