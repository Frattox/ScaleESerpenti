package game.sistema.GestoreEffetti;

import elementi.Casella;
import elementi.Mezzi.Mezzo;
import elementi.Pedina;
import game.sistema.Mattaro.Carta;
import game.sistema.Mattaro.Mattaro;
import game.sistema.Mattaro.MattaroImpl;
import game.sistema.SistemaImpl1;
import game.sistema.commands.*;

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
    public void azionaCasella() {
        Pedina[] pedine = sistema.getPedine();
        int turno = sistema.getTurno();
        Pedina pedinaCorrente = pedine[turno];
        Casella casellaCorrente = pedinaCorrente.getCasella();
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
            default:break;
            }
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
    }
    private void pesca(Pedina pedinaCorrente){
        Carta cartaPrecedente = sistema.getCartaPescata();
        Carta cartaPescata = mattaro.pesca();
        commandHandler.handle(new CartaCommand(sistema,cartaPrecedente, cartaPescata));
        azionaCarta(cartaPescata, pedinaCorrente);
    }
    private void mezzo(Pedina pedinaCorrente){
        HashMap<Casella,Mezzo> mezzi = sistema.getMezzi();
        Mezzo m = mezzi.get(pedinaCorrente.getCasella());
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
