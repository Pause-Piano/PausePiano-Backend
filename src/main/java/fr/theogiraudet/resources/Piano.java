package fr.theogiraudet.resources;

import jakarta.xml.bind.annotation.XmlRootElement;
import java.net.URL;

/**
 * Classe représentant un Piano
 */
@XmlRootElement(name="piano")
public class Piano {

    private int id;
    private double latitude;
    private double longitude;
    private byte rate;
    private Accessibility accessibility;
    private Type type;
    private URL image;

    /**
     * @return la latitude du piano
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude la latitude du piano
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return la longitude du piano
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude la longitude du piano
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * @return la note du du piano, comprise entre 0 et 5
     */
    public byte getRate() {
        return rate;
    }

    /**
     * @param rate la note du piano, comprise entre 0 et 5
     * @throws IllegalArgumentException si <i>rate</i> n'est pas compris entre 0 (inclus) et 5 (inclus)
     */
    public void setRate(byte rate) {
        if(rate >= 0 && rate <= 5)
            this.rate = rate;
        else
            throw new IllegalArgumentException("'rate' must be between 0 (incl.) and 5 (incl.).");
    }

    /**
     * @return l'accessibilité du piano
     */
    public Accessibility getAccessibility() {
        return accessibility;
    }

    /**
     * @param accessibility l'accessibilité du piano
     */
    public void setAccessibility(Accessibility accessibility) {
        this.accessibility = accessibility;
    }

    /**
     * @return le type de piano
     */
    public Type getType() {
        return type;
    }

    /**
     * @param type le type de piano
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * @return l'URL vers l'image du piano
     */
    public URL getImage() {
        return image;
    }

    /**
     * @param image l'URL vers l'image du piano
     */
    public void setImage(URL image) {
        this.image = image;
    }

    /**
     * @return l'ID du piano
     */
    public int getId() {
        return id;
    }

    /**
     * @param id l'ID du piano
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Représente les différentes possibilités d'accès au piano
     */
    public enum Accessibility {
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
    public enum Type {
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
