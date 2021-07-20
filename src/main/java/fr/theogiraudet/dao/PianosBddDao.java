package fr.theogiraudet.dao;

import fr.theogiraudet.resources.Piano;
import fr.theogiraudet.PropertiesLoader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
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
            final var query = "SELECT ST_X(coordinates) as x, ST_Y(coordinates) as y, type, accessibility, rate, image FROM piano_project.pianos;";
            final var result = statement.executeQuery(query);
            final var list = new LinkedList<Piano>();

            while(result.next()) {
                final var piano = new Piano();
                piano.setLatitude(result.getDouble("x"));
                piano.setLongitude(result.getDouble("y"));
                piano.setAccessibility(Piano.Accessibility.valueOf(result.getString("accessibility").toUpperCase()));
                piano.setType(Piano.Type.valueOf(result.getString("type").toUpperCase()));
                piano.setRate(result.getByte("rate"));
                piano.setImage(new URL(result.getString("image")));

                list.add(piano);
            }

            return Optional.of(list);
        } catch(SQLException | MalformedURLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
