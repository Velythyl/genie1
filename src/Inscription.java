import java.sql.Timestamp;

public class Inscription extends GymClass {
    private int proNumber, clientNumber, activityNumber;
    private Timestamp activityDate;

    public Inscription(String comment, int proNumber, int clientNumber, int activityNumber, Timestamp activityDate) {
        super(comment);
        this.proNumber = proNumber;
        this.clientNumber = clientNumber;
        this.activityNumber = activityNumber;
        this.activityDate = activityDate;
    }

    /**
     * Constructeur qui fait plus de sens: on plug direct les instances des autres trucs et boum
     *
     * Note: on peut pas juste plugger la date de l'activite au lieu de activityDate car les activites peuvent avoir
     * plus qu'une date
     * @param comment
     * @param pro
     * @param client
     * @param activity
     * @param activityDate
     */
    public Inscription(String comment, Professionnal pro, Client client, Activity activity, Timestamp activityDate) {
        this(comment, pro.getUuid(), client.getUuid(), activity.getUuid(), activityDate);
    }

    @Override
    public String toString() {
        return "Inscription{" +
                "proNumber=" + proNumber +
                ", clientNumber=" + clientNumber +
                ", activityNumber=" + activityNumber +
                ", activityDate=" + activityDate +
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

    public Timestamp getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(Timestamp activityDate) {
        this.activityDate = activityDate;
    }
}
