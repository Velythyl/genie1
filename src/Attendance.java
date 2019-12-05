public class Attendance extends GymClass {
    private UUID proNumber, clientNumber, activityNumber;

    public Attendance(String comment, UUID9 proNumber, UUID9 clientNumber, UUID7 activityNumber) {
        super(comment);
        this.proNumber = proNumber;
        this.clientNumber = clientNumber;
        this.activityNumber = activityNumber;
    }

    /**
     * Constructeur qui fait plus de sens: on plug direct les instances des autres trucs et boum
     * @param comment
     * @param pro
     * @param client
     * @param activity
     */
    public Attendance(String comment, Professionnal pro, Client client, Activity activity) {
        this(comment, pro.getUuid(), client.getUuid(), activity.getUuid());
    }

    @Override
    public String toString() {
        return "Inscription: " +
                "proNumber= " + proNumber +
                ", clientNumber= " + client.getUuid() +
                ", activityNumber= " + this.getUuid() +
                ", activityDate= " + date +
                ", comment= " + comment +
                ", creationStamp= " + new Stamp() +
                "\n"
    }

    public int getProNumber() {
        return proNumber;
    }

    public void setProNumber(int proNumber) {
        this.proNumber = proNumber;
    }

    public int getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(int clientNumber) {
        this.clientNumber = clientNumber;
    }

    public int getActivityNumber() {
        return activityNumber;
    }

    public void setActivityNumber(int activityNumber) {
        this.activityNumber = activityNumber;
    }
}
