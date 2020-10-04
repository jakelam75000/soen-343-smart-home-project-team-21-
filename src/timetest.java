
import java.math.RoundingMode;
import java.text.DecimalFormat;
public class timetest {
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    static double updatetime(double inputime){
        double outputtime;
        double temphr;
        double tempmin;
        //format hr.min
        String number;
        number = String.valueOf(inputime);
        int indexdecimal = number.indexOf(".");
        tempmin = Double.parseDouble(number.substring(indexdecimal));
        temphr = Double.parseDouble(number.substring(0,indexdecimal));
        if (tempmin >=.5999){
            tempmin = 0;
            temphr++;
        }
        else tempmin = tempmin+0.01;
        if (temphr > 23.99) temphr=0;
        outputtime = tempmin+ temphr;
        outputtime = Double.parseDouble(df2.format(outputtime));
        return outputtime;
    }
    public static void main(String[] args) {
        double initime = 23.34;
        String time = String.valueOf(initime);
        System.out.println("here is the input time: " +time.replace('.',':'));
        for (int i=0; i< 62; i++){
            initime = updatetime(initime);
            time = String.valueOf(initime);
            if (initime > 1)System.out.println("here is the new time: " + time.replace('.',':'));
            else System.out.println("here is the new time: 0" + time.replace('.',':'));
        }
    }
}
