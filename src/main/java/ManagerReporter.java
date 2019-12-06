import java.util.ArrayList;

public class ManagerReporter extends Reporter {

    public static String generateReport() {
        DataStore dataStore = DataStore.getInstance();

        StringBuilder rapport = new StringBuilder();

        // on get la liste de tous les professionnels
        ArrayList<Professionnal> pros = dataStore.getProfessionnals();

        for (Professionnal pro : pros) rapport.append(getReportLine(pro));

        return rapport.toString();
    }

    // il va dans la liste du datastore pi il fait le truc pour tous les professionnels
    private static String getReportLine(Professionnal pro) {
        ArrayList<Activity> list = Reporter.getWeekActivities(pro.getActivities());

        if(list.size() == 0) return "";

        return pro.getName() + "\t"+list.size()+"\t"+ (Reporter.getWeekPrice(list) * ACTIVITY_PERCENTAGE_PAID_TO_PRO) + "\n";
    }
}
