import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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


    public ArrayList<Activity> readAndFilterRepository(Predicate<Activity> filter) {
        ArrayList<Activity> fullList = new ArrayList<>();
        ArrayList<Activity> list = new ArrayList<>();

        try(ObjectInputStream obji = new ObjectInputStream(new FileInputStream("./RepertoireDesServices.txt"))) {
            fullList = (ArrayList<Activity>) obji.readObject();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        fullList.forEach( (Activity a) -> {
            if(filter.test(a)) list.add(a);
        });

        return list;
    }

    public void consultActivities(){
        ArrayList<Activity> list = new ArrayList<>();
        list.add(new Activity("Good for any aspiring Jedi!",
                new Timestamp(System.currentTimeMillis()+100000), new Timestamp(System.currentTimeMillis()+1000000),
                13, 20, existingProfessionnals.get(0).getUuid(),
                new Boolean[]{true, false, false, true, false, true, true}, new Integer[]{0,1}, "Sword Training"));
        try {
            new ObjectOutputStream(new FileOutputStream("./RepertoireDesServices.txt")).writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
            Object[] typeArr = new Object[arr.length];

            for(int i=0; i<arr.length; i++) {
                typeArr[i] = meta_marshallType(arr[i], componentType, null);
            }

            return typeArr;
        }

        switch (type) {
            case "int":
                return Integer.parseInt(str);
            case "String":
                return str;
            case "Timestamp":
                return new Timestamp(Long.parseLong(str));
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
