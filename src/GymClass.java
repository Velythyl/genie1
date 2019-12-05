import java.io.Serializable;

/**
 * All gym entries seem to have a timestamp and a comment field
 */
public abstract class GymClass implements Serializable {
    private String comment;
    private Stamp creationStamp;

    public GymClass(String comment) {
        this.comment = comment;
        creationStamp = new Stamp();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Stamp getCreationStamp() {
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
