import org.junit.Assert;
import org.junit.Test;

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
}