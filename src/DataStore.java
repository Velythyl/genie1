import java.io.*;
import java.util.ArrayList;

public class DataStore implements Serializable {
    private ArrayList<Client> clients;
    private ArrayList<Professionnal> professionnals;
    private ArrayList<Activity> activities;

    private static DataStore self;

    private DataStore() {
        try(ObjectInputStream obji = new ObjectInputStream(new FileInputStream("repertoire.ser"))) {
            DataStore oldStore = (DataStore) obji.readObject();

            this.clients = oldStore.getClients();
            this.professionnals = oldStore.getProfessionnals();
            this.activities = oldStore.getActivities();
        } catch (ClassNotFoundException | IOException e) {

            this.clients = new ArrayList<>();
            this.professionnals = new ArrayList<>();
            this.activities = new ArrayList<>();
        }
    }

    public static DataStore getInstance() {
        if(self == null) return new DataStore();
        else return self;
    }

    public void saveDS() {
        try {
            new ObjectOutputStream(new FileOutputStream("repertoire.ser")).writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
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