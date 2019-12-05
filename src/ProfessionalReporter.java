import java.sql.Timestamp;
import java.util.ArrayList;

public class ProfessionalReporter extends EntityReporter {

    public String generateReport(Professionnal professionnal) {
        DataStore ds = DataStore.getInstance();

        String report = this.getReportHeader(professionnal);

        ArrayList<Activity> weekActs = ActivityAccounting.getWeekActivities(professionnal.getActivities());
        for(Activity a: weekActs) {

            report += "ACTIVITY CODE: "+a.getUuid()+"\n";
            if(a.isPayPerClient()) report += "THIS ACTIVITY IS PAY-PER-CLIENT. PAY: "+(ActivityAccounting.getWeekPrice(a) * ACTIVITY_PERCENTAGE_PAID_TO_PRO) +"\n";
            else report += "THIS ACTIVITY IS PAY-PER-CLIENT. \n";

            for(Attendance att: ActivityAccounting.getWeekAttendances(a.getAttendances())) {
                String date = att.getCreationStamp().toString();

                report += "ACTIVITY DATE: "+date.split(" ")[0]+"\n";
                report += "RECEIVED ATTENDANCE CONFIRMATION ON: "+date+"\n";
                report += "CLIENT NAME: "+ds.getClient(att.getClientNumber()).getName()+"\n";
                report += "CLIENT NUMBER: "+att.getClientNumber()+"\n";
                report += "PAY: " + (att.getPrice() * ACTIVITY_PERCENTAGE_PAID_TO_PRO) + "\n";
            }
        }

        return report;
    }

    // faire une methode pour un professionnel
    public static String getTEF(Professionnal professionnal) {
        return "NAME: "+professionnal.getName()+"\n" +
                "NUMBER: "+professionnal.getUuid()+"\n" +
                "PAY: " + ActivityAccounting.getWeekPrice(professionnal.getActivities());
    }
}
