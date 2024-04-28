package cn.icodening.demo.spring.boot.conditional.sample;

import cn.icodening.demo.spring.boot.conditional.ConditionalOnPropertiesEnabled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author icodening
 * @date 2024.02.02
 */
@Component
@ConditionalOnPropertiesEnabled(type = SampleConfigurationProperties.class)
public class ConditionalSampleRunner implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionalSampleRunner.class);

    private final SampleConfigurationProperties sampleConfigurationProperties;

    public ConditionalSampleRunner(SampleConfigurationProperties sampleConfigurationProperties) {
        this.sampleConfigurationProperties = sampleConfigurationProperties;
    }

    @Override
    public void run(ApplicationArguments args) {
        LOGGER.info("conditional sample runner enabled, the name is '{}'", sampleConfigurationProperties.getName());
    }
}
