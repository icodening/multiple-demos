package cn.icodening.demo.feign;

import feign.Capability;
import feign.InvocationHandlerFactory;

/**
 * @author icodening
 * @date 2022.11.15
 */
public class DelegateCapability implements Capability {

    @Override
    public InvocationHandlerFactory enrich(InvocationHandlerFactory invocationHandlerFactory) {
        return new DelegateInvocationHandlerFactory(invocationHandlerFactory);
    }
}
