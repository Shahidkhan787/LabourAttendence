package com.example.royalconstruction

object datalist {
    private lateinit var dataList:ArrayList<Rvmodel>
    fun getData():ArrayList<Rvmodel>{
        dataList = ArrayList<Rvmodel>()
        dataList.add(Rvmodel(R.drawable.royal, "shahid", details = String()))
        dataList.add(Rvmodel(R.drawable.royal, "hasnain", details = String()))
        dataList.add(Rvmodel(R.drawable.royal, "hasnain", details = String()))

        return dataList
    }
}