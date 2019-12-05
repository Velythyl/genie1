import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
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

    public ArrayList<Activity> getWeekActivities(Timestamp endDate) {
        //https://stackoverflow.com/questions/9307884/retrieve-current-weeks-mondays-date
        ZoneId zoneId = ZoneId.of( "America/Montreal" );
        LocalDate today = LocalDate.now( zoneId );
        long previousSatLong = today.with( TemporalAdjusters.previous( DayOfWeek.SATURDAY ) ).atStartOfDay(zoneId).toInstant().toEpochMilli();
        Timestamp lastSat = new Timestamp(previousSatLong);

        ArrayList<Activity> goodList = new ArrayList<>();
        for(Activity a: activities) {
            if(!a.getEnd().after(lastSat)) continue;

            long nb_days = (endDate.getTime() - lastSat.getTime())/86400000;    // nb de millis dans un jour
            for(int i=0; i<nb_days; i++) {
                if(a.getDays().getDays()[i]) goodList.add(a);
            }
        }

        return goodList;
    }

    public double getWeekPay(Timestamp endDate) {
        return getWeekPay(getWeekActivities(endDate));
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
