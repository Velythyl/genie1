import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Stamp extends Timestamp {

    public Stamp() {
        this(System.currentTimeMillis());
    }

    public Stamp(long time) {
        super(time);
    }

    public Stamp(String inputStr) {
        this(Stamp.strToMillis(inputStr));
    }

    private static long strToMillis(String inputStr) {
        //str is of format JJ-MM-AAAA HH:MM:SS
        //timestamp.valueof needs yyyy-[m]m-[d]d hh:mm:ss[.f...]. The fractional seconds may be omitted. The leading zero for mm and dd may also be omitted.

        String[] bigSmol = inputStr.split(" ");
        String[] bigs = bigSmol[0].split("-");

        if(bigs[3].length() == 4) { // if string is of wrong format for TimeStamp
            String smols = bigSmol.length > 1 ? bigSmol[1] : "00:00:00";

            List<String> reversed = Arrays.asList(bigs);
            Collections.reverse(reversed);
            inputStr = String.join("-", reversed)+" "+smols;
        }

        return Timestamp.valueOf(inputStr).getTime();
    }


    @Override
    public String toString() {
        String bad = super.toString();

        String m = ""+bad.charAt(5)+bad.charAt(6);
        String d = ""+bad.charAt(8)+bad.charAt(9);

        return d+"-"+m+"-"+bad.substring(0, 4)+bad.substring(10, bad.length());
    }
}
