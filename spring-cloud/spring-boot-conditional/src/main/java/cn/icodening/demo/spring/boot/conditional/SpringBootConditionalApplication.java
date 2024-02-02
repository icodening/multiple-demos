package cn.icodening.demo.spring.boot.conditional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * @author icodening
 * @date 2024.02.02
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringBootConditionalApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootConditionalApplication.class, args);
    }

}
