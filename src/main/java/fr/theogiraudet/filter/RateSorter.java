package fr.theogiraudet.filter;

import java.util.List;
import java.util.Objects;

public class RateSorter implements Parameter {

    private final boolean isValid;

    /**
     * @param parameter la liste de valeurs correspondant à ce paramètre (non null)
     */
    public RateSorter(List<String> parameter) {
        Objects.requireNonNull(parameter);
        isValid = parameter.isEmpty();
    }

    /**
     * @return true si le paramètre est valide, false sinon
     */
    @Override
    public boolean isValid() {
        return isValid;
    }

    /**
     * @return la priorité du paramètre, plus la priorité est faible, plus se paramètre sera appliqué tôt relativement
     * aux autres paramètres
     */
    @Override
    public int getPriority() {
        return 4;
    }

    /**
     * @param visitor le visitor à parcourir
     */
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
