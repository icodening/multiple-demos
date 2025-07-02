package cn.icodening.demo.spring.boot.grpc;

import cn.icodening.demo.spring.boot.grpc.configuration.GrpcServerProperties;
import io.grpc.BindableService;
import io.grpc.ServerInterceptor;
import io.grpc.ServerServiceDefinition;
import io.grpc.servlet.jakarta.GrpcServlet;
import io.grpc.servlet.jakarta.ServletServerBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author icodening
 * @date 2025.04.22
 */
public class DefaultGrpcServletProvider extends ApplicationObjectSupport implements GrpcServletProvider {

    private final GrpcServerProperties properties;

    private List<ServerServiceDefinition> serviceDefinitions;

    public DefaultGrpcServletProvider(GrpcServerProperties properties) {
        this.properties = properties;
    }

    @Override
    public GrpcServlet getServlet() {
        ServletServerBuilder builder = new ServletServerBuilder();
        builder.maxInboundMessageSize((int) properties.getServlet().getMaxInboundMessageSize().toBytes());
        getServiceDefinitions().forEach(builder::addService);
        ApplicationContext applicationContext = obtainApplicationContext();
        applicationContext.getBeansOfType(ServerInterceptor.class).values().forEach(builder::intercept);
        return builder.buildServlet();
    }

    @Override
    public List<ServerServiceDefinition> getServiceDefinitions() {
        if (serviceDefinitions != null) {
            return serviceDefinitions;
        }
        ApplicationContext applicationContext = obtainApplicationContext();
        this.serviceDefinitions = applicationContext.getBeansOfType(BindableService.class).values()
                .stream().map(BindableService::bindService)
                .collect(Collectors.toList());
        return this.serviceDefinitions;
    }
}
