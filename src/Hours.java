import java.io.Serializable;
import java.sql.Timestamp;

class Hours extends Stamp implements Serializable {
    Hours(String str) {
        super("2000-01-01 "+str+":00");
    }

    public String toString() {
        return super.toString().substring(11, 16);
    }
}
