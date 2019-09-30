import java.util.Date;

public abstract class Entity extends GymClass {
    private String name, surname, phone, email, address;
    private boolean isMale; //gender
    private Date birthdate;
    private int uuid;    //unique id

    private static int nextUuid = 0;

    public Entity(String name, String surname, String phone, String email, String address, boolean isMale, Date birthdate, String comment) {
        super(comment);

        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.isMale = isMale;
        this.birthdate = birthdate;

        this.uuid = nextUuid;
        Entity.nextUuid++;
    }

    public int getUuid() {   //only a getter here: can't change uuid
        return uuid;
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

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }


}
