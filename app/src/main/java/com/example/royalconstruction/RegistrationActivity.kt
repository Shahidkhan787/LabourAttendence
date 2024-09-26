package com.example.royalconstruction

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.royalconstruction.databinding.ActivityRegistrationBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.database
import com.google.firebase.storage.storage
import com.squareup.picasso.Picasso


class RegistrationActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // initialize firebase auth
        auth = FirebaseAuth.getInstance()


        binding.registerlogin.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }


        // set image on firebase
        binding.ownerimage.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "image/*"
            imageLauncher.launch(intent)
        }
    }

    val imageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                if (it.data != null) {
                    val ref = Firebase.storage.reference.child("Photo/"+System.currentTimeMillis()+" ."+ getFileType(it.data!!.data))
                    ref.putFile(it.data!!.data!!).addOnSuccessListener {
                        ref.downloadUrl.addOnSuccessListener {

                            Firebase.database.reference.child("Photo").push().setValue(it.toString())
                            binding.ownerimage.setImageURI(it)
                            Toast.makeText(this@RegistrationActivity,"owner photo uploades",Toast.LENGTH_SHORT).show()
                            Picasso.get().load(it.toString()).into(binding.ownerimage);
                        }
                    }
                }





                binding.registration.setOnClickListener {

                    // get text from edit text fiels
                    val registerationname: String = binding.registrationname.text.toString()
                    val registrayionemail: String = binding.registrationemail.text.toString()
                    val registrationpassword: String = binding.registrationpassword.text.toString()
                    val repasswordregistration: String =
                        binding.repasswordregistration.text.toString()

                    // check if any field is is blank
                    if (registrayionemail.isEmpty() || registerationname.isEmpty() || registrationpassword.isEmpty() ||
                        repasswordregistration.isEmpty()
                    ) {
                        Toast.makeText(this, "please fill all the details", Toast.LENGTH_SHORT)
                            .show()
                    } else if (registrationpassword != repasswordregistration) {
                        Toast.makeText(this, "Re password must be same", Toast.LENGTH_SHORT).show()
                    } else {
                        auth.createUserWithEmailAndPassword(registrayionemail, registrationpassword)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    val user: FirebaseUser? = auth.currentUser
                                    user?.let {


                                        Toast.makeText(
                                            this,
                                            "Registration Successful",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                        startActivity(Intent(this, LoginActivity::class.java))
                                        finish()
                                    }
                                } else {
                                    Toast.makeText(
                                        this,
                                        "Registration failed : ${task.exception?.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    }

                }
            }
        }

    private fun getFileType(data: Uri?): String? {
        val r = contentResolver
        val mimeType = MimeTypeMap.getSingleton()
        return mimeType.getMimeTypeFromExtension(r.getType(data!!))
    }
}