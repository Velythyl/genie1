import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

public class Main {
    /**
     * inscription au gym: inscription name surname phone email address isMale(boolean) millisTimestamp comment
     * @param args
     */
    public static void main(String[] args) throws IOException {
        Prototype pt = new Prototype();
        //System.out.println(pt.meta_callByString("createActivity","commentaire\t0319413\t31874313\t21\t30\t0\ttrue,true,true,false,true,true,false\tJedi Diplomacy"));
        //System.out.println(pt.meta_callByString("accessGym\t0"));
        //pt.consultActivities();
        //pt.consultInscriptions(2);

        //int t = Integer.MAX_VALUE;

        //System.out.println(test);
        //test = new Activity("bla", new Timestamp(0), new Timestamp(0), new Hours("9:21"), 20, 2, new Days("0:0:0:0:0:0:1"), "Gratin", 21.3);
        //System.out.println(test);

        // create a scanner so we can read the command-line input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenue à l'interface de #GYM. Que voulez-vous faire?\n");
        while(true){
            System.out.println(
                    "Choisissez l'une des options suivantes en ÉCRIVANT SA LETTRE\npuis APPUYEZ SUR ENTER:\n" +
                            "[A] : inscrire un nouveau membre\n" +
                            "[B] : inscrire un nouveau professionnel\n" +
                            "[C] : inscrire un membre a un cours\n" +
                            "[D] : faire accéder un membre au gym\n" +
                            "[E] : inscrire une nouvelle seance/activité\n" +
                            "[F] : Consulter les activités\n" +
                            "[G] : Consulter les inscriptions\n" +
                            "[H] : Confirmer la présence d'un membre a un cours\n"+
                            "[I] : POUR SORTIR DU LOGICIEL");
            switch (scanner.nextLine()) {
                case "A":
                case "[A]":
                case "a":
                case "[a]":{
                    helpMessage(0);
                    ArrayList<String> list = new ArrayList<>();
                    System.out.println("veuillez écrire son nom:");
                    list.add(scanner.nextLine());

                    System.out.println("veuillez écrire son nom de famille:");
                    list.add(scanner.nextLine());

                    System.out.println("veuillez écrire son numéro de téléphone:");
                    list.add(scanner.nextLine());

                    System.out.println("veuillez écrire son addresse courriel:");
                    list.add(scanner.nextLine());

                    System.out.println("veuillez écrire son addresse physique:");
                    list.add(scanner.nextLine());

                    System.out.println("veuillez indique son genre: Homme [h],Femme [f], autre [a]");
                    list.add(scanner.nextLine());

                    System.out.println("sa date de fete?:");
                    list.add(scanner.nextLine());

                    System.out.println("commentaire?");
                    list.add(scanner.nextLine());

                    String elems = String.join("\t", list);

                    pt.meta_callByString("enrollClient", elems);
                    break;
                }
                case "B":
                case "[B]":
                case "b":
                case "[b]": {
                    helpMessage(1);
                    ArrayList<String> list = new ArrayList<>();
                    System.out.println("vous avez choisi : inscrire un nouveau professionnel");
                    System.out.println("veuillez écrire son nom:");
                    list.add(scanner.nextLine());

                    System.out.println("veuillez écrire son nom de famille:");
                    list.add(scanner.nextLine());

                    System.out.println("veuillez écrire son numéro de téléphone:");
                    list.add(scanner.nextLine());

                    System.out.println("veuillez écrire son addresse courriel:");
                    list.add(scanner.nextLine());

                    System.out.println("veuillez écrire son addresse physique:");
                    list.add(scanner.nextLine());

                    System.out.println("veuillez indique son genre: Homme [h],Femme [f], autre [a]");
                    list.add(scanner.nextLine());

                    System.out.println("sa date de fete?:");
                    list.add(scanner.nextLine());

                    System.out.println("commentaire?");
                    list.add(scanner.nextLine());

                    String elems = String.join("\t", list);

                    pt.meta_callByString("enrollProfessionnal", elems);
                    break;
                }
                case "C":
                case "[C]":
                case "c":
                case "[c]":{
                    helpMessage(2);

                    ArrayList<String> list = new ArrayList<>();
                    System.out.println("veuillez écrire son ID:");
                    list.add(scanner.nextLine());

                    System.out.println("veuillez écrire le code de l'activite:");
                    list.add(scanner.nextLine());

                    System.out.println("veuillez inscrire la date");
                    list.add(scanner.nextLine());

                    System.out.println("veuillez inscrire un commentaire");
                    list.add(scanner.nextLine());

                    String elems = String.join("\t", list);

                    pt.meta_callByString("enrollIntoActivity", elems);

                    break;
                }
                case "D":
                case "[D]":
                case "d":
                case "[d]":
                    helpMessage(3);
                    System.out.println("veuillez inscrire l'ID du client.");
                    pt.meta_callByString("accessGym", scanner.nextLine());
                    break;
                case "E":
                case "[E]":
                case "e":
                case "[e]": {
                    helpMessage(4);
                    ArrayList<String> list = new ArrayList<>();
                    System.out.println("veuillez inscrire un commentaire");
                    list.add(scanner.nextLine());
                    System.out.println("veuillez inscrire la date de debut");
                    list.add(scanner.nextLine());
                    System.out.println("veuillez inscrire la date de fin");
                    list.add(scanner.nextLine());
                    System.out.println("veuillez inscrire l'heure du cours.");
                    list.add(scanner.nextLine());
                    System.out.println("veuillez inscrire le nombre max d'utilisateurs");
                    list.add(scanner.nextLine());
                    System.out.println("veuillez inscrire l'ID du professionnel qui donnera ce cours");
                    list.add(scanner.nextLine());
                    System.out.println("veuillez inscrire le numero des jours de la semaine ou il sera offert");
                    list.add(scanner.nextLine());
                    System.out.println("veuillez inscrire le nom du cours");
                    list.add(scanner.nextLine());
                    System.out.println("veuillez inscrire le prix du cours");
                    list.add(scanner.nextLine());

                    String elems = String.join("\t", list);

                    pt.meta_callByString("createActivity", elems);
                    break;
                }
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
                    pt.meta_callByString("consultInscriptions", scanner.nextLine());
                    break;
                case "H":
                case "[H]":
                case "h":
                case "[h]":
                    System.out.println("vous avez bien selectionne : confirmer la presence d'un membre");
                    System.out.println("Voici la liste des activités:");
                    pt.consultActivities();
                    ArrayList<String> list = new ArrayList<>();
                    System.out.println("Veuillez entrer le numero unique du client");
                    list.add(scanner.nextLine());
                    System.out.println("Veuillez entrer le ID de l'activite");
                    list.add(scanner.nextLine());
                    System.out.println("veuillez entrer un commentaire");
                    list.add(scanner.nextLine());

                    String elems = String.join("\t", list);

                    pt.meta_callByString("confirmAttendance", elems);
                    break;
                case "I":
                case "[I]":
                case "i":
                case "[i]":
                    System.out.println("EXITING #GYM");
                    return;
            }
            System.out.printf("Pour continuer appuyer n'importe quelle touche.");
            System.in.read();
        }
    }

    public static void helpMessage(int x){
        String[] cu = {"inscrire un nouveau membre",
                "inscrire un nouveau professionnel",
                "inscrire un membre a un cours",
                "faire accéder un membre au gym",
                "inscrire un nouveau cours au programme",
                ""};

        System.out.println("vous avez choisi " + cu[x]);
    }
}
