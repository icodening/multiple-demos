package cn.icodening.demo.spring.boot.grpc;

import io.grpc.ServerServiceDefinition;
import jakarta.servlet.Servlet;

import java.util.List;

/**
 * @author icodening
 * @date 2025.04.22
 */
public interface GrpcServletProvider {

    Servlet getServlet();

    List<ServerServiceDefinition> getServiceDefinitions();
}
