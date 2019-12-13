public class Attendance extends GymClass {
    private UUID9 proNumber, clientNumber;
    private UUID7 activityNumber;
    private double price;

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

    public String toString(boolean andPrice) {
        return "Inscription: " +
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

