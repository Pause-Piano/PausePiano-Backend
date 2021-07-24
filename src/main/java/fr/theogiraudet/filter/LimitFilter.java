package fr.theogiraudet.filter;

import java.util.List;
import java.util.Objects;

/**
 * Classe représentant un paramètre de filtre sur le nombre maximum de piano à envoyer
 */
public class LimitFilter implements Parameter {

    private int limit;
    private boolean isValid;

    /**
     * @param limitParameters la liste de valeurs correspondant à ce paramètre (non null)
     */
    public LimitFilter(List<String> limitParameters) {
        Objects.requireNonNull(limitParameters);
        isValid = limitParameters.size() == 1;
        if(isValid) {
            try {
                limit = Integer.parseInt(limitParameters.get(0));
            } catch (NumberFormatException e) {
                isValid = false;
            }
        }
    }

    /**
     * @return les valeurs parsée d'accessibilité de ce paramètre
     * @throws IllegalStateException si le paramètre n'est pas valide, c'est-à-dire si la liste de paramètres donnée
     * au constructeur possède plus ou moins d'un élément et si cet élément n'est pas parsable vers un entier
     * @see #isValid()
     */
    public int getLimit() {
        if(!isValid())
            throw new IllegalStateException();
        return limit;
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
        return 3;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(Visitor visitor) {visitor.visit(this); }

}
