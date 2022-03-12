package com.example.csc230_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class HomePageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        page()

    }

    fun page() {

        // get reference to the string array that we just created
        val places = resources.getStringArray(R.array.Best_Tourist_Places)
        // create an array adapter and pass the required parameter
        // in our case pass the context, drop down layout , and array.
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, places)
        // get reference to the autocomplete text view
        val autocompleteTV = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        // set adapter to the autocomplete tv to the arrayAdapter
        autocompleteTV.setAdapter(arrayAdapter)

        val button = findViewById<Button>(R.id.okay)

        val currLoc = findViewById<Button>(R.id.button10)
        currLoc.setOnClickListener{
            val Intent = Intent(this, NameActivity::class.java)
            startActivity(Intent)
        }


        val currAdd = findViewById<Button>(R.id.button11)
        currAdd.setOnClickListener{
            val Intent = Intent(this, RouteActivity::class.java)
            startActivity(Intent)
        }


        if(button != null) {
            button.setOnClickListener {

                val enteredText: String = autocompleteTV.getText().toString()

                if (enteredText.equals("Bangalore")) {

                    val Intent = Intent(this, Bangaloreloc::class.java)
                    startActivity(Intent)

                } else if (enteredText.equals("Delhi")) {
                    val Intent = Intent(this, Delhiloc::class.java)
                    startActivity(Intent)
                } else if (enteredText.equals("Goa")) {
                    val Intent = Intent(this, Goaloc::class.java)
                    startActivity(Intent)
                } else if (enteredText.equals("Gujarat")) {
                    val Intent = Intent(this, Gujaratloc::class.java)
                    startActivity(Intent)
                } else if (enteredText.equals("Hyderabad")) {
                    val Intent = Intent(this, Hyderabadloc::class.java)
                    startActivity(Intent)
                } else if (enteredText.equals("Jaipur")) {
                    val Intent = Intent(this, Jaipurloc::class.java)
                    startActivity(Intent)
                } else if (enteredText.equals("Kerala")) {
                    val Intent = Intent(this, Keralaloc::class.java)
                    startActivity(Intent)
                } else if (enteredText.equals("Mumbai")) {
                    val Intent = Intent(this, Mumbailoc::class.java)
                    startActivity(Intent)
                } else if (enteredText.equals("Punjab")) {
                    val Intent = Intent(this, Punjabloc::class.java)
                    startActivity(Intent)
                } else if (enteredText.equals("TamilNadu")){
                    val Intent = Intent(this, TamilNaduloc::class.java)
                    startActivity(Intent)
                } else{
                    Toast.makeText(this,"Please choose a State to proceed",Toast.LENGTH_SHORT).show()
                }


            }
        }



    }
}