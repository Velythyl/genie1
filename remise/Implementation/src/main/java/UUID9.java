/**
 * Helper class for reflection: simply a UUID of fixed size 9
 */
public class UUID9 extends UUID {
    /**
     * Creates a new UUID
     *
     * @param value value of the UUID as an int
     */
    public UUID9(int value) {
        super(value, 9);
    }
}
