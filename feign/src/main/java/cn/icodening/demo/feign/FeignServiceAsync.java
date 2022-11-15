package cn.icodening.demo.feign;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.concurrent.CompletableFuture;

/**
 * @author icodening
 * @date 2022.11.15
 */
@Path("/demo")
public interface FeignServiceAsync {

    @GET
    @Path("/sayHello")
    CompletableFuture<String> sayHelloAsync(@QueryParam("string") String string);
}
