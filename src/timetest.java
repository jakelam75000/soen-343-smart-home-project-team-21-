
import java.math.RoundingMode;
import java.text.DecimalFormat;
public class timetest {
    static String updatetime(String inputime) {
        String outputtime;
        int temphr;
        int tempmin;
        int tempsec;
        String second;
        String hour;
        String minute;
        //format hr.min
        int indexmid = inputime.indexOf(":");
        int indexmid2 = inputime.substring(indexmid+1).indexOf(":");
        tempmin = Integer.parseInt(inputime.substring(indexmid + 1,indexmid2+indexmid+1));
        temphr = Integer.parseInt(inputime.substring(0, indexmid));
        tempsec = Integer.parseInt(inputime.substring(indexmid2+2 +indexmid));
        if (tempsec > 58) {
            tempsec = 0;
            tempmin++;
        } else tempsec++;
        if (tempmin > 58){
            tempmin =0;
            temphr++;
        }
        if (temphr >= 24) temphr = 0;
        if (temphr <10) hour = "0" + temphr;
        else hour = String.valueOf(temphr);
        if (tempmin <10) minute = "0" + tempmin;
        else minute = String.valueOf(tempmin);
        if (tempsec <10) second = "0" + tempsec;
        else second = String.valueOf(tempsec);
        outputtime = hour+":"+minute+":"+second;
        return outputtime;
    }

    public static void main(String[] args) {
        String s = "3:3:4";
        for(int i=0;i<1000;i++){
            s = updatetime(s);
            System.out.println(s);
        }
    }
}