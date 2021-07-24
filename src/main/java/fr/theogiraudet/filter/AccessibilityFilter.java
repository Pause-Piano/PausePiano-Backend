package fr.theogiraudet.filter;

import fr.theogiraudet.resources.Piano;
import fr.theogiraudet.utils.EnumUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Classe représentant un paramètre de filtre sur l'accessibilité du piano
 */
public class AccessibilityFilter implements Parameter {

    private final List<Piano.Accessibility> accessibilities;
    private final boolean isValid;

    /**
     * @param accessibilities la liste de valeurs correspondant à ce paramètre (non null)
     */
    public AccessibilityFilter(List<String> accessibilities) {
        Objects.requireNonNull(accessibilities);
        this.accessibilities = accessibilities.stream()
                .map(v -> EnumUtils.lookup(Piano.Accessibility.class, v))
                .collect(Collectors.toList());
        this.isValid = !accessibilities.contains(null);
    }

    /**
     * @return les valeurs parsée d'accessibilité de ce paramètre
     * @throws IllegalStateException si les valeurs n'ont pas pu être parsées
     * @see #isValid()
     */
    public List<Piano.Accessibility> getAccessibilities() {
        if(!isValid())
            throw new IllegalStateException();
        return Collections.unmodifiableList(accessibilities);
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
    public boolean isValid() {
        return isValid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(Visitor visitor) {visitor.visit(this); }

}
