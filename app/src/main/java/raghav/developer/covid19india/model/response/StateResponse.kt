package raghav.developer.covid19india.model.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import raghav.developer.covid19india.model.Statewise

@JsonClass(generateAdapter = true)
data class StateResponse(
    @Json(name = "statewise")
    val statewise: List<Statewise>
   )