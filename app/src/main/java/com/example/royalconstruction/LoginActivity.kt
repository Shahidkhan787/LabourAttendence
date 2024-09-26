package com.example.royalconstruction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.royalconstruction.databinding.LoginActivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private  lateinit var binding: LoginActivityBinding

    override fun onStart() {
        super.onStart()
        // check if user is already logged in
        val currentUser:FirebaseUser? = auth.currentUser
        if(currentUser !=null){
            startActivity(Intent(this,MainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initialize firebase auth
        auth = FirebaseAuth.getInstance()



        binding.login.setOnClickListener{
            val loginemail:String = binding.loginemail.text.toString()
            val loginpassword:String = binding.loginpassword.text.toString()

            if(loginemail.isEmpty() || loginpassword.isEmpty()){
                Toast.makeText(this,"Please all the details",Toast.LENGTH_SHORT).show()
            }else {
                auth.signInWithEmailAndPassword(loginemail,loginpassword)
                    .addOnCompleteListener{ task ->
                        if(task.isSuccessful){
                            val user = auth.currentUser
                            Toast.makeText(this,"login Succesful",Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this,MainActivity::class.java))
                            finish()
                        }
                        else{
                            Toast.makeText(this,"Login Failed: ${task.exception?.message}",Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        binding.Registration.setOnClickListener{
         startActivity(Intent(this,RegistrationActivity::class.java))
        }
        }

    }
