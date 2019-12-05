import java.io.Serializable;
import java.util.ArrayList;

public class Week implements Serializable {
    private boolean[] days;

    public Week() {
        days = new boolean[7];
    }

    public Week(String str) {
        this();
        setDays(str);
    }

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

    public void setDays(boolean[] days) {
        this.days = days;
    }

    public void setDays(String days) {
        String[] strArr = days.split(":");
        for(int i=0; i<strArr.length; i++) this.days[i] = strArr[i].equals("1");
    }
}
