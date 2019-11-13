import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.*;
import java.util.function.Predicate;

public class Prototype {
    DataStore ds;
    public Prototype() {
        ds = new DataStore();
    }

    void accessGym(int uuid) {
        Client c = ds.getClient(uuid);

        if(c.getUuid() == uuid) {
            if(c.isSuspended()) System.out.println("Membre suspendu: payez votre compte, profiteurs.");
            else System.out.println("Validé");
            return;
        }

        System.out.println("Numéro invalide");
    }

    void enrollClient(String name, String surname, String phone, String email, String address, String gender, Timestamp birthdate, String comment) {
        Client newClient = new Client(name, surname, phone, email, address, gender, birthdate, comment);
        ds.addClient(newClient);

        System.out.println("Inscription réussie");
        System.out.println("Numero unique du nouveau client = " + newClient.getUuid());
    }

    void modifyClient(int id, String[] fields, String[] values) {
        Client c = ds.getClient(id);
        meta_modify(c, fields, values);
    }

    void modifyActivity(int id, String[] fields, String[] values) {
        Activity c = ds.getActivity(id);
        meta_modify(c, fields, values);
    }

    void modifyProfessionnal(int id, String[] fields, String[] values) {
        Professionnal c = ds.getProfessionnal(id);
        meta_modify(c, fields, values);
    }

    void deleteClient(int id) {
        ds.delClient(id);
    }

    void deleteProfessionnal(int id) {
        ds.delProfessionnal(id);
    }

    void deleteActivity(int id) {
        ds.delActivity(id);
    }

    void enrollProfessionnal(String name, String surname, String phone, String email, String address, String gender,
                                      Timestamp birthdate, String comment) {
        Professionnal newPro = new Professionnal(name, surname, phone, email, address, gender, birthdate, comment);
        ds.addProfessionnal(newPro);

        System.out.println("Inscription réussie");
        System.out.println("Numero unique du nouveau client = " + newPro.getUuid());
    }

    void createActivity(String comment, Timestamp start, Timestamp end, Hours hour, int capacity, int proNumber, Days days, String name, double price) {
        if(capacity > 30) {
            System.out.println("Une activité ne peut avoir plus de 30 de capacitié");
            return;
        }

        if(price > 100.0) {
            System.out.println("Une activité ne peut être plus chère que 100$");
            return;
        }

        Professionnal p = ds.getProfessionnal(proNumber);
        if(p == null) {
            System.out.println("Ce pro n'existe pas.");
            return;
        }

        Activity a = new Activity(comment, start, end, hour, capacity, proNumber, days, name, price);
        p.addActivity(a);
        ds.addActivity(a);

        System.out.println("Validé");
    }

    void confirmAttendance(int clientUuid, int serviceUuid, String comment) {
        Activity a = ds.getActivity(serviceUuid);

        if(a == null) {
            System.out.println("Code invalide!");
        } else if(a.isEnrolled(clientUuid)) {
            a.confirmAttendance(clientUuid, comment);
            System.out.println("Validé!");
        } else {
            System.out.println("Vous n'êtes pas inscrits à cette activité!");
        }
    }

    void enrollIntoActivity(int clientUuid, int serviceUuid, Timestamp onDate, String comment) {
        Activity a = ds.getActivity(serviceUuid);

        if(a == null) {
            System.out.println("Code invalide!");
            return;
        }

        Client c = ds.getClient(clientUuid);
        if(c == null) {
            System.out.println("Ce client n'existe pas!");
            return;
        }

        if(a.getInscriptions().size() < a.getCapacity()) {
            System.out.println("Êtes-vous certains? Y/AnyKey");

            Scanner scanner = new Scanner(System.in);
            String resp = scanner.next();
            scanner.close();

            if(resp.toLowerCase().equals("y")) {
                a.enroll(c, onDate, comment);
                ds.saveDS();
                System.out.println("Validé. Vous devrez payer ce montant: "+a.getPrice());
            } else {
                System.out.println("Vous n’êtes pas inscrits");
            }

        } else {
            System.out.println("L’activité est pleine");
        }
    }

    void printTEFs(Timestamp endDate) {
        for(Professionnal p: ds.getProfessionnals()) {
            File f = new File("./TEFS/"+p.getUuid()+".tef");
            f.getParentFile().mkdirs();
            try {
                FileWriter writer = new FileWriter(f);
                String tef = p.getTEF(endDate);
                writer.write(tef);
                writer.close();
            } catch (IOException e) {
                System.out.println("Quelque chose s'est mal passé. Sortie de la procédure de TEF.");
                return;
            }
        }

        System.out.println("Validé");
    }

    void printReport(Timestamp endDate) {
        String report = "Name\tNumber\tPay\n";

        for(Professionnal p: ds.getProfessionnals()) report += p.getReportLine(endDate)+"\n";

        try {
            File f = new File("report.tsv");
            f.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(f);
            writer.write(report);
            writer.close();
        } catch (IOException e) {
            System.out.println("Quelque chose s'est mal passé. Sortie de la procédure de TEF.");
            return;
        }

        System.out.println("Validé");
    }

    void consultActivities(){
        ArrayList<Activity> fullList = ds.getActivities();

        fullList.forEach( (Activity a) -> {
            if(a.getInscriptions().size() < a.getCapacity()) System.out.println(a);
        });
    }

    void consultInscriptions(int proUuid) {
        for(Activity a: ds.getProfessionnal(proUuid).getActivities()) {
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
    public void meta_modify(GymClass instance, String[] fields, String[] values) {
        if(instance == null) {
            System.out.println("Cet ID n'est pas valide.");
            return;
        }

        Class clas = instance.getClass();

        List<Field> declaredFields = meta_getAllFields(new ArrayList<>(), clas);

        for(int i=0; i<fields.length; i++) {
            String f = fields[i];
            String v = values[i];

            if(v.equals("NC")) continue;

            for(Field df: declaredFields) {
                if(df.getName().equals(f)) {
                    df.setAccessible(true);
                    try {
                        df.set(instance, Prototype.meta_marshallType(v, df.getType().getName()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                }
            }
        }

        ds.saveDS();
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
