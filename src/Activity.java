import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

public class Activity extends UuidGymClass {
    private Timestamp start, end;    //start and end of activity's offering
    private int capacity, proNumber;
    private Hours hour;
    private Days days; //7-sized array
    private ArrayList<Client> inscriptions;    //uuid of clients
    private String name;
    private double price;

    public Activity(String comment, Timestamp start, Timestamp end, Hours hour, int capacity, int proNumber, Days days, String name, double price) {
        super(comment, 7);
        this.start = start;
        this.end = end;
        this.hour = hour;
        this.capacity = capacity;
        this.proNumber = proNumber;
        this.days = days;
        this.name = name;
        this.price = price;

        this.inscriptions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void enroll(Client client, Timestamp date, String comment) {
        this.inscriptions.add(client);

        try(FileWriter fw = new FileWriter(new File("./Inscriptions.txt"), true)) {
            fw.write(new Inscription(comment, this.proNumber, client.getUuid(), this.getUuid(), date).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void confirmAttendance(int clientUuid, String comment) {
        try(FileWriter fw = new FileWriter(new File("./Attendances.txt"), true)) {
            fw.write(new Attendance(comment, this.proNumber, clientUuid, this.getUuid()).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isEnrolled(int clientUuid) {
        for(Client i: this.inscriptions) if(i.getUuid() == clientUuid) return true;

        return false;
    }

    @Override
    public String toString() {
        String hour2 = hour.toString().split(" ")[1];
        hour2 = hour2.split(":")[0] + " " + hour2.split(":")[1];

        return name + ": ID=" + getUuid() + " " +
                "début:" + start.toString().split(" ")[0] +
                ", fin:" + end.toString().split(" ")[0] +
                ", heure:" + hour2 +
                ", capacité:" + capacity +
                ", IDduPro:" + proNumber +
                ", Jours:[" + days.toString() + "]" +
                ", inscriptions:" + inscriptions.size() + " clients";
    }

    public ArrayList<Client> getInscriptions() {
        return inscriptions;
    }

    public void setInscriptions(ArrayList<Client> inscriptions) {
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

    public Hours getHour() {
        return hour;
    }

    public void setHour(Hours hour) {
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

    public Days getDays() {
        return days;
    }

    public void setDays(Days days) {
        this.days = days;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
