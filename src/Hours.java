import java.io.Serializable;
import java.sql.Timestamp;

class Hours extends java.sql.Timestamp implements Serializable {
    Hours(String str) {
        super(Timestamp.valueOf("2000-01-01 "+str+":00").getTime());
    }
}
