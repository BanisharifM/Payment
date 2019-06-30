package ir.rastech.analytics

import java.util.*
import kotlin.collections.HashMap

class AnalyticsRecordView {

    var id: Long? = null
    var userId: String = ""
    var url: String = ""
    var platform: String = ""
    var service: String = ""
    var date: Date? = null
    var durationInS: Long? = null
    var responseTimeInMs: Long? = null
    var userProperty = HashMap<String, String>()
    var requestProperty = HashMap<String, String>()

    init {

    }

    constructor()
    constructor(UserId: String, Url: String) {
        userId = UserId
        url = Url
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AnalyticsRecordView

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
        var result = userId.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + platform.hashCode()
        result = 31 * result + service.hashCode()
        result = 31 * result + (date?.hashCode() ?: 0)
        result = 31 * result + (durationInS?.hashCode() ?: 0)
        result = 31 * result + (responseTimeInMs?.hashCode() ?: 0)
        result = 31 * result + userProperty.hashCode()
        result = 31 * result + requestProperty.hashCode()
        return result
    }


}