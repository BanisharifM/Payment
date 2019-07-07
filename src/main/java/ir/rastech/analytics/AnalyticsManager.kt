package ir.rastech.analytics

import com.idehgostar.jpam.manager.GenericManager
import ir.rastech.analytics.Exception.AnalyticsRecordCreationException
import ir.rastech.analytics.Exception.AnalyticsRecordLoadException

interface AnalyticsManager : GenericManager<AnalyticsRecord, String> {


    @Throws(AnalyticsRecordCreationException::class)
    fun create_user_analytics(recordView: AnalyticsRecordView)

    fun saveAnalyticReportArray(recordViews: ArrayList<AnalyticsRecordView>)

    fun validateRequest(permission: Set<String>): Boolean

    @Throws(AnalyticsRecordLoadException::class)
    fun readAnalyticReports(userId: String): ArrayList<AnalyticsRecord>
}