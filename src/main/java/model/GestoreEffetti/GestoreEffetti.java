package model.GestoreEffetti;

//Colui che si occupa di azionare la casella
public interface GestoreEffetti {
    boolean azionaCasella();
    String getUltimaSosta();
    String getUltimoPremio();
}
