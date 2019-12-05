import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

public abstract class Reporter {
    protected static double ACTIVITY_PERCENTAGE_PAID_TO_PRO = 0.75;

    // generate reports
    // the subclasses will implement it the right way for their type
    // to access datastore : datastore.
    // new professional report manager -- profrepman.generate ** copy les func du professionnel
    // dans la classe abstraite je copie getWeekActivities

    private static Timestamp getLastSat() {
        //https://stackoverflow.com/questions/9307884/retrieve-current-weeks-mondays-date
        ZoneId zoneId = ZoneId.of( "America/Montreal" );
        LocalDate today = LocalDate.now( zoneId );
        long previousSatLong = today.with( TemporalAdjusters.previous( DayOfWeek.SATURDAY ) ).atStartOfDay(zoneId).toInstant().toEpochMilli();

        return new Timestamp(previousSatLong);
    }

    // 1 : prend toutes activites du datastore
    //     - applique getWeekActivities ( me laisse juste les activites qui se sont passes cette semaine
    //     - cette liste la , pour toute les clients, toutes prof etc

    public static ArrayList<Activity> getWeekActivities(ArrayList<Activity> activities) {
        Timestamp lastSat = getLastSat();

        ArrayList<Activity> goodList = new ArrayList<>();
        for(Activity a: activities) {
            if(a.getEnd().after(lastSat)) goodList.add(a);
        }

        return goodList;
    }

    public static ArrayList<Attendance> getWeekAttendances(ArrayList<Attendance> attendances) {
        Timestamp lastSat = getLastSat();

        ArrayList<Attendance> goodList = new ArrayList<>();
        for(Attendance a: attendances) {
            if(a.getCreationStamp().after(lastSat)) goodList.add(a);
        }

        return goodList;
    }

    public static double getWeekPrice(Activity a) {
        if(a.getEnd().before(new Stamp())) return 0.0;

        double pay = 0.0;

        if(a.isPayPerClient()) {
            for(Attendance attendance: getWeekAttendances(a.getAttendances())) {
                pay += attendance.getPrice();
            }
        } else {
            long nb_days = (new Stamp().getTime() - getLastSat().getTime())/86400000;    // nb de millis dans un jour
            for(int i=0; i<nb_days; i++) {
                if(a.getWeek().getDays()[i]) pay += a.getPrice();
            }
        }

        return pay;
    }

    // evident que cest laction du professionel qui check cest quoi sa paye pour la semaine
    // pour la paye additionne le 3/4 des trucs
    public static double getWeekPrice(ArrayList<Activity> list) {
        double pay = 0.0;
        for(Activity a: list) pay += getWeekPrice(a);
        return pay;
    }
}
