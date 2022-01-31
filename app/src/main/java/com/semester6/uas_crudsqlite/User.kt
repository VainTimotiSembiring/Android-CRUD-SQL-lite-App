package com.semester6.uas_crudsqlite

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User : Parcelable{
    var id : Int = 0
    var nama : String = ""
    var no_hp : String = ""
    var email : String = ""
}