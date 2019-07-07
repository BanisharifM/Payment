package ir.rastech.analytics

import com.idehgostar.makhsan.core.response.SimpleResponse
import org.apache.http.protocol.RequestUserAgent
import retrofit2.Call
import retrofit2.http.*
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Created by hossein on 10/25/16.
 */
interface AnalyticApi {
    
    @POST("analytics/createAnalytic")
    fun SubmitRecords(@Body AnalyticsRecordArray: List<AnalyticsRecordView>, @Query("access_token") access_token: String): Call<SimpleResponse>


    @POST("analytics/createAnalytic")
    fun SubmitRecordsTest(@Header("User-Agent") userAgent: String,@Body AnalyticsRecordArray: ArrayList<AnalyticsRecordView>, @Query("access_token") access_token: String): Call<SimpleResponse>

    @GET("analytics/loadUserAnalytics")
    fun loadUserAnalytics(@Query("userId") userId: String, @Query("access_token") accessToken: String): Call<ArrayList<AnalyticsRecordView>>
}
