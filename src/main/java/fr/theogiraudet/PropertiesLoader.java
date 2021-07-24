package fr.theogiraudet;

import java.io.IOException;
import java.util.Properties;

/**
 * Classe stockant les propriétés du fichier 'pause_piano.properties'
 */
public class PropertiesLoader {

    private static PropertiesLoader instance;

    /**
     * @return une instance du loader
     * @throws IOException si aucun fichier 'pause_piano.properties' n'a été trouvé dans le répertoire courant
     */
    public static PropertiesLoader getInstance() throws IOException {
        if(instance == null)
            instance = new PropertiesLoader();
        return instance;
    }

    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    /**
     * Charge les propriétés du fichier 'pause_piano.properties' du répertoire courant
     * @throws IOException si aucun fichier 'pause_piano.properties' n'a été trouvé dans le répertoire courant
     */
    private PropertiesLoader() throws IOException {
        try (final var input = getClass().getClassLoader().getResourceAsStream("pause_piano.properties")) {

            Properties prop = new Properties();
            prop.load(input);

            dbUrl = prop.getProperty("db.url");
            dbUser = prop.getProperty("db.user");
            dbPassword = prop.getProperty("db.password");

        } catch (IOException ex) {
            throw new IOException("Properties file not found.");
        }
    }

    /**
     * @return l'URL d'accès à la vase de données
     */
    public String getDbUrl() {
        return dbUrl;
    }

    /**
     * @return l'utilisateur pour accéder à la base de données
     */
    public String getDbUser() {
        return dbUser;
    }

    /**
     *
     * @return le mot de passe d'accès à la base de données
     */
    public String getDbPassword() {
        return dbPassword;
    }
}
