package com.example.csc230_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        signup()
    }

    fun signup(){
        val nsignup = findViewById<Button>(R.id.button)
        val nclear = findViewById<Button>(R.id.button3)
        val et_register_email = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val et_register_password = findViewById<EditText>(R.id.editTextTextPassword)
        val username = findViewById<EditText>(R.id.editTextTextPersonName)
        val tv_login = findViewById<Button>(R.id.button4)

        tv_login.setOnClickListener{

            onBackPressed()
        }

        nsignup.setOnClickListener{
            when {
                TextUtils.isEmpty(et_register_email.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@SignUpActivity,
                        "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(et_register_password.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@SignUpActivity,
                        "Please enter password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {

                    val email: String = et_register_email.text.toString().trim { it <= ' ' }
                    val password: String = et_register_password.text.toString().trim { it <= ' ' }

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener { task ->

                            if (task.isSuccessful) {

                                val firebaseUser: FirebaseUser = task.result!!.user!!

                                Toast.makeText(
                                    this@SignUpActivity,
                                    "${username.text} successfully registered.",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", firebaseUser.uid)
                                intent.putExtra("email_id", email)
                                startActivity(intent)
                                finish()

                            } else {

                                Toast.makeText(
                                    this@SignUpActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                }
            }
        }

        nclear.setOnClickListener{
            et_register_email.setText("")
            et_register_password.setText("")
            username.setText("")
        }

    }
}