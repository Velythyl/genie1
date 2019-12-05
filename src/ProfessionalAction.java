import java.sql.Timestamp;
import java.util.ArrayList;

public class ProfessionalAction extends Action {
    @Override
    public String generateReport(ArrayList<Activity> activities) {
        return null;
    }

    // evident que cest laction du professionel qui check cest quoi sa paye pour la semaine
    // pour la paye additionne le 3/4 des trucs
    public static double getWeekPay(ArrayList<Activity> list) {
        double pay = 0.0;
        double ACTIVITY_PERCENTAGE_PAID_TO_PRO = 0.75;
        for(Activity a: list) pay += a.getPrice()* ACTIVITY_PERCENTAGE_PAID_TO_PRO;
        return pay;
    }
    public static double getWeekPay(Timestamp endDate, ArrayList<Activity> activities) {
        return ProfessionalAction.getWeekPay(Action.getWeekActivities(endDate, activities));
    }

    // faire une methode pour un professionnel
    public static String getProInfosForTEF(Timestamp endDate, Professionnal professionnal) {
        String tmp = professionnal.getUuidStr();
        return "NAME: "+professionnal.getName()+"\n" +
                "NUMBER: "+tmp+"\n" +
                "ADRESS: "+professionnal.getAddress()+"\n" +
                "CITY: "+professionnal.getCity()+"\n" +
                "PROVINCE: "+professionnal.getProvince() + "\n" +
                "POSTAL CODE: "+ professionnal.getPostalCode() + "\n";
    }

    public String getProServicesForTEF(){
        return null;
    }
}
