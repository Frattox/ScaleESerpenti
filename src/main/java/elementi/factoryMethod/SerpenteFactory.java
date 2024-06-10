package elementi.factoryMethod;

import elementi.Casella;

public class SerpenteFactory  extends MezzoFactory{

    @Override
    public Mezzo factory(){return new Serpente();}
}
