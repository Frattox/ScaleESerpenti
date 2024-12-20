package model.sistema;

import DB.ConfigurazioneGioco;
import model.elementi.*;
import model.GestoreCaselleLibere.GestoreCaselleLibere;
import model.GestoreCaselleLibere.GestoreCaselleLibereImpl;
import model.GestoreEffetti.GestoreEffetti;
import model.GestoreEffetti.GestoreEffettiImpl;
import model.Mattaro.Carta;
import model.commands.*;
import model.varianti.*;
import model.elementi.Mezzi.*;
import util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class SistemaImpl1 implements Sistema{

//--------------------------------------------VARIABILI-------------------------------------------
    private Tabellone tabellone;
    private final List<Mezzo> mezzi;

    private final HashMap<Casella,Mezzo> mezziMap;
    //Utile per le caselle speciali e mezzi
    private GestoreCaselleLibere caselleLibere;
    //per ogni tipo di mezzo, la sua quantità corrispondente
    private final HashMap<TipoMezzo,Integer> mezziQuantita;
    private final HashMap<Util.CaselleSpeciali,Integer> caselleSpecialiQuantita;
    private int
            nMezzi,
            nCaselleSpeciali,
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
    private final HistoryCommandHandler commandHandler;

    // per questioni di evolvibilità del sistema, ho scelto di utilizzare una
    // lista di dadi nel caso si utilizzassero più di 2 dadi per delle
    // implementazioni future
    private final List<Dado> dadi;
    private Carta cartaPescata;
    private final GestoreEffetti gestoreEffetti;

//--------------------------------------------SETTING--------------------------------------------

    public SistemaImpl1(){
        tabellone = new TabelloneMatrix();//tabellone di default = 10*10
        commandHandler = new HistoryCommandHandler(); //maxHistoryLenght di default = 100
        totCaselle = tabellone.getR()* tabellone.getC();
        mezzi = new LinkedList<>();
        mezziMap = new HashMap<>();
        mezziQuantita = new HashMap<>();
        pedine = new Pedina[2]; //default
        dadi = new ArrayList<>();
        nMezzi=0;
        nCaselleSpeciali=0;
        turno=-1;
        VUlterioriCarte = new VarianteUlterioriCarte();
        cartaPescata = null;
        gestoreEffetti = new GestoreEffettiImpl(this);
        caselleSpecialiQuantita = new HashMap<>();
        VDadoSingolo = new VarianteDadoSingolo();
        VDadoSingoloFinale = new VarianteDadoSingoloFinale();
        VDoppioSei = new VarianteDoppioSei();
        VCaselleSosta = new VarianteCaselleSosta();
        VCasellePremio = new VarianteCasellePremio();
        VCasellePescaCarta = new VarianteCasellePescaCarta();
        VUlterioriCarte = new VarianteUlterioriCarte();
        initCaselleSpeciali();
    }

    @Override
    public void setDadoSingolo(boolean flag){
        VDadoSingolo.setActivated(flag,this);
    }
    @Override
    public void setDadoSingoloFinale(boolean flag) throws IllegalArgumentException{
        VDadoSingoloFinale.setActivated(flag,this);
    }
    @Override
    public void setDoppioSei(boolean flag) throws IllegalArgumentException{
        VDoppioSei.setActivated(flag,this);
    }
    @Override
    public void setCaselleSosta(boolean flag){
        VCaselleSosta.setActivated(flag,this);
    }
    @Override
    public void setCasellePremio(boolean flag){
        VCasellePremio.setActivated(flag,this);
    }
    @Override
    public void setPescaCarta(boolean flag){
        VCasellePescaCarta.setActivated(flag,this);
    }
    @Override
    public void setUlterioriCarte(boolean flag) throws IllegalArgumentException{
        VUlterioriCarte.setActivated(flag,this);
    }

    public void setRandomCasellaLibera(Casella.Tipo tipo){
        caselleLibere.setRandomCasellaLibera(tipo);
    }
    public Casella setCasellaLibera(int i,Casella.Tipo tipo){
        return caselleLibere.setCasellaLibera(i,tipo);
    }

    public void setLancio(int lancio) {this.lancio=lancio;}
    public void setTurno(int turno) {this.turno=turno;}

    @Override
    public void setTabellone(int r, int c) throws IllegalArgumentException{
        if(c<2)
            throw new IllegalArgumentException("Numero di colonne inadeguato");
        if(r<2)
            throw new IllegalArgumentException("Numero di righe inadeguato");
        tabellone = new TabelloneMatrix(r,c);
        totCaselle=tabellone.getR()* tabellone.getC();
        caselleLibere = new GestoreCaselleLibereImpl(this);
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
        setMezziAutomatico(tipo,n);
    }

    @Override
    public void setNumberCaselleSpeciali(Util.CaselleSpeciali tipoCasellaSpeciale, int n){
        caselleSpecialiQuantita.put(tipoCasellaSpeciale,n);
        nCaselleSpeciali+=n;
    }

    @Override
    public void controlNumberCaselleSpeciali() throws IllegalArgumentException{
        if(!isNumberCaselleSpecialiOk(nCaselleSpeciali)){
            initCaselleSpeciali();
            throw new IllegalArgumentException(">");
        }
    }

    @Override
    public void beginSettingCaselleSpeciali(){
        setNumberCaselleSosta();
        setNumberCasellePremio();
        setNumberCasellePescaCarta();
    }

    private void setNumberCaselleSosta() throws IllegalArgumentException{
        VCaselleSosta.action(this);
    }

    private void setNumberCasellePremio() throws IllegalArgumentException{
        VCasellePremio.action(this);
    }

    private void setNumberCasellePescaCarta() throws IllegalArgumentException{
        VCasellePescaCarta.action(this);
    }

    private void setMezziAutomatico(TipoMezzo tipo, int n){
        MezzoFactory mezzoFactory = createMezzoFactory(tipo);
        for(int i=0; i<n; i++){
            Mezzo m = mezzoFactory.factory();
            m.autoSet(this);
            mezzi.add(m);
            mezziMap.put(m.getFrom(),m);
        }
    }

    public void setCartaPescata(Carta cartaPescata){if(cartaPescata!=null) this.cartaPescata=cartaPescata;}
    public void setLancioEffettuato(boolean lancioEffettuato){this.lancioEffettuato=lancioEffettuato;}

//--------------------------------------------GETTERS--------------------------------------------
    //utili per fornire informazioni durante il gioco

    public int getTurno() {
        return turno;
    }
    public String getUltimaSosta(){return gestoreEffetti.getUltimaSosta();}
    public String getUltimoPremio(){return gestoreEffetti.getUltimoPremio();}

    public int getLancio() {
        return lancio;
    }
    public HashMap<Casella, Mezzo> getMezziMap() {
        return mezziMap;
    }

    public List<Dado> getDadi() {
        return dadi;
    }
    public int getTotCaselle(){return totCaselle;}
    public int getSizeCaselleLibere(){return caselleLibere.getSize();}
    public int getnCaselleSosta(){return caselleSpecialiQuantita.get(Util.CaselleSpeciali.SOSTA);}
    public int getnCasellePremio() {return caselleSpecialiQuantita.get(Util.CaselleSpeciali.PREMIO);}
    public int getnCasellePescaCarta(){return caselleSpecialiQuantita.get(Util.CaselleSpeciali.PESCA);}
    public int getNPedine(){return pedine.length;}
    public Pedina[] getPedine(){return pedine;}
    public Tabellone getTabellone(){return tabellone;}
    public Carta getCartaPescata(){return cartaPescata;}
    public CommandHandler getCommandHandler(){return commandHandler;}
    public List<Mezzo> getMezzi(){return mezzi;}
    @Override
    public Casella getCasellaCorrente(){return pedine[turno].getCasella();}
    public String getVincitore(){return (Integer.toString(turno+1));}
    public ConfigurazioneGioco getConfigurazioneGioco(){
        return new ConfigurazioneGioco(
                0,
                this.tabellone.getR(),
                this.tabellone.getC(),
                this.pedine.length,
                this.mezziQuantita.get(TipoMezzo.SCALA),
                this.mezziQuantita.get(TipoMezzo.SERPENTE),
                this.isDadoSingolo(),
                this.isDadoSingoloFinale(),
                this.isDoppioSei(),
                this.isCaselleSosta(),
                this.isCasellePremio(),
                this.isPescaCarta(),
                this.isUlterioriCarte(),
                this.caselleSpecialiQuantita.get(Util.CaselleSpeciali.SOSTA),
                this.caselleSpecialiQuantita.get(Util.CaselleSpeciali.PREMIO),
                this.caselleSpecialiQuantita.get(Util.CaselleSpeciali.PESCA));
    }

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
    private int rimbalza(int pos){return totCaselle - 1 - (pos-totCaselle);}

    @Override
    public boolean undo(){ return commandHandler.undo();}
    @Override
    public boolean redo(){return commandHandler.redo();}

    @Override
    public void initCaselleSpeciali() {
        for(Util.CaselleSpeciali tipo: Util.CaselleSpeciali.values())
            caselleSpecialiQuantita.put(tipo,0);
        nCaselleSpeciali = 0;
    }


//--------------------------------------------GAME: OPERAZIONI DI BASE--------------------------------------------
    @Override
    public void prossimoTurno(){
        int turnoPrima = Math.max(turno, 0);
        VDoppioSei.action(this);
        if(pedine[turno].isInSosta()){
            pedine[turno].decSosta();
            VDoppioSei.action(this);
        }
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
    public void avanza() throws IllegalArgumentException{
        if(!lancioEffettuato)
            throw new IllegalArgumentException("Sistema: il lancio non è stato ancora effettuato");

        Pedina giocatore = pedine[turno];
        Casella casellaCorrente = giocatore.getCasella();
        int posCorrente = casellaCorrente.getPos();
        int posSuccessiva = posCorrente+lancio;

        //per vincere, deve ottenere il numero esatto di dadi per la casella finale, altrimenti "rimbalza" dall'ultima casella
        if(posSuccessiva>(totCaselle-1)) {
            posSuccessiva = rimbalza(posSuccessiva);
        }
        Casella casellaSuccessiva = tabellone.getCasella(posSuccessiva);
        Command avanzamento = new AvanzamentoCommand(casellaCorrente,casellaSuccessiva,giocatore);
        commandHandler.handle(avanzamento);
        lancioEffettuato = false;
    }
    @Override
    public boolean azionaCasella() throws IllegalArgumentException{
        if(lancioEffettuato)
            throw new IllegalArgumentException("Sistema: bisogna far avanzare la pedina prima");
        return gestoreEffetti.azionaCasella();
    }

}
