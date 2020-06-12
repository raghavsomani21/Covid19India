package raghav.developer.covid19india.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import raghav.developer.covid19india.api.Covid19IndiaApiService
import raghav.developer.covid19india.utils.isNetworkAvailable
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

//Also uses coroutines
//Because requesting a network should not be done on themain thread.
//It can make the app unresponsive
@ExperimentalCoroutinesApi
val networkModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(Covid19IndiaApiService.BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .client(getOkHttpClient(androidContext()))
            .build()
            .create(Covid19IndiaApiService::class.java)
    }

}

fun getOkHttpClient(context : Context) : OkHttpClient{

    //Could have used Room as well,but since we just have to display some data,
    //using Cache-Control seemed a much better idea
    //We can use Room when we also want to make changes to the data
    //For example Instagram's offline ability
    //Even when offline, when we like a post and comeback online, our likes are still
    //presesnt.
    val cacheSize = (5*1024*1024).toLong()
    val myCache = Cache(context.cacheDir,cacheSize)

    return OkHttpClient.Builder()
        .cache(myCache)
        .addInterceptor { chain ->
            var request  = chain.request()
            request = if (isNetworkAvailable(context)!!)
                request.newBuilder().header("Cache-Control","public,max-age=" +5).build()
                      else
                request.newBuilder().header("Cache-Control","public, only-if-cached, max-stale="+60*60*2*7)
                    .build()
                    chain.proceed(request)
        }
        .build()

}