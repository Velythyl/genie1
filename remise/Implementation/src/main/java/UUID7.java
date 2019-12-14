/**
 * Helper class for reflection: simply a UUID of fixed size 7
 */
public class UUID7 extends UUID {
    /**
     * Creates a new UUID
     *
     * @param value value of the UUID as an int
     */
    public UUID7(int value) {
        super(value, 7);
    }
}
