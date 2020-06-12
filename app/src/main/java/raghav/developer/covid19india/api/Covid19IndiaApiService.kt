package raghav.developer.covid19india.api

import raghav.developer.covid19india.model.response.StateDistrictResponse
import raghav.developer.covid19india.model.response.StateResponse
import retrofit2.Response
import retrofit2.http.GET

interface Covid19IndiaApiService {

    //Using retrofit to call an api
    //NetworkModule is a dependency oinjection(since it depends on OkHttp clinet
    //Class.
    @GET("data.json")
    suspend fun getStateData() : Response<StateResponse>

    @GET("v2/state_district_wise.json")
    suspend fun getStateDistrictData() : Response<List<StateDistrictResponse>>

    companion object{
        const val BASE_URL = "https://api.covid19india.org/"
    }

}