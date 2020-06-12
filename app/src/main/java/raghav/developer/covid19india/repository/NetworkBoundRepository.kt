package raghav.developer.covid19india.repository

import androidx.annotation.MainThread
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import raghav.developer.covid19india.utils.State
import retrofit2.Response
import java.lang.Exception

@ExperimentalCoroutinesApi
abstract class NetworkBoundRepository<T> {
    fun asFlow()  = flow<State<T>> {
        emit(State.loading())
            try{
                // Fetch latest data from remote
                val apiResponse = fetchFromRemote()
                // Parse body
                val remotePosts = apiResponse.body()
                // Check for response validation
                if (apiResponse.isSuccessful && remotePosts != null) {
                    emit(State.success(remotePosts))
                } else {
                    // Something went wrong! Emit error state.
                    emit(State.error(apiResponse.message()))
                }
            }catch (e:Exception){
                emit(State.error("Network error! Can't get latest data."))
                e.printStackTrace()
            }
    }
    @MainThread
    protected abstract suspend fun fetchFromRemote(): Response<T>
}