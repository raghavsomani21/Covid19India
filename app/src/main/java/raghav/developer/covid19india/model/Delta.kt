package raghav.developer.covid19india.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Delta(
    @Json(name = "confirmed")
    val confirmed: Int,
    @Json(name = "deceased")
    val deceased: Int,
    @Json(name = "recovered")
    val recovered: Int
)