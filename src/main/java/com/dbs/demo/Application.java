package com.dbs.demo;

//import com.dbs.demo.customer.CustomerController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * DEVELOPER NOTE: This is the class that starts it all. It doesn't matter what the name of the class is... it doesn't
 * have to be called "Application" (but it's a convenient name to use). It's just a plain Java class with a "main"
 * method (every Java application must have one). The call to the static "SpringApplication.run()" method is what
 * actually launches SpringBoot (by default an instance of an Apache Tomcat web server) and officially launches the
 * application. Spring will scour every class in the Java classpath for classes annotated with @Component or @Controller
 * or any similar annotation and create a single instance of those classes. Then it wires all of those objects together
 * by calling either their @Autowired annotated constructor or directly injecting the instances into their @Autowired
 * properties.
 * <p>
 * You can run this file directly, which will start up Spring for real. If you do, you can hit the following endpoint
 * in Postman or your browser... and get a 404 not found, since there aren't *really* any customers...
 * <p>
 * http://localhost:8080/v1/customers/1
 * <p>
 *
 * @see CustomerController next!
 */
@SpringBootApplication
public class Application {

    /**
     * DEVELOPER NOTE: There must always be a main() for a Java application.
     */
    public static void main(String[] args) {

        // Launch Spring(Boot)
        SpringApplication.run(Application.class, args);
    }
}
