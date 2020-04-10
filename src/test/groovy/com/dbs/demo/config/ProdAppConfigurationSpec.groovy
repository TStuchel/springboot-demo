package com.dbs.demo.config

import org.springframework.test.context.ActiveProfiles

/**
 * DEVELOPER NOTE: The annotation @ActiveProfiles lets you set the current Spring profile in your unit tests. See this
 * class' base class for more info.
 */
@ActiveProfiles("prod")
class ProdAppConfigurationSpec extends BaseAppConfigurationSpec {

    // ------------------------------------------------ SPECIFICATIONS -------------------------------------------------

    def "Loads production configuration"() {

        // --
        given: "the application configuration file has production values for all properties"

        // --
        when: "the properties are loaded from the application configuration"

        // --
        then: "production values should be loaded for all application properties"
        assertAppConfigurationLoaded("demo-prod")
    }

}
