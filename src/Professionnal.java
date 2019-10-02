import java.sql.Timestamp;

public class Professionnal extends Entity {
    public Professionnal(String name, String surname, String phone, String email, String address, String gender, Timestamp birthdate, String comment) {
        super(name, surname, phone, email, address, gender, birthdate, comment);
    }
}
