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
    private ArrayList<Client> existingClients;
    private ArrayList<Professionnal> existingProfessionnals;
    private ArrayList<Activity> existingActivities;

    public Prototype() {
        existingClients = new ArrayList<>();
        existingProfessionnals = new ArrayList<>();
        existingActivities = new ArrayList<>();

        existingClients.add(new Client("Yoda", "N/A", "451-219-2131",
                "yoda_best_jedi@oldrepublic.org", "1 Master's Council ave.", "guy",
                new Timestamp(System.currentTimeMillis()), "Is allergic to latex"));
        Client suspended = new Client("Bob", "Bobert", "313-414-4531", "bobinne@bob.com",
                "3431 rue des Boberts", "male", new Timestamp(System.currentTimeMillis()-700000),
                null);
        suspended.setSuspended(true);
        existingClients.add(suspended);

        existingProfessionnals.add(new Professionnal("Obi Wan", "Kenobi", "451-219-9999",
                "kenobae@oldrepublic.org", "1 Master's Council ave.", "Homme",
                new Timestamp(System.currentTimeMillis()+700000), null));

        /*existingActivities.add(new Activity("Good for any aspiring Jedi!",
                new Timestamp(System.currentTimeMillis()+100000), new Timestamp(System.currentTimeMillis()+1000000),
                13, 20, existingProfessionnals.get(0).getUuid(),
                new boolean[]{true, false, false, true, false, true, true}));*/
    }

    public Client findClient(int uuid) {
        for(Client cl: existingClients) {
            if(cl.getUuid() == uuid) return cl;
        }

        return null;
    }

    public String accessGym(int uuid) {
        for(Client cl: existingClients) {
            if(cl.getUuid() == uuid) {
                if(cl.isSuspended()) return "Membre suspendu";
                else return "Validé";
            }
        }

        return "Numéro invalide";
    }

    public String enrollClient(String name, String surname, String phone, String email, String address, String gender,
                                      Timestamp birthdate, String comment) {

        Client newClient = new Client(name, surname, phone, email, address, gender, birthdate, comment);

        for(Client cl: existingClients) {
            if(cl.equals(newClient)) return "Utilisateur existe déjà";
        }

        existingClients.add(newClient);
        return "Inscription réussie";
    }

    public ArrayList<Activity> readRepository() {
        try(ObjectInputStream obji = new ObjectInputStream(new FileInputStream("./RepertoireDesServices.txt"))) {
            return (ArrayList<Activity>) obji.readObject();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public void createActivity(String comment, Timestamp start, Timestamp end, int hour, int capacity, int proNumber, String days, String name) {
        ArrayList<Activity> list = readRepository();
        list.add(new Activity(comment, start, end, hour, capacity, proNumber, days, name));

        try {
            new ObjectOutputStream(new FileOutputStream("./RepertoireDesServices.txt")).writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Validé");
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
                System.out.println("Validé");
            } else {
                System.out.println("Vous n’êtes pas inscrits");
            }

        } else {
            System.out.println("L’activité est pleine");
        }
    }

    public void consultActivities(){
        ArrayList<Activity> list = new ArrayList<>();
        list.add(new Activity("Good for any aspiring Jedi!",
                new Timestamp(System.currentTimeMillis()+100000), new Timestamp(System.currentTimeMillis()+1000000),
                13, 20, existingProfessionnals.get(0).getUuid(),
                "Lundi, Mardi", "Sword Training"));


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
    public Object meta_marshallType(String str, String type, String componentType) {
        if(type.startsWith("[")) {
            String[] arr = str.split(",");

            Object[] objArr = new Object[arr.length];
            switch (componentType) {
                case "java.lang.Boolean":
                    objArr = new Boolean[arr.length];
            }

            for(int i=0; i<arr.length; i++) {
                objArr[i] = meta_marshallType(arr[i], componentType, null);
            }

            return typeArr;
        }

        switch (type) {
            case "int":
                return Integer.parseInt(str);
            case "java.sql.Timestamp":
                return new Timestamp(Long.parseLong(str));
            case "java.lang.Boolean"
            default:
                return str;
        }
    }

    /**
     * Calls a function by string. Splits on tabs!
     * @param arr
     * @return
     */
    public Object meta_callByString(String arr) {
        String[] array = arr.split("\t");

        try {
            Method t = meta_getMethodByName(array[0]);

            Object[] castParams = new Object[array.length-1];
            for(int i=0; i<array.length-1; i++) {
                Class paramType = t.getParameterTypes()[i];
                String subType = paramType.getComponentType() == null ? null : paramType.getComponentType().getName();

                castParams[i] = meta_marshallType(array[i+1], paramType.getName(), subType);
            }
            //TODO after any operation save system state on disc

            return t.invoke(this, castParams);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}
