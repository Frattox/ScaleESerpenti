package DB;

public class ConfigurazioneGioco{

    private int numeroRighe;
    private int numeroColonne;
    private int numeroGiocatori;
    private int numeroScale;
    private int numeroSerpenti;
    private boolean varianteDadoSingolo;
    private boolean varianteDadoSingoloFinale;
    private boolean varianteDoppioSei;
    private boolean varianteCaselleSosta;
    private boolean varianteCasellePremio;
    private boolean variantePescaCarta;
    private boolean varianteUlterioriCarte;
    private int numeroCaselleSosta;
    private int numeroCasellePremio;
    private int numeroCasellePescaCarta;
    public ConfigurazioneGioco(){}

    // Costruttore con tutti i campi
    public ConfigurazioneGioco(int numeroRighe, int numeroColonne, int numeroGiocatori, int numeroScale, int numeroSerpenti,
                               boolean varianteDadoSingolo, boolean varianteDadoSingoloFinale, boolean varianteDoppioSei,
                               boolean varianteCaselleSosta, boolean varianteCasellePremio, boolean variantePescaCarta, boolean varianteUlterioriCarte,
                               int numeroCaselleSosta, int numeroCasellePremio, int numeroCasellePescaCarta) {
        this.numeroRighe = numeroRighe;
        this.numeroColonne = numeroColonne;
        this.numeroGiocatori = numeroGiocatori;
        this.numeroScale = numeroScale;
        this.numeroSerpenti = numeroSerpenti;
        this.varianteDadoSingolo = varianteDadoSingolo;
        this.varianteDadoSingoloFinale = varianteDadoSingoloFinale;
        this.varianteDoppioSei = varianteDoppioSei;
        this.varianteCaselleSosta = varianteCaselleSosta;
        this.varianteCasellePremio = varianteCasellePremio;
        this.variantePescaCarta = variantePescaCarta;
        this.varianteUlterioriCarte = varianteUlterioriCarte;
        this.numeroCaselleSosta = numeroCaselleSosta;
        this.numeroCasellePremio = numeroCasellePremio;
        this.numeroCasellePescaCarta = numeroCasellePescaCarta;
    }

    // Getters e Setters
    public int getNumeroRighe() {
        return numeroRighe;
    }

    public void setNumeroRighe(int numeroRighe) {
        this.numeroRighe = numeroRighe;
    }

    public int getNumeroColonne() {
        return numeroColonne;
    }

    public void setNumeroColonne(int numeroColonne) {
        this.numeroColonne = numeroColonne;
    }

    public int getNumeroGiocatori() {
        return numeroGiocatori;
    }

    public void setNumeroGiocatori(int numeroGiocatori) {
        this.numeroGiocatori = numeroGiocatori;
    }

    public int getNumeroScale() {
        return numeroScale;
    }

    public void setNumeroScale(int numeroScale) {
        this.numeroScale = numeroScale;
    }

    public int getNumeroSerpenti() {
        return numeroSerpenti;
    }

    public void setNumeroSerpenti(int numeroSerpenti) {
        this.numeroSerpenti = numeroSerpenti;
    }

    public boolean isVarianteDadoSingolo() {
        return varianteDadoSingolo;
    }

    public void setVarianteDadoSingolo(boolean varianteDadoSingolo) {
        this.varianteDadoSingolo = varianteDadoSingolo;
    }

    public boolean isVarianteDadoSingoloFinale() {
        return varianteDadoSingoloFinale;
    }

    public void setVarianteDadoSingoloFinale(boolean varianteDadoSingoloFinale) {
        this.varianteDadoSingoloFinale = varianteDadoSingoloFinale;
    }

    public boolean isVarianteDoppioSei() {
        return varianteDoppioSei;
    }

    public void setVarianteDoppioSei(boolean varianteDoppioSei) {
        this.varianteDoppioSei = varianteDoppioSei;
    }

    public boolean isVarianteCaselleSosta() {
        return varianteCaselleSosta;
    }

    public void setVarianteCaselleSosta(boolean varianteCaselleSosta) {
        this.varianteCaselleSosta = varianteCaselleSosta;
    }

    public boolean isVarianteCasellePremio() {
        return varianteCasellePremio;
    }

    public void setVarianteCasellePremio(boolean varianteCasellePremio) {
        this.varianteCasellePremio = varianteCasellePremio;
    }

    public boolean isVariantePescaCarta() {
        return variantePescaCarta;
    }

    public void setVariantePescaCarta(boolean variantePescaCarta) {
        this.variantePescaCarta = variantePescaCarta;
    }

    public boolean isVarianteUlterioriCarte() {
        return varianteUlterioriCarte;
    }

    public void setVarianteUlterioriCarte(boolean varianteUlterioriCarte) {
        this.varianteUlterioriCarte = varianteUlterioriCarte;
    }

    public int getNumeroCaselleSosta() {
        return numeroCaselleSosta;
    }

    public void setNumeroCaselleSosta(int numeroCaselleSosta) {
        this.numeroCaselleSosta = numeroCaselleSosta;
    }

    public int getNumeroCasellePremio() {
        return numeroCasellePremio;
    }

    public void setNumeroCasellePremio(int numeroCasellePremio) {
        this.numeroCasellePremio = numeroCasellePremio;
    }

    public int getNumeroCasellePescaCarta() {
        return numeroCasellePescaCarta;
    }

    public void setNumeroCasellePescaCarta(int numeroCasellePescaCarta) {
        this.numeroCasellePescaCarta = numeroCasellePescaCarta;
    }
}

