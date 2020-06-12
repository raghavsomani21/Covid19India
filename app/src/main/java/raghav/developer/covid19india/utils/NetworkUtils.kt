package raghav.developer.covid19india.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.core.content.getSystemService

//Util to check internet connection
fun isNetworkAvailable(context: Context) : Boolean? {
    var isConnected : Boolean? = false
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork : NetworkInfo? = connectivityManager.activeNetworkInfo
    if(activeNetwork!=null && activeNetwork.isConnected)
        isConnected = true
    return isConnected
}