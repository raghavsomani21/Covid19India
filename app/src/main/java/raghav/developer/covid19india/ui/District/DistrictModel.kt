package raghav.developer.india.ui.district

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import raghav.developer.covid19india.repository.CovidIndiaRepository
import raghav.developer.covid19india.utils.State

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class DistrictModel(private val covidIndiaRepository: CovidIndiaRepository) : ViewModel() {


}
