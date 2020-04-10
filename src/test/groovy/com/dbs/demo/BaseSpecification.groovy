package com.dbs.demo

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.ConsoleAppender
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import spock.lang.Specification
import uk.co.jemos.podam.api.PodamFactory
import uk.co.jemos.podam.api.PodamFactoryImpl

/**
 * A base specification to contain common test class functionality.
 */
abstract class BaseSpecification extends Specification {

    // ----------------------------------------------- MEMBER VARIABLES ------------------------------------------------

    /**
     * Used for random test values
     */
    protected static final PodamFactory podamFactory = new PodamFactoryImpl()

    /**
     * Serialization/Deserialization
     */
    protected static final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules()


    // ------------------------------------------------ PUBLIC METHODS -------------------------------------------------

    /**
     * Global test initialization.
     */
    def setup() {
        initLogging()
    }

    /**
     * Initialize logging (so that logging configuration does not conflict with SpringBoot users).
     */
    void initLogging() {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory()

        // Console appender
        ConsoleAppender<ILoggingEvent> appender = new ConsoleAppender<ILoggingEvent>()
        appender.setName("STDOUT")
        appender.setContext(lc)
        appender.start()

        // Root
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)
        root.addAppender(appender)
        root.setLevel(Level.INFO)

        // Podam
        Logger podam = (Logger) LoggerFactory.getLogger("uk.co.jemos.podam")
        podam.addAppender(appender)
        podam.setLevel(Level.ERROR)
        podam.setAdditive(true)
        podam
    }

}
