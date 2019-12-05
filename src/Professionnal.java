import java.util.ArrayList;

public class Professionnal extends Entity {
    private ArrayList<Activity> activities;

    public Professionnal(String name, String phone, String address,String province, String city, String postalCode, String comment, String email) {
        super(name, phone, address, province, city, postalCode,  comment, email);
    }
}
