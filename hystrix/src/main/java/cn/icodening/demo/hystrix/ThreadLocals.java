package cn.icodening.demo.hystrix;

/**
 * @author icodening
 * @date 2022.06.16
 */
public class ThreadLocals {

    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    private ThreadLocals() {
    }

    public static void set(String value) {
        THREAD_LOCAL.set(value);
    }

    public static String get() {
        return THREAD_LOCAL.get();
    }
}
