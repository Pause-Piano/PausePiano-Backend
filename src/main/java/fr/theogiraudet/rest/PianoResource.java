package fr.theogiraudet.rest;

import fr.theogiraudet.dao.PianosBddDao;
import fr.theogiraudet.dao.PianosDao;
import fr.theogiraudet.filter.Parameter;
import fr.theogiraudet.filter.ParameterRegister;
import fr.theogiraudet.resources.Piano;
import fr.theogiraudet.resources.PianoData;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/pianos")
public class PianoResource {

    private final PianosDao dao;

    @Context
    private UriInfo uriInfo;

    public PianoResource() {
        dao = new PianosBddDao();
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAllPianos(@Context UriInfo uriInfo) {
        final var optParams = filter(uriInfo);
        if (optParams.isEmpty())
            return Response.status(Response.Status.BAD_REQUEST).build();

        final var listOpt = dao.getAllPianos(optParams.get());
        if (listOpt.isEmpty())
            return Response.serverError().build();

        return Response.ok().entity(listOpt.get().toArray(new Piano[0])).build();
    }

    /**
     * Filtre la liste en fonction des paramètres de l'URI
     *
     * @param uriInfo les informations contenues dans l'URI (non null)
     * @return la liste filtrée, Optional.empty si une erreur est survenue
     */
    public Optional<List<Parameter>> filter(UriInfo uriInfo) {
        Objects.requireNonNull(uriInfo);

        final var params = uriInfo.getQueryParameters();
        final var parsedParam = Arrays.stream(ParameterRegister.values())
                .filter(p -> params.containsKey(p.getId()))
                .map(p -> p.build(params.get(p.getId())))
                .collect(Collectors.toList());

        if (params.size() != parsedParam.size() || !parsedParam.stream().allMatch(Parameter::isValid))
            return Optional.empty();
        return Optional.of(parsedParam);
    }


    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response postPiano(PianoData piano) {
        if (piano == null)
            return Response.status(Response.Status.BAD_REQUEST).build();
        final var existingPiano = dao.exist(piano);
        if (existingPiano.isPresent())
            return Response.status(Response.Status.CONFLICT)
                    .contentLocation(createUri(existingPiano.get().getId()))
                    .build();
        else {
            final var newPiano = dao.addPiano(piano);
            if (newPiano.isPresent())
                return Response.created(createUri(newPiano.get().getId()))
                        .build();
            else
                return Response.serverError().build();
        }
    }


    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getPiano(@PathParam("id") int id) {
        final var pianoOpt = dao.getPiano(id);
        if (pianoOpt.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).build();
        else
            return Response.ok().entity(pianoOpt.get()).build();
    }

    @DELETE
    public Response deleteAllPianos() {
        dao.clearTable();
        return Response.ok().build();
    }

    /**
     * @param id l'ID de la ressource
     * @return l'URI vers la ressource
     */
    private URI createUri(int id) {
        return uriInfo.getAbsolutePathBuilder().path(Integer.toString(id)).build();
    }
}
