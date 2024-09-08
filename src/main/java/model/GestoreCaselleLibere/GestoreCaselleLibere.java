package model.GestoreCaselleLibere;

import model.elementi.Casella;

//Per caselle libere si intendono quelle di tipo Casella.Tipo.NORMALE
public interface GestoreCaselleLibere {
    void setRandomCasellaLibera(Casella.Tipo tipo);
    void setCasellaLibera(int i, Casella.Tipo tipo);
    int getSize();
}
