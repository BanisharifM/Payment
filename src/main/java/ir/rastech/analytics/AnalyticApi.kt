package ir.rastech.analytics

import com.idehgostar.makhsan.core.response.SimpleResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by hossein on 10/25/16.
 */
interface AnalyticApi {

    @POST("analytics/createAnalytic")
    fun SubmitRecords(@Body AnalyticsRecordArray: ArrayList<AnalyticsRecordView>, @Query("access_token") access_token: String): Call<SimpleResponse>

    @GET("analytics/loadUserAnalytics")
    fun loadUserAnalytics(@Query("userId") userId: String, @Query("access_token") accessToken: String): Call<ArrayList<AnalyticsRecordView>>


}
