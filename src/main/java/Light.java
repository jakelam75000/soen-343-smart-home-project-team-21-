/**
 * class Light is a smart object and is in every room
 */
public class Light extends Smartobj {
    private boolean ON;

    /**
     * constructor
     * @param name
     */
    public Light(String name) {
        super(name, SmartObjectType.LIGHT);
    }

    /**
     * Copy constructor
     * @param light
     */
    public Light (Light light) {
        super(light);
        this.ON = light.isON();
    }

    /**
     * accessor method
     * @return
     */
    public boolean isON() {
        return ON;
    }

    /**
     * mutator method
     * @param ON
     */
    public boolean setON(boolean ON) {
        this.ON = ON;
        return true;
    }

    /**
     * returns light name and state
     * @return
     */
    @Override
    public String toString() {
        String value = ON? "on":"off";
        return "This is window " + super.name + " and it is "+ value +"\n";
    }

    /**
     * clones the light
     * @return
     */
    @Override
    public Light clone() {
        return new Light(this);
    }
}
