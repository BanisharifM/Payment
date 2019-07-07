package ir.rastech.analytics

import com.idehgostar.makhsan.core.response.SimpleResponse
import ir.rastech.analytics.Base.BaseWS
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.Response


@Service
@Path("/analytics")
class AnalyticsWS : BaseWS() {

    @Autowired
    lateinit var analyticsManager: AnalyticsManager


    @POST
    @Path("/createAnalytic")
    @Produces("application/json")
    @Consumes("application/json")
    fun receiveRequests(@Context request: HttpServletRequest,
                        recordViews: ArrayList<AnalyticsRecordView>): Response {

        if (analyticsManager.validateRequest(request.getAttribute("userPermissions") as Set<String>)) {
            analyticsManager.saveAnalyticReportArray(recordViews)
            return Response.ok(SimpleResponse(SimpleResponse.Status.Success, "done")).build()
        } else {
            return Response.status(403).entity(SimpleResponse(SimpleResponse.Status.Failed, "faild")).build()
        }
    }

    @GET
    @Path("/loadUserAnalytics")
    @Produces("application/json")
    fun getAllRecordsByUserId(@QueryParam("userId") userId: String): Response {
        try {
            var anal = analyticsManager.readAnalyticReports(userId)
            print(anal.firstOrNull().toString())
            return Response.ok(anal).build()

        } catch (e: IOException) {
            return Response.status(403).entity(SimpleResponse(SimpleResponse.Status.Failed, e.message)).build()
        }
    }
}