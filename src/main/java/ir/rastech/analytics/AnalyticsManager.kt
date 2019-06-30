package ir.rastech.analytics

import com.idehgostar.jpam.manager.GenericManager
import ir.rastech.analytics.Exception.AnalyticsRecordCreationException
import ir.rastech.analytics.Exception.AnalyticsRecordLoadException

interface AnalyticsManager : GenericManager<AnalyticsRecord, String> {

    /**
     * creates a user
     *
     * @param userId
     * @param url
     * @param platform
     * @param service
     * @param date
     * @param durationInS
     * @param responseTimeInM
     * @param userProperty
     * @param requestProperty
     * @param
     *
     * @return
     * @throws AnalyticsRecordCreationException
     * @throws InvalidParameterException
     */

    @Throws(AnalyticsRecordCreationException::class)
    fun create_user_analytics(recordView: AnalyticsRecordView)

    fun saveAnalyticReportArray(recordViews: ArrayList<AnalyticsRecordView>)

    fun validateRequest(permission: Set<String>): Boolean

    @Throws(AnalyticsRecordLoadException::class)
    fun readAnalyticReports(userId: String): ArrayList<AnalyticsRecord>
}