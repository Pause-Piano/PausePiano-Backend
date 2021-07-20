package fr.theogiraudet.dao;

import fr.theogiraudet.resources.Piano;

import java.util.List;
import java.util.Optional;

/**
 * Interface d'accès aux données concernant les pianos
 */
public interface PianosDao {

    /**
     * @return la liste de tous les pianos, Optional.empty si une erreur est survenue lors de la récupération
     */
    Optional<List<Piano>> getAllPianos();

}
