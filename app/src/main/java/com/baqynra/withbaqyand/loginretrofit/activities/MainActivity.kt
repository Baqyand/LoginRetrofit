package com.baqynra.withbaqyand.loginretrofit.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.baqynra.withbaqyand.loginretrofit.R
import com.baqynra.withbaqyand.loginretrofit.api.RetrofitClient
import com.baqynra.withbaqyand.loginretrofit.models.DefaultResponse
import com.baqynra.withbaqyand.loginretrofit.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewLogin.setOnClickListener {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }

        buttonSignUp.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            val name = editTextName.text.toString().trim()
            val school = editTextSchool.text.toString().trim()

            if (email.isEmpty()){
                editTextEmail.error = "Email is required"
                editTextEmail.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()){
                editTextPassword.error = "Password is required"
                editTextPassword.requestFocus()
                return@setOnClickListener
            }
            if (name.isEmpty()){
                editTextName.error = "Name is required"
                editTextName.requestFocus()
                return@setOnClickListener
            }
            if (school.isEmpty()){
                editTextSchool.error = "School is required"
                editTextSchool.requestFocus()
                return@setOnClickListener
            }
            RetrofitClient.instance.createUser(email, name, password, school)
                .enqueue(object: Callback<DefaultResponse>{
                    override fun onResponse(
                        call: Call<DefaultResponse>,
                        response: Response<DefaultResponse>
                    ) {
                        Toast.makeText(applicationContext, response.body()?.massage, Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                    }

                })
        }
    }
//    override fun onStart() {
//        super.onStart()
//
//        if(!SharedPrefManager.getInstance(this).isLoggedIn){
//            val intent = Intent(applicationContext, LoginActivity ::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_CLEAR_TASK
//
//            startActivity(intent)
//        }
//    }
}