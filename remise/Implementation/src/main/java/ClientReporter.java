/**
 * Used to create the Client Reports.
 */
public class ClientReporter extends EntityReporter {
    /**
     * Prints a Report of this general shape:
     *
     * Nom du membre (25 lettres)
     * Numéro du membre (9 chiffres)
     * Adresse du membre (25 caractères)
     * Ville du membre (14 caractères)
     * Province du membre (2 lettres)
     * Code postal du membre (6 caractères).
     * Pour chaque service fourni, les détails suivants sont requis :
     *     Date du service (JJ-MM-AAAA)
     *     Nom du professionnel (25 lettres)
     *     Nom du service (20 caractères)
     *
     * @param c the client to print the report of
     * @return the String report
     */
    public static String generateReport(Client c) {
        String report = EntityReporter.getReportHeader(c);
        DataStore ds = DataStore.getInstance();

        for(Activity a: Reporter.getWeekActivities(c.getActivities())) {
            for(Attendance att: Reporter.getWeekAttendances(a.getAttendances())) {
                report += "\tACTIVITY DATE: "+att.getCreationStamp().toString().split(" ")[0]+"\n";
                report += "\tPROFESSIONAL NAME: "+ds.getProfessionnal(a.getProNumber()).getName()+"\n";
                report += "\tSERVICE NAME: "+a.getName()+"\n";
                report += "---";
            }
        }

        return report;
    }
}
