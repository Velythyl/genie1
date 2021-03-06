import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * The Prototype is the meat of the program. It is here that all the sequence diagrams and use cases are implemented
 */
public class Prototype {
    private static Prototype self;
    private DataStore ds;

    /**
     * Constructor simply gets the instance of the Datastore
     */
    Prototype() {
        ds = DataStore.getInstance();
    }

    /**
     * Prototype is a singleton. This method returns the instance of the Prototype
     *
     * @return the single Prototype instance
     */
    public static Prototype getInstance() {
        if(self == null) self = new Prototype();

        return self;
    }

    /**
     * Allows a client to access the gym. In real life, the uuid will be derived from a QR code
     *
     * @param uuid The UUID of the client
     * @return If the client can access the gym or not
     */
    boolean accessGym(UUID9 uuid) {
        Client c = ds.getClient(uuid);


        if(c!=null && c.getUuid().equals(uuid)) {
            if(c.isSuspended()) System.out.println("Membre suspendu: payez votre compte, profiteurs.");
            else {
                System.out.println("Validé");
                return true;
            }

        }

        System.out.println("Numéro invalide");
        return false;
    }

    /**
     * Enrolls a new client into the gym.
     *
     * This prints out the name and uuid of the new client, and this saves a new QR Code on the disk.
     *
     * @param name name of the client
     * @param address address of the client
     * @param province province of the client
     * @param city city of the client
     * @param postalCode postal code of the client
     * @param comment comment on the client
     * @param email email of the client
     */
    UUID9 enrollClient(String name, String address, String province, String city, String postalCode, String comment, String email) {
        /*
        if(!email.endsWith("@facebook.com")) {
            System.out.println("Votre addresse email n'est pas une addresse facebook valide. Veuillez reessayer.");
            return;
        }*/

        System.out.println("in enroll client: " + name + address + province + city + postalCode +comment + email);
        Client newClient = new Client(name, address, province, city, postalCode, comment, email);
        ds.addClient(newClient);

        System.out.println("Inscription réussie");
        System.out.println("Nom du client = "+newClient.getName());
        System.out.println("Numero unique du nouveau client = " + newClient.getUuid());
        QRUtils.genQR(newClient.getUuid().toString());
        System.out.println("Le code QR du client a été écrit sur le fichier QRCODE.jpg");
        return (UUID9) newClient.getUuid();
    }

    /**
     * Modifies the activity through reflection
     *
     * the fields and the values must be of the same index (so if name is field[1], the new name value must be
     * values[1])
     *
     * @param id the of of the client
     * @param fields the fields of the client
     * @param values of the fields to modify (NC means no changes).
     */
    void modifyClient(UUID9 id, String[] fields, String[] values) {
        Client c = ds.getClient(id);
        meta_modify(c, fields, values);
    }

    /**
     * Modifies the activity through reflection
     *
     * the fields and the values must be of the same index (so if name is field[1], the new name value must be
     * values[1])
     *
     * @param id the of of the client
     * @param fields the fields of the client
     * @param values of the fields to modify (NC means no changes).
     */
    void modifyActivity(UUID7 id, String[] fields, String[] values) {
        Activity c = ds.getActivity(id);
        meta_modify(c, fields, values);
    }

    /**
     * Modifies the pro through reflection
     *
     * the fields and the values must be of the same index (so if name is field[1], the new name value must be
     * values[1])
     *
     * @param id the of of the pro
     * @param fields the fields of the pro
     * @param values of the fields to modify (NC means no changes).
     */
    void modifyProfessionnal(UUID9 id, String[] fields, String[] values) {
        Professionnal c = ds.getProfessionnal(id);
        meta_modify(c, fields, values);
    }

    /**
     * Deletes the client
     * @param id the id of the client
     */
    void deleteClient(UUID9 id) {
        ds.delClient(id);
    }

    /**
     * Deletes the pro
     * @param id the id of the pro
     */
    void deleteProfessionnal(UUID9 id) {
        ds.delProfessionnal(id);
    }

    /**
     * Deletes the activity
     * @param id the id of the activity
     */
    void deleteActivity(UUID7 id) {
        ds.delActivity(id);
    }

