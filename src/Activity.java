import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

public class Activity extends UuidGymClass {
    private Timestamp start, end;    //start and end of activity's offering
    private int hour, capacity, proNumber;
    private Boolean[] days; //7-sized array
    private ArrayList<Integer> inscriptions;    //uuid of clients
    private String name;

    /**
     *
     * @param comment
     * @param start
     * @param end
     * @param hour
     * @param capacity
     * @param proNumber
     * @param days
     * @param inscriptions csv of the inscriptions
     */
    public Activity(String comment, Timestamp start, Timestamp end, int hour, int capacity, int proNumber, Boolean[] days, Integer[] inscriptions, String name) {
        super(comment);
        this.start = start;
        this.end = end;
        this.hour = hour;
        this.capacity = capacity;
        this.proNumber = proNumber;
        this.days = days;
        this.name = name;

        this.inscriptions = new ArrayList<>(Arrays.asList(inscriptions));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        return "Activity{" +
                "start=" + start +
                ", end=" + end +
                ", hour=" + hour +
                ", capacity=" + capacity +
                ", proNumber=" + proNumber +
                ", days=" + (days == null ? null : Arrays.asList(days)) +
                ", inscriptions=" + inscriptions +
                ", name='" + name + '\'' +
                "} " + super.toString();
    }

    public ArrayList<Integer> getInscriptions() {
        return inscriptions;
    }

    public void setInscriptions(ArrayList<Integer> inscriptions) {
        this.inscriptions = inscriptions;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getProNumber() {
        return proNumber;
    }

    public void setProNumber(int proNumber) {
        this.proNumber = proNumber;
    }

    public Boolean[] getDays() {
        return days;
    }

    public void setDays(Boolean[] days) {
        this.days = days;
    }
}
