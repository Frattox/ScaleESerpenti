package model;

import model.Mattaro.Carta;
import model.elementi.Casella;
import model.elementi.Mezzi.Mezzo;
import model.elementi.Mezzi.TipoMezzo;
import model.elementi.Tabellone;

import java.util.HashMap;
import java.util.List;

public interface Sistema {
    void setDadoSingolo(boolean flag);
    void setDadoSingoloFinale(boolean flag);
    void setDoppioSei(boolean flag);
    void setCaselleSosta(boolean flag);
    void setCasellePremio(boolean flag);
    void setPescaCarta(boolean flag);
    void setUlterioriCarte(boolean flag);
    void setTabellone(int r, int c) throws IllegalArgumentException;
    void setNPedine(int n) throws IllegalArgumentException;
    void setNumberMezzi(TipoMezzo tipo, int n) throws IllegalArgumentException;
    boolean isDadoSingolo();
    boolean isDadoSingoloFinale();
    boolean isDoppioSei();
    boolean isCaselleSosta();
    boolean isCasellePremio();
    boolean isPescaCarta();
    boolean isUlterioriCarte();

    void setNumberCaselleSosta(int n) throws IllegalArgumentException;
    void setNumberCasellePremio(int n) throws IllegalArgumentException;
    void setNumberCasellePescaCarta(int n) throws IllegalArgumentException;
    void setDadi();
    boolean undo();
    boolean redo();
    void prossimoTurno();
    void lancia();
    void avanza() throws IllegalArgumentException;
    boolean azionaCasella() throws IllegalArgumentException;
    Tabellone getTabellone();
    int getNPedine();
    int getTurno();
    int getLancio();
    Casella getCasellaCorrente();
    String getVincitore();
    List<Mezzo> getMezzi();
    HashMap<Casella,Mezzo> getMezziMap();
    Carta getCartaPescata();
    String getUltimaSosta();
    String getUltimoPremio();

}
