package cn.icodening.demo.spring.cloud.config.controller;

import cn.icodening.demo.spring.cloud.config.configuration.CustomConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author icodening
 * @date 2022.06.24
 */
@RestController
public class EnvironmentController {

    private final CustomConfigurationProperties customConfigurationProperties;

    public EnvironmentController(CustomConfigurationProperties customConfigurationProperties) {
        this.customConfigurationProperties = customConfigurationProperties;
    }

    @GetMapping("/echoList")
    public List<String> echoList() {
        return customConfigurationProperties.getList();
    }

}
