import java.util.Date;

public class Client extends Entity {
    public Client(String name, String surname, String phone, String email, String address, boolean isMale, Date birthdate) {
        super(name, surname, phone, email, address, isMale, birthdate);
    }
}
