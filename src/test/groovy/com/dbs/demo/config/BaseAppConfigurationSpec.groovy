package com.dbs.demo.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * A base class for testing all lifecycle configurations.
 * <p>
 * DEVELOPER NOTE: We need @ContextConfiguration so that Spring can interact with Spock such that Spring configurations
 * will be loaded as part of this unit test.
 * <p>
 * Why do this at all? Because YAML files are easily broken! Testing our configuration makes sure that we stay honest
 * about modifying our configuration files, and that we've included every configuration for every lifecycle.
 * <p>
 * Arguably, you wouldn't want configuration files at all. Twelve-factor cloud applications should read their
 * configurations from environment variables, not from source-controlled properties files. In practice, most
 * applications respect this factor only for credentials... which is good.
 * <p>
 * !!! NEVER store usernames, passwords, or security keys in GitHub !!!
 * <p>
 * https://12factor.net/
 */
@SpringBootTest
@ContextConfiguration(classes = AppConfiguration, initializers = ConfigFileApplicationContextInitializer)
abstract class BaseAppConfigurationSpec extends Specification {

    // ------------------------------------------------- DEPENDENCIES --------------------------------------------------

    /**
     * DEVELOPER NOTE: @Autowired here so that we let Spring *really* load the properties, and SpringBootTest will let
     * Spring inject the loaded AppConfiguration into this test class.
     */
    @Autowired
    protected AppConfiguration appConfiguration;


    // ------------------------------------------------ SPECIFICATIONS -------------------------------------------------

    void assertAppConfigurationLoaded(String name) {

        // then: values should be loaded for all application properties.
        assert name == appConfiguration.getConfigName()
        assert appConfiguration.getEnvironment() != null
        assert !appConfiguration.getServers().isEmpty()
    }

}
