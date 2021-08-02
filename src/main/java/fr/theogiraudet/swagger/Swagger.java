package fr.theogiraudet.swagger;

import fr.theogiraudet.rest.PianoResource;
import io.swagger.v3.jaxrs2.integration.resources.AcceptHeaderOpenApiResource;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationPath("/sample")
public class Swagger extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return Stream.of(PianoResource.class, OpenApiResource.class, AcceptHeaderOpenApiResource.class).collect(Collectors.toSet());
    }

}
