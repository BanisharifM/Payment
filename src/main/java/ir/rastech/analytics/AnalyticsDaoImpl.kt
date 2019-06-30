package ir.rastech.analytics

import com.idehgostar.jpam.dao.GenericDaoImpl
import ir.rastech.analytics.Exception.AnalyticsRecordLoadException
import javax.persistence.Query


class AnalyticsDaoImpl : GenericDaoImpl<AnalyticsRecord, String>(AnalyticsRecord::class.java), AnalyticsDao {


    @Suppress("UNCHECKED_CAST")
    @Throws(AnalyticsRecordLoadException::class)
    override fun loadByUserId(userId: String): ArrayList<AnalyticsRecord> {
        val q: Query = qsu.createNamedQuery("analytic_loadByUserId").setParameter("userId", userId)
        return q.resultList as ArrayList<AnalyticsRecord>
    }

}