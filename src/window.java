public class window extends Smartobj  {
    boolean blocked;
    boolean open;

    public window (String name){
        super(name);
        blocked = false;
        open = false;
    }
    public void setblocked( boolean blockedornot){
        blocked = blockedornot;
    }
    public void setopen (boolean openorclosed){
        open = openorclosed;
    }
    public boolean isBlocked(){
        return blocked;
    }
    public boolean isOpen(){
        return open;
    }

    @Override
    public String toString() {
        return "this is a window and its name is " + super.name +".\n";
    }
}
