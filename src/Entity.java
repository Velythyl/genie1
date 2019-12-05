//NOTE: on utilise Timestamp partout par souci de convention au lieu de java.util.Date
public abstract class Entity extends UuidGymClass {
    private String name, surname, phone, city, address, gender, province, postalCode;

    public Entity(String name, String surname, String phone, String address,String province, String city, String postalCode, String comment) {
        super(comment);

        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
    }
    // nom surnom numero ville addresse province code postal TODO changer le ui pi enlever toute squi est pas ces affaires la

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Compares two Entities
     *
     * Don't want to be TOO harsh, to resist the same person coming by with a new adress
     * @param obj
     * @return
     */
    public boolean equals(Entity obj) {
        return obj.getName().equals(this.getName()) && obj.getSurname().equals(this.getSurname());
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


    @Override
    public String toString() {
        return name + '\'' +
                " " + surname + '\'' +
                ", uuID=" + getUuidStr() +
                ",\n";
    }
}
