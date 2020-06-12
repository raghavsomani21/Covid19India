package raghav.developer.covid19india.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DistrictData(
    @Json(name = "active")
    val active: Int,
    @Json(name = "confirmed")
    val confirmed: Int,
    @Json(name = "deceased")
    val deceased: Int,
    @Json(name = "delta")
    val delta: Delta,
    @Json(name = "district")
    val district: String,
    @Json(name = "notes")
    val notes: String,
    @Json(name = "recovered")
    val recovered: Int
)