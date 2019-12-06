import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.*;

public class Prototype {
    DataStore ds;
    public Prototype() {
        ds = DataStore.getInstance();
    }

    //TODO dans l'interface juste renommer accessGym pour logInFromApp, ça fait la même chose !
    boolean acessGym(UUID9 uuid) {
        Client c = ds.getClient(uuid);

        if(c.getUuid() == uuid) {
            if(c.isSuspended()) System.out.println("Membre suspendu: payez votre compte, profiteurs.");
            else {
                System.out.println("Validé");
                return true;
            }

        }

        System.out.println("Numéro invalide");
        return false;
    }

    void enrollClient(String name, String address, String province, String city, String postalCode, String comment, String email) {
        if(!email.endsWith("@facebook.com")) {
            System.out.println("Votre addresse email n'est pas une addresse facebook valide. Veuillez reessayer.");
            return;
        }

        Client newClient = new Client(name, address, province, city, postalCode, comment, email);
        ds.addClient(newClient);

        System.out.println("Inscription réussie");
        System.out.println("Nom du client = "+newClient.getName());
        System.out.println("Numero unique du nouveau client = " + newClient.getUuid());
        System.out.println("Le code QR du client a été écrit sur le fichier QRCODE.jpg");
        QRUtils.genQR(newClient.getUuid().toString());
    }

    void modifyClient(UUID9 id, String[] fields, String[] values) {
        Client c = ds.getClient(id);
        meta_modify(c, fields, values);
    }

    void modifyActivity(UUID7 id, String[] fields, String[] values) {
        Activity c = ds.getActivity(id);
        meta_modify(c, fields, values);
    }

    void modifyProfessionnal(UUID9 id, String[] fields, String[] values) {
        Professionnal c = ds.getProfessionnal(id);
        meta_modify(c, fields, values);
    }

    void deleteClient(UUID9 id) {
        ds.delClient(id);
    }

    void deleteProfessionnal(UUID9 id) {
        ds.delProfessionnal(id);
    }

    void deleteActivity(UUID7 id) {
        ds.delActivity(id);
    }

    void enrollProfessionnal(String name,String address,String province, String city, String postalCode, String comment, String email) {
        Professionnal newPro = new Professionnal(name,address, province, city, postalCode, comment, email);
        ds.addProfessionnal(newPro);

        System.out.println("Inscription réussie");
        System.out.println("Numero unique du nouveau professionnel = " + newPro.getUuid());
    }

    //TODO ajouter type dans les questions! type c'est genre "zumba", "polo", "boxe", etc
    void createActivity(String comment, Stamp start, Stamp end, Hours hour, int capacity, UUID9 proNumber, Week week, String name, double price, boolean payPerClient, String type) {
        if(capacity > 30) {
            System.out.println("Une activité ne peut avoir plus de 30 de capacitié");
            return;
        }

        if(price > 999.99) {
            System.out.println("Une activité ne peut être plus chère que 999.99$");
            return;
        }

        Professionnal p = ds.getProfessionnal(proNumber);
        if(p == null) {
            System.out.println("Ce pro n'existe pas.");
            return;
        }

        Activity a = new Activity(comment, start, end, hour, capacity, proNumber, week, name, price, payPerClient, type);
        p.addActivity(a);
        ds.addActivity(a);

        System.out.println("Validé");
    }

    void confirmAttendance(UUID9 clientUuid, UUID7 serviceUuid, String comment) {
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

    void enrollIntoActivity(UUID9 clientUuid, UUID7 serviceUuid, Timestamp onDate, String comment) {
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
            String resp = scanner.nextLine();

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

    void printTEFs() {
        for(Professionnal p: ds.getProfessionnals()) {
            File f = new File("./TEFS/"+p.getUuid()+".tef");
            f.getParentFile().mkdirs();
            try {
                FileWriter writer = new FileWriter(f);
                String tef = ProfessionalReporter.getTEF(p); // TODO add the other part
                writer.write(tef);
                writer.close();
            } catch (IOException e) {
                System.out.println("Quelque chose s'est mal passé. Sortie de la procédure de TEF.");
                return;
            }
        }

        System.out.println("Validé");
    }

    void printAccounting() {
        printReport();
        printTEFs();
    }

    void printReport() {
        String report = ManagerReporter.generateReport();
        try {
            File f = new File("report.tsv");
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

    void consultInscriptions(UUID9 proUuid) {
        Professionnal p = ds.getProfessionnal(proUuid);
        ArrayList<Activity> as = p.getActivities();
        for(Activity a: as) {
            System.out.println("activité : "+a.getName()+": ");
            ArrayList<Client> cList = a.getInscriptions();
            for (Client client : cList) {
                System.out.println("---" + client.toString());
            }
        }
    }

    void printClientReport(UUID9 clientUuid){
        Client client = ds.getClient(clientUuid);
        String clientReport = ClientReporter.generateReport(client);

        try {
            File f = new File(genEntityFileName(client));
            FileWriter writer = new FileWriter(f);
            writer.write(clientReport);
            writer.close();
        } catch (IOException e) {
            System.out.println("Quelque chose s'est mal passé. Sortie de la procédure de TEF.");
        }
    }

    void printProReport(UUID9 proUuid){
        Professionnal professionnal = ds.getProfessionnal(proUuid);
        String proReport = ProfessionalReporter.generateReport(professionnal);
        try {
            File f = new File(genEntityFileName(professionnal));
            FileWriter writer = new FileWriter(f);
            writer.write(proReport);
            writer.close();
        } catch (IOException e) {
            System.out.println("Quelque chose s'est mal passé. Sortie de la procédure de TEF.");
        }
    }

    String genEntityFileName(Entity entity){
        Stamp stamp = new Stamp();
        return entity.getName() + stamp.toString().split(" ")[0];
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
    public static Object meta_marshallType(String str, String type) throws Exception {
        switch (type) {
            case "int":
                return Integer.parseInt(str);
            case "Stamp":
                return new Stamp(str);
            case "Hours":
                return new Hours(str);
            case "Week":
                return new Week(str);
            case "UUID9":
                if(str.length() > 9) {
                    System.out.println("La longueur de cet ID '"+str+"' doit être plus petite que "+9);
                    throw new Exception("UUID");
                }

                try {
                    int test = Integer.parseInt(str);
                } catch (NumberFormatException e) {
                    System.out.println("Cet ID '"+str+"' n'est pas un chiffre!");
                    throw new Exception("UUID");
                }

                return new UUID9(Integer.parseInt(str));
            case "UUID7":
                if(str.length() > 7) {
                    System.out.println("La longueur de cet ID '"+str+"' doit être plus petite que "+7);
                    throw new Exception("UUID");
                }

                try {
                    int test = Integer.parseInt(str);
                } catch (NumberFormatException e) {
                    System.out.println("Cet ID '"+str+"' n'est pas un chiffre!");
                    throw new Exception("UUID");
                }

                return new UUID7(Integer.parseInt(str));
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
