import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * All gym entries seem to have a timestamp and a comment field
 */
public abstract class GymClass implements Serializable {
    private String comment;
    private Timestamp creationStamp;

    public GymClass(String comment) {
        this.comment = comment;
        creationStamp = new Timestamp(System.currentTimeMillis());
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getCreationStamp() {
        return creationStamp;
    }

    @Override
    public String toString() {
        return "GymClass{" +
                "comment='" + comment + '\'' +
                ", creationStamp=" + creationStamp +
                '}';
    }
}
