package fr.theogiraudet.dao;

import fr.theogiraudet.filter.*;
import fr.theogiraudet.resources.Piano;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Classe pour générer les filtres sur les pianos issues des paramètres HTTP
 */
public class FilterGenerator implements Visitor {

    private final List<String> queryFragments;
    private final List<Function<Stream<Piano>, Stream<Piano>>> functions;

    /**
     * Prépare la classe à générer les filtres en remplissant les deux listes passées en paramètre
     * @param queryFragments la liste des propositions SQL à mettre dans le 'WHERE' de la requête (modifiable, non null)
     * @param functions la liste des actions à effectuer sur la liste de piano (modifiable, non null)
     */
    public FilterGenerator(List<String> queryFragments, List<Function<Stream<Piano>, Stream<Piano>>> functions) {
        Objects.requireNonNull(queryFragments);
        Objects.requireNonNull(functions);

        this.queryFragments = queryFragments;
        this.functions = functions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitAll(List<? extends Visitable> list) {
        Objects.requireNonNull(list);
        list.forEach(v -> v.accept(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(AccessibilityFilter parameter) {
        Objects.requireNonNull(parameter);
        final var query = parameter.getAccessibilities()
                .stream()
                .map(a -> "accessibility = '" + a.toString() + '\'')
                .collect(Collectors.joining(" OR "));
        queryFragments.add('(' + query + ')');
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(TypeFilter parameter) {
        Objects.requireNonNull(parameter);
        final var query = parameter.getTypes()
                .stream()
                .map(a -> "type = '" + a.toString() + '\'')
                .collect(Collectors.joining(" OR "));
        queryFragments.add('(' + query + ')');
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(LimitFilter parameter) {
        Objects.requireNonNull(parameter);
        functions.add(s -> s.limit(parameter.getLimit()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(OffsetFilter parameter) {
        Objects.requireNonNull(parameter);
        functions.add(s -> s.skip(parameter.getOffset()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(LocationSorter parameter) {
        Objects.requireNonNull(parameter);
        functions.add(s -> s.sorted(Comparator.comparingDouble(p -> p.computeDistanceFrom(parameter.getLongitude(), parameter.getLatitude()))));
    }

    /**
     * @param parameter le ReverseFilter à visiter (non null)
     */
    @Override
    public void visit(ReverseSorter parameter) {
        Objects.requireNonNull(parameter);
        if(parameter.isReverse())
            functions.add(s -> {
                var list = s.collect(Collectors.toCollection(LinkedList::new));
                Collections.reverse(list);
                return list.stream();
            });
    }

    /**
     * @param parameter le RateFilter à visiter (non null)
     */
    @Override
    public void visit(RateSorter parameter) {
        Objects.requireNonNull(parameter);
        functions.add(s -> s.sorted(Comparator.comparingInt(Piano::getRate)));
    }
}
