import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

/**
 * Reporter is an abstract Reporter class. All specific Reporters depend on the methods defined in this superclass, and
 * thus inherit from it.
 */
public abstract class Reporter {
    protected static double ACTIVITY_PERCENTAGE_PAID_TO_PRO = 0.75;

    /**
     *
     * @return the last Saturday for the current moment.
     */
    private static Timestamp getLastSat() {
        //https://stackoverflow.com/questions/9307884/retrieve-current-weeks-mondays-date
        ZoneId zoneId = ZoneId.of( "America/Montreal" );
        LocalDate today = LocalDate.now( zoneId );
        long previousSatLong = today.with( TemporalAdjusters.previous( DayOfWeek.SATURDAY ) ).atStartOfDay(zoneId).toInstant().toEpochMilli();

        return new Timestamp(previousSatLong);
    }

    /**
     * Filters a list of activities to only keep those that happened during this week
     *
     * @param activities the list of activities
     * @return the filtered list
     */
    public static ArrayList<Activity> getWeekActivities(ArrayList<Activity> activities) {
        Timestamp lastSat = getLastSat();

        ArrayList<Activity> goodList = new ArrayList<>();
        for(Activity a: activities) {
            if(a.getEnd().after(lastSat)) goodList.add(a);
        }

        return goodList;
    }

    /**
     * Filters a list of attendances to only keep those that happened during this week
     *
     * @param attendances the list of attendances
     * @return the filtered list
     */
    public static ArrayList<Attendance> getWeekAttendances(ArrayList<Attendance> attendances) {
        Timestamp lastSat = getLastSat();

        ArrayList<Attendance> goodList = new ArrayList<>();
        for(Attendance a: attendances) {
            if(a.getCreationStamp().after(lastSat)) goodList.add(a);
        }

        return goodList;
    }

    /**
     * Returns the price of the week.
     *
     * This "price of the week" is the combined price of the activity for the week. If the activity is not
     * pay-per-client, we count the number of times it happened during the week, and add price * [that number] into the
     * total. Otherwise, we look at all the attendances for the activity for the week, and we add
     * price * [number of attendances] to the total.
     *
     * @param a the activity
     * @return the price of the activity for the week
     */
    public static double getWeekPrice(Activity a) {
        if(a.getEnd().before(new Stamp())) return 0.0;

        double pay = 0.0;

        if(a.isPayPerClient()) {
            for(Attendance attendance: getWeekAttendances(a.getAttendances())) {
                pay += attendance.getPrice();
            }
        } else {
            long nb_days = (new Stamp().getTime() - getLastSat().getTime())/86400000;    // tres arbitraire? nope! nb de millis dans un jour
            for(int i=0; i<nb_days; i++) {
                if(a.getWeek().getDays()[i]) pay += a.getPrice();
            }
        }

        return pay;
    }

    /**
     * Calls getWeekPrice on every activity in a list, and adds their totals together
     *
     * @param list the list of activities
     * @return the total combined price of the activities for the week
     */
    public static double getWeekPrice(ArrayList<Activity> list) {
        double pay = 0.0;
        for(Activity a: list) pay += getWeekPrice(a);
        return pay;
    }
}
