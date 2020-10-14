/*
class window is a smart object which is stored in rooms
@author Jake Lamothe
@version 0.1
@since 2020-09-30
 */
public class Window extends Smartobj  {
    boolean blocked;
    boolean open;
    /*
    constructor
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
    /*
    mutator method
    @param blockedornot is the new value of blocked [is the window blocked from closing)
     */
    public void setblocked( boolean blockedornot){
        blocked = blockedornot;
    }
    /*
   mutator method
   @param openorclosed is the new value of open [is the window open or closed)
    */
    public void setopen (boolean openorclosed){
        open = openorclosed;
    }
    /*
    accesor method
     */
    public boolean isBlocked(){
        return blocked;
    }
    /*
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
