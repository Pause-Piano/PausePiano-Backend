package fr.theogiraudet.filter;

import fr.theogiraudet.resources.Piano;
import fr.theogiraudet.utils.EnumUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Classe représentant un paramètre de filtre sur le type du piano
 */
public class TypeFilter implements Parameter {

    private final List<Piano.Type> types;
    private final boolean isValid;

    /**
     * @param types la liste de valeurs correspondant à ce paramètre (non null)
     */
    public TypeFilter(List<String> types) {
        Objects.requireNonNull(types);
        this.types = types.stream()
                .map(v -> EnumUtils.lookup(Piano.Type.class, v))
                .collect(Collectors.toList());
        this.isValid = !types.contains(null);
    }

    /**
     * @return les valeurs parsée de type de ce paramètre
     * @throws IllegalStateException si les valeurs n'ont pas pu être parsées
     * @see #isValid()
     */
    public List<Piano.Type> getTypes() {
        return Collections.unmodifiableList(types);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid() {
        return isValid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPriority() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(Visitor visitor) {visitor.visit(this); }
}
