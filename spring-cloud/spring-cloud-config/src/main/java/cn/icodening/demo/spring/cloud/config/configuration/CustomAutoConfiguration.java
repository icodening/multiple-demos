package cn.icodening.demo.spring.cloud.config.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author icodening
 * @date 2022.06.24
 */
@EnableConfigurationProperties(CustomConfigurationProperties.class)
@Configuration
public class CustomAutoConfiguration {

}
