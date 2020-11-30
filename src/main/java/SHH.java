import java.util.ArrayList;

public class SHH implements Observer{

    private static SHH instance;

    static {
        instance = new SHH();
    }

    private SHH(){}

    public static SHH getInstance(){
        return instance;
    }

    @Override
    public void update(Observable o) {

    }
}
