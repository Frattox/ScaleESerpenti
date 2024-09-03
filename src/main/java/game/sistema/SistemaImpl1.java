package game.sistema;

import elementi.*;
import elementi.GestoreCaselleLibere.GestoreCaselleLibere;
import elementi.GestoreCaselleLibere.GestoreCaselleLibereImpl;
import elementi.factoryMethod.*;
import game.sistema.commands.*;
import game.sistema.varianti.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//NOTAZIONI:
// PDR -> Probabilmente da rimuovere
// DS -> Da Sistemare

public class SistemaImpl1 implements Sistema{

//--------------------------------------------VARIABILI-------------------------------------------
    private Tabellone tabellone;
    private MezzoFactory mezzoFactory;
    private HashMap<Casella,Mezzo> mezzi;
    //Utile per le caselle speciali e mezzi
    private GestoreCaselleLibere caselleLibere;

    //per ogni tipo di mezzo, la sua quantità corrispondente
    private HashMap<TipoMezzo,Integer> mezziQuantita;
    private int
            nMezzi,
            nCaselleSosta,
            nCasellePremio,
            turno,
            lancio,
            caselleCoperte,
            totCaselle; //utile per il setting dei tipi di caselle
    private boolean lancioEffettuato;

    //varianti
    private Variante
            VDadoSingolo,
            VDadoSingoloFinale,
            VDoppioSei,
            VCaselleSosta;

    private Pedina[] pedine;
    private HistoryCommandHandler commandHandler;

    // per questioni di evolvibilità del sistema, ho scelto di utilizzare una
    // lista di dadi nel caso si utilizzassero più di 2 dadi per delle
    // implementazioni future
    private List<Dado> dadi;

//--------------------------------------------SETTING--------------------------------------------

    //DS
    public SistemaImpl1(){
        tabellone = new TabelloneMatrix();
        commandHandler = new HistoryCommandHandler(); //maxHistoryLenght di default = 100
        totCaselle = tabellone.getR()* tabellone.getC();
        mezzi = new HashMap<>();
        mezziQuantita = new HashMap<>();
        pedine = new Pedina[2]; //default
        dadi = new ArrayList<>();
        nMezzi=0;
        nCaselleSosta=0;
        nCasellePremio=0;
        caselleCoperte=0;
        turno=-1;
        caselleLibere = new GestoreCaselleLibereImpl(this);
    }

    public void setDadoSingolo(boolean flag){
        VDadoSingolo = VarianteDadoSingolo.getInstance();
        VDadoSingolo.setActivated(flag);
    }
    public void setDadoSingoloFinale(boolean flag){
        VDadoSingoloFinale = VarianteDadoSingoloFinale.getInstance();
        VDadoSingoloFinale.setActivated(flag);
    }
    public void setDoppioSei(boolean flag){
        VDoppioSei = VarianteDoppioSei.getInstance();
        VDoppioSei.setActivated(flag);
    }
    public void setCaselleSosta(boolean flag){
        VCaselleSosta = VarianteCaselleSosta.getInstance();
        VCaselleSosta.setActivated(flag);
    }
    //TODO: gli altri set delle varianti rimanenti


    public void setRandomCasellaLibera(Casella.Tipo tipo){caselleLibere.setRandomCasellaLibera(tipo);}
    public void setCasellaLibera(int i,Casella.Tipo tipo){caselleLibere.setCasellaLibera(i,tipo);}

    public void setLancio(int lancio) {this.lancio=lancio;}
    public void setTurno(int turno) {this.turno=turno;}

    public void setTabellone(int r, int c){
        tabellone = new TabelloneMatrix(r,c);
    }

    public void setNPedine(int n)
    {
        if(n<1)
            throw new IllegalArgumentException("Sistema: numero di pedine invalido");
        pedine = new Pedina[n];
        Casella start = tabellone.getCasella(0,0);
        for(int i=0; i<n; i++){
            pedine[i] = new Pedina(start); //tutte le pedine iniziano dalla casella start
        }
    }

    public void setDadi()
    {
        VDadoSingolo.action(this);
    }

    public void setNumberMezzi(TipoMezzo tipo, int n){
        if(n<0)
            throw new IllegalArgumentException("Sistema: numero di "+ tipo.toString() +" invalido");
        if(!isNumberMezziOk(n))
            throw new IllegalArgumentException("Sistema: numero superiore a quello disponibile di mezzi.");
        mezziQuantita.put(tipo,n);
        nMezzi+=n;
        setMezziAutomatico();
    }

