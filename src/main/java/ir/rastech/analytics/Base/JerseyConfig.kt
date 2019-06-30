package ir.rastech.analytics.Base

import ir.rastech.analytics.AnalyticsWS
import org.glassfish.jersey.server.ResourceConfig
import org.springframework.context.annotation.Configuration


@Configuration
class JerseyConfig : ResourceConfig {

    constructor() {
        register(AnalyticsWS::class)
    }
}