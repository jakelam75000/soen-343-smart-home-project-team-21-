/*
class smartobj, which the smart obj general type
its abstract as there is no smartobject that is simply a smart object. it must be a
child class
@author Jake Lamothe
@version 0.1
@since 2020-09-30
 */
public abstract class Smartobj {
    String name;
    /*
    constructor
     */
    public Smartobj (String nam){
        name = nam;
    }

    @Override
    public String toString() {
        return " this object is named "+ name+ "\n";
    }
}
