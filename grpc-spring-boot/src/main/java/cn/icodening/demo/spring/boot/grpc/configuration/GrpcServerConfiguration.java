package cn.icodening.demo.spring.boot.grpc.configuration;

import cn.icodening.demo.spring.boot.grpc.DefaultGrpcServletProvider;
import cn.icodening.demo.spring.boot.grpc.GrpcContextPathRewriteFilter;
import cn.icodening.demo.spring.boot.grpc.GrpcServletProvider;
import jakarta.servlet.Filter;
import jakarta.servlet.Servlet;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @author icodening
 * @date 2025.04.22
 */
@EnableConfigurationProperties(GrpcServerProperties.class)
@Configuration
@RequiredArgsConstructor
public class GrpcServerConfiguration {

    private final GrpcServerProperties grpcServerProperties;

    @Bean
    @ConditionalOnMissingBean(GrpcServletProvider.class)
    public GrpcServletProvider defaultGrpcServletProvider() {
        return new DefaultGrpcServletProvider(grpcServerProperties);
    }

    @Bean
    public ServletRegistrationBean<Servlet> grpcServlet(GrpcServletProvider grpcServletProvider) {
        Servlet grpcServlet = grpcServletProvider.getServlet();
        ServletRegistrationBean<Servlet> servletRegistrationBean = new ServletRegistrationBean<>(grpcServlet);
        servletRegistrationBean.setName(grpcServerProperties.getServlet().getName());
        servletRegistrationBean.setInitParameters(grpcServerProperties.getServlet().getInitParameters());
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<Filter> grpcRewriteFilter() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new GrpcContextPathRewriteFilter(this.grpcServerProperties.getServlet().getContextPath()));
        filterFilterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        filterFilterRegistrationBean.addServletNames(grpcServerProperties.getServlet().getName());
        filterFilterRegistrationBean.setName(grpcServerProperties.getServlet().getName() + "-rewrite");
        return filterFilterRegistrationBean;
    }

}
