package org.example.webservice;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import javax.jws.WebService;
import java.util.Map;

@WebService
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationPath("hello")
public interface HelloService {

    @GET
    @Path("test")
    Map<String, Object> test(@BeanParam Map<String, Object> param);
}
