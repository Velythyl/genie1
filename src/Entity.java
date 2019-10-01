import java.sql.Time;
import java.sql.Timestamp;

//NOTE: on utilise Timestamp partout par souci de convention au lieu de java.util.Date
public abstract class Entity extends UuidGymClass {
    private String name, surname, phone, email, address;
    private boolean isMale; //gender
    private Timestamp birthdate;

    public Entity(String name, String surname, String phone, String email, String address, boolean isMale, Timestamp birthdate, String comment) {
        super(comment);

        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.isMale = isMale;
        this.birthdate = birthdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public Timestamp getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Timestamp birthdate) {
        this.birthdate = birthdate;
    }


}
