package cn.icodening.demo.spring.boot.grpc.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author icodening
 * @date 2025.04.22
 */
@Data
@ConfigurationProperties(prefix = "grpc.server")
public class GrpcServerProperties {

    private Servlet servlet = new Servlet();

    @Data
    public static class Servlet {

        private String name = "grpc-servlet";

        private String contextPath = "/";

        private DataSize maxInboundMessageSize = DataSize.ofMegabytes(4);

        private Map<String, String> initParameters = new LinkedHashMap<>();
    }
}
