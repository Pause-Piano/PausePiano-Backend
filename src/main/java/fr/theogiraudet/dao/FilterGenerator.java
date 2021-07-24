package fr.theogiraudet.dao;

import fr.theogiraudet.filter.*;
import fr.theogiraudet.resources.Piano;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilterGenerator implements Visitor {

    private final List<String> queryFragments;
    private final List<Function<Stream<Piano>, Stream<Piano>>> functions;

    public FilterGenerator(List<String> queryFragments, List<Function<Stream<Piano>, Stream<Piano>>> functions) {
        this.queryFragments = queryFragments;
        this.functions = functions;
    }

    @Override
    public void visitAll(List<? extends Visitable> list) {
        list.forEach(v -> v.accept(this));
    }

    @Override
    public void visit(AccessibilityFilter parameter) {
        final var query = parameter.getAccessibilities()
                .stream()
                .map(a -> "accessibility = '" + a.toString() + '\'')
                .collect(Collectors.joining(" OR "));
        queryFragments.add('(' + query + ')');
    }

    @Override
    public void visit(TypeFilter parameter) {
        final var query = parameter.getTypes()
                .stream()
                .map(a -> "type = '" + a.toString() + '\'')
                .collect(Collectors.joining(" OR "));
        queryFragments.add('(' + query + ')');
    }

    @Override
    public void visit(LimitFilter parameter) {
        functions.add(s -> s.limit(parameter.getLimit()));
    }

    @Override
    public void visit(OffsetFilter parameter) {
        functions.add(s -> s.skip(parameter.getOffset()));
    }

    @Override
    public void visit(LocationSorter parameter) {
        functions.add(s -> s.sorted(Comparator.comparingDouble(p -> p.computeDistanceFrom(parameter.getLongitude(), parameter.getLatitude()))));
    }

}
