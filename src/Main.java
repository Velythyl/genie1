import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static ArrayList<Client> existingClients = new ArrayList<>();
    static ArrayList<Professionnal> existingProfessionnals = new ArrayList<>();
    static ArrayList<Activity> existingActivities = new ArrayList<>();

    public void initPrototype() {
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

        /*
        existingActivities.add(new Activity("Good for any aspiring Jedi!",
                new Timestamp(System.currentTimeMillis()+100000), new Timestamp(System.currentTimeMillis()+1000000),
                13, 20, existingProfessionnals.get(0).getUuid(),
                new boolean[]{true, false, false, true, false, true, true}));*/
    }

    public static Client findClient(int uuid) {
        for(Client cl: existingClients) {
            if(cl.getUuid() == uuid) return cl;
        }

        return null;
    }

    public static String accessGym(int uuid) {
        Client cl = findClient(uuid);

        if(cl == null) return "Numéro invalide";
        else if(cl.isSuspended()) return "Membre suspendu";
        else return "Validé";
    }

    public static String enrollClient(String name, String surname, String phone, String email, String address, String gender,
                                      Timestamp birthdate, String comment) {

        for(Client cl: existingClients) {
            if(cl.getName().equals(name) && cl.getSurname().equals(surname) && cl.getEmail().equals(email)) return "Utilisateur existe déjà";
        }

        existingClients.add(new Client(name, surname, phone, email, address, gender, birthdate, comment));
        return "Inscription réussie";
    }
    /**
     * inscription au gym: inscription name surname phone email address isMale(boolean) millisTimestamp comment
     * @param args
     */
    public static void main(String[] args) {

        Prototype pt = new Prototype();
        //System.out.println(pt.meta_callByString("createActivity","commentaire\t0319413\t31874313\t21\t30\t0\ttrue,true,true,false,true,true,false\tJedi Diplomacy"));
        //System.out.println(pt.meta_callByString("accessGym\t0"));
        //pt.consultActivities();
        //pt.consultInscriptions(2);


        // create a scanner so we can read the command-line input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenue à l'interface de #GYM. Que voulez-vous faire?\n");
        while(true){
            System.out.println(
                    "Choisissez l'une des options suivantes en ÉCRIVANT SA LETTRE puis APPUYEZ SUR ENTER:\n" +
                    "[A] : inscrire un nouveau membre\n" +
                    "[B] : inscrire un nouveau professionnel\n" +
                    "[C] : inscrire un membre a un cours\n" +
                    "[D] : faire accéder un membre au gym\n" +
                    "[E] : inscrire un nouveau cours au programme\n" +
                    "[F] : Consulter les activités\n" +
                    "[G] : Consulter les inscriptions\n" +
                    "[H] : Confirmer la présence d'un membre a un cours\n"+
                    "[I] : POUR SORTIR DU LOGICIEL");
            switch (scanner.next()) {
                case "A":
                case "[A]":
                case "a":
                case "[a]":
                    helpMessage(0, "prenom nom telephone addresse_email addresse genre anniversaire commentaire");
                    pt.meta_callByString("enrollClient", scanner.next());
                    break;
                case "B":
                case "[B]":
                case "b":
                case "[b]": { // TODO : ajouter une question sur son domaine professionnel
                    helpMessage(1, "prenom nom telephone addresse_email addresse genre anniversaire commentaire");
                    pt.meta_callByString("enrollProfessionnal", scanner.next());
                    break;
                }
                case "C":
                case "[C]":
                case "c":
                case "[c]":
                    helpMessage(2, "clientID idService date comment");
                    pt.meta_callByString("enrollIntoActivity", scanner.next());
                    break;
                case "D":
                case "[D]":
                case "d":
                case "[d]":
                    helpMessage(3, "clientID");
                    pt.meta_callByString("accessGym", scanner.next());
                    break;
                case "E":
                case "[E]":
                case "e":
                case "[e]":
                    helpMessage(4, "commentaire date_debut date_fin heure nbr_max_d'utilisateurs professionelID jours_offerts nom_du_cours");
                    pt.meta_callByString("createActivity", scanner.next());
                    break;
                case "F":
                case "[F]":
                case "f":
                case "[f]":
                    System.out.println("vous avez bien selectionne : consulter les activites");
                    pt.consultActivities();
                    break;
                case "G":
                case "[G]":
                case "g":
                case "[g]":
                    System.out.println("vous avez bien selectionne : consulter les inscriptions\n" +
                            "veuillez entrer le numero du professionnel qui veut les consulter.");
                    pt.meta_callByString("consultInscriptions", scanner.next());
                    break;
                case "H":
                case "[H]":
                case "h":
                case "[h]":
                    System.out.println("vous avez bien selectionne : confirmer la presence d'un membre\n" +
                            "veuillez entrer le numero unique du client");
                    pt.meta_callByString("confirmAttendance", scanner.next());
                    break;
                case "I":
                case "[I]":
                case "i":
                case "[i]":
                    System.out.println("EXITING #GYM");
                    return;
            }
        }
    }

    public static void helpMessage(int x, String text){
        String[] cu = {"inscrire un nouveau membre",
                "inscrire un nouveau professionnel",
                "inscrire un membre a un cours",
                "faire accéder un membre au gym",
                "inscrire un nouveau cours au programme"};

        System.out.println("vous avez choisi " + cu[x] +
                "\nEntrez les informations suivantes separees de \";\"\n" +
                text + "\nAppuyez ensuite sur entree." +
                "\nformat des dates : jj/mm/yyyy\n" + // TODO : changer l'ordre
                "heures : hh:mm");
    }
}
