package model;

import model.elementi.Mezzi.TipoMezzo;

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
    void setNumberCaselleSosta(int n) throws IllegalArgumentException;
    void setNumberCasellePremio(int n) throws IllegalArgumentException;
    void setNumberCasellePescaCarta(int n) throws IllegalArgumentException;
    void undo();
    void redo();
    void prossimoTurno();
    void lancia();
    boolean avanza() throws IllegalArgumentException;
    void azionaCasella() throws IllegalArgumentException;
}
