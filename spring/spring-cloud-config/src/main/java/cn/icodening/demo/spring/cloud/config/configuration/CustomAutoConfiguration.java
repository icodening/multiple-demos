package cn.icodening.demo.spring.cloud.config.configuration;

import cn.icodening.demo.spring.cloud.config.processor.ForcedRefreshPropertiesPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author icodening
 * @date 2022.06.24
 */
@EnableConfigurationProperties(CustomConfigurationProperties.class)
@Configuration
public class CustomAutoConfiguration {

    @Bean
    public BeanPostProcessor forcedRefreshPropertiesPostProcessor() {
        return new ForcedRefreshPropertiesPostProcessor();
    }
}
