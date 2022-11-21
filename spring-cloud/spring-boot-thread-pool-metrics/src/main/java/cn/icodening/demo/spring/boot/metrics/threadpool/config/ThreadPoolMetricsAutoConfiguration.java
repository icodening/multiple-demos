package cn.icodening.demo.spring.boot.metrics.threadpool.config;

import cn.icodening.demo.spring.boot.metrics.threadpool.binder.ThreadPoolExecutorMeterBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author icodening
 * @date 2022.11.22
 */
@Configuration
public class ThreadPoolMetricsAutoConfiguration {

    @Bean
    public ThreadPoolExecutorMeterBinder threadPoolExecutorMeterBinder(Map<String, ThreadPoolExecutor> executorMap) {
        return new ThreadPoolExecutorMeterBinder(executorMap);
    }

    @Bean
    public ThreadPoolExecutor bizExecutor() {
        return new ThreadPoolExecutor(10, 10, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    }

}
