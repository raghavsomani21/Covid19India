package raghav.developer.covid19india.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import raghav.developer.covid19india.api.Covid19IndiaApiService
import raghav.developer.covid19india.model.response.StateDistrictResponse
import raghav.developer.covid19india.model.response.StateResponse
import raghav.developer.covid19india.utils.State
import retrofit2.Response

@FlowPreview
@ExperimentalCoroutinesApi
class CovidIndiaRepository(private val apiService: Covid19IndiaApiService) {

    fun getStateData(): Flow<State<StateResponse>> {
        return object : NetworkBoundRepository<StateResponse>() {
            override suspend fun fetchFromRemote(): Response<StateResponse> = apiService.getStateData()
        }.asFlow().flowOn(Dispatchers.IO).map { state ->
            when (state) {
                is State.Loading -> State.loading()
                is State.Success -> {
                    val data = state.data

                    if (data != null) {
                        State.success<StateResponse>(data)
                    } else {
                        State.error<StateResponse>("No data found")
                    }
                }
                is State.Error -> {
                    State.error<StateResponse>(state.message)
                }
            }
        }
    }
    fun getStateDetailsData(stateName: String): Flow<State<StateDistrictResponse>> {
        return object : NetworkBoundRepository<List<StateDistrictResponse>>() {
            override suspend fun fetchFromRemote(): Response<List<StateDistrictResponse>> =
                apiService.getStateDistrictData()
        }.asFlow().flowOn(Dispatchers.IO).map { state ->
            when (state) {
                is State.Loading -> State.loading()
                is State.Success -> {
                    val data = state.data.find{state.equals(stateName) }

                    if (data != null) {
                        State.success<StateDistrictResponse>(data)
                    } else {
                        State.error<StateDistrictResponse>("No data found of state '$stateName'")
                    }
                }
                is State.Error -> {
                    State.error<StateDistrictResponse>(state.message)
                }
            }
        }
    }
}