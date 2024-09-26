package com.example.royalconstruction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.royalconstruction.databinding.ActivityProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class ProfileActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding:ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        binding.oursite.setOnClickListener{
//            startActivity(Intent(this,AdddetailsActivity::class.java))
//        }

        // log out
        binding.logout.setOnClickListener{
            Firebase.auth.signOut()
            Toast.makeText(this,"log out succesfull", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }


        binding.addnewlabour.setOnClickListener{
            startActivity(Intent(this,AddNewLabour::class.java))
            finish()
        }
    }
}

