package elementi;

public class Posizione {
    private int x, y;

    public Posizione(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int compareTo(Posizione p){
        if(this.x==p.x) return Integer.compare(this.y,p.y);
        return Integer.compare(this.x,p.y);
    }
}
