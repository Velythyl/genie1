public abstract class UuidGymClass extends GymClass {
    private int uuid;    //unique id
    private int maxUuidLength;
    private static int nextUuid = 0;

    public UuidGymClass(String comment) {
        this(comment, 9);
    }

    public UuidGymClass(String comment, int maxUuidLength) {
        super(comment);

        this.maxUuidLength = maxUuidLength;
        this.uuid = nextUuid;
        UuidGymClass.nextUuid++;
        this.verifyUuid();
    }

    public void verifyUuid() {
        if(Integer.toString(uuid).length() > maxUuidLength) {
            System.out.println("UUID overflows: plus que "+maxUuidLength+" caractères.\nLe programme va maintenant se fermer.");
            System.exit(1);
        }
    }

    public int getUuid() {   //only a getter here: can't change uuid
        return uuid;
    }

    @Override
    public String toString() {
        String num = Integer.toString(uuid);
        int len = num.length();

        String pad = "";
        while(pad.length() < (maxUuidLength - len)) pad += "0";

        pad += num;

        return "UuidGymClass{" +
                "uuid=" + pad +
                "} " + super.toString();
    }
}
