import java.io.Serializable;
import java.util.ArrayList;

/**
 * A week is like a enum for the days of the week (indicating which day is marked as true and which aren't), but it also
 * contains helpful string methods.
 */
public class Week implements Serializable {
    private boolean[] days;

    /**
     * Creates a new Week object with the requested days marked a true.
     *
     * The week starts on a saturday because it is specified in the requirements that the automated accounting method is
     * printed on fridays. So the accounting weeks go saturday -> friday.
     *
     * So the input string (a colon-separated list of 0s and 1s (if 0, the day is marked false, else it is marked true))
     * must match the weekday order: samedi, dimanche, lundi, mardi, mecredi, jeudi, vendredi.
     *
     * @param str a colon-separated list of 0s and 1s (if 0, the day is marked false, else it is marked true).
     */
    public Week(String str) {
        days = new boolean[7];
        setDays(str);
    }

    /**
     * The toString of a Week is the human-readable representation of its boolean array: we simply add the days that are
     * marked true to a comma-separated string list.
     *
     * @return the string representation of the Week
     */
    @Override
    public String toString() {
        String[] jours = new String[]{"samedi", "dimanche", "lundi", "mardi", "mecredi", "jeudi", "vendredi"};
        ArrayList<String> list = new ArrayList<>();
        for(int i=0; i<7; i++) {
            if(days[i]) list.add(jours[i]);
        }

        return String.join(", ", list);
    }

    public boolean[] getDays() {
        return days;
    }

    /**
     * setDays reads a colon-separated list of 0s and 1s (if 0, the day is marked false, else it is marked true) and
     * saves it into a boolean array format
     *
     * @param days the days string, a colon-separated list of 0s and 1s (if 0, the day is marked false, else it is
     *             marked true)
     */
    public void setDays(String days) {
        String[] strArr = days.split(":");
        for(int i=0; i<strArr.length; i++) this.days[i] = strArr[i].equals("1");
    }
}
