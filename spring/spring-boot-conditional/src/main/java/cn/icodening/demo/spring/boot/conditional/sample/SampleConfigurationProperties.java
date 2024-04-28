package cn.icodening.demo.spring.boot.conditional.sample;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author icodening
 * @date 2024.02.02
 */
@ConfigurationProperties(prefix = "conditional.sample")
public class SampleConfigurationProperties {

    /**
     * 是否启用
     */
    private boolean enabled = false;

    /**
     * 配置名字
     */
    private String name;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
