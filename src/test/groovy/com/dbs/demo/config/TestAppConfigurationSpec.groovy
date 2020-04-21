package com.dbs.demo.config

import org.springframework.test.context.ActiveProfiles

/**
 * DEVELOPER NOTE: The annotation @ActiveProfiles lets you set the current Spring profile in your unit tests. See this
 * class' base class for more info.
 */
@ActiveProfiles("test")
class TestAppConfigurationSpec extends BaseAppConfigurationSpec {

    // ------------------------------------------------ DOCUMENTATION --------------------------------------------------

    def setupSpec() {
        reportHeader """
        <br/>
        The application dynamically loads configuration based on the current active profile as specified by the current
        execution environment. This specification deals with loading test configurations.
        <br/>
        """
    }


    // ------------------------------------------------ SPECIFICATIONS -------------------------------------------------

    def "Load test configuration"() {

        // --
        given: "the application configuration file has test values for all properties"

        // --
        when: "the properties are loaded from the application configuration"

        // --
        then: "production values should be loaded for all application properties"
        assertAppConfigurationLoaded("demo-test")
    }

}
