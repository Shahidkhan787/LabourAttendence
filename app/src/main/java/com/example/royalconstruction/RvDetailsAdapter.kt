package com.example.royalconstruction


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.royalconstruction.databinding.DashboardItemBinding


class RvDetailsAdapter(private val note: MutableList<DetailsItem>):RecyclerView.Adapter<RvDetailsAdapter.MyViewHolder> () {
//    constructor(datalist: ArrayList<Rvdetails>, dashboardActivity: DashboardActivity) : this(datalist)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = DashboardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note = note[position]
        holder.bind(note)

        // holder.binding.rate.text = datalist[position].toString().
        // holder.binding.attendence.text = datalist[position].toString()
        //  holder.binding.rate.text=datalist.get(position).R
        //    holder.binding.attendance.text=datalist.get(position).attendance
    }

    override fun getItemCount(): Int {
        return note.size
    }

    inner class MyViewHolder(private val binding: DashboardItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(note: DetailsItem) {
          binding.detailsattendence.text = note.Attendence
          binding.detailsname.text = note.Name
        }
    }
}


