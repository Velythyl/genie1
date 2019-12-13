import java.io.Serializable;

/**
 * UUID is a representation of a unique ID. Helpful for our reflection strategy: having a UUID type allows us to use it
 * directly in meta_marshallType
 */
public class UUID implements Serializable {
    private String uuid;

    /**
     * Builds an UUID from an int, padding the int with leading 0s until it fits the required size
     *
     * @param value the value of the UUID as an int
     * @param maxUuidLength the length of the UUID
     */
    public UUID(int value, int maxUuidLength) {
        this(StringUtils.pad(value, maxUuidLength));
    }

    /**
     * Creates an UUID from a String.
     *
     * We trust the string is the right size, and do no check it!!!
     *
     * @param value the value of the UUID
     */
    public UUID(String value) {
        this.uuid = value;
    }

    public String getUuid() { return uuid; }

    /**
     * Compares two UUIDs for equality
     *
     * Comparing two UUIDs is the same as comparing their string representations.
     * @param obj the other UUID to compare to
     * @return wether or not they are equal
     */
    public boolean equals(UUID obj) {
        if(obj == null) return false;
        else return this.uuid.equals(obj.getUuid());
    }

    @Override
    public String toString() {
        return this.uuid;
    }
}
