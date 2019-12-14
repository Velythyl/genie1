import java.io.Serializable;
import java.sql.Timestamp;

/**
 * An Hour is a sql.timestamp that has been set to 2000-01-01 by our convention.
 *
 * This way, all timestamp comparison methods still work: we can check if an Hour is after or before another, if it's
 * the same, etc. But since all Hours are on 2000-01-01, we known the comparisons all make sense.
 */
class Hour extends Stamp implements Serializable {
    /**
     * Creates a new hour
     *
     * @param str the string representation of the Hour (HH:MM)
     */
    public Hour(String str) {
        super("2000-01-01 "+str+":00");
    }

    /**
     *
     * @return the Hour in string format (HH:MM)
     */
    public String toString() {
        return super.toString().substring(11, 16);
    }
}
