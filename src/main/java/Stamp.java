import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A Stamp is a wrapper around a sql.Timestamp
 *
 * We need this class to make it compatible with the time formats specified in the requirements: sql.Timestamp does not
 * respect it.
 */
public class Stamp extends Timestamp {

    /**
     * A Stamp with no construction parameters is a stamp of the current moment (calls Stamp(long time))
     */
    public Stamp() {
        this(System.currentTimeMillis());
    }

    /**
     * Creates a new Stamp for the specified time
     *
     * @param time the time in millis of the Stamp
     */
    public Stamp(long time) {
        super(time);
    }

    /**
     * Creates a new Stamp from a string in JJ-MM-AAAA [HH:MM:SS] format (HH:MM:SS is optional) (calls Stamp(long time))
     * @param inputStr
     */
    public Stamp(String inputStr) {
        this(Stamp.strToMillis(inputStr));
    }

    /**
     * Transforms a string into the corresponding milliseconds.
     *
     * This seems fairly dumb: why no simple build the Stamp directly from the string, inside the constructor?
     *
     * Well, sql.Timestamp does not have a string constructor, only a static method Timestamp.valueOf(string s). So we
     * have to:
     *
     * 1. transform the input string into the format used by Timestamp
     * 2. Create a new Timestamp using valueOf
     * 3. return the corresponding millis time of that timestamp.
     *
     * @param inputStr the input string in JJ-MM-AAAA [HH:MM:SS] format (HH:MM:SS is optional)
     * @return the corresponding millis time
     */
    private static long strToMillis(String inputStr) {
        //str is of format JJ-MM-AAAA HH:MM:SS
        //timestamp.valueof needs yyyy-[m]m-[d]d hh:mm:ss[.f...]. The fractional seconds may be omitted. The leading zero for mm and dd may also be omitted.

        String[] bigSmol = inputStr.split(" ");
        String[] bigs = bigSmol[0].split("-");

        if(bigs[2].length() == 4) { // if string is of wrong format for TimeStamp
            String smols = bigSmol.length > 1 ? bigSmol[1] : "00:00:00";

            List<String> reversed = Arrays.asList(bigs);
            Collections.reverse(reversed);
            inputStr = String.join("-", reversed)+" "+smols;
        }

        return Timestamp.valueOf(inputStr).getTime();
    }

    /**
     * The toString for a Stamp is simple: it's the same as the one for a Timestamp, but we switch the days, months and
     * years around to make it fit our format
     *
     * @return the well-formatted string
     */
    @Override
    public String toString() {
        String bad = super.toString();

        String m = ""+bad.charAt(5)+bad.charAt(6);
        String d = ""+bad.charAt(8)+bad.charAt(9);

        return d+"-"+m+"-"+bad.substring(0, 4)+bad.substring(10, bad.length());
    }
}
