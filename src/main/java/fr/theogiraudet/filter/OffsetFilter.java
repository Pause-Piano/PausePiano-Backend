package fr.theogiraudet.filter;

import java.util.List;
import java.util.Objects;

/**
 * Classe représentant un paramètre de filtre pour jeter les premiers éléments de la liste de piano
 */
public class OffsetFilter implements Parameter {

    private int offset;
    private boolean isValid;

    /**
     * @param offsetParameters la liste de valeurs correspondant à ce paramètre (non null)
     */
    public OffsetFilter(List<String> offsetParameters) {
        Objects.requireNonNull(offsetParameters);
        isValid = offsetParameters.size() == 1;
        if(isValid) {
            try {
                offset = Integer.parseInt(offsetParameters.get(0));
                if(offset < 0)
                    isValid = false;
            } catch (NumberFormatException e) {
                isValid = false;
            }
        }
    }

    /**
     * @return la valeur de décalage stockée dans ce paramètre
     *  @throws IllegalStateException si ce paramètre n'est pas valide, c'est-à-dire si la liste donnée au constructeur
     *  contient plus ou moins d'un élément et si cet élément n'est pas interprétable comme étant un entier positif
     *  @see #isValid()
     */
    public int getOffset() {
        return offset;
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
        return 2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(Visitor visitor) {visitor.visit(this); }
}
