package com.example.royalconstruction


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.royalconstruction.databinding.RvItemBinding

class RvAdapter(private val notes: MutableList<LabourItem>):RecyclerView.Adapter<RvAdapter.MyViewHolder> () {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
//        holder.binding.rvrate.text=notes.get(position).labourrate
//        holder.binding.rvlabourname.text= notes.get(position).labourname
//        holder.binding.rvlabourtype.text=notes.get(position).labourtype
    }

    override fun getItemCount(): Int {
        return notes.size
    }
inner class MyViewHolder(var binding: RvItemBinding):RecyclerView.ViewHolder(binding.root) {
    fun bind(note: LabourItem) {
        binding.rvlabourname.text = note.labourname
        binding.rvlabourtype.text = note.labourtype
        binding.rvrate.text = note.labourrate

        // set on clicklistener
        binding.rvdetailsbutton.setOnClickListener{
            val context = binding.rvdetailsbutton.context
            val intent = Intent(context,DashboardActivity::class.java)
            intent.putExtra("LabourItem",note)
            context.startActivity(intent)
        }

    }
        }


    }

private fun Intent.putExtra(s: String, note: LabourItem) {
}
