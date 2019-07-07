package ir.rastech.analytics.analyticsFilter

import ir.rastech.analytics.AnalyticsRecordView
import org.apache.solr.store.hdfs.HdfsLocalityReporter.logger
import org.springframework.beans.factory.annotation.Autowired
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.servlet.*
import javax.servlet.http.HttpServletRequest


class AnalyticsRecordFilter : Filter {

    @Autowired
    lateinit var recordscollection : RecordsCollectionProcessor

    @Throws(ServletException::class)
    override fun init(filterConfig: FilterConfig) {

    }

    @Throws(IOException::class)
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        if(request != null) {

            var analyticsrecordview=AnalyticsRecordView()
            analyticsrecordview.userId=request.getAttribute("userId") as String
            analyticsrecordview.url =(request as HttpServletRequest).getRequestURL().toString()
            analyticsrecordview.date = Date(System.currentTimeMillis())

            //set user platform
            var userAgent = request.getHeader("User-Agent").toLowerCase()

            if (userAgent.indexOf("windows") >= 0) {
                analyticsrecordview.platform = "Windows"
            } else if (userAgent.indexOf("mac") >= 0) {
                analyticsrecordview.platform = "Mac"
            } else if (userAgent.indexOf("x11") >= 0) {
                analyticsrecordview.platform = "Unix"
            } else if (userAgent.indexOf("android") >= 0) {
                analyticsrecordview.platform = "Android"
            } else if (userAgent.indexOf("iphone") >= 0) {
                analyticsrecordview.platform = "IPhone"
            } else {
                analyticsrecordview.platform = userAgent
            }

            //set user browser
            val key = "browser"
            if (userAgent.contains("safari"))
                analyticsrecordview.userProperty.put(key,"Safari")
            else if(userAgent.contains("opr"))
                analyticsrecordview.userProperty.put(key,"OPR")
            else if(userAgent.contains("opera"))
                analyticsrecordview.userProperty.put(key,"Opera")
            else if(userAgent.toLowerCase().contains("chrome"))
                analyticsrecordview.userProperty.put(key,"Chrome")
            else if(userAgent.contains("firefox"))
                analyticsrecordview.userProperty.put(key,"Firefox")
            else
                analyticsrecordview.userProperty.put(key,userAgent)

            request!!.setAttribute("Analyticsrecordview", analyticsrecordview)

            val startTime = System.currentTimeMillis()

            chain?.doFilter(request, response)

            val endTime = System.currentTimeMillis()
            val seconds = (endTime - startTime)
            analyticsrecordview.responseTimeInMs= seconds

            recordscollection.addRecord(analyticsrecordview)
        }
    }

    override fun destroy() {

    }
}