package game.sistema;

import elementi.*;
import elementi.factoryMethod.*;
import game.sistema.commands.*;
import game.sistema.varianti.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//NOTAZIONI:
// PDR -> Probabilmente da rimuovere

public class SistemaImpl1 implements Sistema{

//--------------------------------------------VARIABILI-------------------------------------------
    private Tabellone tabellone;
    private MezzoFactory mezzoFactory;
    private HashMap<Casella,Mezzo> mezzi;

    //per ogni tipo di mezzo, la sua quantità corrispondente
    private HashMap<TipoMezzo,Integer> mezziQuantita;
    private int
            nMezzi,
            turno,
            lancio,
            caselleCoperte,
            totCaselle; //utile per il setting dei tipi di caselle
    private boolean lancioEffettuato;

    //PDR
//    private enum Variante{
//        DADO_SINGOLO,
//        DADO_SINGOLO_FINALE, //=> !DADO_SINGOLO
//        DOPPIO_SEI, //=> !DADO_SINGOLO
//        CASELLA_SOSTA,
//        CASELLA_PREMIO,
//        CASELLA_PESCA,
//        ULTERIORI_CARTE //=> CASELLA_PESCA
//    }

    private Variante VDadoSingolo, VDadoSingoloFinale;

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
        caselleCoperte=0;
        turno=-1;
        //PDR
//        varianti = new HashMap<>();
//        for(Variante v: Variante.values())
//            varianti.put(v,false);
        VDadoSingolo = VarianteDadoSingolo.getInstance();
        VDadoSingoloFinale = VarianteDadoSingoloFinale.getInstance();

    }

    public void setDadoSingolo(boolean flag){VDadoSingolo.setActivated(flag);}

    public void setDadoSingoloFinale(boolean flag){VDadoSingoloFinale.setActivated(flag);}
    //TODO: gli altri set delle varianti rimanenti


    public void setLancio(int lancio) {this.lancio=lancio;}

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

    public void setDadi()
    {
        VDadoSingolo.action(this);
    }

    public void setNumberMezzi(TipoMezzo tipo, int n){
        if(n<0)
            throw new IllegalArgumentException("Sistema: numero di "+ tipo.toString() +" invalido");
        //non è possibile inserire un numero di mezzi totale superiore alla
        //metà del numero totale di caselle (normali)
        int nMaxMezzi = (totCaselle-caselleCoperte-2)/2;
        if((nMezzi+n)<=nMaxMezzi)
            throw new IllegalArgumentException("Sistema: numero superiore a quello disponibile di mezzi.");
        mezziQuantita.put(tipo,n);
        caselleCoperte+=n;
    }

    public void setNumberCaselleSosta(int n){
        if(n<0)
            throw new IllegalArgumentException("Sistema: numero di caselle sosta invalido");
        if(n>totCaselle)
            throw new IllegalArgumentException("Sistema: numero superiore a quello disponibile di mezzi.");
        //TODO
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
    public int getTotCaselle(){return totCaselle;}
    public Pedina[] getPedine(){return pedine;}

//--------------------------------------------UTIL--------------------------------------------

    public boolean isDadoSingolo(){return VDadoSingolo.isActivated();}
    public boolean isDadoSingoloFinale(){return VDadoSingoloFinale.isActivated();}

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

    private boolean isLancioDoppioSei(){
        return lancio%6==0;
    }

    public void undo(){commandHandler.undo();}
    public void redo(){commandHandler.redo();}


//--------------------------------------------GAME: OPERAZIONI DI BASE--------------------------------------------

    public void prossimoTurno(){
        //NO DADO SINGOLO + l'ultimo lancio è doppio 6 + IS DOPPIO SEI => non effettuo l'aumento del turno
        if(!(!isDadoSingolo() && isLancioDoppioSei() && isDoppioSei()))
            turno = (turno+1)%pedine.length; //così turn rimane nel range [0,pedine.lenght-1]
        
    }

    //PDR
    public void lancia(boolean flag){
        if(dadi == null || dadi.isEmpty())
            throw new IllegalArgumentException("Sistema: non hai inserito il numero di dadi da utilizzare.");
        lancio = 0;
        if(flag)
            lancio = dadi.get(0).lancia();
        else
            for(Dado d: dadi)
                lancio += d.lancia();
        lancioEffettuato=true;
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
