import java.util.ArrayList;

/**
 * An Entity is an IRL person (so a Client or a Professional).
 * <p>
 * Entities have a list of activity whose meaning changes depending on which Entity it is: if it's a Client, it's the
 * list of activities he's enrolled into. If it's a pro, it's the list of activities he gives/teaches/etc.
 */
public abstract class Entity extends GymClass implements UuidGymClass {
    private static int nextUuid = 0;
    private String name;
    private String city;
    private String address;
    private String province;
    private String postalCode;
    private String email;
    private UUID9 uuid;
    private ArrayList<Activity> activities;

    /**
     * Creates a new entity.
     * <p>
     * Here we create a new 9-char UUID from a static int. In real life, this method would be handled by the SQL
     * database of the program.
     * <p>
     * See descendants' constructors for more details.
     *
     * @param name
     * @param address
     * @param province
     * @param city
     * @param postalCode
     * @param comment
     * @param email
     */
    public Entity(String name, String address, String province, String city, String postalCode, String comment, String email) {
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

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    public ArrayList<Activity> getActivities() {
        return this.activities;
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
        return "Client's name: " + name +
                "\t client's UUID=" + getUuid() +
                "\n";
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
