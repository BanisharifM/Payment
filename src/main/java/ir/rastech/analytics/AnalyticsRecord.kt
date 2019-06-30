package ir.rastech.analytics

import ir.rastech.analytics.Base.JsonType
import org.hibernate.annotations.Parameter
import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*


@Entity
//select u from AnalyticsRecord u where u.userId=:userId
@NamedQueries(NamedQuery(name = "analytic_loadByUserId", query = "select u from AnalyticsRecord u where u.userId=:userId"))
class AnalyticsRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var userId: String = ""
    var url: String = ""
    var platform: String = ""
    var service: String = ""
    var date: Date? = null
    var durationInS: Long? = null
    var responseTimeInMs: Long? = null

    @Type(type = "ir.rastech.analytics.Base.JsonType",
            parameters = arrayOf<Parameter>(Parameter(name = "type", value = JsonType.MAP_TYPE),
                    Parameter(name = "key", value = "java.lang.String"),
                    Parameter(name = "value", value = "java.lang.String")))
    var userProperty: Map<String, String>? = HashMap()

    @Type(type = "ir.rastech.analytics.Base.JsonType",
            parameters = arrayOf<Parameter>(Parameter(name = "type", value = JsonType.MAP_TYPE),
                    Parameter(name = "key", value = "java.lang.String"),
                    Parameter(name = "value", value = "java.lang.String")))
    var requestProperty: Map<String, String>? = HashMap()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AnalyticsRecord

        if (id != other.id) return false
        if (userId != other.userId) return false
        if (url != other.url) return false
        if (platform != other.platform) return false
        if (service != other.service) return false
        if (date != other.date) return false
        if (durationInS != other.durationInS) return false
        if (responseTimeInMs != other.responseTimeInMs) return false
        if (userProperty != other.userProperty) return false
        if (requestProperty != other.requestProperty) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + userId.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + platform.hashCode()
        result = 31 * result + service.hashCode()
        result = 31 * result + (date?.hashCode() ?: 0)
        result = 31 * result + (durationInS?.hashCode() ?: 0)
        result = 31 * result + (responseTimeInMs?.hashCode() ?: 0)
        result = 31 * result + (userProperty?.hashCode() ?: 0)
        result = 31 * result + (requestProperty?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "AnalyticsRecord(id=$id, userId=$userId, url='$url', platform='$platform', service='$service', date=$date, durationInS=$durationInS, responseTimeInMs=$responseTimeInMs, userProperty=$userProperty, requestProperty=$requestProperty)"
    }


}
