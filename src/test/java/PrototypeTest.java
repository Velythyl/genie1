import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Tests on the prototype
 */
public class PrototypeTest {

    Prototype testPrototype = new Prototype();
    String name="mathieu";
    String address = "233 des erables";
    String province = "QC";
    String city = "MTL";
    String postalCode = "G8A1C1";
    String comment = "blablabla";
    String email = "math@blabla.com";

    DataStore ds = DataStore.getInstance();

    /**
     * Tests if accessGym works
     */
    @Test
    public void accessGym() {
        //arrange
        Client newClient = new Client(name, address, province, city, postalCode, comment, email);


        //act       // cest ou on appel la methode test
        UUID9 idTest = testPrototype.enrollClient(newClient.getName(),
                newClient.getAddress(),newClient.getProvince(),newClient.getCity(),
                newClient.getPostalCode(),newClient.getComment(),newClient.getEmail());

        boolean testRez = testPrototype.accessGym(idTest);
        boolean testRez2 = testPrototype.accessGym(new UUID9(123123123));

        //assert
           // une fois que tas appelé la methode avec les valeurs en haut cest la qu tu verifie si tas les bons resultat
        Assert.assertEquals(true, testRez);
        Assert.assertEquals(false,testRez2);
    }

    /**
     * Tests if enrollClient works
     */
    @Test
    public void enrollClient() {
        //arrange
        Client newClient = new Client(name, address, province, city, postalCode, comment, email);


        //act
        UUID9 uuidtest = testPrototype.enrollClient(newClient.getName(),
                newClient.getAddress(),newClient.getProvince(),newClient.getCity(),
                newClient.getPostalCode(),newClient.getComment(),newClient.getEmail());

        //assert
        Client dsClient = ds.getClient(uuidtest);
        Assert.assertArrayEquals(
                new String[]{
                        newClient.getName(),
                        newClient.getAddress(),
                        newClient.getCity(),
                        newClient.getPostalCode(),
                        newClient.getComment(),
                        newClient.getProvince()},

                new String[]{
                        dsClient.getName(),
                        dsClient.getAddress(),
                        dsClient.getCity(),
                        dsClient.getPostalCode(),
                        dsClient.getComment(),
                        dsClient.getProvince()});

    }

    /**
     * Tests if modifyClient works
     */
    @Test
    public void modifyClient() {
        //arrange
        Client newClient = new Client(name, address, province, city, postalCode, comment, email);

        UUID9 uuidtest = testPrototype.enrollClient(newClient.getName(),
                newClient.getAddress(),newClient.getProvince(),newClient.getCity(),
                newClient.getPostalCode(),newClient.getComment(),newClient.getEmail());

        Client dsClient = ds.getClient(uuidtest);

        String[] entityFields = {
                "name",
                "address",
                "province",
                "city",
                "postalCode",
                "comment",
                "email"
        };

        String[] list = { "mathilde","Sans abris","BC","NC","NC","WHUUUT", "NC" };

        //act
        testPrototype.modifyClient(uuidtest, entityFields, list );


        //assert
        Assert.assertArrayEquals(
                new String[]{
                        "mathilde",
                        "Sans abris",
                        newClient.getCity(),
                        newClient.getPostalCode(),
                        "WHUUUT",
                        "BC",
                        newClient.getEmail()},
                new String[]{
                        dsClient.getName(),
                        dsClient.getAddress(),
                        dsClient.getCity(),
                        dsClient.getPostalCode(),
                        dsClient.getComment(),
                        dsClient.getProvince(),
                        dsClient.getEmail()});


    }

    /**
     * Tests if modifyProfessionnal works
     */
    @Test
    public void modifyProfessionnal() {
        //arrange
        Professionnal professionnal = new Professionnal(name, address, province, city, postalCode, comment, email);
        UUID9 idTest = testPrototype.enrollProfessionnal(professionnal.getName(),professionnal.getAddress(),
                professionnal.getProvince(),professionnal.getCity(),professionnal.getPostalCode(),
                professionnal.getComment(), professionnal.getEmail());

        Professionnal proInSys = ds.getProfessionnal(idTest);

        String[] entityFields = {
                "name",
                "address",
                "province",
                "city",
                "postalCode",
                "comment",
                "email"
        };

        String[] list = { "mathilde","Sans abris","BC","NC","NC","WHUUUT", "NC" };

        //act
        testPrototype.modifyProfessionnal(idTest, entityFields, list );

        //assert
        Assert.assertArrayEquals(
                new String[]{
                        "mathilde",
                        "Sans abris",
                        professionnal.getCity(),
                        professionnal.getPostalCode(),
                        "WHUUUT",
                        "BC",
                        professionnal.getEmail()},
                new String[]{
                        proInSys.getName(),
                        proInSys.getAddress(),
                        proInSys.getCity(),
                        proInSys.getPostalCode(),
                        proInSys.getComment(),
                        proInSys.getProvince(),
                        proInSys.getEmail()});
    }

