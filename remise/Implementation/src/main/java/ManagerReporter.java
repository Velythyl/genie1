import java.util.ArrayList;

/**
 * The Reporter for the Manager report
 */
public class ManagerReporter extends Reporter {
    /**
     * Generates a report of this general shape:
     *
     * "Le rapport concerne les comptes payables et décrit la liste de tous les professionnels qui doivent être payés
     * cette semaine-là, le nombre de services de chacun et le total de leurs frais pour cette semaine-là. À la fin du
     * rapport, le nombre total de professionnels qui ont fourni des services cette semaine-là, le nombre total de
     * services et le total des frais doivent apparaitre."
     *
     * @return the String report
     */
    public static String generateReport() {
        DataStore dataStore = DataStore.getInstance();

        StringBuilder rapport = new StringBuilder();

        // on get la liste de tous les professionnels
        ArrayList<Professionnal> pros = dataStore.getProfessionnals();

        int nbPro = 0;
        int nbAct = 0;
        double pay = 0.0;
        for (Professionnal pro : pros) {
            ArrayList<Activity> list = Reporter.getWeekActivities(pro.getActivities());

            if(list.size() == 0) continue;

            rapport.append(pro.getName()).append("\t").append(list.size()).append("\t").append(Reporter.getWeekPrice(list) * ACTIVITY_PERCENTAGE_PAID_TO_PRO).append("\n");
            nbPro += 1;
            nbAct += list.size();
            pay += Reporter.getWeekPrice(list) * ACTIVITY_PERCENTAGE_PAID_TO_PRO;
        }

        return rapport.toString()+nbPro+"\t"+nbAct+"\t"+pay+"\n";
    }
}
