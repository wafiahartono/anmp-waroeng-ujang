package test.s160419098.anmp.wu.auth

import com.google.gson.annotations.SerializedName

data class User(
    val name: String,
    val username: String,
    val password: String?,
    val sex: Sex,
    @SerializedName("entry_date") val entryDate: String,
)

enum class Sex {
    @SerializedName("male")
    MALE,

    @SerializedName("female")
    FEMALE,
}