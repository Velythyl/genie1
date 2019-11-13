import java.io.*;
import java.util.ArrayList;

public class DataStore implements Serializable {
    private ArrayList<Client> clients;
    private ArrayList<Professionnal> professionnals;
    private ArrayList<Activity> activities;

    public DataStore() {
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
        return getById(clients, id);
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
        saveDS();
    }

    public void delClient(int id) {
        getOrDelByID(clients, id, true);
    }

    public void addClient(Client c) {
        addToList(clients, c);
    }

    public ArrayList<Professionnal> getProfessionnals() {
        return professionnals;
    }

    public Professionnal getProfessionnal(int id) {
        return getById(professionnals, id);
    }

    public void setProfessionnals(ArrayList<Professionnal> professionnals) {
        this.professionnals = professionnals;
        saveDS();
    }

    public void delProfessionnal(int id) {
        getOrDelByID(professionnals, id, true);
    }

    public void addProfessionnal(Professionnal p){
        addToList(professionnals, p);
    }

    public ArrayList<Activity> getActivities() {
        return activities;
    }

    public Activity getActivity(int id) {
        return getById(activities, id);
    }

    public void setActivities(ArrayList<Activity> activities) {
        this.activities = activities;
        saveDS();
    }

    public void delActivity(int id) {
        getOrDelByID(activities, id, true);
    }

    public void addActivity(Activity a){
        addToList(activities, a);
    }
}