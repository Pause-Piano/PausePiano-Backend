package fr.theogiraudet.filter;

import java.util.List;
import java.util.function.Function;

/**
 * Enumération pour enregistrer les différents paramètres possibles appliquables sur la liste des pianos
 */
public enum ParameterRegister {

    ACCESSIBILITY("accessibility", AccessibilityFilter::new),
    TYPE("type", TypeFilter::new),
    LIMIT("limit", LimitFilter::new),
    OFFSET("offset", OffsetFilter::new),
    LOCATION("location", LocationSorter::new),
    RATE("rate", RateSorter::new),
    REVERSE("reverse", ReverseSorter::new);

    private final String id;
    private final Function<List<String>, Parameter> constructor;

    /**
     * @param id l'ID du paramètre, celui utilisé dans la requête HTTP
     * @param constructor le constructeur du paramètre à appeler
     */
    ParameterRegister(String id, Function<List<String>, Parameter> constructor) {
        this.id = id;
        this.constructor = constructor;
    }

    /**
     * @param list la liste des valeurs pour ce paramètre
     * @return un nouveau paramètre auquel la liste est donnée en paramètre de son constructeur
     */
    public Parameter build(List<String> list) {
        return constructor.apply(list);
    }

    /**
     * @return l'ID du paramètre
     */
    public String getId() {
        return id;
    }
}
