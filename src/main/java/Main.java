import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main is the interface with the oustide world. The command line interface is a simulation of what the mobile app sees.
 */
public class Main {
    /**
     * inscription au gym: inscription name surname phone email address isMale(boolean) millisTimestamp comment
     * @param args
     */
    public static Scanner scannermain;

    public static void main(String[] args) {
        Prototype pt = Prototype.getInstance();
<<<<<<< HEAD
        //System.out.println(pt.meta_callByString("createActivity","commentaire\t0319413\t31874313\t21\t30\t0\ttrue,true,true,false,true,true,false\tJedi Diplomacy"));
        //System.out.println(pt.meta_callByString("accessGym\t0"));
        //pt.consultActivities();
        //pt.consultInscriptions(2);

        //int t = Integer.MAX_VALUE;

        //System.out.println(test);
        //test = new Activity("bla", new Timestamp(0), new Timestamp(0), new Hour("9:21"), 20, 2, new Week("0:0:0:0:0:0:1"), "Gratin", 21.3);
        //System.out.println(test);
=======
>>>>>>> eed93d3a5ed43b3d049fba7a99d3da5e9c445687

        // create a scanner so we can read the command-line input
        scannermain = new Scanner(System.in);
        System.out.println("Bienvenue à l'interface de #GYM. Que voulez-vous faire?\n");
        while(true){
            try {
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
                                "[I] : Imprimer le rapport comptable pour le Gérant\n" +
                                "[J] : procedure comptable (TEF)( on sait que c'est pas dans les CU mais pour vos tests)\n" +
                                "[K] : imprimer un rapport Client ou Professionel\n"  +
                                "[L] : SORTIR DU LOGICIEL");
                label1:
                switch (scannermain.nextLine()) {
                    case "A":
                    case "[A]":
                    case "a":
                    case "[a]": {
                        helpMessage(0);
                        addModifSupressMessage();
                        String choice = scannermain.nextLine();
                        label:
                        while(true) {
                            switch (choice) {
                                case "1":
                                    pt.meta_callByString("enrollClient", entityPrompt());
                                    break label;
                                case "2":
                                    System.out.println("vous avez choisi option 2 : modifier client.\n" +
                                            "Maintenant, écrivez le ID du client que vous voulez" +
                                            " modifier puis appuyez sur ENTER");
                                    UUID9 ID = (UUID9) Prototype.meta_marshallType(scannermain.nextLine(), "UUID9");
                                    System.out.println("Maintenant tous les fields requis vont vous être demandés.\n" +
                                            "SI VOUS NE VOULEZ PAS MODIFIER UN CHAMP ÉCRIVEZ SIMPLEMENT NC\n" +
                                            "pour proceder, appuyez sur ENTER");
                                    System.in.read();
                                    String[] entityFields = {
                                            "name",
                                            "address",
                                            "province",
                                            "city",
                                            "postalCode",
                                            "comment",
                                            "email"
                                    };
                                    String[] list = entityPrompt().split("\t");
                                    pt.modifyClient(ID, entityFields, list);
                                    break label;
                                case "3":
                                    System.out.println("Vous avez choisi option 3," +
                                            " veuillez entrer l'id du client à supprimer," +
                                            " puis appuyez sur ENTER");
                                    String id = scannermain.next();
                                    pt.meta_callByString("deleteClient", id);
                                    break label;
                                default:
                                    System.out.println("option invalide (1, 2 or 3) appuyez sur ENTER pour continuer");
                                    break;
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
                        String choice = scannermain.nextLine();

                        switch (choice) {
                            case "1":
                                pt.meta_callByString("enrollProfessionnal", entityPrompt());
                                break label1;
                            case "2":
                                System.out.println("vous avez choisi option 2 : modifier professionel.\n" +
                                        "Maintenant, écrivez le ID du professionel que vous voulez modifier" +
                                        " puis appuyez sur ENTER");
                                UUID9 ID = (UUID9) Prototype.meta_marshallType(scannermain.nextLine(), "UUID9");
                                System.out.println("Maintenant tous les fields requis vont vous être demandés.\n" +
                                        "SI VOUS NE VOULEZ PAS MODIFIER UN CHAMP ÉCRIVEZ SIMPLEMENT NC\n" +
                                        "Pour procéder, appuyez sur ENTER");
                                System.in.read();
                                String[] entityFields = {
                                        "name",
                                        "address",
                                        "province",
                                        "city",
                                        "postal code",
                                        "comment",
                                        "email"
                                };
                                String[] list = entityPrompt().split("\t");
                                pt.modifyProfessionnal(ID, entityFields, list);
                                break label1;
                            case "3":
                                System.out.println("Vous avez choisi option 3.\n" +
                                        " Veuillez écrire le ID du professionel à supprimer puis appuyez sur ENTER");
                                String id = scannermain.next();
                                pt.meta_callByString("deleteProfessionnal", id);
                                break label1;
                            default:
                                System.out.println("option invalide (1, 2 or 3) appuyez sur ENTER pour continuer");
                                break;
                        }
                        break;
                    }
                    case "C":
                    case "[C]":
                    case "c":
                    case "[c]":{
                        helpMessage(2);



                        ArrayList<String> list = new ArrayList<>();
                        System.out.println("veuillez écrire le ID du membre:");
                        list.add(scannermain.nextLine());

                        System.out.println("Voici la liste des activités:");
                        pt.consultActivities();
                        System.out.println("veuillez écrire le code de l'activite:");
                        list.add(scannermain.nextLine());

                        System.out.println("veuillez inscrire la date a laquelle il va participer a l'activité: JJ-MM-AAAA");
                        list.add(scannermain.nextLine());

                        System.out.println("veuillez inscrire un commentaire");
                        list.add(scannermain.nextLine());

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
                        pt.meta_callByString("accessGym", scannermain.nextLine());
                        break;
                    case "E":
                    case "[E]":
                    case "e":
                    case "[e]": {
                        helpMessage(4);

                        addModifSupressMessage();
                        String choice = scannermain.nextLine();

                        switch (choice) {
                            case "1":
                                pt.meta_callByString("createActivity", activityPrompt());
                                break label1;
                            case "2":
                                System.out.println("vous avez choisi option 2 : modifier activité.\nÉcrivez le ID de l'activité que vous voulez modifier puis appuyez sur ENTER");
                                UUID7 ID = (UUID7) Prototype.meta_marshallType(scannermain.nextLine(), "UUID7");
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
                                break label1;
                            case "3":
                                System.out.println("Vous avez choisi option 3\n écrivez le ID de l'activité a supprimer puis appuyez sur ENTER");
                                String id = scannermain.next();
                                pt.meta_callByString("deleteActivity", id);
                                break label1;
                            default:
                                System.out.println("option invalide (1, 2 or 3) appuyez sur ENTER pour continuer");
                                break;
                        }
                        break;
                    }
                    case "F":
                    case "[F]":
                    case "f":
                    case "[f]":
                        helpMessage(7);
                        pt.consultActivities();
                        break;
                    case "G":
                    case "[G]":
                    case "g":
                    case "[g]":
                        System.out.println("vous avez bien selectionne : consulter les inscriptions\n" +
                                "veuillez entrer le numero du professionnel qui veut les consulter.");
                        pt.meta_callByString("consultInscriptions", scannermain.nextLine());
                        break;
                    case "H":
                    case "[H]":
                    case "h":
                    case "[h]":
                        helpMessage(5);
                        System.out.println("Voici la liste des activités:");
                        pt.consultActivities();
                        ArrayList<String> list = new ArrayList<>();
                        System.out.println("numero unique du membre");
                        list.add(scannermain.nextLine());
                        System.out.println("ID de l'activité");
                        list.add(scannermain.nextLine());
                        System.out.println("commentaire");
                        list.add(scannermain.nextLine());

                        String elems = String.join("\t", list);

                        pt.meta_callByString("confirmAttendance", elems);
                        break;
                    case "I":
                    case "[I]":
                    case "i":
                    case "[i]":
                        System.out.println("le rapport comptable est maintenant imprimé dans le fichier report.tsv .\n");
                        pt.printTEFs();
                        break;
                    case "J":
                    case "[J]":
                    case "j":
                    case "[j]":
                        System.out.println("le rapport TEF est maintenant imprime dans le dossier TEFS.\n" +
                                "le rapport comptable est maintenant imprimé dans le fichier report.tsv .\n");
                        pt.printAccounting();
                        break;
                    case "K":
                    case "[K]":
                    case "k":
                    case "[k]":
                        helpMessage(6);
                        System.out.println("veuillez indiquer le rapport que vous voulez:\n" +
                                "[1] : rapport client\n" +
                                "[2] : rapport Professionel\n" +
                                "[3] : rapport Manager");
                        String choice = scannermain.nextLine();

                        if(choice.equals("1")){
                            System.out.println("svp écrire Uuid du client");
                            pt.printClientReport(new UUID9(Integer.parseInt(scannermain.nextLine())));
                        } else if (choice.equals("2")){
                            System.out.println("svp écrire Uuid du professionel");
                            pt.printProReport(new UUID9(Integer.parseInt(scannermain.nextLine())));
                        } else {
                            System.out.println("choisir une option pour le rapport du manager:\n" +
                                    "[1] : rapport partie 1\n" +
                                    "[2] : rapport partie 2");
                        }

                        break;
                    case "L":
                    case "[L]":
                    case "l":
                    case "[l]":
                        System.out.println("EXITING #GYM");
                        return;
                }
                System.out.print("Pour continuer, appuyez sur ENTER");
                System.in.read();
            } catch (Exception ignored) {
                System.out.println("Une erreur est survenue. Veuillez réessayer.");
            }
        }
    }

    /**
     * Prints the choice confirmation message. Used in case we want to change de confirmation message: they're now all
     * grouped in the same method.
     *
     * @param x which message to print
     */
    public static void helpMessage(int x){
        String[] cu = {
                "ajouter/modifier/supprimer un nouveau membre",
                "ajouter/modifier/supprimer un nouveau professionnel",
                "inscrire un membre a un cours",
                "faire accéder un membre au gym",
                "ajouter/modifier/supprimer une séance",
                "confirmer la presence d'un membre",
                "imprimer un rapport (Client, Professionel où Manager",
                "consulter les activites"};

        System.out.println("vous avez choisi " + cu[x]);
    }

    /**
     * Used to print the message asking for the Activity/Pro/Client options: add, modify, or delete?
     */
    public static void addModifSupressMessage(){
        System.out.println("voulez-vous ajouter [1], modifier [2] ou supprimer ? [3]");
    }

    /**
     * Prints the prompt that asks all the information for an entity (pro or client)
     *
     * @return A tab-separated representation of all the info
     */
    public static String entityPrompt(){
        //TODO tester si les grandeurs des string fournis sont ok: il faut que la province soit 2 caracteres, la ville 14 max, etc
        ArrayList<String> list = new ArrayList<>();
        System.out.println("nom:");
        String name = scannermain.nextLine();
        while (name.length()>25){
            System.out.println("mauvais format: entrez 25 lettres maximum.\n" +
                    "veuillez réécrire votre nom correctement.");
            name = scannermain.nextLine();
        }
        list.add(name);

        System.out.println("addresse physique:");
        String addr = scannermain.nextLine();
        while (addr.length()>25){
            System.out.println("mauvais format: entrez 25 lettres maximum.\n" +
                    "veuillez réécrire votre addresse correctement.");
            addr = scannermain.nextLine();
        }
        list.add(addr);

        System.out.println("province (exemple : QC):");
        String prov = scannermain.nextLine();
        prov = prov.replaceAll(" ", "");
        while (prov.length()>2){
            System.out.println("mauvais format: entrez 2 lettres seulement...  exemple : QC\n" +
                    "veuillez réécrire votre province correctement.");
            prov = scannermain.nextLine().replaceAll(" ", "");;
        }
        list.add(prov);

        System.out.println("ville ( exemple : St-Augustine ):");
        String city = scannermain.nextLine();
        while (city.length()>14){
            System.out.println("mauvais format: entrez 14 lettres maximum... \n" +
                    "veuillez réécrire votre ville correctement.");
            city = scannermain.nextLine();
        }
        list.add(city);

        System.out.println("Code Postal ( exemple : H172M9 ):");
        String pc = scannermain.nextLine();
        while (pc.length()>6){
            System.out.println("mauvais format: entrez 6 lettres maximum... \n" +
                    "veuillez réécrire votre code postal correctement.");
            pc = scannermain.nextLine();
        }
        list.add(pc);


        System.out.println("commentaire");
        list.add(scannermain.nextLine());

        System.out.println("addresse courriel:");
        list.add(scannermain.nextLine());

        System.out.println(list.toString());
        return String.join("\t", list);
    }

    /**
     * Prints the prompt that asks all the info for an activity
     *
     * @return A tab-separated representation of all the info
     */
    public static String activityPrompt(){
        ArrayList<String> list = new ArrayList<>();

        System.out.println("commentaire");
        list.add(scannermain.nextLine());

        System.out.println("date de debut: JJ-MM-AAAA");
        list.add(scannermain.nextLine());

        System.out.println("date de fin: JJ-MM-AAAA");
        list.add(scannermain.nextLine());

        System.out.println("heure du cours. HH:MM");
        list.add(scannermain.nextLine());

        System.out.println("nombre max d'utilisateurs");
        list.add(scannermain.nextLine());

        System.out.println("ID du professionnel qui donnera ce cours");
        list.add(scannermain.nextLine());

        ArrayList<String> days = new ArrayList<>();
        String[] jours = new String[]{"samedi", "dimanche", "lundi", "mardi", "mecredi", "jeudi", "vendredi"};
        for(int i=0; i<7; i++){
            System.out.println("le cours aura-t-il lieu le " + jours[i] + "? y = oui , n = non");
            if (scannermain.nextLine().equals("n")){
                days.add("0");
            } else {
                days.add("1");
            }
            if(i==6) continue;
            days.add(":");
        }
        list.add(String.join("",days));

        System.out.println("veuillez inscrire le nom du cours");
        String className = scannermain.nextLine();
        while (className.length()>20){
            System.out.println("mauvais format: entrez 20 lettres maximum... \n" +
                    "veuillez réécrire le nom du service correctement.");
            className = scannermain.nextLine();
        }
        list.add(className);

        System.out.println("veuillez inscrire le prix du cours");
        double price = Double.parseDouble(scannermain.nextLine());
        while (price>999.99){
            System.out.println("le service doit couter moins de 999.99$ ... \n" +
                    "veuillez réécrire le prix du cours :-> exemple : 25");
            price = Double.parseDouble(scannermain.nextLine());
        }
        list.add(Double.toString(price));

        System.out.println("veuillez inscrire le type du cours");//TODO test
        list.add(scannermain.nextLine());

        return String.join("\t", list);
    }

}
