public abstract class Smartobj {
    String name;
    public Smartobj (String nam){
        name = nam;
    }

    @Override
    public String toString() {
        return " this object is named "+ name+ "\n";
    }
}
