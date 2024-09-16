package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectConfigurazioneDB {

    private Connection connection;
    public ConnectConfigurazioneDB() {
        String url = "jdbc:sqlite:configurazioneGioco.db";
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("connection Successful");
            creaTabellaConfigurazione();
        } catch (SQLException e) {
            System.out.println("Error Connecting to Database");
            e.printStackTrace();
        }
    }
    public Connection getConnection() {return connection;}
    public void closeConnection() {
        try {
            if (connection != null) {
                System.out.println("Connection Closed");
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void creaTabellaConfigurazione() {
        String sql = "CREATE TABLE IF NOT EXISTS ConfigurazioneGioco ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "numeroRighe INTEGER NOT NULL,"
                + "numeroColonne INTEGER NOT NULL,"
                + "numeroGiocatori INTEGER NOT NULL,"
                + "numeroScale INTEGER NOT NULL,"
                + "numeroSerpenti INTEGER NOT NULL,"
                + "varianteDadoSingolo BOOLEAN NOT NULL,"
                + "varianteDadoSingoloFinale BOOLEAN NOT NULL,"
                + "varianteDoppioSei BOOLEAN NOT NULL,"
                + "varianteCaselleSosta BOOLEAN NOT NULL,"
                + "varianteCasellePremio BOOLEAN NOT NULL,"
                + "variantePescaCarta BOOLEAN NOT NULL,"
                + "numeroCaselleSosta INTEGER DEFAULT 0,"
                + "numeroCasellePremio INTEGER DEFAULT 0,"
                + "numeroCasellePescaCarta INTEGER DEFAULT 0"
                + ");";

        try (Statement stmt = connection.createStatement()) {
            // Esegui la query per creare la tabella
            stmt.execute(sql);
            System.out.println("Tabella ConfigurazioneGioco verificata/creata con successo.");
        } catch (SQLException e) {
            System.out.println("Errore nella creazione della tabella.");
            e.printStackTrace();
        }
    }
}

