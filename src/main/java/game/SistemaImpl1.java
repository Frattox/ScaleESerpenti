package game;

import elementi.*;
import elementi.factoryMethod.*;
import game.commands.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SistemaImpl1 implements Sistema{

//--------------------------------------------VARIABILI-------------------------------------------
    private Tabellone tabellone;
    private final int totCaselle;
    private MezzoFactory mezzoFactory;
    private HashMap<Casella,Mezzo> mezzi;

    //per ogni tipo di mezzo, la sua quantità corrispondente
    private HashMap<TipoMezzo,Integer> mezziQuantita;
    private int nMezzi;
    private int turno;
    private int lancio;
    private boolean lancioEffettuato;
    private enum Variante{
        DADO_SINGOLO,
        DADO_SINGOLO_FINALE,
        DOPPIO_SEI,
        CASELLA_SOSTA,
        CASELLA_PREMIO,
        CASELLA_PESCA,
        ULTERIORI_CARTE
    }
    private Map<Variante,Boolean> varianti;
    private Pedina[] pedine;
    private HistoryCommandHandler commandHandler;

    // per questioni di evolvibilità del sistema, ho scelto di utilizzare una
    // lista di dadi nel caso si utilizzassero più di 2 dadi per delle
    // implementazioni future
    private List<Dado> dadi;

//--------------------------------------------SETTING--------------------------------------------

    public SistemaImpl1(){
        tabellone = new TabelloneMatrix();
        commandHandler = new HistoryCommandHandler(); //maxHistoryLenght di default = 100
        totCaselle = tabellone.getR()* tabellone.getC();
        mezzi = new HashMap<>();
        mezziQuantita = new HashMap<>();
        pedine = new Pedina[2]; //default
        dadi = new ArrayList<>();
        nMezzi=0;
        turno=-1;
        varianti = new HashMap<>();
        for(Variante v: Variante.values())
            varianti.put(v,false);
    }

    public void setTabellone(int r, int c){
        tabellone = new TabelloneMatrix(r,c);
    }

    public void setNPedine(int n)
    {
        if(n<1)
            throw new IllegalArgumentException("Sistema: numero di pedine invalido");
        pedine = new Pedina[n];
        for(int i=0; i<n; i++){
            Casella start = tabellone.getCasella(0,0);
            pedine[i] = new Pedina(start); //tutte le pedine iniziano dalla casella start
        }
    }

    public void dadoSingolo(){varianti.put(Variante.DADO_SINGOLO,true);}
    public boolean isDadoSingolo(){return varianti.get(Variante.DADO_SINGOLO);}

    public void setDadi()
    {
        if(!varianti.get(Variante.DADO_SINGOLO))
            dadi.add(new Dado());
        dadi.add(new Dado());
    }

    public void setNumberMezzi(TipoMezzo tipo, int n){
        if(n<0)
            throw new IllegalArgumentException("Sistema: numero di "+ tipo.toString() +" invalido");
        //non è possibile inserire un numero di mezzi totale superiore alla
        //metà del numero totale di caselle
        int nMaxMezzi = (tabellone.getR()* tabellone.getC()-2)/2;
        if((nMezzi+n)<=nMaxMezzi)
            throw new IllegalArgumentException("Sistema: numero superiore a quello disponibile di mezzi.");
        mezziQuantita.put(tipo,n);
    }


    public void setMezziAutomatico(){
        for(TipoMezzo t: mezziQuantita.keySet()) {
            mezzoFactory = createMezzoFactory(t);
            int quantita = mezziQuantita.get(t);
            for(int i=0; i<quantita; i++){
                Mezzo m = mezzoFactory.factory();
                m.autoSet(tabellone);
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
//--------------------------------------------UTIL--------------------------------------------

    private MezzoFactory createMezzoFactory(TipoMezzo tipo){
        if(tipo== TipoMezzo.SERPENTE)
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
            return;
        }
        //TODO: le altre tipologie di caselle speciali
    }

    private void rimbalza(int pos, int lancio){pos = totCaselle - (pos-totCaselle);}

    public void verificaCasella(){
        if(lancioEffettuato)
            throw new IllegalArgumentException("Sistema: bisogna far avanzare la pedina prima");
        Pedina giocatore = pedine[turno];
        if(giocatore.getCasella().isCovered()){ //se la casella corrente NON è una normale
            azionaCasella(giocatore);
        }
    }

    public void undo(){commandHandler.undo();}
    public void redo(){commandHandler.redo();}


//--------------------------------------------GAME: OPERAZIONI DI BASE--------------------------------------------

    public void prossimoTurno(){
        turno = (turno+1)%pedine.length; //così turn rimane nel range [0,pedine.lenght-1]
    }

    public void lancia(){
        if(dadi == null || dadi.isEmpty())
            throw new IllegalArgumentException("Sistema: non hai inserito il numero di dadi da utilizzare.");
        lancio = 0;
        if(isDadoSingolo())
            lancio = dadi.get(0).lancia();
        for(Dado d: dadi)
            lancio += d.lancia();
        lancioEffettuato=true;
    }

    public boolean avanza(){
        Pedina giocatore = pedine[turno];
        Casella casellaCorrente = giocatore.getCasella();
        int posCorrente = casellaCorrente.getPos();
        if(!lancioEffettuato)
            throw new IllegalArgumentException("Sistema: il lancio non è stato ancora effettuato");
        int posSuccessiva = posCorrente+lancio;
        if(posSuccessiva>totCaselle)
            rimbalza(posSuccessiva,lancio);
        if(posSuccessiva<totCaselle){
            Casella casellaSuccessiva = tabellone.getCasella(posSuccessiva);
            Command avanzamento = new StepCommand(casellaCorrente,casellaSuccessiva,giocatore);
            commandHandler.handle(avanzamento);
        }
        lancioEffettuato = false;
        return posSuccessiva==totCaselle;
    }


}
