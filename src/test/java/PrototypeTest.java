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
                        dsClient.getName(),
                        dsClient.getAddress(),
                        dsClient.getCity(),
                        dsClient.getPostalCode(),
                        dsClient.getComment(),
                        dsClient.getProvince()},
                new String[]{
                        newClient.getName(),
                        newClient.getAddress(),
                        newClient.getCity(),
                        newClient.getPostalCode(),
                        newClient.getComment(),
                        newClient.getProvince()});

    }


    @Test
    public void modifyClient() {
        //arrange
        Client newClient = new Client(name, address, province, city, postalCode, comment, email);

        UUID9 uuidtest = testPrototype.enrollClient(newClient.getName(),
                newClient.getAddress(),newClient.getProvince(),newClient.getCity(),
                newClient.getPostalCode(),newClient.getComment(),newClient.getEmail());

        Client dsClient = ds.getClient(uuidtest);
        System.out.println(dsClient.toString());

        String[] entityFields = {
                "name",
                "address",
                "province",
                "city",
                "postalCode",
                "comment",
                "email"
        };

        String[] list = { "mathilde","NC","NC","NC","NC","NC", "NC" };

        //act
        testPrototype.modifyClient(uuidtest, entityFields, list );


        //assert
        Assert.assertArrayEquals(
                new String[]{
                        "mathilde",
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
    public void modifyProfessionnal() {
    }

    @Test
    public void deleteClient() {
    }

    @Test
    public void deleteProfessionnal() {
    }

    @Test
    public void enrollProfessionnal() {
    }
}