public abstract class Entity extends GymClass implements UuidGymClass {
    private String name, surname, phone, email, address, gender;
    private Stamp birthdate;

    private UUID9 uuid;
    private static int nextUuid = 0;

    public Entity(String name, String surname, String phone, String email, String address, String gender, Stamp birthdate, String comment) {
        super(comment);

        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.birthdate = birthdate;

        this.uuid = new UUID9(nextUuid);
        nextUuid++;
    }

    /**
     * Compares two Entities
     *
     * Don't want to be TOO harsh, to resist the same person coming by with a new adress
     * @param obj
     * @return
     */
    public boolean equals(Entity obj) {
        return obj.getName().equals(this.getName()) && obj.getSurname().equals(this.getSurname()) && obj.getBirthdate().equals(this.getBirthdate());
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Stamp getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Stamp birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return name + '\'' +
                " " + surname + '\'' +
                ", uuID=" + getUuid() +
                ",\n";
    }

    public UUID getUuid() {
        return this.uuid;
    }
}
