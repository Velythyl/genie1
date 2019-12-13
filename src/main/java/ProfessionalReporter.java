import java.util.ArrayList;

/**
 * The Reporter for Pros
 */
public class ProfessionalReporter extends EntityReporter {

    /**
     * Generates a report of this general format:
     *
     * Nom du professionnel (25 lettres)
     * Numéro du professionnel (9 chiffres)
     * Adresse du professionnel (25 caractères)
     * Ville du professionnel (14 caractères)
     * Province du professionnel (2 lettres)
     * Code postal du professionnel (6 caractères).
     * Pour chaque service fourni, les détails suivants sont requis :
     *     Date du service (JJ-MM-AAA)
     *     Date et heure à laquelle les données étaient reçues par l'ordinateur (JJ-MM-AAAA HH:MM:SS)
     *     Nom du membre (25 caractères)
     *     Numéro du membre (9 chiffres)
     *     Code de la séance (7 chiffres)
     *     Montant à payer (jusqu'à 999.99$), si facturé par inscription, sinon un seul montant est affiché.
     *
     * @param professionnal the pro in question
     * @return the String report
     */
    public static String generateReport(Professionnal professionnal) {
        DataStore ds = DataStore.getInstance();

        String report = EntityReporter.getReportHeader(professionnal);

        ArrayList<Activity> weekActs = Reporter.getWeekActivities(professionnal.getActivities());
        for(Activity a: weekActs) {

            report += "ACTIVITY CODE: "+a.getUuid()+"\n";
            if(a.isPayPerClient()) report += "THIS ACTIVITY IS PAY-PER-CLIENT. PAY: "+(Reporter.getWeekPrice(a) * ACTIVITY_PERCENTAGE_PAID_TO_PRO) +"\n";
            else report += "THIS ACTIVITY IS PAY-PER-CLIENT. \n";

            for(Attendance att: Reporter.getWeekAttendances(a.getAttendances())) {
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

    /**
     * Pros also need TEFs: these need to be created for the banks to pay the pros
     *
     * @param professionnal the pro in question
     * @return the TEF as a String
     */
    public static String getTEF(Professionnal professionnal) {
        return "NAME: "+professionnal.getName()+"\n" +
                "NUMBER: "+professionnal.getUuid()+"\n" +
                "PAY: " + Reporter.getWeekPrice(professionnal.getActivities());
    }
}
