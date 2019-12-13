import org.junit.Assert;
import org.junit.Test;

public class PrototypeTest {



    @Test
    public void accessGym() {
        //arrange
        // ici on met les propriétés que plusieurs methodes ont besoin
        Prototype testPrototype = new Prototype();
        String name="mathieu";
        String address = "233 des erables";
        String province = "QC";
        String city = "MTL";
        String postalCode = "G8A1C1";
        String comment = "blablabla";
        String email = "math@blabla.com";

        boolean expectedAnswer = true;

        //act       // cest ou on appel la methode test
        Client newClient = new Client(name, address, province, city, postalCode, comment, email);
        UUID9 idTest = testPrototype.enrollClient(newClient.getName(),
                newClient.getAddress(),newClient.getProvince(),newClient.getCity(),
                newClient.getPostalCode(),newClient.getComment(),newClient.getEmail());
        boolean testRez = testPrototype.accessGym(idTest);

        //assert
           // une fois que tas appelé la methode avec les valeurs en haut cest la qu tu verifie si tas les bons resultat
        Assert.assertEquals(expectedAnswer, testRez);
    }
}