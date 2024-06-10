package elementi;

import elementi.factoryMethod.Posizione;

public class TabelloneMatrix extends Tabellone{

    private Casella[][] tabella;

    public TabelloneMatrix()
    {
        super();
        this.tabella = new Casella[r][c];
        init();
    }

    public TabelloneMatrix(int r, int c) {
        super(r, c);
        this.tabella = new Casella[r][c];
        init();
    }

    private void init(){
        for(int i=0;i<r;i++)
            for(int j=0; j<c; j++)
            {
                int pos = i*this.c+j;
                this.tabella[i][j] = new Casella(Casella.Tipo.NORMALE,pos);
            }
    }

    @Override
    public Casella getCasella(int i, int j){return tabella[i][j];}

    @Override
    public Casella getCasella(int pos) {
        Posizione p = getPosCasella(pos);
        return tabella[p.getX()][p.getY()];
    }
}
