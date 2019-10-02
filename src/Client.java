import java.sql.Timestamp;

public class Client extends Entity {
    private boolean isSuspended;

    public Client(String name, String surname, String phone, String email, String address, String gender, Timestamp birthdate, String comment) {
        super(name, surname, phone, email, address, gender, birthdate, comment);

        this.isSuspended = false;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public void setSuspended(boolean suspended) {
        isSuspended = suspended;
    }

}
