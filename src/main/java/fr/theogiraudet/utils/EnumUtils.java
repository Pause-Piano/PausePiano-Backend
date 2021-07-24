package fr.theogiraudet.utils;

public class EnumUtils {

    private EnumUtils() {}

    public static <E extends Enum<E>> E lookup(Class<E> clazz, String id) {
        try {
            return Enum.valueOf(clazz, id.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
