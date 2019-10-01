import java.sql.Timestamp;
import java.util.ArrayList;

public class Main {

    ArrayList<Client> existingClients = new ArrayList<>();
    ArrayList<Professionnal> existingProfessionnals = new ArrayList<>();
    ArrayList<Activity> existingActivities = new ArrayList<>();

    public void initPrototype() {
        existingClients.add(new Client("Yoda", "N/A", "451-219-2131",
                "yoda_best_jedi@oldrepublic.org", "1 Master's Council ave.", true,
                new Timestamp(System.currentTimeMillis()), "Is allergic to latex"));
        Client suspended = new Client("Bob", "Bobert", "313-414-4531", "bobinne@bob.com",
                "3431 rue des Boberts", true, new Timestamp(System.currentTimeMillis()-700000),
                null);
        suspended.setSuspended(true);
        existingClients.add(suspended);

        existingProfessionnals.add(new Professionnal("Obi Wan", "Kenobi", "451-219-9999",
                "kenobae@oldrepublic.org", "1 Master's Council ave.", true,
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

    public String enrollClient(String name, String surname, String phone, String email, String address, boolean isMale,
                               Timestamp birthdate, String comment) {

        for(Client cl: existingClients) {
            if(cl.getName().equals(name) && cl.getSurname().equals(surname) && cl.getEmail().equals(email)) return "Utilisateur existe déjà";
        }

        existingClients.add(new Client(name, surname, phone, email, address, isMale, birthdate, comment));
    }

    /**
     * inscription au gym: inscription name surname phone email address isMale(boolean) millisTimestamp comment
     * @param args
     */
    public static void main(String[] args) {

    }
}
