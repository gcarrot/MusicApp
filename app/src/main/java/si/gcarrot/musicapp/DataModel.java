package si.gcarrot.musicapp;

/**
 * Created by Urban on 5/5/17.
 */

public class DataModel {

    String Title;
    String Autor;
    long Duration;
    long ID;

    public DataModel(String title, String autor, long duration, long id) {
        this.Title = title;
        this.Autor = autor;
        this.Duration = duration;
        this.ID = id;

    }

    public String getTitle() {
        return Title;
    }

    public String getAutor() {
        return Autor;
    }

    public long getDuration() {
        return Duration;
    }

    public long getID() {
        return ID;
    }

}