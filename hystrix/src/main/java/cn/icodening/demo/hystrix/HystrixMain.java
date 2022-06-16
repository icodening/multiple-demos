package cn.icodening.demo.hystrix;

import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;

import java.util.concurrent.ThreadPoolExecutor;

import java.util.function.Supplier;

/**
 * Hystrix线程池隔离策略下，线程的上下文传递解决方案demo.
 * <p>
 * DEMO原理:
 * 1.Hystrix构造线程池前会使用{@link HystrixPlugins}获取线程池策略
 * 2.优先使用System.getProperty("hystrix.plugin.HystrixConcurrencyStrategy.implementation");获取扩展策略的全限定名
 * 3.如果第2步没有取到值,则使用JDK原生SPI方式(ServiceLoader.load())加载HystrixConcurrencyStrategy的扩展实现
 * 4.扩展实现返回一个自定义的线程池,该线程池重写了{@link ThreadPoolExecutor#execute(Runnable)}包装真实Runnable,在执行之前会获取父线程上下文并传递后再执行真实业务逻辑
 *
 * <p>
 * 常见场景: 链路追踪链路ID传递、RequestContextHolder请求上下文传递、Slf4j中的MDC上下文传递
 *
 * @author icodening
 * @date 2022.06.16
 * @see HystrixConcurrencyStrategy
 * @see HystrixPlugins
 * @see CustomThreadPoolStrategy
 * @see CustomThreadPoolExecutor
 */
public class HystrixMain {

    public static void main(String[] args) {
        //demo默认使用系统环境变量演示扩展
        System.setProperty("hystrix.plugin.HystrixConcurrencyStrategy.implementation", "cn.icodening.demo.hystrix.CustomThreadPoolStrategy");

        CustomHystrixCommand command = new CustomHystrixCommand("Hello-World", buildSupplier());
        ThreadLocals.set("Hello World ThreadLocal Value");
        System.out.println(command.execute());
    }

    private static Supplier<String> buildSupplier() {
        return () -> {
            String threadName = Thread.currentThread().getName();
            String threadLocalValue = ThreadLocals.get();
            try {
                Thread.sleep(500);
                String result = "[%s] Success: Hello World Hystrix.\nThreadLocal value is: %s";
                return String.format(result, threadName, threadLocalValue);
            } catch (InterruptedException e) {
                String result = "[%s] Interrupted: Hello World Hystrix.\nThreadLocal value is: %s";
                return String.format(result, threadName, threadLocalValue);
            }
        };
    }
}
