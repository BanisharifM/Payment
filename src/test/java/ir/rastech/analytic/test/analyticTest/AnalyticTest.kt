package ir.rastech.analytic.test.analyticTest

import ir.rastech.analytic.test.jerseytest.WithJettyTest
import ir.rastech.analytics.AnalyticsRecordView
import org.junit.Assert
import org.junit.Test
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class AnalyticTest : WithJettyTest {

    var test = AnalyticsRecordView()
    var array = ArrayList<AnalyticsRecordView>()

    var token: String = "Li1ezbkBCwvn21xPvKgQQaQe9BquIgaeOBHEYjX++TftmZHWGKQjMGzrwBJ9LdOJfxOzJOzruV8rVS1/Rr8vdl7Nd5OmmLTmrBTvs9O+f6LQJQHjGpXAVVCS6TRlRd6hnVmPmIMQAVa3mCLSz5FKlG3F0mYa5+jHcohOKPYrn8UwlkjT2+c6d/zogkvQ1E2d9y2ggcYrcXjfwX3FSXWGcvc4cmdrl8lnwsNnxm0YUm1NQkmxNXGWAln1Z5lonhBqAiAk+JqHlgW91L1P83UpiUSFen863BhiXfmOAsNrRsZb74Mi9zJRXkc3LLh6Zp3ISMz9ff9Nq6y0xlcpeRnGgKoqy9mU8itAxs+ZdX7Yzp+4ozmXjPMhAUO/v3la1gpwatNCYbeB+luWaUYZP/svmrDi+UX6XkOZxQWVSRt2YMu93XWsKsNarv0l21SGS/JOQTf/zn669JSSxcs15OnqJQ8ANqoGu2D2ORawhVsFm2jqaRKhRY/RkU7Oe/bqfBs8beq8jzLrYNVav2Ao92Dvd7DGw5D9AWsH/sErIk9xR6tY+dExzSjMlFBC1d2+ZF9sRinj81AzYFWNS5lvEvi4nYDi3y8+WycGNynQWz0BUOxI3pfNbMxlufz87NdWHSlE"


    constructor() {
        test.userId = "56"
        test.url = "/test"
        test.platform = "Windows"
        test.service = "chom!"
        test.date = Date(13760626)
        test.durationInS = 189
        test.responseTimeInMs = 20
        test.userProperty.getOrDefault("first", "firstUserProperty")
        test.userProperty.getOrDefault("second", "secondUserProperty")
        test.requestProperty.getOrDefault("first", "firstRequestProperty")
        test.requestProperty.getOrDefault("second", "secondRequestProperty")
        array.add(test)
    }

    @Test
    @Throws(IOException::class)
    fun analyticTest() {

        val userId: String = "56"

        val platforms = arrayOf("windows chrome","mac firefox","x11 opr","android opera","iphone safari")

        for (i in 1..10)
            api.SubmitRecordsTest(platforms[i%5],array, token).execute()

        for (i in 1..10)
            api.loadUserAnalytics(userId, token).execute()


        val execute = api.loadUserAnalytics(userId, token).execute()
        assertSuccess(execute)
        execute.body().let {
            Assert.assertEquals(10, it.size)

            var windowsOS=0
            it.forEach {
                Assert.assertEquals(test, it.also { it.id = null })

                println(it.platform)

                if(it.platform.equals("Windows"))
                    windowsOS++
            }
            Assert.assertEquals(10,windowsOS)
        }
        Thread.sleep(6000)
        val execute2 = api.loadUserAnalytics("7", token).execute()
        assertSuccess(execute2)
        execute2.body().let {
            Assert.assertEquals(20, it.size)

            var windowsOS=0
            var macOS=0
            var x11OS=0
            var androidOS=0
            var iphoneOS=0

            it.forEach{
                when(it.platform){
                    "Windows" -> windowsOS++
                    "Mac" -> macOS++
                    "Unix" ->x11OS++
                    "Android" ->androidOS++
                    "IPhone" -> iphoneOS++
                }
            }

            Assert.assertEquals(2,windowsOS)
            Assert.assertEquals(2,macOS)
            Assert.assertEquals(2,x11OS)
            Assert.assertEquals(2,androidOS)
            Assert.assertEquals(2,iphoneOS)

            Assert.assertEquals(10, it.filter { it.url == "http://localhost:5718/rest/analytics/createAnalytic" }.size)
            Assert.assertEquals(10, it.filter { it.url == "http://localhost:5718/rest/analytics/loadUserAnalytics" }.size)
        }

    }
}