/**
 *class Door is a smart object which is stored in rooms
 *@since 2020-09-30
 */
public class Door extends Smartobj implements Cloneable {
    private boolean locked;
    private boolean open;

    /**
     * constructor
     */
    public Door(String name) {
        super(name, SmartObjectType.DOOR);
        locked = false;
        open = false;
    }

    /**
     * copy cosntructor
     *
     * @param door Door to be copied
     */
    public Door(Door door) {
        super(door);
        this.locked = door.islocked();
        this.open = door.isOpen();

    }


    /**
     * mutator method
     *
     * @param lockedornot Boolean is the new value of locked (is the door locked from opening)
     */
    public boolean setlocked(boolean lockedornot) {
        if (open) return false;
        else {
            locked = lockedornot;
            return true;
        }
    }

    /**
     * mutator method
     *
     * @param openorclosed is the new value of open [is the Door open or closed)
     */
    public boolean setOpen(boolean openorclosed) {
        if (locked) {
            return false;
        } else {
            open = openorclosed;
            return true;
        }
    }

    /**
     * @return
     */
    public boolean islocked() {
        return locked;
    }

    /**
     * accesor method
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * Value of the obejct as a string
     *
     * @return String the value of the objest as a string
     */
    @Override
    public String toString() {
        return "this is a Door and its name is " + super.name + ".\n";
    }

    /**
     * clones the Door
     *
     * @return Door the new close of the door
     */
    @Override
    public Door clone() {
        return new Door(this);
    }
}