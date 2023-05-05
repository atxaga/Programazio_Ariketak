package model;

import java.util.Date;

public class Fotos {

    private int pictureId;
    private String title;
    private Date date_;
    private String file_;
    private int visits;
    private int photographerId;

    public Fotos(int pictureId, String title, Date date_, String file_, int visits, int photographerId) {
        this.pictureId = pictureId;
        this.title = title;
        this.date_ = date_;
        this.file_ = file_;
        this.visits = visits;
        this.photographerId = photographerId;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate_() {
        return date_;
    }

    public void setDate_(Date date_) {
        this.date_ = date_;
    }

    public String getFile_() {
        return file_;
    }

    public void setFile_(String file_) {
        this.file_ = file_;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public int getPhotographerId() {
        return photographerId;
    }

    public void setPhotographerId(int photographerId) {
        this.photographerId = photographerId;
    }
}
