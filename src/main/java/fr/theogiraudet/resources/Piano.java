package fr.theogiraudet.resources;

import java.net.URL;

public interface Piano {

    /**
     * @return la latitude du piano
     */
    double getLatitude();

    /**
     * @param longitude la longitude du piano
     * @param latitude la latitude du piano
     */
    void setLocation(double longitude, double latitude);

    /**
     * @return la longitude du piano
     */
    double getLongitude();

    /**
     * @return la note du du piano, comprise entre 0 et 5
     */
    byte getRate();

    /**
     * @param rate la note du piano, comprise entre 0 et 5
     * @throws IllegalArgumentException si <i>rate</i> n'est pas compris entre 0 (inclus) et 5 (inclus)
     */
    void setRate(byte rate);

    /**
     * @return l'accessibilité du piano
     */
    Piano.Accessibility getAccessibility();

    /**
     * @param accessibility l'accessibilité du piano
     */
    void setAccessibility(Piano.Accessibility accessibility);

    /**
     * @return le type de piano
     */
    Piano.Type getType();

    /**
     * @param type le type de piano
     */
    void setType(Piano.Type type);

    /**
     * @return l'URL vers l'image du piano
     */
    URL getImage();

    /**
     * @param image l'URL vers l'image du piano
     */
    void setImage(URL image);

    /**
     * @return l'ID du piano
     */
    int getId();

    /**
     * @param id l'ID du piano
     */
    void setId(int id);

    /**
     * @param longitude la longitude du point source
     * @param latitude la latitude du point source
     * @return la distance entre le point spécifié en paramètre et la position de ce piano, en kilomètre
     */
    double computeDistanceFrom(double longitude, double latitude);

    /**
     * Représente les différentes possibilités d'accès au piano
     */
    enum Accessibility {
        /**
         * Piano accessible en libre service
         */
        SELF_SERVICE("accessibility.self_service"),
        /**
         * Piano accessible en libre service, sous condition d'accès à un autre service
         */
        CONDITIONALLY("accessibility.conditionally"),
        /**
         * Piano à accessibilité restreinte
         */
        PRIVATE("accessibility.private");

        private final String code;

        /**
         * @param code le code de traduction de la possibilité d'accès
         */
        Accessibility(String code) {
            this.code = code;
        }

        /**
         * @return le code de traduction de la possibilité d'accès
         */
        public String getCode() {
            return code;
        }
    }

    /**
     * Représente les différents types de piano
     */
    enum Type {
        /**
         * Piano électrique
         */
        ELECTRIC("type.electric"),
        /**
         * Piano à queue
         */
        GRAND("type.grand"),
        /**
         * Piano droit
         */
        UPRIGHT("type.upright");

        private final String code;

        /**
         * @param code le code de traduction de la possibilité d'accès
         */
        Type(String code) {
            this.code = code;
        }

        /**
         * @return le code de traduction de la possibilité d'accès
         */
        public String getCode() {
            return code;
        }
    }

}
