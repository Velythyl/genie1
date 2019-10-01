import java.sql.Timestamp;

public class Client extends Entity {
    private boolean isSuspended;

    public Client(String name, String surname, String phone, String email, String address, boolean isMale, Timestamp birthdate, String comment) {
        super(name, surname, phone, email, address, isMale, birthdate, comment);

        this.isSuspended = false;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public void setSuspended(boolean suspended) {
        isSuspended = suspended;
    }

}
