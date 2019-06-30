package ir.rastech.analytics

import com.idehgostar.jpam.dao.GenericDao


interface AnalyticsDao : GenericDao<AnalyticsRecord, String> {
    fun loadByUserId(userId: String): ArrayList<AnalyticsRecord>
}