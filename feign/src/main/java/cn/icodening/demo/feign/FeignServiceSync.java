package cn.icodening.demo.feign;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 * @author icodening
 * @date 2022.11.15
 */
@Path("/demo")
public interface FeignServiceSync {

    @GET
    @Path("/sayHello")
    String sayHello(@QueryParam("string") String string);
}
