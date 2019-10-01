import java.sql.Timestamp;

public class Professionnal extends Entity {
    public Professionnal(String name, String surname, String phone, String email, String address, boolean isMale, Timestamp birthdate, String comment) {
        super(name, surname, phone, email, address, isMale, birthdate, comment);
    }
}
