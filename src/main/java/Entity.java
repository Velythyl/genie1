import java.util.ArrayList;

//NOTE: on utilise Timestamp partout par souci de convention au lieu de java.util.Date
public abstract class Entity extends GymClass implements UuidGymClass {
    private String name;
    private String city;
    private String address;
    private String province;
    private String postalCode;

    private String email;
    private UUID9 uuid;
    private static int nextUuid = 0;

    private ArrayList<Activity> activities;

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    public ArrayList<Activity> getActivities() {
        return this.activities;
    }

    public Entity(String name, String address,String province, String city, String postalCode, String comment, String email) {
        super(comment);

        this.name = name;
        this.address = address;
        this.email = email;

        this.uuid = new UUID9(nextUuid);
        nextUuid++;

        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.activities = new ArrayList<>();
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
        return obj.getName().equals(this.getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return name +
                ", uuID=" + getUuid() +
                ",\n";
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
