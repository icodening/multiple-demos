package cn.icodening.demo.spring.boot.metrics.threadpool.binder;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.MeterBinder;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author icodening
 * @date 2022.11.21
 */
public class ThreadPoolExecutorMeterBinder implements MeterBinder {

    private final Map<String, ThreadPoolExecutor> executorMap;

    public ThreadPoolExecutorMeterBinder(Map<String, ThreadPoolExecutor> executorMap) {
        this.executorMap = executorMap;
    }

    @Override
    public void bindTo(MeterRegistry registry) {
        Set<Map.Entry<String, ThreadPoolExecutor>> entries = executorMap.entrySet();
        for (Map.Entry<String, ThreadPoolExecutor> entry : entries) {
            String poolName = entry.getKey();
            ThreadPoolExecutor executor = entry.getValue();
            Tags tags = Tags.of("thread.executor.name", poolName);

            Gauge.builder("thread_executor_active_count", executor::getActiveCount)
                    .tags(tags)
                    .register(registry);
            Gauge.builder("thread_executor_completed_task_total", executor::getCompletedTaskCount)
                    .tags(tags)
                    .register(registry);
            Gauge.builder("thread_executor_queue_size", () -> executor.getQueue().size())
                    .tags(tags)
                    .register(registry);
        }
    }
}
