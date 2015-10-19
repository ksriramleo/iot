package com.iot.dataservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * This is the main class that is used to start the given REST simulator. It does the component scan for the given
 * simulator by parsing the simType argument from the command-line.
 */
@SpringBootApplication
@ComponentScan("com.iot.dataservice")
@Configuration
public class IOTSpringApplication {

    /**
     * This method will be executed to start the simulator.
     *
     * @param args system arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(IOTSpringApplication.class, args);
    }

}