    public void setNumberCaselleSosta(int n){
        if(n<0)
            throw new IllegalArgumentException("Sistema: numero di caselle sosta invalido");
        if(!isNumberCaselleSpecialiOk(n))
            throw new IllegalArgumentException("Sistema: numero superiore a quello disponibile di mezzi.");
        nCaselleSosta=n;
        VCaselleSosta.action(this);
    }

    public void setNumberCasellePremio(int n){
        if(n<0)
            throw new IllegalArgumentException("Sistema: numero di caselle sosta invalido");
        if(!isNumberCaselleSpecialiOk(n))
            throw new IllegalArgumentException("Sistema: numero superiore a quello disponibile di mezzi.");
        nCaselleSosta=n;
        VCaselleSosta.action(this);
    }

    private void setMezziAutomatico(){
        for(TipoMezzo t: mezziQuantita.keySet()) {
            mezzoFactory = createMezzoFactory(t);
            int quantita = mezziQuantita.get(t);
            for(int i=0; i<quantita; i++){
                Mezzo m = mezzoFactory.factory();
                m.autoSet(this);
            }
        }
    }

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
    public int getCaselleCoperte(){return caselleCoperte;}
    public int getSizeCaselleLibere(){return caselleLibere.getSize();}
    public int getnCaselleSosta(){return nCaselleSosta;}
    public int getnCasellePremio() {return nCasellePremio;}
    public Pedina[] getPedine(){return pedine;}
    public Tabellone getTabellone(){return tabellone;}

//--------------------------------------------UTIL--------------------------------------------

    public boolean isDadoSingolo(){return VDadoSingolo.isActivated();}
    public boolean isDadoSingoloFinale(){return VDadoSingoloFinale.isActivated();}
    public boolean isDoppioSei(){return VDoppioSei.isActivated();}
    public boolean isCaselleSosta(){return VCaselleSosta.isActivated();}

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

    private MezzoFactory createMezzoFactory(TipoMezzo tipo){
        if(tipo==TipoMezzo.SERPENTE)
            return new SerpenteFactory();
        return new ScalaFactory();
    }

    private void azionaCasella(Pedina giocatore) {
        Command cmd;
        Casella casella = giocatore.getCasella();
        //se la casella è il punto di partenza di un mezzo
        if(mezzi.containsKey(casella)) {
            Mezzo m = mezzi.get(casella);
            cmd = new VehicleCommand(m, giocatore);
            commandHandler.handle(cmd);
        }else {
            //TODO: le altre tipologie di caselle speciali
        }
    }

    private int rimbalza(int pos, int lancio){return totCaselle - (pos-totCaselle);}

    public void verificaCasella(){
        if(lancioEffettuato)
            throw new IllegalArgumentException("Sistema: bisogna far avanzare la pedina prima");
        Pedina giocatore = pedine[turno];
        if(giocatore.getCasella().isCovered()){ //se la casella corrente NON è una normale
            azionaCasella(giocatore);
        }
    }

    //PDR
    private boolean isUltimeCaselle() {
        int pos = pedine[turno].getCasella().getPos();
        return pos>=totCaselle-6;
    }

    public void undo(){commandHandler.undo();}
    public void redo(){commandHandler.redo();}


//--------------------------------------------GAME: OPERAZIONI DI BASE--------------------------------------------
    public void prossimoTurno(){
        VDoppioSei.action(this);
    }

    public void lancia()
    {
        if(dadi == null || dadi.isEmpty())
            throw new IllegalArgumentException("Sistema: non hai inserito il numero di dadi da utilizzare.");
        lancio = 0;
        VDadoSingoloFinale.action(this);
        lancioEffettuato = true;
    }

    //ritorna true se il giocatore ha vinto
    public boolean avanza(){
        if(!lancioEffettuato)
            throw new IllegalArgumentException("Sistema: il lancio non è stato ancora effettuato");

        Pedina giocatore = pedine[turno];
        Casella casellaCorrente = giocatore.getCasella();
        int posCorrente = casellaCorrente.getPos();
        int posSuccessiva = posCorrente+lancio;

        //deve essere il numero esatto di caselle, altrimenti "rimbalza" dall'ultima casella
        if(posSuccessiva>totCaselle)
            posSuccessiva = rimbalza(posSuccessiva,lancio);

        Casella casellaSuccessiva = tabellone.getCasella(posSuccessiva);
        Command avanzamento = new StepCommand(casellaCorrente,casellaSuccessiva,giocatore);
        commandHandler.handle(avanzamento);
        lancioEffettuato = false;
        return posSuccessiva==totCaselle;
    }
}
