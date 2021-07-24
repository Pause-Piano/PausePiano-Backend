package fr.theogiraudet.utils;

/**
 * Une classe utilitaire pour les énumérations
 */
public class EnumUtils {

    private EnumUtils() {}

    /**
     * @param clazz la classe de l'énumération
     * @param id l'ID de la valeur à parser
     * @param <E> l'énumération
     * @return la valeur parsée selon l'énumération de la valeur <i>id</i>
     */
    public static <E extends Enum<E>> E lookup(Class<E> clazz, String id) {
        try {
            return Enum.valueOf(clazz, id.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
