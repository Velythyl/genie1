import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

public abstract class Action {
    // generate reports
    // the subclasses will implement it the right way for their type
    // to access datastore : datastore.
    // new professional report manager -- profrepman.generate ** copy les func du professionnel
    // dans la classe abstraite je copie getWeekActivities

    // 1 : prend toutes activites du datastore
    //     - applique getWeekActivities ( me laisse juste les activites qui se sont passes cette semaine
    //     - cette liste la , pour toute les clients, toutes prof etc

    public static ArrayList<Activity> getWeekActivities(Timestamp endDate, ArrayList<Activity> activities) {
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

    // sur la liste d'attendances d'un activite foreach activite getWeekEvent des attendance ca va te donner la liste dattendance
    // cette activite la semaine pi apres ca taura toutes les infos pour faire le rapport
    // foreach event au rapport fait tel tel affaire pi activité pi attendant vont etendre events
    public static ArrayList<Activity> getWeekEvents(Timestamp endDate, ArrayList<Event> events) {
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

    /*
    public static String createReportString(DataStore ds) {

        String report = "Name\tNumber\tPay\n";
        Timestamp endDate = new Timestamp(System.currentTimeMillis());

        double payTotal = 0;
        for (Professionnal p : ds.getProfessionnals()) {
            report += ProfessionalAction.getReportLine(endDate, p.getActivities(), p.getName()) + "\n";
            payTotal += ProfessionalAction.getWeekPay(endDate, p.getActivities());
        }

        report += "TOTAL\t-1\t" + payTotal;
        return report;
    }*/

    public abstract String generateReport(ArrayList<Activity> activities);
}
