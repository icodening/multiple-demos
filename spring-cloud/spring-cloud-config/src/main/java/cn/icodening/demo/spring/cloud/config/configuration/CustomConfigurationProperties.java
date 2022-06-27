package cn.icodening.demo.spring.cloud.config.configuration;

import cn.icodening.demo.spring.cloud.config.annotation.ForcedRefresh;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.Collections;
import java.util.List;

/**
 * @author icodening
 * @date 2022.06.24
 */
@ConfigurationProperties(prefix = "custom.config")
@RefreshScope
@ForcedRefresh
public class CustomConfigurationProperties {

    private List<String> studentNames = Collections.emptyList();

    public List<String> getStudentNames() {
        return studentNames;
    }

    public CustomConfigurationProperties setStudentNames(List<String> studentNames) {
        this.studentNames = studentNames;
        return this;
    }
}
