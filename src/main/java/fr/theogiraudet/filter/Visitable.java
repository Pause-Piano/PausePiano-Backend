package fr.theogiraudet.filter;

/**
 * Représente un objet visitable (pattern visitor)
 */
public interface Visitable {

    /**
     * @param visitor le visitor à parcourir
     */
    void accept(Visitor visitor);

}
