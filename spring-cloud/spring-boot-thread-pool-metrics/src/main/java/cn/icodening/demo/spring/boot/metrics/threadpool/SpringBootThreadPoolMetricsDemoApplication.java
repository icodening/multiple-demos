package cn.icodening.demo.spring.boot.metrics.threadpool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring容器中的线程池指标采集DEMO，启动后访问 /actuator/prometheus 即可
 *
 * @author icodening
 * @date 2022.11.21
 */
@SpringBootApplication
public class SpringBootThreadPoolMetricsDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootThreadPoolMetricsDemoApplication.class);
    }
}
