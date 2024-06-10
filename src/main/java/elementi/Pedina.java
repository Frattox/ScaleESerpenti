package elementi;

import elementi.factoryMethod.Posizione;

public class Pedina {
    private Casella curr;

    public Pedina(Casella curr) {
        this.curr = curr;
    }

    public Casella getCasella(){
        return curr;
    }

    public void setPos(Casella curr) {
        this.curr = curr;
    }
}
