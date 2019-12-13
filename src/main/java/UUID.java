import java.io.Serializable;

public class UUID implements Serializable {
    private String uuid;

    public UUID(int value, int maxUuidLength) {
        this(StringUtils.pad(value, maxUuidLength));
    }

    public UUID(String value) {
        this.uuid = value;
    }

    public String getUuid() { return uuid; }

    public boolean equals(UUID obj) {
        if(obj == null) return false;
        else return this.uuid.equals(obj.getUuid());
    }

    @Override
    public String toString() {
        return this.uuid;
    }
}
