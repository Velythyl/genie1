import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class Prototype {
    public Prototype() {}

    void accessGym(int uuid) {
        for(Client cl: readClients()) {
            if(cl.getUuid() == uuid) {
                if(cl.isSuspended()) System.out.println("Membre suspendu");
                else System.out.println("Validé");
                return;
            }
        }

        System.out.println("Numéro invalide");
    }

    void enrollClient(String name, String surname, String phone, String email, String address, String gender, Timestamp birthdate, String comment) {
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

    void modifyClient(int id, String[] fields, String[] values) {
        ArrayList<Client> list = readClients();
        for(Client c: list) {
            if(c.getUuid() == id) {
                meta_modify(c, fields, values);
            }
        }
        saveClients(list);
    }

    void modifyService(int id, String[] fields, String[] values) {
        ArrayList<Activity> list = readRepository();
        for(Activity c: list) {
            if(c.getUuid() == id) {
                meta_modify(c, fields, values);
            }
        }
        saveActivities(list);
    }

    void modifyProfessionnal(int id, String[] fields, String[] values) {
        ArrayList<Professionnal> list = readProfessionnals();
        for(Professionnal c: list) {
            if(c.getUuid() == id) {
                meta_modify(c, fields, values);
            }
        }
        saveProfessionnals(list);
    }

    private <T> ArrayList<T> deleteFromList(int id, ArrayList<T> list) {
        ArrayList<T> goodList = new ArrayList<>();
        for(T c: list) {
            UuidGymClass u = (UuidGymClass) c;
            if(u.getUuid() != id) {
                goodList.add(c);
            }
        }
        return goodList;
    }

    void deleteClient(int id) {
        saveClients(deleteFromList(id, readClients()));
    }

    void deleteProfessionnal(int id) {
        saveProfessionnals(deleteFromList(id, readProfessionnals()));
    }

    void deleteActivity(int id) {
        saveActivities(deleteFromList(id, readRepository()));
    }

    void enrollProfessionnal(String name, String surname, String phone, String email, String address, String gender,
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
        System.out.println(newPro.getUuid());
    }

    private <T> ArrayList<T> readList(String file) {
        try(ObjectInputStream obji = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<T>) obji.readObject();

        } catch (ClassNotFoundException | IOException e) {}

        return new ArrayList<>();
    }

    private ArrayList<Activity> readRepository() {
        return readList("./RepertoireDesServices.txt");
    }

    private ArrayList<Client> readClients() {
        return readList("./Clients.txt");
    }

    private ArrayList<Professionnal> readProfessionnals() {
        return readList("./Professionnals.txt");
    }

    private void saveActivities(ArrayList<Activity> list) {
        ArrayList<Professionnal> proList = readProfessionnals();
        for(Activity a: list) {
            for(Professionnal p: proList) {
                if(a.getProNumber() == p.getUuid()) {

                    ArrayList<Activity> proActivities = p.getActivities();

                    for(int i=0; i<proActivities.size(); i++) {
                        if(proActivities.get(i).getUuid() == a.getUuid()) {
                            proActivities.set(i, a);
                        }
                    }

                    p.setActivities(proActivities);
                }
            }
        }

        saveProfessionnals(proList);
        saveList(list, "./RepertoireDesServices.txt");
    }

    private void saveClients(ArrayList<Client> list) {
        saveList(list, "./Clients.txt");
    }

    private void saveProfessionnals(ArrayList<Professionnal> list) {
        saveList(list, "./Professionnals.txt");
    }

    private void saveList(ArrayList list, String file) {
        try {
            new ObjectOutputStream(new FileOutputStream(file)).writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void createActivity(String comment, Timestamp start, Timestamp end, Hours hour, int capacity, int proNumber, Days days, String name, double price) {
        ArrayList<Activity> list = readRepository();

        if(capacity > 30) {
            System.out.println("Une activité ne peut avoir plus de 30 de capacitié");
            return;
        }

        if(price > 100.0) {
            System.out.println("Une activité ne peut être plus chère que 100$");
            return;
        }

        Activity a = new Activity(comment, start, end, hour, capacity, proNumber, days, name, price);

        boolean found = false;
        ArrayList<Professionnal> proList = readProfessionnals();
        for(Professionnal cl: proList) {
            if(cl.getUuid() == proNumber) { //TODO add activity to pro
                found = true;
                cl.addActivities(a);
                continue;
            }
        }
        if(!found) {
            System.out.println("Ce pro n'existe pas.");
            return;
        }

        list.add(a);

        saveProfessionnals(proList);
        saveActivities(list);

        System.out.println("Validé");
    }

    ArrayList<Activity> readAllFromRepository() {
        return readAndFilterRepository( (Activity a) -> true);
    }

    private ArrayList<Activity> readAndFilterRepository(Predicate<Activity> filter) {
        ArrayList<Activity> fullList = readRepository();
        ArrayList<Activity> list = new ArrayList<>();

        fullList.forEach( (Activity a) -> {
            if(filter.test(a)) list.add(a);
        });

        return list;
    }

    void confirmAttendance(int clientUuid, int serviceUuid, String comment) {
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

    void enrollIntoActivity(int clientUuid, int serviceUuid, Timestamp onDate, String comment) throws IOException {
        /*ArrayList<Activity> list = readAndFilterRepository( (Activity a) -> a.getUuid() == serviceUuid );

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
        }*/
        System.out.println("VOICI LA LISTE DES ACTIVITES CORRESPONDANT AU FILTRE\n" +
                "1 - zumba\n" +
                "2 - yoga\n" +
                "3 - boxe\n" +
                "selectionnez une activite en ecrivant son chiffre puis en appuyant sur ENTER");
        System.in.read();
        System.out.println("pour confirmer appuyez sur ENTER");
        System.in.read();
        System.out.println("Validé");
    }

    void consultActivities(){
        for(Activity a: readAndFilterRepository( (Activity a) -> a.getInscriptions().size() < a.getCapacity())) {
            System.out.println(a);
        }
    }

    void consultInscriptions(int proUuid) {
        for(Activity a: readAndFilterRepository((Activity a) -> a.getProNumber() == proUuid)) {
            System.out.println(""+a.getName()+": "+a.getInscriptions());
        }
    }

    //https://stackoverflow.com/questions/1042798/retrieving-the-inherited-attribute-names-values-using-java-reflection
    private static java.util.List<Field> meta_getAllFields(java.util.List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));

        if (type.getSuperclass() != null) {
            meta_getAllFields(fields, type.getSuperclass());
        }

        return fields;
    }

    //https://blog.sevagas.com/Modify-any-Java-class-field-using-reflection
    private void meta_modify(GymClass instance, String[] fields, String[] values) {
        Class clas = instance.getClass();

        List<Field> declaredFields = meta_getAllFields(new ArrayList<>(), clas);

        for(int i=0; i<fields.length; i++) {
            String f = fields[i];
            String v = values[i];

            for(Field df: declaredFields) {
                if(df.getName().equals(f)) {
                    df.setAccessible(true);
                    try {
                        df.set(instance, Prototype.meta_marshallType(v, df.getType().getName()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    continue;
                }
            }
        }
    }

    private Method meta_getMethodByName(String name) throws ClassNotFoundException {
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
    private static Object meta_marshallType(String str, String type) throws Exception {
        switch (type) {
            case "int":
                return Integer.parseInt(str);
            case "java.sql.Timestamp":

                //str is of format JJ-MM-AAAA HH:MM:SS
                //timestamp.valueof needs yyyy-[m]m-[d]d hh:mm:ss[.f...]. The fractional seconds may be omitted. The leading zero for mm and dd may also be omitted.

                String[] bigSmol = str.split(" ");
                String[] bigs = bigSmol[0].split("-");
                String smols = bigSmol.length > 1 ? bigSmol[1] : "00:00:00";


                List<String> reversed = Arrays.asList(bigs);
                Collections.reverse(reversed);
                String good = String.join("-", reversed)+" "+smols;

                return Timestamp.valueOf(good);  //TODO temp for prototype
            case "Hours":
                return new Hours(str);
            case "Days":
                return new Days(str);
            case "double":
                return Double.parseDouble(str);
            default:
                if(str.length()>100) {
                    System.out.println("Le système ne supporte pas les string de plus que 100 caractères.");
                    throw new Exception();
                }
                return str;
        }
    }

    /**
     * Calls a function by string. Splits on tabs!
     * @param params
     */
    void meta_callByString(String commandName, String params) {
        String[] array = params.split("\t");

        try {
            Method t = meta_getMethodByName(commandName);

            Object[] castParams = new Object[array.length];
            for(int i=0; i<array.length; i++) {
                castParams[i] = meta_marshallType(array[i], t.getParameterTypes()[i].getName());
            }

            t.invoke(this, castParams);
            return;
        } catch (Exception ignored) {}

        System.out.println("Quelque chose s'est mal passé. Êtes-vous certains d'avoir bien tapé votre commande?");

    }
}
