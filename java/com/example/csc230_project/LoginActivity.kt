package com.example.csc230_project

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login1()
    }

    fun login1() {
        val tv_login = findViewById<Button>(R.id.button)
        val username = findViewById<EditText>(R.id.editTextTextPersonName)
        val newclear = findViewById<Button>(R.id.button2)
        val et_login_email = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val et_login_password = findViewById<EditText>(R.id.editTextTextPassword)
        val tv_register = findViewById<Button>(R.id.button5)

        tv_register.setOnClickListener{

            startActivity(Intent(this@LoginActivity,SignUpActivity::class.java))
        }

        tv_login.setOnClickListener{
            when {
                TextUtils.isEmpty(et_login_email.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(et_login_password.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {

                    val email: String = et_login_email.text.toString().trim { it <= ' ' }
                    val password: String = et_login_password.text.toString().trim { it <= ' ' }

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener { task ->

                            if (task.isSuccessful) {

                                Toast.makeText(
                                    this@LoginActivity,
                                    "${username.text} successfully logged in.",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent =
                                    Intent(this@LoginActivity, HomePageActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra(
                                    "user_id",
                                    FirebaseAuth.getInstance().currentUser!!.uid
                                )
                                intent.putExtra("email_id", email)
                                startActivity(intent)
                                finish()

                            } else {

                                Toast.makeText(
                                    this@LoginActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                }
            }
        }

        newclear.setOnClickListener {
            et_login_email.setText("")
            et_login_password.setText("")
            username.setText("")
        }

    }
}