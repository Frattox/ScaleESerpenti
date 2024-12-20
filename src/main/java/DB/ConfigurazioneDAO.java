package DB;

import model.sistema.Sistema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConfigurazioneDAO{

    private final ConnectConfigurazioneDB connectDB;
    private Connection connection;
    private Sistema sistema;

    public ConfigurazioneDAO(Sistema sistema) {
        this.connectDB = new ConnectConfigurazioneDB();
        this.connection = connectDB.getConnection();
        this.sistema = sistema;
    }

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
        pstmt.setInt(1, Integer.parseInt(config.getNumeroRighe()));
        pstmt.setInt(2, Integer.parseInt(config.getNumeroColonne()));
        pstmt.setInt(3, Integer.parseInt(config.getNumeroGiocatori()));
        pstmt.setInt(4, Integer.parseInt(config.getNumeroScale()));
        pstmt.setInt(5, Integer.parseInt(config.getNumeroSerpenti()));
        pstmt.setBoolean(6, Boolean.parseBoolean(config.getVarianteDadoSingolo()));
        pstmt.setBoolean(7, Boolean.parseBoolean(config.getVarianteDadoSingoloFinale()));
        pstmt.setBoolean(8, Boolean.parseBoolean(config.getVarianteDoppioSei()));
        pstmt.setBoolean(9, Boolean.parseBoolean(config.getVarianteCaselleSosta()));
        pstmt.setBoolean(10, Boolean.parseBoolean(config.getVarianteCasellePremio()));
        pstmt.setBoolean(11, Boolean.parseBoolean(config.getVariantePescaCarta()));
        pstmt.setBoolean(12, Boolean.parseBoolean(config.getVarianteUlterioriCarte()));
        pstmt.setInt(13, Integer.parseInt(config.getNumeroCaselleSosta()));
        pstmt.setInt(14, Integer.parseInt(config.getNumeroCasellePremio()));
        pstmt.setInt(15, Integer.parseInt(config.getNumeroCasellePescaCarta()));
    }

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

    public List<ConfigurazioneGioco> getAll() {
        List<ConfigurazioneGioco> configurazioni = new ArrayList<>();
        String sql = "SELECT * FROM ConfigurazioneGioco";

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ConfigurazioneGioco config = new ConfigurazioneGioco(
                        rs.getInt("id"),
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
                configurazioni.add(config);
            }

        } catch (SQLException e) {
            System.out.println("Errore nel recupero delle configurazioni.");
            e.printStackTrace();
        }
        return configurazioni;
    }
}
