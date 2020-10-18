/**
class smartobj, which the smart obj general type
its abstract as there is no smartobject that is simply a smart object. it must be a
child class
@author Jake Lamothe
@version 0.1
@since 2020-09-30
 */
public abstract class Smartobj implements Cloneable{
    String name;
    SmartObjectType type;

    /**
     * Constructor
     * @param nam String is the name of the object
     * @param typ Smartobjecttype is the obejct type
     */
    public Smartobj (String nam, SmartObjectType typ){
        name = nam;
        type = typ;
    }

    /**
     *copy constructor
     * @param so smartobject to be copied
     */
    public Smartobj (Smartobj so){
        this.name = so.getName();
        this.type = so.getType();
    }

    /**
     * getter method
     * @return String the name of the object
     */
    public String getName(){
        return name;
    }

    /**
     * getter method
     * @return SmartObjectType the type of smartobject it is
     */
    public SmartObjectType getType(){
        return type;
    }

    /**
     * tostring
     * @return String the string value of the obejct
     */

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return " this object is named "+ name+ "\n";
    }

    /**
     * Clone method.
     *
     * @return a copy of the Smartobj object.
     */
    @Override
    public abstract Smartobj clone();

}
