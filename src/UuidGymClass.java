public abstract class UuidGymClass extends GymClass {
    protected int uuid;    //unique id
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

    public String getUuidStr() {
        return StringUtils.pad(uuid, maxUuidLength);
    }

    @Override
    public String toString() {
        return "UuidGymClass{" +
                "uuid=" + getUuidStr() +
                "} " + super.toString();
    }
}
