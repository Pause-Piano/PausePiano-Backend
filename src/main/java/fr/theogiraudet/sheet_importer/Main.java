package fr.theogiraudet.sheet_importer;

import com.opencsv.CSVReader;
import fr.theogiraudet.dao.PianosBddDao;
import fr.theogiraudet.resources.PianoData;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class Main {


    public static void main(String... args) {
        final var stream = Main.class.getClassLoader().getResourceAsStream("bdd.csv");
        final var dao = new PianosBddDao();
        Objects.requireNonNull(stream);

        try (final var reader = new CSVReader(new InputStreamReader(stream))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                if(line[0] == null)
                    break;
                if(!line[0].equals("Latitude")) {
                    final var piano = new PianoData();
                    piano.setLatitude(Double.parseDouble(line[0]));
                    piano.setLongitude(Double.parseDouble(line[1]));
                    piano.setType(convertType(line[2]));
                    piano.setAccessibility(convertAccessibility(line[3]));
                    piano.setImage("https://image.flaticon.com/icons/png/512/3100/3100457.png");
                    dao.addPiano(piano);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param accessibility l'accessibilité dans la syntaxe du CSV (non null)
     * @return la valeur parsée
     */
    private static String convertAccessibility(String accessibility) {
        Objects.requireNonNull(accessibility);
        switch(accessibility.toLowerCase().trim()) {
            case "libre": return "SELF_SERVICE";
            case "limité": return "CONDITIONALLY";
            case "privé": return "PRIVATE";
            default: throw new IllegalArgumentException("'" + accessibility.toLowerCase() + "'");
        }
    }

    /**
     * @param type le type dans la syntaxe du CSV (non null)
     * @return la valeur parsée
     */
    private static String convertType(String type) {
        Objects.requireNonNull(type);
        switch(type.toLowerCase()) {
            case "droit": return "UPRIGHT";
            case "queue": return "GRAND";
            case "electrique": return "ELECTRIC";
            default: throw new IllegalArgumentException();
        }
    }

}
