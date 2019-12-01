import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DataStore implements Serializable {
    private ArrayList<Client> clients;
    private ArrayList<Professionnal> professionnals;
    private ArrayList<Activity> activities;

    private ArrayList<String> activityNames;
    private ArrayList<HashMap<String, Integer>> usedActivityIDs;

    public DataStore() {
        try(ObjectInputStream obji = new ObjectInputStream(new FileInputStream("repertoire.ser"))) {
            DataStore oldStore = (DataStore) obji.readObject();

            this.clients = oldStore.getClients();
            this.professionnals = oldStore.getProfessionnals();
            this.activities = oldStore.getActivities();

            this.activityNames = oldStore.getActivityNames();
            this.usedActivityIDs = oldStore.getUsedActivityIDs();
        } catch (ClassNotFoundException | IOException e) {

            this.clients = new ArrayList<>();
            this.professionnals = new ArrayList<>();
            this.activities = new ArrayList<>();

            this.activityNames = new ArrayList<>();
            this.usedActivityIDs = new ArrayList<>();
        }
    }

    public void saveDS() {
        try {
            new ObjectOutputStream(new FileOutputStream("repertoire.ser")).writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getActivityNames() {
        return activityNames;
    }

    public ArrayList<HashMap<String, Integer>> getUsedActivityIDs() {
        return usedActivityIDs;
    }

    /**
     * Generates an appropriate Activity ID for the number of a pro and the type of an activity
     *
     * To do this, we first look for the appropriate activity type in our list. This type number is the index where we
     * find the type (if we don't find it, we add it to the end of the list).
     *
     * Then, we look in another list containing hashmaps for each type associating the last two digits of a pro number
     * to an int. We access the hashmap using the last two digits of our current pro, and we thus get the activity nb.
     *
     * Then, we simply return the concatenation of those things as an int.
     *
     * (We must also save the DS everytime this method is accessed to preserve its usefulness...)
     *
     * @param proNumber The String representation (so begining with padded zeros) of a pro number
     * @param activity  The activity type
     * @return  The activity ID as an int.
     */
    public int generateActivityID(String proNumber, String activity) {
        String lastTwoProNb = proNumber.substring(proNumber.length() - 2);

        //Commence par lire la liste des activites, et on y cherche l'activite pour savoir le code a assigner.
        int activityIndex = this.activityNames.indexOf(activity);
        if(activityIndex == -1) {
            activityIndex = this.activityNames.size();
            activityNames.add(activity);

            this.usedActivityIDs.add(new HashMap<>());
        }

        String typeCode = StringUtils.pad(activity, 3);

        String activityNumber;
        HashMap<String, Integer> usedIDs = this.usedActivityIDs.get(activityIndex);
        if(!usedIDs.containsKey(lastTwoProNb)) {
            usedIDs.put(lastTwoProNb, 1);
            activityNumber = "00";
        } else {
            int temp = usedIDs.get(lastTwoProNb);
            activityNumber = StringUtils.pad(temp,2);
            usedIDs.put(lastTwoProNb, temp+1);
        }

        saveDS();

        return Integer.parseInt(typeCode+activityNumber+lastTwoProNb);
    }

    private <T extends UuidGymClass> T getById(ArrayList<T> list, int id) {
        return getOrDelByID(list, id, false);
    }

    private <T extends UuidGymClass> T getOrDelByID(ArrayList<T> list, int id, boolean del) {
        for(int i=0; i<list.size(); i++) {
            T t = list.get(i);
            if(t.getUuid() == id) {
                if(del) {
                    list.remove(i);
                    saveDS();
                } else return t;
            }
        }
        //TODO
        return null;
    }

    private <T> void addToList(ArrayList<T> list, T t) {
        list.add(t);
        saveDS();
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public Client getClient(int id) {
        Client c = getById(clients, id);
        return c;
    }

    public void delClient(int id) {
        Client c = getOrDelByID(clients, id, true);
    }

    public void addClient(Client c) {
        addToList(clients, c);
    }

    public ArrayList<Professionnal> getProfessionnals() {
        return professionnals;
    }

    public Professionnal getProfessionnal(int id) {
        Professionnal p = getById(professionnals, id);
        return p;
    }

    public void delProfessionnal(int id) {
        Professionnal p = getOrDelByID(professionnals, id, true);
    }

    public void addProfessionnal(Professionnal p){
        addToList(professionnals, p);
    }

    public ArrayList<Activity> getActivities() {
        return activities;
    }

    public Activity getActivity(int id) {
        Activity a = getById(activities, id);
        return a;
    }

    public void delActivity(int id) {
        Activity a = getOrDelByID(activities, id, true);
    }

    public void addActivity(Activity a){
        addToList(activities, a);
    }
}