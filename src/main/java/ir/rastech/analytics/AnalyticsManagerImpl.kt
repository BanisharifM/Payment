package ir.rastech.analytics

import com.idehgostar.jpam.dao.GenericDao
import com.idehgostar.jpam.manager.GenericManagerImpl
import ir.rastech.analytics.Base.Permission
import ir.rastech.analytics.Exception.AnalyticsRecordCreationException
import ir.rastech.analytics.Exception.AnalyticsRecordLoadException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController


@RestController
class AnalyticsManagerImpl : GenericManagerImpl<AnalyticsRecord, String>, AnalyticsManager {

    @Autowired
    var analyticDao: AnalyticsDao

    var userAnalyticsRecord = AnalyticsRecord()

    constructor(genericdao: GenericDao<AnalyticsRecord, String>) : super(genericdao) {
        analyticDao = genericdao as AnalyticsDao
    }

    @Throws(AnalyticsRecordCreationException::class)
    override fun create_user_analytics(recordView: AnalyticsRecordView) {

        userAnalyticsRecord.userId = recordView.userId

        userAnalyticsRecord.date = recordView.date
        userAnalyticsRecord.durationInS = recordView.durationInS
        userAnalyticsRecord.platform = recordView.platform
        userAnalyticsRecord.responseTimeInMs = recordView.responseTimeInMs
        userAnalyticsRecord.service = recordView.service
        userAnalyticsRecord.url = recordView.url

        if (recordView.requestProperty != null)
            userAnalyticsRecord.requestProperty = recordView.requestProperty

        if (recordView.userProperty != null)
            userAnalyticsRecord.userProperty = recordView.userProperty

        analyticDao.save(userAnalyticsRecord)

    }

    override fun saveAnalyticReportArray(recordViews: ArrayList<AnalyticsRecordView>) {

        for (data in recordViews) {
            create_user_analytics(data)
        }
    }

    override fun validateRequest(permission: Set<String>): Boolean {
        return permission.contains(Permission.ReportAnalytics.toString())
    }

    @Throws(AnalyticsRecordLoadException::class)
    override fun readAnalyticReports(userId: String): ArrayList<AnalyticsRecord> {
        return analyticDao.loadByUserId(userId)
    }

}