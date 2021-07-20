package fr.theogiraudet.rest;

import fr.theogiraudet.dao.PianosBddDao;
import fr.theogiraudet.resources.Piano;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/pianos")
public class PianoResource {

    private final PianosBddDao dao;

    {
        dao = new PianosBddDao();
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getPianos() {
        final var listOpt = dao.getAllPianos();
        if(listOpt.isPresent())
            return Response.ok().entity(listOpt.get().toArray(new Piano[0])).build();
        else
            return Response.serverError().build();
    }
}
