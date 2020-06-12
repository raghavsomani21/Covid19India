package raghav.developer.covid19india.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Statewise(
    @Json(name = "active")
    val active: String,
    @Json(name = "confirmed")
    val confirmed: String,
    @Json(name = "deaths")
    val deaths: String,
    @Json(name = "deltaconfirmed")
    val deltaconfirmed: String,
    @Json(name = "deltadeaths")
    val deltadeaths: String,
    @Json(name = "deltarecovered")
    val deltarecovered: String,
    @Json(name = "lastupdatedtime")
    val lastupdatedtime: String,
    @Json(name = "migratedother")
    val migratedother: String,
    @Json(name = "recovered")
    val recovered: String,
    @Json(name = "state")
    val state: String,
    @Json(name = "statecode")
    val statecode: String,
    @Json(name = "statenotes")
    val statenotes: String
)