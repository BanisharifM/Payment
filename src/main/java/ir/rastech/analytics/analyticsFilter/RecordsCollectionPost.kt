package ir.rastech.analytics.analyticsFilter

import ir.rastech.analytics.AnalyticApi
import ir.rastech.analytics.AnalyticsRecordView
import org.eclipse.jetty.util.BlockingArrayQueue
import org.omg.CORBA.Object
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class RecordsCollectionPost : Thread() {

    var ready_records = BlockingArrayQueue<AnalyticsRecordView>()
    var url :String? = null
    var tokendata : String ? =null


    override fun run() {
        println("Thread is run")
        while(true)
        {
            synchronized (this) {
                (this as java.lang.Object).wait()
                val retrofit = Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(JacksonConverterFactory.create())
                        .build()
                var analyticApi = retrofit.create(AnalyticApi::class.java)
                Thread.sleep(1000)
                var response = analyticApi.SubmitRecords(ready_records, tokendata!!).execute()
                assert(response.isSuccessful())
            }
        }
    }

    fun setPostParams(ready_records: BlockingArrayQueue<AnalyticsRecordView>
                      , url: String ?
                      , tokendata: String ?) {

        this.ready_records.addAll(ready_records)
        this.url=url
        this.tokendata=tokendata
    }
}