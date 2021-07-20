package fr.theogiraudet.dao;

import fr.theogiraudet.PropertiesLoader;
import fr.theogiraudet.resources.Piano;
import fr.theogiraudet.resources.PianoData;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Implémentation de PianosDao pour une base de données
 */
public class PianosBddDao implements PianosDao {

    // Lancer la BDD : docker run -p 3306:3306 --name mysql2 -e MYSQL_ROOT_PASSWORD=123456789 -e MYSQL_ROOT_HOST=% -d mysql/mysql-server:latest

    private Connection connection = null;

    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            tryConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tente de se connecter à la base de données à partir des identifiants présents dans le fichier properties
     */
    private void tryConnection() {
        try {
            final var loader = PropertiesLoader.getInstance();
            connection = DriverManager.getConnection(loader.getDbUrl(), loader.getDbUser(), loader.getDbPassword());
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void tryClose() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return la liste de tous les pianos, Optional.empty si une erreur est survenue lors de la récupération
     */
    @Override
    public Optional<List<Piano>> getAllPianos() {
        try(final var statement = connection.createStatement()) {
            final var query = "SELECT id, ST_X(coordinates) as x, ST_Y(coordinates) as y, type, accessibility, rate, image FROM piano_project.pianos;";
            final var result = statement.executeQuery(query);
            final var list = new LinkedList<Piano>();

            while(result.next()) {
                list.add(toPiano(result));
            }

            return Optional.of(list);
        } catch(SQLException | MalformedURLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * @param piano un PianoData
     * @return vrai si le piano existe déjà parmi les données, false sinon
     */
    @Override
    public Optional<Piano> exist(PianoData piano) {
        final var query = "SELECT id, ST_X(coordinates) as x, ST_Y(coordinates) as y, type, accessibility, rate, image FROM piano_project.pianos WHERE ST_X(coordinates) = ? AND ST_Y(coordinates) = ?;";
        try(final var statement = connection.prepareStatement(query)) {
            statement.setDouble(1, piano.getLongitude());
            statement.setDouble(2, piano.getLatitude());
            final var result = statement.executeQuery();
            return result.next() ? Optional.of(toPiano(result)) : Optional.empty();
        } catch (SQLException | MalformedURLException throwables) {
            throwables.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * Ajoute un nouveau piano aux données
     * @param piano un nouveau piano
     * @return l'instance du nouveau piano (différente de celle passée en paramètre)
     */
    @Override
    public Optional<Piano> addPiano(PianoData piano) {
        final var query = "INSERT INTO piano_project.pianos (coordinates, type, accessibility, rate, image) VALUE (POINT(?, ?), ?, ?, ?, ?);";
        try(final var statement = connection.prepareStatement(query)) {
            statement.setDouble(1, piano.getLongitude());
            statement.setDouble(2, piano.getLatitude());
            statement.setString(3, piano.getType().toString());
            statement.setString(4, piano.getAccessibility().toString());
            statement.setByte(5, (byte)0);
            statement.setString(6, piano.getImage().toString());
            statement.executeUpdate();
            return getLastPiano();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return Optional.empty();
        }
    }


    /**
     * @return le Piano avec le plus grand ID
     */
    private Optional<Piano> getLastPiano() {
        final var query = "SELECT id, ST_X(coordinates) as x, ST_Y(coordinates) as y, type, accessibility, rate, image FROM piano_project.pianos WHERE id = (SELECT MAX(id) FROM piano_project.pianos);";
        try(final var statement = connection.createStatement()) {
            final var result = statement.executeQuery(query);
            return result.next() ? Optional.of(toPiano(result)) : Optional.empty();
        } catch (SQLException | MalformedURLException throwables) {
            throwables.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * Vide la table de pianos
     */
    public void clearTable() {
        final var query = "TRUNCATE TABLE piano_project.pianos";
        try(final var statement = connection.createStatement()) {
            final var result = statement.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * @param result un ResultSet
     * @return le premier Piano du ResultSet passé en paramètre
     * @throws SQLException
     * @throws MalformedURLException
     */
    private Piano toPiano(ResultSet result) throws SQLException, MalformedURLException {
        final var piano = new Piano();
        piano.setId(result.getInt("id"));
        piano.setLatitude(result.getDouble("y"));
        piano.setLongitude(result.getDouble("x"));
        piano.setAccessibility(Piano.Accessibility.valueOf(result.getString("accessibility").toUpperCase()));
        piano.setType(Piano.Type.valueOf(result.getString("type").toUpperCase()));
        piano.setRate(result.getByte("rate"));
        piano.setImage(new URL(result.getString("image")));
        return piano;
    }
}
