package model.elementi.Mezzi;

public class SerpenteFactory  extends MezzoFactory{

    @Override
    public Mezzo factory(){return new Serpente();}
}
