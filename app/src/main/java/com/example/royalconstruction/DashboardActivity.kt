package com.example.royalconstruction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.royalconstruction.databinding.ActivityDashboardBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DashboardActivity : AppCompatActivity() {
    private val binding:ActivityDashboardBinding by lazy {
        ActivityDashboardBinding.inflate(layoutInflater)
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var databaseReference: DatabaseReference
    private lateinit var Adapter: RvDetailsAdapter
    private lateinit var auth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityDashboardBinding.inflate(layoutInflater)
    //    setContentView(binding.root)

        recyclerView = binding.rvDashboard
        recyclerView.layoutManager = LinearLayoutManager(this)

        // initialize firebaase database reference
        databaseReference =FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        currentUser?.let { user ->
            val noteReference= databaseReference.child("Details information").child(user.uid).child("details labour name")
            noteReference.addValueEventListener(object:ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val datalist = mutableListOf<DetailsItem>()
                    for(noteSnapshot: DataSnapshot  in snapshot.children) {
                        val note:DetailsItem? = noteSnapshot.getValue(DetailsItem::class.java)
                        note?.let {
                            datalist.add(it)
                        }
                    }
                    datalist.reverse()
                    val adapter = RvDetailsAdapter(datalist)
                    recyclerView.adapter= adapter

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })


        }
        // get text from edit text
        binding.addeddetails.setOnClickListener {
            val name: String = binding.name.text.toString()
            val Attendence: String = binding.Attendence.text.toString()

            if (name.isEmpty() && Attendence.isEmpty()) {
                Toast.makeText(this, "fill all the fields", Toast.LENGTH_SHORT).show()

            } else {
                val currentUser: FirebaseUser? = auth.currentUser
                currentUser?.let { user ->

                    // genereata a unique key
                    val noteKey: String? =
                        databaseReference.child("Detail information").child(user.uid)
                            .child(" detail labour name").push().key

                    val DetailsItem = DetailsItem(name, Attendence)
                    if (noteKey != null) {
                        databaseReference.child("Detail  information").child(user.uid)
                            .child(" detail labour name")
                            .child(noteKey).setValue(DetailsItem).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        this,
                                        "Details add succesful",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    startActivity(Intent(this, DashboardActivity::class.java))
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this,
                                        "failed to add Details",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }


                    }
                }


            }
        }
    }
}



//        binding.rv.setOnClickListener{
//            startActivity(Intent(this,detailsActivity::class.java))
//        }




