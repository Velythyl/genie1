import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * An activity represents a seance
 */
public class Activity extends GymClass implements UuidGymClass {
    private Stamp start, end;    //start and end of activity's offering
    private int capacity;
    private UUID9 proNumber;
    private Hour hour;
    private Week week; //7-sized array
    private ArrayList<Client> inscriptions;    //uuid of clients
    private String name;
    private double price;

    private UUID7 uuid;

    private ArrayList<Attendance> attendances;

    private boolean payPerClient;

    /**
     * Builds a new activity
     *
     * @param comment the activity's comment
     * @param start the activity's start
     * @param end the activity's end
     * @param hour the activity's hour
     * @param capacity the activity's capcity
     * @param proNumber the activity's profesionnal
     * @param week the activity's reccuring days
     * @param name the activity's name
     * @param price the activity's price
     * @param payPerClient is the activity pay-per-client or pay-per-activity?
     * @param type the activity's type (boxing, swimming, etc)
     */
    public Activity(String comment, Stamp start, Stamp end, Hour hour, int capacity, UUID9 proNumber, Week week, String name, double price, boolean payPerClient, String type) {
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

    public ArrayList<Attendance> getAttendances() {
        return attendances;
    }

    public boolean isPayPerClient() {
        return payPerClient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Enrolls a client into this activity
     *
     * This is NOT saved on disk: we do NOT assume the client will show up, and so we only print a report in the
     * Inscriptions file
     *
     * @param client
     * @param date
     * @param comment
     */
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

    /**
     * Confirms the attendance of the client
     *
     * This is the TRUE marker of a client's attendance, and the pay of a payperclient activity is dependant on this
     *
     * @param clientUuid The ID of the client
     * @param comment
     */
    public void confirmAttendance(UUID9 clientUuid, String comment) {
        Attendance a = new Attendance(comment, this.proNumber, clientUuid, this.getUuid(), this.price);
        this.attendances.add(a);

        try(FileWriter fw = new FileWriter(new File("./Attendances.txt"), true)) {
            fw.write(a.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Is the client enrolled into this activity?
     *
     * @param clientUuid
     * @return
     */
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

    public Stamp getEnd() {
        return end;
    }

    public int getCapacity() {
        return capacity;
    }

    public UUID9 getProNumber() {
        return proNumber;
    }

    public Week getWeek() {
        return week;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public UUID7 getUuid() {
        return uuid;
    }
}
