package com.example.royalconstruction

data class DetailsItem(val Name:String, val Attendence:String,){

    constructor():this("","",)
}
//data class DetailsItem(
//    var someField: String? = null,
//    var anotherField: Int? = null
//) {
//    // No-argument constructor
//    constructor() : this(null, null)
//}