package model.GestoreEffetti;

import model.elementi.Casella;
import model.elementi.Mezzi.Mezzo;
import model.elementi.Pedina;
import model.Mattaro.Carta;
import model.Mattaro.Mattaro;
import model.Mattaro.MattaroImpl;
import model.SistemaImpl1;
import model.commands.CartaCommand;
import model.commands.CommandHandler;
import model.commands.MezzoCommand;
import model.commands.SostaCommand;

import java.util.HashMap;

public class GestoreEffettiImpl implements GestoreEffetti{

    private final SistemaImpl1 sistema;
    private static final int SOSTA_PANCHINA = 1, SOSTA_LOCANDA = 3;
    private final Mattaro mattaro;
    private final CommandHandler commandHandler;

    public GestoreEffettiImpl(SistemaImpl1 sistema){
        if(sistema==null)
            throw new IllegalArgumentException("GestoreEffettiImpl: sistema null");
        this.sistema=sistema;
        this.commandHandler=sistema.getCommandHandler();
        this.mattaro=new MattaroImpl(sistema);
    }

    @Override
    public boolean azionaCasella() {
        Pedina[] pedine = sistema.getPedine();
        int turno = sistema.getTurno();
        Pedina pedinaCorrente = pedine[turno];
        Casella casellaCorrente = pedinaCorrente.getCasella();
        boolean vittoria = false;
        switch (casellaCorrente.getTipo()){
            case MEZZO_FROM:
                mezzo(pedinaCorrente);
                break;
            case LOCANDA:
                sosta(pedinaCorrente,SOSTA_LOCANDA);
                break;
            case PANCHINA:
                sosta(pedinaCorrente, SOSTA_PANCHINA);
                break;
            case MOLLA:
                molla();
                break;
            case DADI://rilancia i dadi
                dadi();
                break;
            case PESCA:
                pesca(pedinaCorrente);
                break;
            case FINE:
                vittoria = true;
                break;
            default:break;
            }
        return vittoria;
    }

    //per semplicità, il divieto di sosta viene usato ogni qualvolta si arriva su una sosta
    private void sosta(Pedina pedinaCorrente, int sosta){
        if(sistema.isUlterioriCarte() && pedinaCorrente.isInDivietoDiSosta())
            pedinaCorrente.setDivietoDiSosta(false);
        else
            commandHandler.handle(new SostaCommand(pedinaCorrente,sosta));
    }

    //la pedina avanza con lo stesso lancio di dadi effettuato
    private void molla(){
        sistema.setLancioEffettuato(true);
        sistema.avanza(); //step command viene gestito già qui
    }
    private void dadi(){
        sistema.lancia();//dadi command viene gestito già qui
        sistema.avanza();
    }
    private void pesca(Pedina pedinaCorrente){
        Carta cartaPrecedente = sistema.getCartaPescata();
        Carta cartaPescata = mattaro.pesca();
        commandHandler.handle(new CartaCommand(sistema,cartaPrecedente, cartaPescata));
        azionaCarta(cartaPescata, pedinaCorrente);
    }
    private void mezzo(Pedina pedinaCorrente){
        Mezzo m = sistema.getMezziMap().get(pedinaCorrente.getCasella());
        commandHandler.handle(new MezzoCommand(m,pedinaCorrente));
    }

    private void azionaCarta(Carta cartaPescata, Pedina pedinaCorrente){
        switch (cartaPescata){
            case LOCANDA:
                sosta(pedinaCorrente,SOSTA_LOCANDA);
            case PANCHINA:
                sosta(pedinaCorrente,SOSTA_PANCHINA);
                break;
            case MOLLA://la pedina avanza con lo stesso lancio di dadi effettuato
                molla();
                break;
            case DADI://rilancia i dadi
                dadi();
                break;
            case DIVIETO_SOSTA:
                pedinaCorrente.setDivietoDiSosta(true);
                break;
            default:break;
        }
    }
}
