package raghav.developer.covid19india.utils

//When calling a function which returns data,
// there is often the need to handle errors which might occur.
//HANDLING OPTIONAL ERRORS USING KOTLIN SEALED CLASSES
//This state class determines the state of the called function
sealed class State<T> {
    class Loading<T> : State<T>()
    data class Success<T>(val data: T) : State<T>()
    data class Error<T>(val message: String) : State<T>()

    companion object {
        fun <T> loading() = Loading<T>()

        fun <T> success(data: T) = Success(data)

        fun <T> error(message: String) = Error<T>(message)
    }
}