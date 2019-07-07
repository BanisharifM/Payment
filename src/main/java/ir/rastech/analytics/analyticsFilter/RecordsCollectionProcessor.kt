package ir.rastech.analytics.analyticsFilter

import ir.rastech.analytics.AnalyticsRecordView
import org.eclipse.jetty.util.BlockingArrayQueue
import org.omg.CORBA.Object
import org.springframework.beans.factory.annotation.Autowired
import java.util.*


class RecordsCollectionProcessor {

    var tokendata: String? = null
    var url: String? = null

    lateinit var recordsCollectionPost : RecordsCollectionPost

    constructor() {
        tokendata = "Li1ezbkBCwvn21xPvKgQQaQe9BquIgaeOBHEYjX++TftmZHWGKQjMGzrwBJ9LdOJfxOzJOzruV8rVS1/Rr8vdl7Nd5OmmLTmrBTvs9O+f6LQJQHjGpXAVVCS6TRlRd6hnVmPmIMQAVa3mCLSz5FKlG3F0mYa5+jHcohOKPYrn8UwlkjT2+c6d/zogkvQ1E2d9y2ggcYrcXjfwX3FSXWGcvc4cmdrl8lnwsNnxm0YUm1NQkmxNXGWAln1Z5lonhBqAiAk+JqHlgW91L1P83UpiUSFen863BhiXfmOAsNrRsZb74Mi9zJRXkc3LLh6Zp3ISMz9ff9Nq6y0xlcpeRnGgKoqy9mU8itAxs+ZdX7Yzp+4ozmXjPMhAUO/v3la1gpwatNCYbeB+luWaUYZP/svmrDi+UX6XkOZxQWVSRt2YMu93XWsKsNarv0l21SGS/JOQTf/zn669JSSxcs15OnqJQ8ANqoGu2D2ORawhVsFm2jqaRKhRY/RkU7Oe/bqfBs8beq8jzLrYNVav2Ao92Dvd7DGw5D9AWsH/sErIk9xR6tY+dExzSjMlFBC1d2+ZF9sRinj81AzYFWNS5lvEvi4nYDi3y8+WycGNynQWz0BUOxI3pfNbMxlufz87NdWHSlE"
        url = "http://localhost:5718/rest/"
        recordsCollectionPost.start()
    }

    constructor(Tokendata: String, Url: String,recordsCollectionPost : RecordsCollectionPost) {
        tokendata = Tokendata
        url = Url
        this.recordsCollectionPost=recordsCollectionPost
        recordsCollectionPost.start()
        println("after thread start")
    }

    var records = BlockingArrayQueue<AnalyticsRecordView>()
    var ready_records= BlockingArrayQueue<AnalyticsRecordView>()

    fun addRecord(analyticRecord: AnalyticsRecordView) {
        records.add(analyticRecord)
        if (records.size >= 20) {
            for(i in 1..20)
                ready_records.add(records.remove())

            synchronized(recordsCollectionPost) {
                try {
                    recordsCollectionPost.setPostParams(ready_records,url,tokendata)
                    (recordsCollectionPost as java.lang.Object).notify()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }
}