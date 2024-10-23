package DB;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ConfigurazioneGioco {

    public ConfigurazioneGioco(int numeroRighe, int numeroColonne, int numeroGiocatori, int numeroScale,
                               int numeroSerpenti, boolean varianteDadoSingolo, boolean varianteDadoSingoloFinale,
                               boolean varianteDoppioSei, boolean varianteCaselleSosta, boolean varianteCasellePremio,
                               boolean variantePescaCarta, boolean varianteUlterioriCarte, int numeroCaselleSosta,
                               int numeroCasellePremio, int numeroCasellePescaCarta) {
        setNumeroRighe(String.valueOf(numeroRighe));
        setNumeroColonne(String.valueOf(numeroColonne));
        setNumeroGiocatori(String.valueOf(numeroGiocatori));
        setNumeroScale(String.valueOf(numeroScale));
        setNumeroSerpenti(String.valueOf(numeroSerpenti));
        setVarianteDadoSingolo(String.valueOf(varianteDadoSingolo));
        setVarianteDadoSingoloFinale(String.valueOf(varianteDadoSingoloFinale));
        setVarianteDoppioSei(String.valueOf(varianteDoppioSei));
        setVarianteCaselleSosta(String.valueOf(varianteCaselleSosta));
        setVarianteCasellePremio(String.valueOf(varianteCasellePremio));
        setVariantePescaCarta(String.valueOf(variantePescaCarta));
        setVarianteUlterioriCarte(String.valueOf(varianteUlterioriCarte));
        setNumeroCaselleSosta(String.valueOf(numeroCaselleSosta));
        setNumeroCasellePremio(String.valueOf(numeroCasellePremio));
        setNumeroCasellePescaCarta(String.valueOf(numeroCasellePescaCarta));
    }


    private StringProperty numeroRighe;
    public void setNumeroRighe(String value) { numeroRigheProperty().set(value); }
    public String getNumeroRighe() { return numeroRigheProperty().get(); }
    public StringProperty numeroRigheProperty() {
        if (numeroRighe == null) numeroRighe = new SimpleStringProperty(this, "numeroRighe");
        return numeroRighe;
    }

    private StringProperty numeroColonne;
    public void setNumeroColonne(String value) { numeroColonneProperty().set(value); }
    public String getNumeroColonne() { return numeroColonneProperty().get(); }
    public StringProperty numeroColonneProperty() {
        if (numeroColonne == null) numeroColonne = new SimpleStringProperty(this, "numeroColonne");
        return numeroColonne;
    }

    private StringProperty numeroGiocatori;
    public void setNumeroGiocatori(String value) { numeroGiocatoriProperty().set(value); }
    public String getNumeroGiocatori() { return numeroGiocatoriProperty().get(); }
    public StringProperty numeroGiocatoriProperty() {
        if (numeroGiocatori == null) numeroGiocatori = new SimpleStringProperty(this, "numeroGiocatori");
        return numeroGiocatori;
    }

    private StringProperty numeroScale;
    public void setNumeroScale(String value) { numeroScaleProperty().set(value); }
    public String getNumeroScale() { return numeroScaleProperty().get(); }
    public StringProperty numeroScaleProperty() {
        if (numeroScale == null) numeroScale = new SimpleStringProperty(this, "numeroScale");
        return numeroScale;
    }

    private StringProperty numeroSerpenti;
    public void setNumeroSerpenti(String value) { numeroSerpentiProperty().set(value); }
    public String getNumeroSerpenti() { return numeroSerpentiProperty().get(); }
    public StringProperty numeroSerpentiProperty() {
        if (numeroSerpenti == null) numeroSerpenti = new SimpleStringProperty(this, "numeroSerpenti");
        return numeroSerpenti;
    }

    private StringProperty varianteDadoSingolo;
    public void setVarianteDadoSingolo(String value) { varianteDadoSingoloProperty().set(value); }
    public String getVarianteDadoSingolo() { return varianteDadoSingoloProperty().get(); }
    public StringProperty varianteDadoSingoloProperty() {
        if (varianteDadoSingolo == null) varianteDadoSingolo = new SimpleStringProperty(this, "varianteDadoSingolo");
        return varianteDadoSingolo;
    }

    private StringProperty varianteDadoSingoloFinale;
    public void setVarianteDadoSingoloFinale(String value) { varianteDadoSingoloFinaleProperty().set(value); }
    public String getVarianteDadoSingoloFinale() { return varianteDadoSingoloFinaleProperty().get(); }
    public StringProperty varianteDadoSingoloFinaleProperty() {
        if (varianteDadoSingoloFinale == null) varianteDadoSingoloFinale = new SimpleStringProperty(this, "varianteDadoSingoloFinale");
        return varianteDadoSingoloFinale;
    }

    private StringProperty varianteDoppioSei;
    public void setVarianteDoppioSei(String value) { varianteDoppioSeiProperty().set(value); }
    public String getVarianteDoppioSei() { return varianteDoppioSeiProperty().get(); }
    public StringProperty varianteDoppioSeiProperty() {
        if (varianteDoppioSei == null) varianteDoppioSei = new SimpleStringProperty(this, "varianteDoppioSei");
        return varianteDoppioSei;
    }

    private StringProperty varianteCaselleSosta;
    public void setVarianteCaselleSosta(String value) { varianteCaselleSostaProperty().set(value); }
    public String getVarianteCaselleSosta() { return varianteCaselleSostaProperty().get(); }
    public StringProperty varianteCaselleSostaProperty() {
        if (varianteCaselleSosta == null) varianteCaselleSosta = new SimpleStringProperty(this, "varianteCaselleSosta");
        return varianteCaselleSosta;
    }

    private StringProperty varianteCasellePremio;
    public void setVarianteCasellePremio(String value) { varianteCasellePremioProperty().set(value); }
    public String getVarianteCasellePremio() { return varianteCasellePremioProperty().get(); }
    public StringProperty varianteCasellePremioProperty() {
        if (varianteCasellePremio == null) varianteCasellePremio = new SimpleStringProperty(this, "varianteCasellePremio");
        return varianteCasellePremio;
    }

    private StringProperty variantePescaCarta;
    public void setVariantePescaCarta(String value) { variantePescaCartaProperty().set(value); }
    public String getVariantePescaCarta() { return variantePescaCartaProperty().get(); }
    public StringProperty variantePescaCartaProperty() {
        if (variantePescaCarta == null) variantePescaCarta = new SimpleStringProperty(this, "variantePescaCarta");
        return variantePescaCarta;
    }

    private StringProperty varianteUlterioriCarte;
    public void setVarianteUlterioriCarte(String value) { varianteUlterioriCarteProperty().set(value); }
    public String getVarianteUlterioriCarte() { return varianteUlterioriCarteProperty().get(); }
    public StringProperty varianteUlterioriCarteProperty() {
        if (varianteUlterioriCarte == null) varianteUlterioriCarte = new SimpleStringProperty(this, "varianteUlterioriCarte");
        return varianteUlterioriCarte;
    }

    private StringProperty numeroCaselleSosta;
    public void setNumeroCaselleSosta(String value) { numeroCaselleSostaProperty().set(value); }
    public String getNumeroCaselleSosta() { return numeroCaselleSostaProperty().get(); }
    public StringProperty numeroCaselleSostaProperty() {
        if (numeroCaselleSosta == null) numeroCaselleSosta = new SimpleStringProperty(this, "numeroCaselleSosta");
        return numeroCaselleSosta;
    }

    private StringProperty numeroCasellePremio;
    public void setNumeroCasellePremio(String value) { numeroCasellePremioProperty().set(value); }
    public String getNumeroCasellePremio() { return numeroCasellePremioProperty().get(); }
    public StringProperty numeroCasellePremioProperty() {
        if (numeroCasellePremio == null) numeroCasellePremio = new SimpleStringProperty(this, "numeroCasellePremio");
        return numeroCasellePremio;
    }

    private StringProperty numeroCasellePescaCarta;
    public void setNumeroCasellePescaCarta(String value) { numeroCasellePescaCartaProperty().set(value); }
    public String getNumeroCasellePescaCarta() { return numeroCasellePescaCartaProperty().get(); }
    public StringProperty numeroCasellePescaCartaProperty() {
        if (numeroCasellePescaCarta == null) numeroCasellePescaCarta = new SimpleStringProperty(this, "numeroCasellePescaCarta");
        return numeroCasellePescaCarta;
    }

    @Override
    public String toString() {
        return "ConfigurazioneGioco{" +
                "numeroRighe=" + getNumeroRighe() +
                ", numeroColonne=" + getNumeroColonne() +
                ", numeroGiocatori=" + getNumeroGiocatori() +
                ", numeroScale=" + getNumeroScale() +
                ", numeroSerpenti=" + getNumeroSerpenti() +
                ", varianteDadoSingolo=" + getVarianteDadoSingolo() +
                ", varianteDadoSingoloFinale=" + getVarianteDadoSingoloFinale() +
                ", varianteDoppioSei=" + getVarianteDoppioSei() +
                ", varianteCaselleSosta=" + getVarianteCaselleSosta() +
                ", varianteCasellePremio=" + getVarianteCasellePremio() +
                ", variantePescaCarta=" + getVariantePescaCarta() +
                ", varianteUlterioriCarte=" + getVarianteUlterioriCarte() +
                ", numeroCaselleSosta=" + getNumeroCaselleSosta() +
                ", numeroCasellePremio=" + getNumeroCasellePremio() +
                ", numeroCasellePescaCarta=" + getNumeroCasellePescaCarta() +
                '}';
    }

}
