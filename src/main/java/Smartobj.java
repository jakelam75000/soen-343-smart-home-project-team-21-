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
    SmartObjectType type;
    /*
    constructor
     */
    public Smartobj (String nam, SmartObjectType typ){
        name = nam;
        type = typ;
    }

    public Smartobj (Smartobj so){
        this.name = so.getName();
        this.type = so.getType();
    }

    public String getName(){
        return name;
    }

    public SmartObjectType getType(){
        return type;
    }


    @Override
    public String toString() {
        return " this object is named "+ name+ "\n";
    }

    public abstract Smartobj clone();

}
