package raghav.developer.covid19india.repository

import raghav.developer.covid19india.model.StateDetailsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import raghav.developer.covid19india.api.Covid19IndiaApiService
import raghav.developer.covid19india.model.StateResponse
import raghav.developer.covid19india.utils.State
import retrofit2.Response

@FlowPreview
@ExperimentalCoroutinesApi
class CovidIndiaRepository(private val apiService: Covid19IndiaApiService) {


}