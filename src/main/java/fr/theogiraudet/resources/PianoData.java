package fr.theogiraudet.resources;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

@XmlRootElement(name = "piano")
public class PianoData {

    private double latitude;
    private double longitude;
    private Piano.Accessibility accessibility;
    private Piano.Type type;
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
     * @return l'accessibilité du piano
     */
    public Piano.Accessibility getAccessibility() {
        return accessibility;
    }

    /**
     * @param accessibility l'accessibilité du piano (non null)
     */
    public void setAccessibility(String accessibility) {
        Objects.requireNonNull(accessibility);
        this.accessibility = Piano.Accessibility.valueOf(accessibility);
    }

    /**
     * @return le type de piano
     */
    public Piano.Type getType() {
        return type;
    }

    /**
     * @param type le type de piano (non null)
     */
    public void setType(String type) {
        Objects.requireNonNull(type);
        this.type = Piano.Type.valueOf(type);
    }

    /**
     * @return l'URL vers l'image du piano
     */
    public URL getImage() {
        return image;
    }

    /**
     * @param image l'URL vers l'image du piano (non null)
     */
    public void setImage(String image) throws MalformedURLException {
        Objects.requireNonNull(image);
        this.image = new URL(image);
    }
}
