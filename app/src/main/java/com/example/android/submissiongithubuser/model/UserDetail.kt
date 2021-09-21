package com.example.android.submissiongithubuser.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(
    tableName = "user_table"
)
@Parcelize
data class UserDetail(
    var username: String? = null,
    var name: String? = null,
    var avatar: String? = null,
    var company: String? = null,
    var location: String? = null,
    var followers: Int? = null,
    var following: Int? = null
) : Parcelable {
    @PrimaryKey(autoGenerate = false)
    var id: Int? = null
}
