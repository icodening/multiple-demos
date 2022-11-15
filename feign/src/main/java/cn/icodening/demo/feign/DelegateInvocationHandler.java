package cn.icodening.demo.feign;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 增强feign生成的InvocationHandler，属于feign最前置的AOP实现方式，该阶段并未转换成HTTP请求模型，可以获得原始的方法名和参数
 * 场景：1.日志切面 2.参数校验 3.耗时统计 等
 *
 * @author icodening
 * @date 2022.11.15
 */
public class DelegateInvocationHandler implements InvocationHandler {

    /**
     * feign原始的动态代理处理器
     */
    private final InvocationHandler invocationHandler;

    public DelegateInvocationHandler(InvocationHandler invocationHandler) {
        this.invocationHandler = invocationHandler;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (args == null) {
            return invocationHandler.invoke(proxy, method, null);
        }
        for (int i = 0; i < args.length; i++) {
            System.out.println("params[" + i + "]:" + args[i]);
        }
        return invocationHandler.invoke(proxy, method, args);
    }
}