    /**
     * Tests if deleteClient works
     */
    @Test
    public void deleteClient() {
        //arrange
        Client newClient = new Client(name, address, province, city, postalCode, comment, email);

        UUID9 uuidtest = testPrototype.enrollClient(newClient.getName(),
                newClient.getAddress(),newClient.getProvince(),newClient.getCity(),
                newClient.getPostalCode(),newClient.getComment(),newClient.getEmail());

        boolean clientIsInSystemBefore = testPrototype.accessGym(uuidtest);
        //act
        testPrototype.deleteClient(uuidtest);
        boolean clientIsInSystemAft = testPrototype.accessGym(uuidtest);
        //assert
        Assert.assertNotEquals(clientIsInSystemBefore,clientIsInSystemAft);
    }

    /**
     * Tests if deleteProfessionnal works
     */
    @Test
    public void deleteProfessionnal() {
        //arrange
        Professionnal professionnal = new Professionnal(name, address, province, city, postalCode, comment, email);
        UUID9 idTest = testPrototype.enrollProfessionnal(professionnal.getName(),professionnal.getAddress(),
                professionnal.getProvince(),professionnal.getCity(),professionnal.getPostalCode(),
                professionnal.getComment(), professionnal.getEmail());
        boolean proIsInSystemBefore = ds.getProfessionnal(idTest)!=null;
        //act
        testPrototype.deleteProfessionnal(idTest);
        boolean proIsInSystemAft = ds.getProfessionnal(idTest)!=null;
        //assert
        Assert.assertNotEquals(proIsInSystemBefore,proIsInSystemAft);
    }

    /**
     * Tests if enrollProfessionnal works
     */
    @Test
    public void enrollProfessionnal() {
        //arrange
        Professionnal newPro = new Professionnal(name, address, province, city, postalCode, comment, email);


        //act
        UUID9 uuidtest = testPrototype.enrollProfessionnal(newPro.getName(),
                newPro.getAddress(),newPro.getProvince(),newPro.getCity(),
                newPro.getPostalCode(),newPro.getComment(),newPro.getEmail());

        //assert
        Professionnal dsPro = ds.getProfessionnal(uuidtest);
        Assert.assertArrayEquals(
                new String[]{
                        newPro.getName(),
                        newPro.getAddress(),
                        newPro.getCity(),
                        newPro.getPostalCode(),
                        newPro.getComment(),
                        newPro.getProvince()},

                new String[]{
                        dsPro.getName(),
                        dsPro.getAddress(),
                        dsPro.getCity(),
                        dsPro.getPostalCode(),
                        dsPro.getComment(),
                        dsPro.getProvince()});

    }

    /**
     * Tests if consultActivities works
     *
     * Also tests if the unique way of generating activity IDs works
     */
    @Test
    public void consultActivities() {
        ds.wipe();

        Activity a1 = new Activity("comment", new Stamp("01-01-1000"),
                new Stamp("01-01-1000"), new Hour("11:11"), 1, new UUID9(0),
                new Week("1:1:1:1:1:1:1"), "le nom", 0.10, false, "zumba");

        Activity a2 = new Activity("comment2", new Stamp("01-01-1000"),
                new Stamp("01-01-1000"), new Hour("11:11"), 1, new UUID9(0),
                new Week("1:1:1:1:1:1:1"), "le nom2", 0.10, false, "zumba");

        Assert.assertEquals("0000000", a1.getUuid().toString());
        Assert.assertEquals("0000100", a2.getUuid().toString());

        ds.addActivity(a1);
        ds.addActivity(a2);

        //https://stackoverflow.com/questions/8708342/redirect-console-output-to-string-in-java
        // Create a stream to hold the output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        // IMPORTANT: Save the old System.out!
        PrintStream old = System.out;
        // Tell Java to use your special stream
        System.setOut(ps);

        testPrototype.consultActivities();

        // Put things back
        System.out.flush();
        System.setOut(old);

        // Show what happened
        String outputWas = baos.toString();
        Assert.assertEquals("Nom de l'activité: le nom\tID: 0000000\tDate de début: 01-01-1000 00:00:00.0\tDate" +
                " de fin: 01-01-1000 00:00:00.0\tHeure: 11:11\tCapacité:1\tID du Pro: 000000000\tJours:[samedi, dimanche" +
                ", lundi, mardi, mecredi, jeudi, vendredi]\tInscriptions:0 clients\n" +
                "\n" +
                "Nom de l'activité: le nom2\tID: 0000100\tDate de début: 01-01-1000 00:00:00.0\tDate de fin: 01-01-1000 " +
                "00:00:00.0\tHeure: 11:11\tCapacité:1\tID du Pro: 000000000\tJours:[samedi, dimanche, lundi, mardi, " +
                "mecredi, jeudi, vendredi]\tInscriptions:0 clients\n\n", outputWas);

    }
}