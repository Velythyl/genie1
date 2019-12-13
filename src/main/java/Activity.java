import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Activity extends GymClass implements UuidGymClass {
    private Stamp start, end;    //start and end of activity's offering
    private int capacity;
    private UUID9 proNumber;
    private Hours hour;
    private Week week; //7-sized array
    private ArrayList<Client> inscriptions;    //uuid of clients
    private String name;
    private double price;

    private UUID7 uuid;

    private ArrayList<Attendance> attendances;

    public ArrayList<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(ArrayList<Attendance> attendances) {
        this.attendances = attendances;
    }

    public boolean isPayPerClient() {
        return payPerClient;
    }

    public void setPayPerClient(boolean payPerClient) {
        this.payPerClient = payPerClient;
    }

    private boolean payPerClient;

    public Activity(String comment, Stamp start, Stamp end, Hours hour, int capacity, UUID9 proNumber, Week week, String name, double price, boolean payPerClient, String type) {
        super(comment);
        this.start = start;
        this.end = end;
        this.hour = hour;
        this.capacity = capacity;
        this.proNumber = proNumber;
        this.week = week;
        this.name = name;
        this.price = price;

        this.inscriptions = new ArrayList<>();
        this.attendances = new ArrayList<>();

        DataStore ds = DataStore.getInstance();
        this.uuid = ds.generateActivityID(proNumber, type);

        this.payPerClient = payPerClient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void enroll(Client client, Timestamp date, String comment) {
        this.inscriptions.add(client);
        client.addActivity(this);

        try(FileWriter fw = new FileWriter(new File("./Inscriptions.txt"), true)) {
            fw.write(
                    "Inscription: " +
                    "proNumber= " + proNumber +
                    ", clientNumber= " + client.getUuid() +
                    ", activityNumber= " + this.getUuid() +
                    ", activityDate= " + date +
                    ", comment= " + comment +
                    ", creationStamp= " + new Stamp() +
                    "\n"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void confirmAttendance(UUID9 clientUuid, String comment) {
        Attendance a = new Attendance(comment, this.proNumber, clientUuid, this.getUuid(), this.price);
        this.attendances.add(a);

        try(FileWriter fw = new FileWriter(new File("./Attendances.txt"), true)) {
            fw.write(a.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isEnrolled(UUID clientUuid) {
        for(Client i: this.inscriptions) if(i.getUuid().equals(clientUuid)) return true;

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
                ", Jours:[" + week.toString() + "]" +
                ", inscriptions:" + inscriptions.size() + " clients";
    }

    public ArrayList<Client> getInscriptions() {
        return inscriptions;
    }

    public void setInscriptions(ArrayList<Client> inscriptions) {
        this.inscriptions = inscriptions;
    }

    public Stamp getStart() {
        return start;
    }

    public void setStart(Stamp start) {
        this.start = start;
    }

    public Stamp getEnd() {
        return end;
    }

    public void setEnd(Stamp end) {
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

    public UUID9 getProNumber() {
        return proNumber;
    }

    public void setProNumber(UUID9 proNumber) {
        this.proNumber = proNumber;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public UUID7 getUuid() {
        return uuid;
    }
}
