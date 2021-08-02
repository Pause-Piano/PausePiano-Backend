package fr.theogiraudet.filter;

import java.util.List;
import java.util.Objects;

public class ReverseSorter implements Parameter {

    private boolean isValid;
    private boolean reverse;

    /**
     * @param rateParameters la liste de valeurs correspondant à ce paramètre (non null)
     */
    public ReverseSorter(List<String> rateParameters) {
        Objects.requireNonNull(rateParameters);
        isValid = rateParameters.size() == 1;

        if(isValid) {
            switch(rateParameters.get(0).toLowerCase()) {
                case "true": isValid = true; break;
                case "false": isValid = false; break;
                default: isValid = false; reverse = false;
            }
        }
    }


    /**
     * @return true si le paramètre est un boolean valide, false sinon
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
        return 5;
    }

    /**
     * @param visitor le visitor à parcourir
     */
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    /**
     * @return true si la liste doit être inversé, false sinon
     */
    public boolean isReverse() {
        return reverse;
    }
}
