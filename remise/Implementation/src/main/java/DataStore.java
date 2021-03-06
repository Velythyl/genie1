import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Simulation of a Database for our homework
 *
 * In real life, would be an interface to a SQL database.
 *
 * DO NOTE that in Real Life, this Datastore would have a version for Activity, Pro, and Client. But as it is here, it
 * would be weird to split it up: even now, it does pretty much nothing else than search lists.
 */
public class DataStore implements Serializable {
    private ArrayList<Client> clients;
    private ArrayList<Professionnal> professionnals;
    private ArrayList<Activity> activities;

    private static DataStore self;

    private ArrayList<String> activityNames;
    private ArrayList<HashMap<String, Integer>> usedActivityIDs;

    /**
     * Creates a Datastore from the serealization of an old one found on disk.
     *
     * This way, anytime one starts the Gym program, the datastores is instanciated with all the old data intact.
     *
     * This constructor is private because the Datastore is a singleton. See getInstance()
     */
    private DataStore() {
        try(ObjectInputStream obji = new ObjectInputStream(new FileInputStream("repertoire.ser"))) {
            DataStore oldStore = (DataStore) obji.readObject();

            this.clients = oldStore.getClients();
            this.professionnals = oldStore.getProfessionnals();
            this.activities = oldStore.getActivities();

            this.activityNames = oldStore.getActivityNames();
            this.usedActivityIDs = oldStore.getUsedActivityIDs();
        } catch (ClassNotFoundException | IOException e) {
            wipe();
        }
    }

    /**
     * Completely wipes the datastore. Very dangerous! Used only for tests and for the initial construction of the
     * Datastore.
     */
    void wipe() {
        this.clients = new ArrayList<>();
        this.professionnals = new ArrayList<>();
        this.activities = new ArrayList<>();

        this.activityNames = new ArrayList<>();
        this.usedActivityIDs = new ArrayList<>();
    }

    /**
     * The datastore is a singleton: it is a simulation of a single database.
     *
     * @return the unique Datastore instance
     */
    public static DataStore getInstance() {
        if(self == null) self = new DataStore();

        return self;
    }

    /**
     * Saves the Datastore (and any objects it has reference to)
     */
    public void saveDS() {
        try {
            new ObjectOutputStream(new FileOutputStream("repertoire.ser")).writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
      * @return the list of known activity types
     */
    public ArrayList<String> getActivityNames() {
        return activityNames;
    }

    /**
     *
     * @return a list of hashmaps, the list index being the activity type and the hashmap keys being the pro numbers
     * that have activities of that type
     */
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
     * Why is this in the Datastore? Because in real-life, this is something that would automatically be generated by
     * the SQL database...
     *
     * @param proUUID The String representation (so begining with padded zeros) of a pro number
     * @param activity  The activity type
     * @return  The activity ID
     */
    public UUID7 generateActivityID(UUID9 proUUID, String activity) {
        String proNumber = proUUID.toString();

        String lastTwoProNb = proNumber.substring(proNumber.length() - 2);

        //Commence par lire la liste des activites, et on y cherche l'activite pour savoir le code a assigner.
        int activityIndex = this.activityNames.indexOf(activity);
        if(activityIndex == -1) {
            activityIndex = this.activityNames.size();
            activityNames.add(activity);

            this.usedActivityIDs.add(new HashMap<>());
        }

        String typeCode = StringUtils.pad(activityIndex, 3);

        String activityNumber;
        HashMap<String, Integer> usedIDs = this.usedActivityIDs.get(activityIndex);
        //cherche la liste de pros qui ont donne des activites de ce type et trouve le prochain ID libre pour ce pro
        if(!usedIDs.containsKey(lastTwoProNb)) {
            usedIDs.put(lastTwoProNb, 1);
            activityNumber = "00";
        } else {
            int temp = usedIDs.get(lastTwoProNb);
            activityNumber = StringUtils.pad(temp,2);
            usedIDs.put(lastTwoProNb, temp+1);
        }

        saveDS();

        return new UUID7(Integer.parseInt(typeCode+activityNumber+lastTwoProNb));
    }

    /**
     * Returns the element that has that ID in the list
     *
     * @param list
     * @param id
     * @param <T> the Type of the elements of the list
     * @return the element
     */
    private <T extends UuidGymClass> T getById(ArrayList<T> list, UUID id) {
        return getOrDelByID(list, id, false);
    }

    /**
     * Finds an element from the list, and possibly deletes it.
     *
     * Otherwise, return sai element.
     *
     * @param list
     * @param id the element's ID
     * @param del shall we delete the element?
     * @param <T>
     * @return The element if we found it, or null (and null if we deleted it)
     */
    private <T extends UuidGymClass> T getOrDelByID(ArrayList<T> list, UUID id, boolean del) {
        for(int i=0; i<list.size(); i++) {
            T t = list.get(i);
            if(t.getUuid().equals(id)) {
                if(del) {
                    list.remove(i);
                    saveDS();
                } else return t;
            }
        }
        return null;
    }

    private <T> void addToList(ArrayList<T> list, T t) {
        list.add(t);
        saveDS();
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public Client getClient(UUID9 id) {
        return getById(clients, id);
    }

    public void delClient(UUID9 id) {
        getOrDelByID(clients, id, true);
    }

    public void addClient(Client c) {
        addToList(clients, c);
    }

    public ArrayList<Professionnal> getProfessionnals() {
        return professionnals;
    }

    public Professionnal getProfessionnal(UUID9 id) {
        return getById(professionnals, id);
    }

    public void delProfessionnal(UUID9 id) {
        getOrDelByID(professionnals, id, true);
    }

    public void addProfessionnal(Professionnal p){
        addToList(professionnals, p);
    }

    public ArrayList<Activity> getActivities() {
        return activities;
    }

    public Activity getActivity(UUID7 id) {
        return getById(activities, id);
    }

    public void delActivity(UUID7 id) {
        Activity a = getOrDelByID(activities, id, true);
    }

    public void addActivity(Activity a){
        addToList(activities, a);
    }
}