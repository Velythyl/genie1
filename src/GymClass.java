import java.sql.Timestamp;

/**
 * All gym entries seem to have a timestamp and a comment field
 */
public abstract class GymClass {
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

}
