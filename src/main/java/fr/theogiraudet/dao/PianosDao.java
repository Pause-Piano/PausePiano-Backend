package fr.theogiraudet.dao;

import fr.theogiraudet.filter.Parameter;
import fr.theogiraudet.resources.Piano;
import fr.theogiraudet.resources.PianoData;

import java.util.List;
import java.util.Optional;

/**
 * Interface d'accès aux données concernant les pianos
 */
public interface PianosDao {

    /**
     * @param parameters la liste des paramètres de la requête (non null)
     * @return la liste de tous les pianos, Optional.empty si une erreur est survenue lors de la récupération
     */
    Optional<List<Piano>> getAllPianos(List<Parameter> parameters);

    /**
     *
     * @param piano un Piano (non null)
     * @return Si le piano existe déjà, retourne l'instance du piano existant, sinon, retourne Optional.empty
     */
    Optional<Piano> exist(PianoData piano);

    /**
     * Ajoute un nouveau piano aux données
     * @param piano un nouveau piano (non null)
     * @return l'instance du nouveau piano (différente de celle passée en paramètre)
     */
    Optional<Piano> addPiano(PianoData piano);

    /**
     * Vide la table de pianos
     */
    void clearTable();

    /**
     * @param id un ID
     * @return le piano dont l'ID est <i>id</i> si il existe, Optional.empty sinon
     */
    Optional<Piano> getPiano(int id);

}
