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
    lateinit var recordscollection: RecordsCollectionProcessor

    @Throws(ServletException::class)
    override fun init(filterConfig: FilterConfig) {

    }

    @Throws(IOException::class)
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        if (request != null) {

            var analyticsrecordview = AnalyticsRecordView(request.getAttribute("userId") as String,
                    (request as HttpServletRequest).getRequestURL().toString())

            var userAgent = request.getHeader("User-Agent")
            if (request.getHeader("User-Agent").indexOf("Mobile") != -1) {
                analyticsrecordview.platform = "Mobile"
            } else {
                analyticsrecordview.platform = "Desktop"
            }
            //LocalTime.now()
            val formatter = SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z")
            analyticsrecordview.date = Date(System.currentTimeMillis())
            request!!.setAttribute("Analyticsrecordview", analyticsrecordview)

            logger.info("fg")
            val startTime = System.currentTimeMillis()

            chain?.doFilter(request, response)

            val endTime = System.currentTimeMillis()
            val seconds = (endTime - startTime)
            analyticsrecordview.durationInS = seconds

            recordscollection.addRecord(analyticsrecordview)


        }

    }

    override fun destroy() {

    }
}