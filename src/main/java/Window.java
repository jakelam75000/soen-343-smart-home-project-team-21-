/**
*class window is a smart object which is stored in rooms
*@author Jake Lamothe
*@version 0.1
*@since 2020-09-30
 */
public class Window extends Smartobj  {
    private boolean blocked;
    private boolean open;

    /**
    * constructor
    */
    public Window(String name){
        super(name, SmartObjectType.WINDOW);
        blocked = false;
        open = false;
    }

    public Window(Window window){
        super(window);
        this.blocked = window.isBlocked();
        this.open = window.isOpen();

    }


    /**
     * mutator method
     * @param blockedornot Boolean is the new value of blocked (is the window blocked from closing)
     */
    public void setBlocked(boolean blockedornot){
        blocked = blockedornot;
    }

    /**
     * mutator method
     * @param openorclosed is the new value of open [is the window open or closed)
     */
    public boolean setOpen (boolean openorclosed){
        if(blocked){
            return false;
        }
        else{
            open = openorclosed;
            return true;
        }
    }

    /**
     *
     * @return
     */
    public boolean isBlocked(){
        return blocked;
    }
    /**
    accesor method
     */
    public boolean isOpen(){
        return open;
    }

    @Override
    public String toString() {
        return "this is a window and its name is " + super.name +".\n";
    }

    public Window clone(){
        return new Window(this);
    }

}
