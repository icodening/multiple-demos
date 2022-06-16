package cn.icodening.demo.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;

import java.util.function.Supplier;

/**
 * @author icodening
 * @date 2022.06.16
 */
public class CustomHystrixCommand extends HystrixCommand<String> {

    private final Supplier<String> supplier;

    protected CustomHystrixCommand(String key, Supplier<String> supplier) {
        super(HystrixCommand.Setter.withGroupKey(
                        HystrixCommandGroupKey.Factory.asKey(key))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("ThreadPool-" + key))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        .withCoreSize(2)
                        .withKeepAliveTimeMinutes(0)
                        .withMaximumSize(10)
                        .withMaxQueueSize(10)
                        .withMetricsRollingStatisticalWindowBuckets(2)
                        .withMetricsRollingStatisticalWindowInMilliseconds(1000)));
        this.supplier = supplier;
    }

    @Override
    protected String run() throws Exception {
        return supplier.get();
    }
}
