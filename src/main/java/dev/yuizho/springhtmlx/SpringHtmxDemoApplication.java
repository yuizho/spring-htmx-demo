package dev.yuizho.springhtmlx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("dev.yuizho.springhtmlx.configs")
public class SpringHtmxDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringHtmxDemoApplication.class, args);
    }

}
