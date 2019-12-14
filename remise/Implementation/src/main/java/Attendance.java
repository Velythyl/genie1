/**
 * An attendance is the marker of a client's attendance to an acitity
 *
 * The pay is calculated from Attendances.
 */
public class Attendance extends GymClass {
    private UUID9 proNumber, clientNumber;
    private UUID7 activityNumber;
    private double price;

    /**
     * Buils a new Attendance
     *
     * @param comment
     * @param proNumber
     * @param clientNumber
     * @param activityNumber
     * @param price The price is saved here
     */
    public Attendance(String comment, UUID9 proNumber, UUID9 clientNumber, UUID7 activityNumber, double price) {
        super(comment);
        this.proNumber = proNumber;
        this.clientNumber = clientNumber;
        this.activityNumber = activityNumber;
        this.price = price;
    }

    @Override
    public String toString() {
        return toString(false);
    }

    public double getPrice() {
        return price;
    }

    /**
     * Makes a string from this Attendance
     *
     * @param andPrice indicates wether or not to include the price in the string
     * @return
     */
    public String toString(boolean andPrice) {
        return "Attendance: " +
                "proNumber= " + proNumber +
                ", clientNumber= " + clientNumber +
                ", activityNumber= " + activityNumber +
                ", comment= " + this.getComment() +
                ", creationStamp= " + this.getCreationStamp() +
                (andPrice ? ", price= " + this.price : "") +
                "\n";
    }

    public UUID9 getClientNumber() {
        return clientNumber;
    }
}

