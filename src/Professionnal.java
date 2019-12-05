import java.sql.Timestamp;
import java.util.ArrayList;

public class Professionnal extends Entity {
    private ArrayList<Activity> activities;

    public Professionnal(String name, String surname, String phone, String email, String address, String gender, Timestamp birthdate, String comment) {
        super(name, surname, phone, email, address, gender, birthdate, comment);
        this.activities = new ArrayList<>();
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    public ArrayList<Activity> getActivities() {
        return this.activities;
    }


    public double getWeekPay(Timestamp endDate) {
        return getWeekPay(ReportManager.getWeekActivities(endDate, activities));
    }

    public double getWeekPay(ArrayList<Activity> list) {
        double pay = 0.0;
        for(Activity a: list) pay += a.getPrice();
        return pay;
    }

    public String getTEF(Timestamp endDate) {
        String tmp = this.getUuidStr();
        return "NAME: "+this.getName()+". NUMBER: "+tmp+". PAY: "+this.getWeekPay(endDate);
    }

    public String getReportLine(Timestamp endDate) {
        ArrayList<Activity> list = getWeekActivities(endDate);
        if(list.size() == 0) return "";

        return this.getName()+"\t"+list.size()+"\t"+getWeekPay(list);
    }
}
