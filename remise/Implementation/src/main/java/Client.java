/**
 * Represents a Client for the gym
 */
public class Client extends Entity {
    private boolean isSuspended;

    /**
     * Enrolls a new client into the gym.
     *
     * @param name name of the client
     * @param address address of the client
     * @param province province of the client
     * @param city city of the client
     * @param postalCode postal code of the client
     * @param comment comment on the client
     * @param email email of the client
     */
    public Client(String name, String address, String province, String city, String postalCode, String comment, String email) {
        super(name, address, province, city, postalCode, comment, email);

        this.isSuspended = false;
    }

    public boolean isSuspended() {
        return isSuspended;
    }
}
