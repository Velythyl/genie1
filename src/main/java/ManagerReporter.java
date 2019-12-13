import java.util.ArrayList;

public class ManagerReporter extends Reporter {

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
