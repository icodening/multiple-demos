package cn.icodening.demo.spring.boot.grpc;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;

/**
 * @author icodening
 * @date 2025.05.11
 */
public class GrpcContextPathRewriteFilter extends HttpFilter {

    private final String contextPath;

    public GrpcContextPathRewriteFilter(String contextPath) {
        this.contextPath = contextPath;
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (Objects.isNull(contextPath) || Objects.equals("/", contextPath) || Objects.equals("", contextPath.trim())) {
            super.doFilter(request, response, chain);
            return;
        }
        String requestURI = request.getRequestURI();
        int idx = requestURI.indexOf(contextPath);
        if (idx == -1) {
            super.doFilter(request, response, chain);
            return;
        }
        String newURI = requestURI.substring(idx + contextPath.length());
        super.doFilter(new RewriteRequest(request, newURI), response, chain);
    }

    private static class RewriteRequest extends HttpServletRequestWrapper {

        private final String newURI;

        private RewriteRequest(HttpServletRequest request, String newURI) {
            super(request);
            this.newURI = newURI;
        }

        @Override
        public String getRequestURI() {
            return newURI;
        }
    }
}
