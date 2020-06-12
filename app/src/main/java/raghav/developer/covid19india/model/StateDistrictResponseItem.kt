package raghav.developer.covid19india.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StateDistrictResponseItem(
    @Json(name = "districtData")
    val districtData: List<DistrictData>,
    @Json(name = "state")
    val state: String,
    @Json(name = "statecode")
    val statecode: String
)