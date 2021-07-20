package fr.theogiraudet.rest;

import fr.theogiraudet.dao.PianosBddDao;
import fr.theogiraudet.dao.PianosDao;
import fr.theogiraudet.resources.Piano;
import fr.theogiraudet.resources.PianoData;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;


@Path("/pianos")
/**
 * API Rest pour les pianos
 */
public class PianoResource {

    private final PianosDao dao;

    @Context
    private UriInfo uriInfo;

    {
        dao = new PianosBddDao();
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    /**
     * Obtenir tous les pianos
     */
    public Response getPianos() {
        final var listOpt = dao.getAllPianos();
        if(listOpt.isPresent())
            return Response.ok().entity(listOpt.get().toArray(new Piano[0])).build();
        else
            return Response.serverError().build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    /**
     * Ajouter un nouveau piano
     */
    public Response postPiano(PianoData element) {
        final var piano = element;
        final var existingPiano = dao.exist(piano);
        if(existingPiano.isPresent())
            return Response.status(Response.Status.CONFLICT)
                    .contentLocation(uriInfo.getAbsolutePathBuilder().path("" + existingPiano.get().getId()).build())
                    .build();
        else {
            final var newPiano = dao.addPiano(piano);
            if(newPiano.isPresent())
                return Response.created(uriInfo.getAbsolutePathBuilder().path("" + newPiano.get().getId()).build())
                    .build();
            else
                return Response.serverError().build();
        }
    }

    @DELETE
    /**
     * Vide la table de piano
     */
    public Response deleteAllPianos() {
        dao.clearTable();
        return Response.ok().build();
    }
}
