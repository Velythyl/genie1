import java.util.Date;

public class Activity extends GymClass {
    private Date start, end;    //start and end of activity's offering
    private int hour, capacity, proNumber, uuid;
    private boolean[] days; //7-sized array

    private static int nextUuid = 0;

    public Activity(String comment, Date start, Date end, int hour, int capacity, int proNumber, boolean[] days) {
        super(comment);
        this.start = start;
        this.end = end;
        this.hour = hour;
        this.capacity = capacity;
        this.proNumber = proNumber;
        this.days = days;

        this.uuid = nextUuid;
        nextUuid++;
    }

    public int getUuid() {
        return uuid;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
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

    public boolean[] getDays() {
        return days;
    }

    public void setDays(boolean[] days) {
        this.days = days;
    }
}
