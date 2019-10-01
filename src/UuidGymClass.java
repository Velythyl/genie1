public abstract class UuidGymClass extends GymClass {
    private int uuid;    //unique id

    private static int nextUuid = 0;

    public UuidGymClass(String comment) {
        super(comment);

        this.uuid = nextUuid;
        UuidGymClass.nextUuid++;
    }

    public int getUuid() {   //only a getter here: can't change uuid
        return uuid;
    }
}
