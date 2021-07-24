package fr.theogiraudet.resources;

import jakarta.xml.bind.annotation.XmlRootElement;
import org.geotools.referencing.GeodeticCalculator;

import java.awt.geom.Point2D;
import java.net.URL;

/**
 * Classe représentant un Piano
 */
@XmlRootElement(name="piano")
public class PianoImpl implements Piano {

    private int id;
    private Point2D location;
    private byte rate;
    private Accessibility accessibility;
    private Type type;
    private URL image;

    /**
     * @return la latitude du piano
     */
    @Override
    public double getLatitude() {
        return location.getY();
    }

    /**
     * @param longitude la longitude du piano
     * @param latitude la latitude du piano
     */
    @Override
    public void setLocation(double longitude, double latitude) {
        location = new Point2D.Double(longitude, latitude);
    }

    /**
     * @return la longitude du piano
     */
    @Override
    public double getLongitude() {
        return location.getX();
    }


    /**
     * @return la note du du piano, comprise entre 0 et 5
     */
    @Override
    public byte getRate() {
        // nouvelle_note = ((note * nb_vote) + vote) / nb_vote + 1
        return rate;
    }

    /**
     * @param rate la note du piano, comprise entre 0 et 5
     * @throws IllegalArgumentException si <i>rate</i> n'est pas compris entre 0 (inclus) et 5 (inclus)
     */
    @Override
    public void setRate(byte rate) {
        if(rate >= 0 && rate <= 5)
            this.rate = rate;
        else
            throw new IllegalArgumentException("'rate' must be between 0 (incl.) and 5 (incl.).");
    }

    /**
     * @return l'accessibilité du piano
     */
    @Override
    public Accessibility getAccessibility() {
        return accessibility;
    }

    /**
     * @param accessibility l'accessibilité du piano
     */
    @Override
    public void setAccessibility(Accessibility accessibility) {
        this.accessibility = accessibility;
    }

    /**
     * @return le type de piano
     */
    @Override
    public Type getType() {
        return type;
    }

    /**
     * @param type le type de piano
     */
    @Override
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * @return l'URL vers l'image du piano
     */
    @Override
    public URL getImage() {
        return image;
    }

    /**
     * @param image l'URL vers l'image du piano
     */
    @Override
    public void setImage(URL image) {
        this.image = image;
    }

    /**
     * @return l'ID du piano
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * @param id l'ID du piano
     */
    @Override
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param longitude la longitude du point source
     * @param latitude la latitude du point source
     * @return la distance entre le point spécifié en paramètre et la position de ce piano, en kilomètre
     */
    public double computeDistanceFrom(double longitude, double latitude) {
        final var pointRef = new Point2D.Double(longitude, latitude);
        final var calc = new GeodeticCalculator();

        calc.setStartingGeographicPoint(pointRef);
        calc.setDestinationGeographicPoint(location);
        return calc.getOrthodromicDistance() / 1000d;
    }
}
