import java.sql.Array;
import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

public class Main {
    private static String[] options = {
            "inscrire/modifer/supprimer membre\n",
            "inscrire/modifer/supprimer professionnel\n",
            "inscrire un membre a un cours\n",
            "faire accéder un membre au gym\n",
            "inscrire/modifer/supprimer une nouvelle seance/activité\n",
            "Consulter les activités\n",
            "Consulter les inscriptions\n",
            "Confirmer la présence d'un membre a un cours\n",
            "sortir du logiciel"
    };

    private static String[] keys = {
            "[A]",
            "[B]",
            "[C]",
            "[D]",
            "[E]",
            "[F]",
            "[G]",
            "[H]",
            "[Q]",
    };

    public static void printMenu() {
        for(int i=0; i<keys.length; i++) System.out.println(keys[i]+"\t"+options[i]);
    }

    public static int addModOrDel(Scanner scanner) {
        System.out.println("Voulez vous ajouter [1], modifier [2], ou supprimer [3]?");
        String choice = scanner.nextLine().toLowerCase();
        if(choice.length() != 3) choice = "["+choice+"]";

        for(int i=1; i<4; i++){
            if(choice.equals("["+i+"]")) return i;
        }

        return -1;
    }

    public static String[] entityFields = {
            "name",
            "surname",
            "phone",
            "email",
            "address",
            "gender",
            "birthdate",
            "comment"
    };
    public static ArrayList<String> askEntityInfo(Scanner scanner) {
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

        return list;
    }

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
            printMenu();
            int choice = translateChoice(scanner.nextLine());
            helpMessage(choice);

            switch (choice) {
                case 0: {
                    int subchoice = addModOrDel(scanner);

                    if(subchoice == 3) {
                        System.out.println("Quel est l'identifiant du client à supprimer?");
                        String id = scanner.nextLine();
                        pt.meta_callByString("deleteClient", id);
                        break;
                    }


                    if(subchoice == 2) {
                        System.out.println("Quel est l'identifiant du client?");
                        String id = scanner.nextLine();
                        System.out.println("Quoi modifier? (Simplement faire ENTER lorsque vous ne voulez pas changer la valeur.");
                        ArrayList<String> list = askEntityInfo(scanner);
                        for(int i=0; i<list.size(); i++) {
                            list.set(i, list.get(i).equals("") ? "❎": list.get(i));
                        }
                        pt.modifyClient(Integer.parseInt(id), entityFields, (String[]) list.toArray());
                        break;
                    }

                    ArrayList<String> list = askEntityInfo(scanner);
                    String elems = String.join("\t", list);

                    pt.meta_callByString("enrollClient", elems);
                    break;
                }
                case 1: {
                    int subchoice = addModOrDel(scanner);

                    if(subchoice == 3) {
                        System.out.println("Quel est l'identifiant du professionnel à supprimer?");
                        String id = scanner.nextLine();
                        pt.meta_callByString("deleteProfessionnal", id);
                        break;
                    }


                    if(subchoice == 2) {
                        System.out.println("Quel est l'identifiant du professionnel?");
                        String id = scanner.nextLine();
                        System.out.println("Quoi modifier? (Simplement faire ENTER lorsque vous ne voulez pas changer la valeur.");
                        ArrayList<String> list = askEntityInfo(scanner);
                        for(int i=0; i<list.size(); i++) {
                            list.set(i, list.get(i).equals("") ? "❎": list.get(i));
                        }
                        pt.modifyClient(Integer.parseInt(id), entityFields, (String[]) list.toArray());
                        break;
                    }

                    ArrayList<String> list = askEntityInfo(scanner);
                    String elems = String.join("\t", list);

                    pt.meta_callByString("enrollProfessionnal", elems);
                    break;
                }
                case 2:{
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
                case 3:
                    System.out.println("veuillez inscrire l'ID du client.");
                    pt.meta_callByString("accessGym", scanner.nextLine());
                    break;
                case 4: {
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
                case 5:
                    pt.consultActivities();
                    break;
                case 6:
                    System.out.println("Veuillez entrer le numero du professionnel qui veut les consulter.");
                    pt.meta_callByString("consultInscriptions", scanner.nextLine());
                    break;
                case 7:
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
                case 8:
                    return;
                case -1:
                    System.out.println("Entrée inconnue. Veuillez réessayer.");
                    break;
            }
            System.out.printf("Pour continuer appuyer n'importe quelle touche.");
            System.in.read();
        }
    }

    public static int translateChoice(String choice) {
        choice = choice.toLowerCase();
        if(choice.length() == 1) choice = "["+choice+"]";

        for(int i=0; i<keys.length; i++) {
            if(keys[i].equals(choice)) return i;
        }

        return -1;
    }

    public static void helpMessage(int x){
        System.out.println("Input: "+keys[x]+". Vous avez choisi: " + options[x]);
    }
}