    /**
     * Enrolls a new pro into the gym.
     *
     * @param name name of the pro
     * @param address address of the pro
     * @param province province of the pro
     * @param city city of the pro
     * @param postalCode postal code of the pro
     * @param comment comment on the pro
     * @param email email of the pro
     */
    UUID9 enrollProfessionnal(String name,String address,String province, String city, String postalCode, String comment, String email) {
        Professionnal newPro = new Professionnal(name,address, province, city, postalCode, comment, email);
        ds.addProfessionnal(newPro);

        System.out.println("Inscription réussie");
        System.out.println("Numero unique du nouveau professionnel = " + newPro.getUuid());

        return (UUID9) newPro.getUuid();
    }

    /**
     * Creates a new Activity
     *
     * @param comment the activity's comment
     * @param start the activity's start
     * @param end the activity's end
     * @param hour the activity's hour
     * @param capacity the activity's capcity (max 30)
     * @param proNumber the activity's profesionnal
     * @param week the activity's reccuring days
     * @param name the activity's name
     * @param price the activity's price (max 999.99)
     * @param payPerClient is the activity pay-per-client or pay-per-activity?
     * @param type the activity's type (boxing, swimming, etc)
     */
    void createActivity(String comment, Stamp start, Stamp end, Hour hour, int capacity, UUID9 proNumber, Week week, String name, double price, boolean payPerClient, String type) {
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

    /**
     * Confim the attendance of a client to an activity
     *
     * @param clientUuid the client's ID
     * @param serviceUuid the activity's ID
     * @param comment
     */
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

    /**
     * Enrolls a client into an activity.
     *
     * @param clientUuid the ID of the client
     * @param serviceUuid the ID of the activity
     * @param onDate the date on which the clients wants to begin the activity
     * @param comment
     */
    void enrollIntoActivity(UUID9 clientUuid, UUID7 serviceUuid, Stamp onDate, String comment) {
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

    /**
     * Prints all the TEFs into their files in the TEFS folder
     */
    void printTEFs() {
        for(Professionnal p: ds.getProfessionnals()) {
            File f = new File("./TEFS/"+p.getUuid()+".tef");
            f.getParentFile().mkdirs();
            try {
                FileWriter writer = new FileWriter(f);
                String tef = ProfessionalReporter.getTEF(p);
                writer.write(tef);
                writer.close();
            } catch (IOException e) {
                System.out.println("Quelque chose s'est mal passé. Sortie de la procédure de TEF.");
                return;
            }
        }

        System.out.println("Validé");
    }

    /**
     * Prints the accounting report (the one that's done every Friday)
     */
    void printAccounting() {
        printManagerReport();
        printTEFs();
    }

    /**
     * Prints the manager's report
     */
    void printManagerReport() {
        String report = ManagerReporter.generateReport();
        try {
            File f = new File("report.tsv");
            FileWriter writer = new FileWriter(f);
            writer.write(report);
            writer.close();
        } catch (IOException e) {
            System.out.println("Quelque chose s'est mal passé. Sortie de la procédure de printManagerReport.");
            return;
        }

        System.out.println("Validé");
    }

    /**
     * Consult the list of activities that haven't reached full capacity
     */
    void consultActivities(){
        ArrayList<Activity> fullList = ds.getActivities();

        if(fullList.size() == 0) {
            System.out.println("Il n'y a pas d'activités libres");
            return;
        }


        for(int i=0; i<fullList.size(); i++){
            Activity a = fullList.get(i);
            if(a.getInscriptions().size() < a.getCapacity()) System.out.println(a.toString());
        }
    }

    /**
     * Consult the inscriptions of the activities of a certain pro
     *
     * @param proUuid the ID of the pro
     */
    void consultInscriptions(UUID9 proUuid) {
        Professionnal p = ds.getProfessionnal(proUuid);
        ArrayList<Activity> as = p.getActivities();

        if(as.size() == 0) {
            System.out.println("Ce professionel n'a pas d'activités");
            return;
        }

        for(Activity a: as) {
            System.out.println("activité : "+a.getName()+": ");
            ArrayList<Client> cList = a.getInscriptions();
            for (Client client : cList) {
                System.out.println("\t" + client.toString());
            }
        }
    }

    /**
     * Prints a client's Report
     *
     * @param clientUuid the ID of the client
     */
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

    /**
     * Prints a pro's Report
     *
     * @param proUuid the ID of the pro
     */
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

    /**
     * Generates a file name for the day for a certain entity
     *
     * @param entity the entity we want a filename for
     * @return the file name
     */
    private String genEntityFileName(Entity entity){
        Stamp stamp = new Stamp();
        return entity.getName() + stamp.toString().split(" ")[0];
    }

    /**
     * Utilitary method for meta_modify.
     *
     * Allows us to recursively see all decladed fields of a class and its superclasses
     *
     * @param fields the fields accumulator for this recursive method
     * @param type the type of the class
     * @return the list of all the fields
     */
    private static java.util.List<Field> meta_getAllFields(java.util.List<Field> fields, Class<?> type) {
        //https://stackoverflow.com/questions/1042798/retrieving-the-inherited-attribute-names-values-using-java-reflection

        fields.addAll(Arrays.asList(type.getDeclaredFields()));

        if (type.getSuperclass() != null) {
            meta_getAllFields(fields, type.getSuperclass());
        }

        return fields;
    }

    /**
     * Meta_modify modifies an instance of a class. This uses reflection: very powerful, but also very dangerous and
     * sometimes hinders development.
     *
     * Here, we use it to NOT have to re-write a enormous amount of boilerplate code. Every time one wants to modify an
     * instance, one would have to ask which fields, and then check if each field is to be modified, and then call the
     * corresponding setter, and then when the requirements change and we have to add fields to a class, we'd have to
     * rewrite everything again...
     *
     * So we believe for this use-case Reflection is perfect. You give the list of fields, the list of new values, and
     * in a score of lines we do what would take hundres otherwise.
     *
     * @param instance the instance to be modified
     * @param fields the list of fields to modify
     * @param values the new values for each field (must follow the same order as the fields) ('NC' means that the value
     *               won't be changed)
     */
    public void meta_modify(GymClass instance, String[] fields, String[] values) {
        //https://blog.sevagas.com/Modify-any-Java-class-field-using-reflection

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

    /**
     * Gets a method of this prototype by name
     *
     * @param name name of the method
     * @return the method object
     * @throws ClassNotFoundException if no methods of this name exist, throw an Exception
     */
    private Method meta_getMethodByName(String name) throws ClassNotFoundException {
        for(Method m: Class.forName("Prototype").getDeclaredMethods()) {
            if(m.getName().equals(name)) return m;
        }
        throw new ClassNotFoundException();
    }

    /**
     * Marshalls a String into a requested Type.
     *
     * If the type doesn't exist, we return the original String by default.
     *
     * @param str the string to marshall
     * @param type the type to marshall into
     * @return the new Object
     */
    public static Object meta_marshallType(String str, String type) throws Exception {
        switch (type) {
            case "int":
                return Integer.parseInt(str);
            case "Stamp":
                return new Stamp(str);
            case "Hour":
                return new Hour(str);
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
            case "boolean":
                return (str.equals("1") || str.equals("t") || str.equals("y"));
            default:
                if(str.length()>100) {
                    System.out.println("Le système ne supporte pas les string de plus que 100 caractères.");
                    throw new Exception();
                }
                return str;
        }
    }

    /**
     * Calls a method of the Prototype by it's string name.
     *
     * Why use reflection? For the same reasons as for meta_modify: whenever requirements change, we'd have to rewrite
     * a lot of boilerplate type marshalling and we'd have to change said marshalling inside Main, this would be messy,
     * and hard to maintain, etc.
     *
     * Also, since we're doing a CLI, we're dealing with tons of strings, and having to explicitly marshall each string
     * into its correct type would be extremely tedious and cost lots of man-hours in real life.
     *
     * This is why we believe reflection is the correct way to do this. This way, one just needs to call the method with
     * string params, and they'll all be automatically converted.
     *
     * @param commandName The name of the method
     * @param params the params to pass to it (they will be marshalled by meta_marshallType)
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

        System.out.println("Quelque chose s'est mal passé. Êtes-vous certains d'avoir bien tapé votre commande? (meta_callbystring)");

    }
}
