package com.example.royalconstruction

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

import com.example.royalconstruction.databinding.ActivityAddNewLabourBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.google.firebase.storage.storage
import com.squareup.picasso.Picasso


class AddNewLabour : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityAddNewLabourBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddNewLabourBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        // set image on firebase
        binding.labourimage.setOnClickListener {
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
                    val ref = Firebase.storage.reference.child(
                        "Photo/" + System.currentTimeMillis() + " ." + getFileType(it.data!!.data)
                    )
                    ref.putFile(it.data!!.data!!).addOnSuccessListener {
                        ref.downloadUrl.addOnSuccessListener {

                            Firebase.database.reference.child("Photo").push()
                                .setValue(it.toString())
                            binding.labourimage.setImageURI(it)
                            Toast.makeText(
                                this@AddNewLabour,
                                "labour photo uploades",
                                Toast.LENGTH_SHORT
                            ).show()
                            Picasso.get().load(it.toString()).into(binding.labourimage);
                        }
                    }
                }



                binding.added.setOnClickListener {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }

                // iniitialize firebase
                databaseReference = FirebaseDatabase.getInstance().reference
                auth = FirebaseAuth.getInstance()
                binding.added.setOnClickListener {

                    // get text from  edit text
                    val labourname: String = binding.labourname.text.toString()
                    val labourrate: String = binding.labourrate.text.toString()
                    val labourtype: String = binding.labourtype.text.toString()

                    if (labourname.isEmpty() && labourrate.isEmpty() && labourtype.isEmpty()) {
                        Toast.makeText(this, " fill all the fields", Toast.LENGTH_SHORT).show()
                    } else {
                        val currentUser: FirebaseUser? = auth.currentUser
                        currentUser?.let { user ->
                            // genereata a unique key
                            val noteKey: String? =
                                databaseReference.child("labour information").child(user.uid)
                                    .child("labour name").push().key

                            val labouritem = LabourItem(labourname, labourrate, labourtype)
                            if (noteKey != null) {
                                databaseReference.child("labour information").child(user.uid)
                                    .child("labour name")
                                    .child(noteKey).setValue(labouritem)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(
                                                this,
                                                "labour add succesful",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            startActivity(Intent(this, MainActivity::class.java))
                                            finish()
                                        } else {
                                            Toast.makeText(
                                                this,
                                                "failed to add labour",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }


//
//                            // initialize firebaase database reference
//                            databaseReference = FirebaseDatabase.getInstance().reference
//                            auth = FirebaseAuth.getInstance()
//
//                            val currentUser = auth.currentUser
//                            currentUser.let { user ->
//                                val noteReference: DatabaseReference =
//                                    databaseReference.child("labour information").child(user.uid)
//                                        .child("labour name")
//                                noteReference.addValueEventListener(object : ValueEventListener {
//                                    override fun onDataChange(snapshot: DataSnapshot) {
//                                        val noteList = mutableListOf<LabourItem>()
//                                        for (noteSnapshot: DataSnapshot in snapshot.children) {
//                                            val note: LabourItem? =
//                                                noteSnapshot.getValue(LabourItem::class.java)
//                                            note?.let {
//                                                noteList.add(it)
//                                            }
//                                        }
//
//                                        val adapter = rvAdapter(LabourItem)
//                                        recyclerView.adapter = adapter
//
//                                    }
//
//                                    override fun onCancelled(error: DatabaseError) {
//                                        TODO("Not yet implemented")
//                                    }
//
//                                })
//
//
//                            }


                            }

                        }


                    }
                }
            }
        }

    private fun getFileType(data: Uri?): Any? {
        val r = contentResolver
        val mimeType = MimeTypeMap.getSingleton()
        return mimeType.getMimeTypeFromExtension(r.getType(data!!))
    }
}










