import java.util.ArrayList;

public class ClientReporter extends EntityReporter {
    public String generateReport(Client c) {
        String report = this.getReportHeader(c);
        DataStore ds = DataStore.getInstance();

        for(Activity a: Reporter.getWeekActivities(c.getActivities())) {
            for(Attendance att: Reporter.getWeekAttendances(a.getAttendances())) {
                report += "ACTIVITY DATE: "+att.getCreationStamp().toString().split(" ")[0]+"\n";
                report += "PROFESSIONAL NAME: "+ds.getProfessionnal(a.getProNumber()).getName()+"\n";
                report += "SERVICE NAME: "+a.getName()+"\n";
            }
        }

        return report;
    }
}
