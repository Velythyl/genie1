import java.sql.Timestamp;

public abstract class ReportManager {
    // generate reports
    // the subclasses will implement it the right way for their type
    // to access datastore : datastore.
    // new professional report manager -- profrepman.generate ** copy les func du professionnel
    // dans la classe abstraite je copie getWeekActivities

    // 1 : prend toutes activites du datastore
    //     - applique getWeekActivities ( me laisse juste les activites qui se sont passes cette semaine
    //     - cette liste la , pour toute les clients, toutes prof etc

    // sortir les trucs qui creent le string quon va ecrire pi mettre dans report manager
    public static String createReportString(DataStore ds) {

        String report = "Name\tNumber\tPay\n";
        Timestamp endDate = new Timestamp(System.currentTimeMillis());

        double payTotal = 0;
        for (Professionnal p : ds.getProfessionnals()) {
            report += p.getReportLine(endDate) + "\n";
            payTotal += p.getWeekPay(endDate);
        }

        report += "TOTAL\t-1\t" + payTotal;
        return report;
    }


}
