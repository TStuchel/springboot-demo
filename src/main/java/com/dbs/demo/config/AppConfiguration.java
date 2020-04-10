package com.dbs.demo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * DEVELOPER NOTE: A single instance of this class will be instantiated and populated based on the contents of
 * src/main/java/resources/application.yml which is further decided based on the currently active profile. There are
 * other ways to load properties into a Spring application, such as injecting properties using @Value.
 * <p>
 * See: https://www.baeldung.com/properties-with-spring
 * <p>
 * The @Getter and @Setter annotations are Lombok library annotations that build getter and setter methods for each of
 * of the properties. These are needed for the configuration to be loaded. The full details of configuration properties
 * can be found here:
 * <p>
 * https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html
 */
@Getter
@Setter
@EnableConfigurationProperties
@ConfigurationProperties
public class AppConfiguration {

    private String configName;
    private String environment;
    private List<String> servers = new ArrayList<>();
}
