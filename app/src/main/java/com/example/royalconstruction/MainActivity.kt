package com.example.royalconstruction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.royalconstruction.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RvAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var databaseReference: DatabaseReference
    private lateinit var searchView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = binding.rv
        recyclerView.layoutManager = LinearLayoutManager(this)

//        searchView = findViewById(R.id.SearchView);
//        searchView.clearFocus();



        // set iamge
        Glide.with(binding.ownerprofile.context)
            .load(Rvmodel.imageUrl())
            .into(binding.ownerprofile)


     binding.ownerprofile.setOnClickListener{
         startActivity(Intent(this,ProfileActivity::class.java))
         finish()
     }



       // initialize firebaase database reference
       databaseReference =FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        currentUser?.let { user ->
            val noteReference = databaseReference.child("labour information").child(user.uid).child("labour name")
            val addValueEventListener =
                noteReference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val noteList = mutableListOf<LabourItem>()
                        for (noteSnapshot: DataSnapshot in snapshot.children) {
                            val note: LabourItem? = noteSnapshot.getValue(LabourItem::class.java)
                            note?.let {
                                noteList.add(it)
                            }
                        }
                        noteList.reverse()
                        val adapter = RvAdapter(noteList)
                        recyclerView.adapter = adapter
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
        }
    }
}












