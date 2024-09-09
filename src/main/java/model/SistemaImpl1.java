package model;

import model.elementi.*;
import model.GestoreCaselleLibere.GestoreCaselleLibere;
import model.GestoreCaselleLibere.GestoreCaselleLibereImpl;
import model.GestoreEffetti.GestoreEffetti;
import model.GestoreEffetti.GestoreEffettiImpl;
import model.Mattaro.Carta;
import model.commands.*;
import model.varianti.*;
import model.elementi.Mezzi.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SistemaImpl1 implements Sistema{

//--------------------------------------------VARIABILI-------------------------------------------
    private Tabellone tabellone;
    //CIAO
    private MezzoFactory mezzoFactory;
    private HashMap<Casella, Mezzo> mezzi;
    //Utile per le caselle speciali e mezzi
    private GestoreCaselleLibere caselleLibere;
    //per ogni tipo di mezzo, la sua quantità corrispondente
    private HashMap<TipoMezzo,Integer> mezziQuantita;
    private int
            nMezzi,
            nCaselleSosta,
            nCasellePremio,
            nCasellePescaCarta,
            turno,
            lancio,
            totCaselle; //utile per il setting dei tipi di caselle
    private boolean lancioEffettuato;

    //varianti
    private Variante
            VDadoSingolo,
            VDadoSingoloFinale,
            VDoppioSei,
            VCaselleSosta,
            VCasellePremio,
            VCasellePescaCarta,
            VUlterioriCarte;

    private Pedina[] pedine;
    private HistoryCommandHandler commandHandler;

    // per questioni di evolvibilità del sistema, ho scelto di utilizzare una
    // lista di dadi nel caso si utilizzassero più di 2 dadi per delle
    // implementazioni future
    private List<Dado> dadi;
    private Carta cartaPescata;
    private GestoreEffetti gestoreEffetti;

//--------------------------------------------SETTING--------------------------------------------

    public SistemaImpl1(){
        tabellone = new TabelloneMatrix();//tabellone di default = 10*10
        commandHandler = new HistoryCommandHandler(); //maxHistoryLenght di default = 100
        totCaselle = tabellone.getR()* tabellone.getC();
        mezzi = new HashMap<>();
        mezziQuantita = new HashMap<>();
        pedine = new Pedina[2]; //default
        dadi = new ArrayList<>();
        nMezzi=0;
        nCaselleSosta=0;
        nCasellePremio=0;
        nCasellePescaCarta=0;
        turno=-1;
        caselleLibere = new GestoreCaselleLibereImpl(this);
        VUlterioriCarte = new VarianteUlterioriCarte();
        cartaPescata = null;
        gestoreEffetti = new GestoreEffettiImpl(this);
    }

    @Override
    public void setDadoSingolo(boolean flag){
        VDadoSingolo = new VarianteDadoSingolo();
        VDadoSingolo.setActivated(flag,this);
    }
    @Override
    public void setDadoSingoloFinale(boolean flag) throws IllegalArgumentException{
        VDadoSingoloFinale = new VarianteDadoSingoloFinale();
        VDadoSingoloFinale.setActivated(flag,this);
    }
    @Override
    public void setDoppioSei(boolean flag) throws IllegalArgumentException{
        VDoppioSei = new VarianteDoppioSei();
        VDoppioSei.setActivated(flag,this);
    }
    @Override
    public void setCaselleSosta(boolean flag){
        VCaselleSosta = new VarianteCaselleSosta();
        VCaselleSosta.setActivated(flag,this);
    }
    @Override
    public void setCasellePremio(boolean flag){
        VCasellePremio = new VarianteCasellePremio();
        VCasellePremio.setActivated(flag,this);
    }
    @Override
    public void setPescaCarta(boolean flag){
        VCasellePescaCarta = new VarianteCasellePescaCarta(this);
        VCasellePescaCarta.setActivated(flag,this);
    }
    @Override
    public void setUlterioriCarte(boolean flag) throws IllegalArgumentException{
        VUlterioriCarte = new VarianteUlterioriCarte();
        VUlterioriCarte.setActivated(flag,this);
    }

    public void setRandomCasellaLibera(Casella.Tipo tipo){caselleLibere.setRandomCasellaLibera(tipo);}
    public void setCasellaLibera(int i,Casella.Tipo tipo){caselleLibere.setCasellaLibera(i,tipo);}

    public void setLancio(int lancio) {this.lancio=lancio;}
    public void setTurno(int turno) {this.turno=turno;}

    @Override
    public void setTabellone(int r, int c) throws IllegalArgumentException{
        if(r<2 || c<2)
            throw new IllegalArgumentException("Sistema: numero righe e/o colonne non idonee");
        tabellone = new TabelloneMatrix(r,c);
        totCaselle=tabellone.getR()* tabellone.getC();
    }
    @Override
    public void setNPedine(int n) throws IllegalArgumentException{
        if(n<2)
            throw new IllegalArgumentException("Sistema: numero di pedine non idoneo");
        pedine = new Pedina[n];
        Casella start = tabellone.getCasella(0,0);
        for(int i=0; i<n; i++){
            pedine[i] = new Pedina(start); //tutte le pedine iniziano dalla casella start
        }
    }


    public void setDadi() {VDadoSingolo.action(this);}

    @Override
    public void setNumberMezzi(TipoMezzo tipo, int n) throws IllegalArgumentException{
        if(n<0)
            throw new IllegalArgumentException("Sistema: numero di "+ tipo.toString() +" non idoneo");
        if(!isNumberMezziOk(n))
            throw new IllegalArgumentException("Sistema: numero superiore a quello disponibile di mezzi.");
        mezziQuantita.put(tipo,n);
        nMezzi+=n;
        setMezziAutomatico();
    }
    @Override
    public void setNumberCaselleSosta(int n) throws IllegalArgumentException{
        if(n<0)
            throw new IllegalArgumentException("Sistema: numero di caselle sosta non idoneo");
        if(!isNumberCaselleSpecialiOk(n))
            throw new IllegalArgumentException("Sistema: numero superiore a quello disponibile di caselle.");
        nCaselleSosta=n;
        VCaselleSosta.action(this);
    }
    @Override
    public void setNumberCasellePremio(int n) throws IllegalArgumentException{
        if(n<0)
            throw new IllegalArgumentException("Sistema: numero di caselle sosta invalido");
        if(!isNumberCaselleSpecialiOk(n))
            throw new IllegalArgumentException("Sistema: numero superiore a quello disponibile di caselle.");
        nCasellePremio=n;
        VCasellePremio.action(this);
    }
    @Override
    public void setNumberCasellePescaCarta(int n) throws IllegalArgumentException{
        if(n<0)
            throw new IllegalArgumentException("Sistema: numero di caselle sosta invalido");
        if(!isNumberCaselleSpecialiOk(n))
            throw new IllegalArgumentException("Sistema: numero superiore a quello disponibile di caselle.");
        nCasellePescaCarta=n;
        VCasellePescaCarta.action(this);
    }

    private void setMezziAutomatico(){
        if(mezziQuantita.isEmpty())
            return;
        for(TipoMezzo t: mezziQuantita.keySet()) {
            mezzoFactory = createMezzoFactory(t);
            int quantita = mezziQuantita.get(t);
            for(int i=0; i<quantita; i++){
                Mezzo m = mezzoFactory.factory();
                m.autoSet(this);
                mezzi.put(m.getFrom(),m);
            }
        }
    }

    public void setCartaPescata(Carta cartaPescata){if(cartaPescata!=null) this.cartaPescata=cartaPescata;}
    public void setLancioEffettuato(boolean lancioEffettuato){this.lancioEffettuato=lancioEffettuato;}

//--------------------------------------------GETTERS--------------------------------------------
    //utili per fornire informazioni durante il gioco

    public int getTurno() {
        return turno;
    }

    public int getLancio() {
        return lancio;
    }

    public List<Dado> getDadi() {
        return dadi;
    }
    public int getTotCaselle(){return totCaselle;}
    public int getSizeCaselleLibere(){return caselleLibere.getSize();}
    public int getnCaselleSosta(){return nCaselleSosta;}
    public int getnCasellePremio() {return nCasellePremio;}
    public int getnCasellePescaCarta(){return nCasellePescaCarta;}
    public Pedina[] getPedine(){return pedine;}
    public Tabellone getTabellone(){return tabellone;}
    public Carta getCartaPescata(){return cartaPescata;}
    public CommandHandler getCommandHandler(){return commandHandler;}
    public HashMap<Casella,Mezzo> getMezzi(){return mezzi;}

    @Override
    public boolean isDadoSingolo(){return VDadoSingolo.isActivated();}
    @Override
    public boolean isDadoSingoloFinale(){return VDadoSingoloFinale.isActivated();}
    @Override
    public boolean isDoppioSei(){return VDoppioSei.isActivated();}
    @Override
    public boolean isCaselleSosta(){return VCaselleSosta.isActivated();}
    @Override
    public boolean isCasellePremio(){return VCasellePremio.isActivated();}
    @Override
    public boolean isPescaCarta(){return VCasellePescaCarta.isActivated();}
    @Override
    public boolean isUlterioriCarte(){return VUlterioriCarte.isActivated();}

    private boolean isNumberMezziOk(int n){
        //non è possibile inserire un numero di mezzi totale superiore alla
        //metà del numero totale di caselle libere (normali)
        int nMaxMezzi = getSizeCaselleLibere()/2;
        return (nMezzi+n)<=nMaxMezzi;
    }
    private boolean isNumberCaselleSpecialiOk(int n) {
        //non è possibile inserire un numero di caselle speciali superiore al numero totale di caselle disponibili
        return n<=getSizeCaselleLibere();
    }

//--------------------------------------------UTIL--------------------------------------------

    private MezzoFactory createMezzoFactory(TipoMezzo tipo){
        if(tipo==TipoMezzo.SERPENTE)
            return new SerpenteFactory();
        return new ScalaFactory();
    }
    private int rimbalza(int pos){return totCaselle - (pos-totCaselle);}

    @Override
    public void undo(){commandHandler.undo();}
    @Override
    public void redo(){commandHandler.redo();}


//--------------------------------------------GAME: OPERAZIONI DI BASE--------------------------------------------
    @Override
    public void prossimoTurno(){
        int turnoPrima = turno;
        VDoppioSei.action(this);
        if(turno!=turnoPrima){
            commandHandler.handle(new TurnoCommand(this,turnoPrima,turno));
        }
    }
    @Override
    public void lancia() {
        if(dadi == null || dadi.isEmpty())
            throw new IllegalArgumentException("Sistema: non hai inserito il numero di dadi da utilizzare.");
        int lancioPrima = lancio;
        lancio = 0;
        VDadoSingoloFinale.action(this);
        int lancioDopo = lancio;
        commandHandler.handle(new DadiCommand(this,lancioPrima,lancioDopo));
        lancioEffettuato = true;
    }
    @Override
    //ritorna true se il giocatore ha vinto
    public boolean avanza() throws IllegalArgumentException{
        if(!lancioEffettuato)
            throw new IllegalArgumentException("Sistema: il lancio non è stato ancora effettuato");

        Pedina giocatore = pedine[turno];
        Casella casellaCorrente = giocatore.getCasella();
        int posCorrente = casellaCorrente.getPos();
        int posSuccessiva = posCorrente+lancio;

        //per vincere, deve ottenere il numero esatto di dadi per la casella finale, altrimenti "rimbalza" dall'ultima casella
        if(posSuccessiva>(totCaselle-1))
            posSuccessiva = rimbalza(posSuccessiva);

        Casella casellaSuccessiva = tabellone.getCasella(posSuccessiva);
        Command avanzamento = new AvanzamentoCommand(casellaCorrente,casellaSuccessiva,giocatore);
        commandHandler.handle(avanzamento);
        lancioEffettuato = false;
        return posSuccessiva==(totCaselle-1);
    }
    @Override
    public void azionaCasella() throws IllegalArgumentException{
        if(lancioEffettuato)
            throw new IllegalArgumentException("Sistema: bisogna far avanzare la pedina prima");
        Pedina giocatore = pedine[turno];
        if(giocatore.getCasella().isCovered()){ //se la casella corrente NON è una normale
            gestoreEffetti.azionaCasella();
        }
    }

}
