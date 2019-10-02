import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

public class Prototype {
    static ArrayList<Client> existingClients = new ArrayList<>();
    static ArrayList<Professionnal> existingProfessionnals = new ArrayList<>();
    static ArrayList<Activity> existingActivities = new ArrayList<>();

    public Prototype() {
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

        existingActivities.add(new Activity("Good for any aspiring Jedi!",
                new Timestamp(System.currentTimeMillis()+100000), new Timestamp(System.currentTimeMillis()+1000000),
                13, 20, existingProfessionnals.get(0).getUuid(),
                new boolean[]{true, false, false, true, false, true, true}));
    }

    public Client findClient(int uuid) {
        for(Client cl: existingClients) {
            if(cl.getUuid() == uuid) return cl;
        }

        return null;
    }

    public String accessGym(int uuid) {
        Client cl = findClient(uuid);

        if(cl == null) return "Numéro invalide";
        else if(cl.isSuspended()) return "Membre suspendu";
        else return "Validé";
    }

    public String enrollClient(String name, String surname, String phone, String email, String address, String gender,
                                      Timestamp birthdate, String comment) {

        for(Client cl: existingClients) {
            if(cl.getName().equals(name) && cl.getSurname().equals(surname) && cl.getEmail().equals(email)) return "Utilisateur existe déjà";
        }

        existingClients.add(new Client(name, surname, phone, email, address, gender, birthdate, comment));
        return "Inscription réussie";
    }

    public Method getMethodByName(String name) throws ClassNotFoundException {
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
    public Object marshallType(String str, String type) {
        switch (type) {
            case "int":
                return Integer.parseInt(str);
            case "String":
                return str;
            default:
                    return str;
        }
    }

    public Object callByString(String arr) {
        String[] array = arr.split("\t");

        try {
            Method t = getMethodByName(array[0]);

            Object[] castParams = new Object[array.length-1];
            for(int i=0; i<array.length-1; i++) {
                castParams[i] = marshallType(array[i+1], t.getParameterTypes()[i].getName());
            }

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
