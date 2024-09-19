package DB;

import model.Sistema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfigurazioneDAO{

    private final ConnectConfigurazioneDB connectDB;
    private Connection connection;
    private Sistema sistema;

    public ConfigurazioneDAO(Sistema sistema) {
        this.connectDB = new ConnectConfigurazioneDB();
        this.connection = connectDB.getConnection();
        this.sistema = sistema;
    }

    // Metodo per salvare una nuova configurazione
    public void save() {
        String sql = "INSERT INTO ConfigurazioneGioco (numeroRighe, numeroColonne, numeroGiocatori, numeroScale, numeroSerpenti, "
                + "varianteDadoSingolo, varianteDadoSingoloFinale, varianteDoppioSei, varianteCaselleSosta, varianteCasellePremio, "
                + "variantePescaCarta, varianteUlterioriCarte, numeroCaselleSosta, numeroCasellePremio, numeroCasellePescaCarta) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ConfigurazioneGioco config = sistema.getConfigurazioneGioco();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            setParameters(config, pstmt);
            pstmt.executeUpdate();
            System.out.println("Configurazione salvata con successo.");
        } catch (SQLException e) {
            System.out.println("Errore nel salvataggio della configurazione.");
            e.printStackTrace();
        }
    }

    private void setParameters(ConfigurazioneGioco config, PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, config.getNumeroRighe());
        pstmt.setInt(2, config.getNumeroColonne());
        pstmt.setInt(3, config.getNumeroGiocatori());
        pstmt.setInt(4, config.getNumeroScale());
        pstmt.setInt(5, config.getNumeroSerpenti());
        pstmt.setBoolean(6, config.isVarianteDadoSingolo());
        pstmt.setBoolean(7, config.isVarianteDadoSingoloFinale());
        pstmt.setBoolean(8, config.isVarianteDoppioSei());
        pstmt.setBoolean(9, config.isVarianteCaselleSosta());
        pstmt.setBoolean(10, config.isVarianteCasellePremio());
        pstmt.setBoolean(11, config.isVariantePescaCarta());
        pstmt.setBoolean(12, config.isVarianteUlterioriCarte());
        pstmt.setInt(13, config.getNumeroCaselleSosta());
        pstmt.setInt(14, config.getNumeroCasellePremio());
        pstmt.setInt(15, config.getNumeroCasellePescaCarta());
    }

    // Metodo per recuperare una configurazione per ID
    public ConfigurazioneGioco getById(int id) {
        String sql = "SELECT * FROM ConfigurazioneGioco WHERE id = ?";
        ConfigurazioneGioco config = null;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                config = new ConfigurazioneGioco(
                        rs.getInt("numeroRighe"),
                        rs.getInt("numeroColonne"),
                        rs.getInt("numeroGiocatori"),
                        rs.getInt("numeroScale"),
                        rs.getInt("numeroSerpenti"),
                        rs.getBoolean("varianteDadoSingolo"),
                        rs.getBoolean("varianteDadoSingoloFinale"),
                        rs.getBoolean("varianteDoppioSei"),
                        rs.getBoolean("varianteCaselleSosta"),
                        rs.getBoolean("varianteCasellePremio"),
                        rs.getBoolean("variantePescaCarta"),
                        rs.getBoolean("varianteUlterioriCarte"),
                        rs.getInt("numeroCaselleSosta"),
                        rs.getInt("numeroCasellePremio"),
                        rs.getInt("numeroCasellePescaCarta")
                );
            }
        } catch (SQLException e) {
            System.out.println("Errore nel recupero della configurazione.");
            e.printStackTrace();
        }

        return config;
    }

    // Metodo per aggiornare una configurazione
    public void aggiornaConfigurazione(ConfigurazioneGioco config, int id) {
        String sql = "UPDATE ConfigurazioneGioco SET "
                + "numeroRighe = ?, numeroColonne = ?, numeroGiocatori = ?, numeroScale = ?, numeroSerpenti = ?, "
                + "varianteDadoSingolo = ?, varianteDadoSingoloFinale = ?, varianteDoppioSei = ?, "
                + "varianteCaselleSosta = ?, varianteCasellePremio = ?, variantePescaCarta = ?,  varianteUlterioriCarte = ?,"
                + "numeroCaselleSosta = ?, numeroCasellePremio = ?, numeroCasellePescaCarta = ? "
                + "WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            setParameters(config, pstmt);
            pstmt.setInt(16, id);

            pstmt.executeUpdate();
            System.out.println("Configurazione aggiornata con successo.");
        } catch (SQLException e) {
            System.out.println("Errore nell'aggiornamento della configurazione.");
            e.printStackTrace();
        }
    }

    // Metodo per eliminare una configurazione
    public void deleteById(int id) {
        String sql = "DELETE FROM ConfigurazioneGioco WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Configurazione eliminata con successo.");
        } catch (SQLException e) {
            System.out.println("Errore nell'eliminazione della configurazione.");
            e.printStackTrace();
        }
    }
}
