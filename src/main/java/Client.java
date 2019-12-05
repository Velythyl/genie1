import java.sql.Timestamp;
import java.util.ArrayList;

public class Client extends Entity {
    private boolean isSuspended;

    public Client(String name, String phone, String address, String province, String city, String postalCode, String comment, String email) {
        super(name, phone, address, province, city, postalCode, comment, email);

        this.isSuspended = false;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public void setSuspended(boolean suspended) {
        isSuspended = suspended;
    }
}
