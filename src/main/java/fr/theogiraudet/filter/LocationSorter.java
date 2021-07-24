package fr.theogiraudet.filter;

import java.util.List;
import java.util.Objects;

/**
 * Classe représentant un paramètre de trie sur la proximité d'un piano à un point donné
 */
public class LocationSorter implements Parameter {

    private double latitude;
    private double longitude;
    private boolean isValid;

    /**
     * @param params la liste de valeurs correspondant à ce paramètre (non null)
     */
    public LocationSorter(List<String> params) {
        Objects.requireNonNull(params);
        if(params.size() != 1)
            unvalidate();
        else {
            try {
                final var param = params.get(0).split(",");
                if(param.length != 2)
                    unvalidate();
                else {
                    longitude = Double.parseDouble(param[0]);
                    latitude = Double.parseDouble(param[1]);
                    isValid = true;
                }
            } catch (NumberFormatException e) {
                unvalidate();
            }
        }
    }

    /**
     * Invalide le paramètre
     */
    private void unvalidate() {
        isValid = false;
        longitude = latitude = Double.MIN_VALUE;
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
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    /**
     * @return la latitude stockée dans ce paramètre
     *  @throws IllegalStateException si ce paramètre n'est pas valide, c'est-à-dire si la liste donnée au constructeur
     *  contient plus ou moins d'un élément et si cet élément n'est pas interprétable comme étant un
     *  <code>&lt;longitude: double&gt;,&lt;latitude: double&gt;</code>
     *  @see #isValid()
     */
    public double getLatitude() {
        if(!isValid())
            throw new IllegalStateException();
        return latitude;
    }


    /**
     * @return la longitude stockée dans ce paramètre
     *  @throws IllegalStateException si ce paramètre n'est pas valide, c'est-à-dire si la liste donnée au constructeur
     *  contient plus ou moins d'un élément et si cet élément n'est pas interprétable comme étant un
     *  <code>&lt;longitude: double&gt;,&lt;latitude: double&gt;</code>
     */
    public double getLongitude() {
        if(!isValid())
            throw new IllegalStateException();
        return longitude;
    }
}
