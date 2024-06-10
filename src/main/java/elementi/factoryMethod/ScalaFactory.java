package elementi.factoryMethod;

public class ScalaFactory extends MezzoFactory{

    @Override
    public Mezzo factory() {
        return new Scala();
    }
}
