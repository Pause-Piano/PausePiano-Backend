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
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
    public Response getAllPianos(@Context UriInfo uriInfo) {
        final var listOpt = dao.getAllPianos();
        if(listOpt.isPresent()) {
            final var listFilteredOpt = filter(listOpt.get(), uriInfo);
            if(listFilteredOpt.isEmpty())
                return Response.status(Response.Status.BAD_REQUEST).build();
            return Response.ok().entity(listFilteredOpt.get().toArray(new Piano[0])).build();
        } else
            return Response.serverError().build();
    }

    /**
     * Filtre la liste en fonction des paramètres de l'URI
     * @param list la liste à filtrer
     * @param uriInfo les informations contenues dans l'URI
     * @return la liste filtrée, Optional.empty si une erreur est survenue
     */
    public Optional<List<Piano>> filter(List<Piano> list, UriInfo uriInfo) {

        final var params = uriInfo.getQueryParameters();
        Optional<Predicate<Piano>> predicate = Optional.of(p -> true);
        Optional<Function<Stream<Piano>, Stream<Piano>>> actions = Optional.of(Function.identity());

        if(params.containsKey("accessibility")) {
            final var predOpt = generateEnumFilter(Piano.Accessibility.values(), params.get("accessibility"), Piano::getAccessibility);
            predicate = predicate.flatMap(p -> predOpt.map(p::and));
        }

        if(params.containsKey("type")) {
            final var predOpt = generateEnumFilter(Piano.Type.values(), params.get("type"), Piano::getType);
            predicate = predicate.flatMap(p -> predOpt.map(p::and));
        }

        if(params.containsKey("offset")) {
            final var limit = parseIntQueryParam(params.get("offset"));
            actions = actions.flatMap(a -> limit.map(i -> a.andThen(s -> s.skip(i))));
        }

        if(params.containsKey("limit")) {
            final var limit = parseIntQueryParam(params.get("limit"));
            actions = actions.flatMap(a -> limit.map(i -> a.andThen(s -> s.limit(i))));
        }

        /*Optional<Double> longitude = null;
        if(params.containsKey("longitude"))
            longitude = parseDoubleQueryParam(params.get("longitude"));

        if(params.containsKey("latitude")) {
            latitude = parseDoubleQueryParam(params.get("latitude"));
        }*/

        final var pred = predicate;
        actions = actions.flatMap(a -> pred.map(p -> a.compose(s -> s.filter(p))));

        return actions.map(a -> a.apply(list.stream())).map(s -> s.collect(Collectors.toList()));
    }

    /**
     * @param values la liste des valeurs associée à un paramètre d'URI
     * @return le double valeur du paramètre si celui-ci est valide et seul élément de la liste, Optional.empty sinon
     */
    public Optional<Double> parseDoubleQueryParam(List<String> values) {
        if (values.size() > 1)
            return Optional.empty();
        try {
            final double number = Double.parseDouble(values.get(0));
            return Optional.of(number);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    /**
     * @param values la liste des valeurs associée à un paramètre d'URI
     * @return l'entier valeur du paramètre si celui-ci est valide et seul élément de la liste, Optional.empty sinon
     */
    public Optional<Integer> parseIntQueryParam(List<String> values) {
        if (values.size() > 1)
            return Optional.empty();
        try {
            final int limit = Integer.parseInt(values.get(0));
            return Optional.of(limit);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    /**
     * Génère le filtre sur un attribut Enum de Piano
     * @param possibilities la liste des valeurs possibles
     * @param provided la liste des valeurs fournies
     * @param attribute l'attribut de Piano sur lequel filtrer
     * @return un prédicat à appliquer pour filtrer, Optional.empty si les valeurs fournies ne sont pas inclues dans les valeurs possibles
     */
    public Optional<Predicate<Piano>> generateEnumFilter(Enum<?>[] possibilities, List<String> provided, Function<Piano, Object> attribute) {
        final var possibilitiesLC = Arrays.stream(possibilities)
                .map(e -> e.toString().toLowerCase())
                .collect(Collectors.toList());
        final var providedLC = provided
                .stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        if(!possibilitiesLC.containsAll(providedLC))

            return Optional.empty();
        return Optional.of(p -> provided.contains(attribute.apply(p).toString().toLowerCase()));
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    /**
     * Ajouter un nouveau piano
     * @param element les informations du piano à ajouter
     */
    public Response postPiano(PianoData element) {
        final var piano = element;
        final var existingPiano = dao.exist(piano);
        if(existingPiano.isPresent())
            return Response.status(Response.Status.CONFLICT)
                    .contentLocation(createUri(existingPiano.get().getId()))
                    .build();
        else {
            final var newPiano = dao.addPiano(piano);
            if(newPiano.isPresent())
                return Response.created(createUri(newPiano.get().getId()))
                    .build();
            else
                return Response.serverError().build();
        }
    }


    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    /**
     * Obtenir le piano dont l'ID est <i>id</i>
     * @param id l'ID du piano à obtenir
     */
    public Response getPiano(@PathParam("id") int id) {
        final var pianoOpt = dao.getPiano(id);
        if(pianoOpt.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).build();
        else
            return Response.ok().entity(pianoOpt.get()).build();
    }

    @DELETE
    /**
     * Vide la table de piano
     */
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
