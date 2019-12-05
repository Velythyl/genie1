import java.sql.Timestamp;
import java.util.ArrayList;

public class ManagerAction extends Action {

    @Override
    public String generateReport(ArrayList<Activity> activities) {
        DataStore dataStore = DataStore.getInstance();

        StringBuilder rapport = new StringBuilder();

        // on get la liste de tous les professionnels
        ArrayList<Professionnal> pros = dataStore.getProfessionnals();
        Timestamp endDate = new Timestamp(System.currentTimeMillis());

        for (Professionnal pro : pros) {
            rapport.append(getReportLine(endDate, pro));
        }

        return rapport.toString();

    }

    // il va dans la liste du datastore pi il fait le truc pour tous les professionnels
    public static String getReportLine(Timestamp endDate, Professionnal pro) {
        ArrayList<Activity> list = Action.getWeekActivities(endDate, pro.getActivities());
        if(list.size() == 0) return "";

        return pro.getName() + "\t"+list.size()+"\t"+ ProfessionalAction.getWeekPay(list) + "\n";
    }
}
