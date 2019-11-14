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
    public static Scanner scanner;

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
        scanner = new Scanner(System.in);
        System.out.println("Bienvenue à l'interface de #GYM. Que voulez-vous faire?\n");
        while(true){
            System.out.println(
                    "Choisissez l'une des options suivantes en ÉCRIVANT SA LETTRE\npuis APPUYEZ SUR ENTER:\n" +
                            "[A] : ajouter/modifier/supprimer un membre\n" +
                            "[B] : ajouter/modifier/supprimer un nouveau professionnel\n" +
                            "[C] : inscrire un membre a un cours\n" +
                            "[D] : faire accéder un membre au gym\n" +
                            "[E] : ajouter/modifier/supprimer une nouvelle seance/activité\n" +
                            "[F] : Consulter les activités/séances\n" +
                            "[G] : Consulter les inscriptions\n" +
                            "[H] : Confirmer la présence d'un membre a un cours/séance/activité\n"+
                            "[I] : Imprimer le rapport\n" +
                            "[J] : procedure comptable ( on sait que c'est pas dans les CU mais pour vos tests)\n" +
                            "[K] : SORTIR DU LOGICIEL");
            switch (scanner.nextLine()) {
                case "A":
                case "[A]":
                case "a":
                case "[a]": {
                    helpMessage(0);
                    addModifSupressMessage();
                    String choice = scanner.nextLine();
                    while(true) {
                        if (choice.equals("1")) {
                            pt.meta_callByString("enrollClient", clientPrompt());
                            break;
                        } else if (choice.equals("2")) {
                            System.out.println("vous avez choisi option 2 : modifier client.\nMaintenant, écrivez le ID du client que vous voulez modifier puis appuyez sur ENTER");
                            int ID = Integer.parseInt(scanner.nextLine());
                            System.out.println("Maintenant tous les fields requis vont vous être demandés.\n" +
                                    "SI VOUS NE VOULEZ PAS MODIFIER UN CHAMP ÉCRIVEZ SIMPLEMENT NC\n" +
                                    "pour proceder, appuyez sur ENTER");
                            System.in.read();
                            String[] entityFields = {
                                    "name",
                                    "surname",
                                    "phone",
                                    "email",
                                    "address",
                                    "gender",
                                    "birthdate",
                                    "comment"
                            };
                            String[] list = clientPrompt().split("\t");
                            pt.modifyClient(ID, entityFields, list);
                            break;
                        } else if (choice.equals("3")) {
                            System.out.println("you chose option 3 write the ID of the client to delete, then press ENTER");
                            String id = scanner.next();
                            pt.deleteClient(Integer.parseInt(id));
                            break;
                        } else {
                            System.out.println("option invalide (1, 2 or 3) appuyez sur ENTER pour continuer");
                        }
                    }
                    break;
                }
                case "B":
                case "[B]":
                case "b":
                case "[b]": {
                    helpMessage(1);
                    System.out.println("vous avez choisi : inscrire un nouveau professionnel");

                    addModifSupressMessage();
                    String choice = scanner.nextLine();

                    if(choice.equals("1")) {
                        pt.meta_callByString("enrollProfessionnal", clientPrompt());
                        break;
                    } else if (choice.equals("2")) {
                        System.out.println("vous avez choisi option 2 : modifier professionel.\nMaintenant, écrivez le ID du professionel que vous voulez modifier puis appuyez sur ENTER");
                        int ID = Integer.parseInt(scanner.nextLine());
                        System.out.println("Maintenant tous les fields requis vont vous être demandés.\n" +
                                "SI VOUS NE VOULEZ PAS MODIFIER UN CHAMP ÉCRIVEZ SIMPLEMENT NC\n" +
                                "Pour procéder, appuyez sur ENTER");
                        System.in.read();
                        String[] entityFields = {
                                "name",
                                "surname",
                                "phone",
                                "email",
                                "address",
                                "gender",
                                "birthdate",
                                "comment"
                        };
                        String[] list = clientPrompt().split("\t");
                        pt.modifyProfessionnal(ID, entityFields, list);
                        break;
                    } else if (choice.equals("3")) {
                        System.out.println("Vous avez choisi option 3\n écrivez le ID du professionel a supprimer puis appuyez sur ENTER");
                        String id = scanner.next();
                        pt.deleteProfessionnal(Integer.parseInt(id));
                        break;
                    } else {
                        System.out.println("option invalide (1, 2 or 3) appuyez sur ENTER pour continuer");
                    }
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

                    System.out.println("veuillez inscrire la date a laquelle il va participer a l'activité: JJ-MM-AAAA");
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

                    addModifSupressMessage();
                    String choice = scanner.nextLine();

                    if(choice.equals("1")) {
                        pt.meta_callByString("createActivity", activityPrompt());
                        break;
                    } else if (choice.equals("2")) {
                        System.out.println("vous avez choisi option 2 : modifier activité.\nÉcrivez le ID de l'activité que vous voulez modifier puis appuyez sur ENTER");
                        int ID = Integer.parseInt(scanner.nextLine());
                        System.out.println("Maintenant tous les fields requis vont vous être demandés.\n" +
                                "SI VOUS NE VOULEZ PAS MODIFIER UN CHAMP ÉCRIVEZ SIMPLEMENT NC\n" +
                                "Pour procéder, appuyez sur ENTER");
                        System.in.read();
                        String[] entityFields = {
                                "comment",
                                "start",
                                "end",
                                "hour",
                                "capacity",
                                "proNumber",
                                "days",
                                "name",
                                "price"
                        };
                        String[] list = activityPrompt().split("\t");
                        pt.modifyActivity(ID, entityFields, list);
                        break;
                    } else if (choice.equals("3")) {
                        System.out.println("Vous avez choisi option 3\n écrivez le ID du professionel a supprimer puis appuyez sur ENTER");
                        String id = scanner.next();
                        pt.deleteProfessionnal(Integer.parseInt(id));
                        break;
                    } else {
                        System.out.println("option invalide (1, 2 or 3) appuyez sur ENTER pour continuer");
                    }
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
                    System.out.println("numero unique du membre");
                    list.add(scanner.nextLine());
                    System.out.println("ID de l'activité");
                    list.add(scanner.nextLine());
                    System.out.println("commentaire");
                    list.add(scanner.nextLine());

                    String elems = String.join("\t", list);

                    pt.meta_callByString("confirmAttendance", elems);
                    break;
                case "I":
                case "[I]":
                case "i":
                case "[i]":
                    System.out.println("le rapport comptable est maintenant imprime dans le fichier report.tsv .\n");
                    pt.printTEFs();
                    break;
                case "J":
                case "[J]":
                case "j":
                case "[j]":
                    System.out.println("le rapport TEF est maintenant imprime dans le dossier TEFS.\n");
                    pt.printReport();
                    break;
                case "K":
                case "[K]":
                case "k":
                case "[k]":
                    System.out.println("EXITING #GYM");
                    return;
            }
            System.out.printf("Pour continuer, appuyez sur ENTER");
            System.in.read();
        }
    }

    public static void helpMessage(int x){
        String[] cu = {"ajouter/modifier/supprimer un nouveau membre",
                "ajouter/modifier/supprimer un nouveau professionnel",
                "inscrire un membre a un cours",
                "faire accéder un membre au gym",
                "ajouter/modifier/supprimer une séance",
                ""};

        System.out.println("vous avez choisi " + cu[x]);
    }
    public static void addModifSupressMessage(){
        System.out.println("voulez-vous ajouter [1], modifier [2] ou supprimer ? [3]");
    }

    public static String clientPrompt(){
        ArrayList<String> list = new ArrayList<>();
        System.out.println("nom:");
        list.add(scanner.nextLine());

        System.out.println("nom de famille:");
        list.add(scanner.nextLine());

        System.out.println("numéro de téléphone:");
        list.add(scanner.nextLine());

        System.out.println("addresse courriel:");
        list.add(scanner.nextLine());

        System.out.println("addresse physique:");
        list.add(scanner.nextLine());

        System.out.println("genre: Homme [h],Femme [f], autre [a]");
        list.add(scanner.nextLine());

        System.out.println("date de fete: JJ-MM-AAAA");
        list.add(scanner.nextLine());

        System.out.println("commentaire");
        list.add(scanner.nextLine());

        return String.join("\t", list);
    }

    public static String activityPrompt(){
        String[] jours = new String[]{"samedi", "dimanche", "lundi", "mardi", "mecredi", "jeudi", "vendredi"};
        ArrayList<String> list = new ArrayList<>();
        System.out.println("commentaire");
        list.add(scanner.nextLine());
        System.out.println("date de debut: JJ-MM-AAAA");
        list.add(scanner.nextLine());
        System.out.println("date de fin: JJ-MM-AAAA");
        list.add(scanner.nextLine());
        System.out.println("heure du cours. HH:MM");
        list.add(scanner.nextLine());
        System.out.println("nombre max d'utilisateurs");
        list.add(scanner.nextLine());
        System.out.println("ID du professionnel qui donnera ce cours");
        list.add(scanner.nextLine());
        ArrayList<String> list2 = new ArrayList<>();
        for(int i=0; i<7; i++){
            System.out.println("le cours aura-t-il lieu le" + jours[i] + "? y = oui , n = no");
            if (scanner.nextLine().equals("n")){
                list2.add("0");
            } else {
                list2.add("1");
            }
            if(i==6) continue;
            list2.add(":");
        }
        list.add(String.join("",list2));
        System.out.println("veuillez inscrire le nom du cours");
        list.add(scanner.nextLine());
        System.out.println("veuillez inscrire le prix du cours");
        list.add(scanner.nextLine());

        return String.join("\t", list);
    }

}
