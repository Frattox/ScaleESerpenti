package game.menu;

public interface Menu {
    void setNumeroCaselle(int r, int c);
    void setNumeroSerpenti(int n);
    void setNumeroScale(int n);
    void setNumeroPedine(int n);
    void varianteDadoSingolo(boolean flag);
    void varianteLancioUnSoloDado(boolean flag);
    void varianteDoppioSei(boolean flag);
    void varianteCaselleSosta(boolean flag);
    void varianteCasellePremio(boolean flag);
    void variantePesca(boolean flag);
    void varianteUlterioriCarte(boolean flag);
    void save();
    void Import();
    void start();


}
