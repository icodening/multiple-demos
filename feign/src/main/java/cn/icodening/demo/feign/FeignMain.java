package cn.icodening.demo.feign;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import feign.AsyncFeign;
import feign.Capability;
import feign.Contract;
import feign.Feign;
import feign.jaxrs2.JAXRS2Contract;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author icodening
 * @date 2022.11.15
 */
public class FeignMain {

    public static void main(String[] args) throws IOException {
        int port = 8080;
        String remoteAddress = "http://127.0.0.1:" + port;
        HttpServer httpServer = startInternalServer(port);
        Contract contract = new JAXRS2Contract();
        Capability capability = new DelegateCapability();

        //sync client
        FeignServiceSync feignServiceSync = Feign.builder()
                .contract(contract)
                .addCapability(capability)
                .target(FeignServiceSync.class, remoteAddress);
        System.out.println(feignServiceSync.sayHello("hello world"));

        //async client
        FeignServiceAsync feignServiceAsync = AsyncFeign.builder()
                .contract(contract)
                .addCapability(capability)
                .target(FeignServiceAsync.class, remoteAddress);
        feignServiceAsync.sayHelloAsync("hello world async")
                .thenAcceptAsync(System.out::println)
                .thenRun(() -> httpServer.stop(0));

    }

    private static HttpServer startInternalServer(int port) throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        httpServer.createContext("/demo/sayHello", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                Map<String, String> queryMap = decodeQuery(exchange.getRequestURI().getQuery());
                String resp = "echo from server:" + queryMap.get("string");
                exchange.sendResponseHeaders(200, resp.getBytes().length);
                try (OutputStream outputStream = exchange.getResponseBody()) {
                    outputStream.write(resp.getBytes());
                    outputStream.flush();
                }
            }
        });
        httpServer.start();
        return httpServer;
    }

    private static Map<String, String> decodeQuery(String queryString) {
        Map<String, String> queryMap = new HashMap<>();
        String[] kvs = queryString.split("&");
        for (String entry : kvs) {
            String[] kv = entry.split("=");
            String value = null;
            if (kv.length == 2) {
                value = kv[1];
            }
            queryMap.put(kv[0], value);
        }
        return queryMap;
    }
}
