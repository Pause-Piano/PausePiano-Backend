package fr.theogiraudet.filter;

/**
 * Une interface représentant un paramètre possible pour l'obtention de pianos
 */
public interface Parameter extends Visitable {

    /**
     * @return true si le paramètre est valide, false sinon
     */
    boolean isValid();

    /**
     * @return la priorité du paramètre, plus la priorité est faible, plus se paramètre sera appliqué tôt relativement
     * aux autres paramètres
     */
    int getPriority();
}