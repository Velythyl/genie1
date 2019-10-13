import java.awt.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Predicate;

public class Prototype {
    public Prototype() {}

    public void accessGym(int uuid) {
        for(Client cl: readClients()) {
            if(cl.getUuid() == uuid) {
                if(cl.isSuspended()) System.out.println("Membre suspendu");
                else System.out.println("Validé");
                return;
            }
        }

        System.out.println("Numéro invalide");
    }

    public void enrollClient(String name, String surname, String phone, String email, String address, String gender, Timestamp birthdate, String comment) {

        ArrayList<Client> list = readClients();
        Client newClient = new Client(name, surname, phone, email, address, gender, birthdate, comment);

        for(Client cl: list) {
            if(cl.equals(newClient)) {
                System.out.println("Utilisateur existe déjà");
                return;
            }
        }

        list.add(newClient);
        saveClients(list);
        System.out.println("Inscription réussie");
        System.out.println("Numero unique du nouveau client = " + newClient.getUuid());
    }

    public void enrollProfessionnal(String name, String surname, String phone, String email, String address, String gender,
                                      Timestamp birthdate, String comment) {
        ArrayList<Professionnal> list = readProfessionnals();
        Professionnal newPro = new Professionnal(name, surname, phone, email, address, gender, birthdate, comment);

        for(Professionnal cl: list) {
            if(cl.equals(newPro)) {
                System.out.println("Utilisateur existe déjà");
                return;
            }
        }

        list.add(newPro);
        saveProfessionnals(list);
        System.out.println("Inscription réussie");
    }

    public <T> ArrayList<T> readList(String file) {
        try(ObjectInputStream obji = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<T>) obji.readObject();

        } catch (ClassNotFoundException | IOException e) {}

        return new ArrayList<>();
    }

    public ArrayList<Activity> readRepository() {
        return readList("./RepertoireDesServices.txt");
    }

    public ArrayList<Client> readClients() {
        return readList("./Clients.txt");
    }

    public ArrayList<Professionnal> readProfessionnals() {
        return readList("./Professionnals.txt");
    }

    public void saveActivities(ArrayList<Activity> list) {
        saveList(list, "./RepertoireDesServices.txt");
    }

    public void saveClients(ArrayList<Client> list) {
        saveList(list, "./Clients.txt");
    }

    public void saveProfessionnals(ArrayList<Professionnal> list) {
        saveList(list, "./Professionnals.txt");
    }

    public void saveList(ArrayList list, String file) {
        try {
            new ObjectOutputStream(new FileOutputStream(file)).writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createActivity(String comment, Timestamp start, Timestamp end, int hour, int capacity, int proNumber, String days, String name) {
        ArrayList<Activity> list = readRepository();
        list.add(new Activity(comment, start, end, hour, capacity, proNumber, days, name));
        saveActivities(list);

        System.out.println("Validé");
    }

    public ArrayList<Activity> readAllFromRepository() {
        return readAndFilterRepository( (Activity a) -> true);
    }

    public ArrayList<Activity> readAndFilterRepository(Predicate<Activity> filter) {
        ArrayList<Activity> fullList = readRepository();
        ArrayList<Activity> list = new ArrayList<>();

        fullList.forEach( (Activity a) -> {
            if(filter.test(a)) list.add(a);
        });

        return list;
    }

    public void confirmAttendance(int clientUuid, int serviceUuid, String comment) {
        ArrayList<Activity> list = readAndFilterRepository( (Activity a) -> a.getUuid() == serviceUuid );

        if(list.size() == 0) {
            System.out.println("Code invalide!");
        } else if(list.get(0).isEnrolled(clientUuid)) {
            list.get(0).confirmAttendance(clientUuid, comment);
            System.out.println("Validé!");
        } else {
            System.out.println("Vous n'êtes pas inscrits à cette activité!");
        }
    }

    public void enrollIntoActivity(int clientUuid, int serviceUuid, Timestamp onDate, String comment) {
        ArrayList<Activity> list = readAndFilterRepository( (Activity a) -> a.getUuid() == serviceUuid );

        if(list.size() == 0) {
            System.out.println("Code invalide!");
            return;
        }

        Activity a = list.get(0);
        if(a.getInscriptions().size() < a.getCapacity()) {
            System.out.println("Êtes-vous certains? Y/AnyKey");

            Scanner scanner = new Scanner(System.in);
            String resp = scanner.next();
            scanner.close();

            if(resp.toLowerCase().equals("y")) {
                list.get(0).enroll(clientUuid, onDate, comment);
                saveActivities(list);
                System.out.println("Validé");
            } else {
                System.out.println("Vous n’êtes pas inscrits");
            }

        } else {
            System.out.println("L’activité est pleine");
        }
    }

    public void consultActivities(){
        for(Activity a: readAndFilterRepository( (Activity a) -> a.getInscriptions().size() < a.getCapacity())) {
            System.out.println(a);
        }
    }

    public void consultInscriptions(int proUuid) {
        for(Activity a: readAndFilterRepository((Activity a) -> a.getProNumber() == proUuid)) {
            System.out.println(""+a.getName()+": "+a.getInscriptions());
        }
    }

    public Method meta_getMethodByName(String name) throws ClassNotFoundException {
        for(Method m: Class.forName("Prototype").getDeclaredMethods()) {
            if(m.getName().equals(name)) return m;
        }
        throw new ClassNotFoundException();
    }

    /**
     * Tries to make str into type
     *
     * @param str
     * @param type
     * @return
     */
    public Object meta_marshallType(String str, String type) {
        switch (type) {
            case "int":
                return Integer.parseInt(str);
            case "java.sql.Timestamp":
                return new Timestamp(Long.parseLong("831984971"));  //TODO temp for prototype
            default:
                return str;
        }
    }

    /**
     * Calls a function by string. Splits on tabs!
     * @param params
     * @return
     */
    public Object meta_callByString(String commandName, String params) {
        String[] array = params.split(";");

        try {
            Method t = meta_getMethodByName(commandName);

            Object[] castParams = new Object[array.length];
            for(int i=0; i<array.length; i++) {
                castParams[i] = meta_marshallType(array[i], t.getParameterTypes()[i].getName());
            }

            return t.invoke(this, castParams);
        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException e) {}

        System.out.println("Quelque chose s'est mal passé. Êtes-vous certains d'avoir bien tapé votre commande?");

        return null;
    }
}
