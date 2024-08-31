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
// DS -> Da Sistemare

public class SistemaImpl1 implements Sistema{

//--------------------------------------------VARIABILI-------------------------------------------
    private Tabellone tabellone;
    private ArrayList<Casella> caselleLibere;
    private MezzoFactory mezzoFactory;
    private HashMap<Casella,Mezzo> mezzi;

    //per ogni tipo di mezzo, la sua quantità corrispondente
    private HashMap<TipoMezzo,Integer> mezziQuantita;
    private int
            nMezzi,
            nCaselleSosta,
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
        caselleCoperte=0;
        turno=-1;
        caselleLibere = caselleLibereIniziali();
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
        caselleCoperte+=n;
    }

    public void setNumberCaselleSosta(int n){
        if(n<0)
            throw new IllegalArgumentException("Sistema: numero di caselle sosta invalido");
        if(!isNumberCaselleSpecialiOk(n))
            throw new IllegalArgumentException("Sistema: numero superiore a quello disponibile di mezzi.");
        nCaselleSosta=n;
        caselleCoperte+=n;
        VCaselleSosta.action(this);
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
    public int getCaselleCoperte(){return caselleCoperte;}
    public int getnCaselleSosta(){return nCaselleSosta;}
    public Pedina[] getPedine(){return pedine;}

//--------------------------------------------UTIL--------------------------------------------

    public boolean isDadoSingolo(){return VDadoSingolo.isActivated();}
    public boolean isDadoSingoloFinale(){return VDadoSingoloFinale.isActivated();}
    public boolean isDoppioSei(){return VDoppioSei.isActivated();}
    public boolean isCaselleSosta(){return VCaselleSosta.isActivated();}

    private boolean isNumberMezziOk(int n){
        //non è possibile inserire un numero di mezzi totale superiore alla
        //metà del numero totale di caselle disponibili(normali)
        int nMaxMezzi = (totCaselle-caselleCoperte-2)/2;
        return (nMezzi+n)<=nMaxMezzi;
    }

    private boolean isNumberCaselleSpecialiOk(int n) {
        //non è possibile inserire un numero di caselle speciali superiore al numero totale di caselle disponibili
        int nMaxCaselleSpeciali = totCaselle-caselleCoperte-2;
        return n<=nMaxCaselleSpeciali;
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

    private ArrayList<Casella> caselleLibereIniziali(){
        ArrayList<Casella> ret = new ArrayList<>();
        //esclusa la pos (0,0), ovvero la prima casella
        for(int i=1;i<tabellone.getR();i++)
            for(int j=0;j< tabellone.getC();j++)
                ret.add(tabellone.getCasella(i,j));
        //esclusa la pos (R-1,C-2), ovvero l'ultima casella
        ret.remove(ret.size()-1);
        return ret;
    }


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
