package Products;

import java.io.Serializable;
import java.time.LocalDate;

public class Episode implements Serializable {
    private String Title;
    private LocalDate Release_Date;
    private int Length;

    public Episode(String title, LocalDate release_Date, int length) {
        Title = title;
        Release_Date = release_Date;
        Length = length;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public LocalDate getRelease_Date() {
        return Release_Date;
    }

    public void setRelease_Date(LocalDate release_Date) {
        Release_Date = release_Date;
    }

    public int getLength() {
        return Length;
    }

    public void setLength(int length) {
        Length = length;
    }
}
