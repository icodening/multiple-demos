package cn.icodening.demo.feign;

import feign.InvocationHandlerFactory;
import feign.Target;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author icodening
 * @date 2022.11.15
 */
public class DelegateInvocationHandlerFactory implements InvocationHandlerFactory {

    private final InvocationHandlerFactory invocationHandlerFactory;

    public DelegateInvocationHandlerFactory(InvocationHandlerFactory invocationHandlerFactory) {
        this.invocationHandlerFactory = invocationHandlerFactory;
    }

    @Override
    public InvocationHandler create(Target target, Map<Method, MethodHandler> dispatch) {
        return new DelegateInvocationHandler(invocationHandlerFactory.create(target, dispatch));
    }
}
