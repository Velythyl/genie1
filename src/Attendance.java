public class Attendance extends GymClass {
    private int proNumber, clientNumber, activityNumber;

    public Attendance(String comment, int proNumber, int clientNumber, int activityNumber) {
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
        return "Attendance{" +
                "proNumber=" + proNumber +
                ", clientNumber=" + clientNumber +
                ", activityNumber=" + activityNumber +
                "} " + super.toString();
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
