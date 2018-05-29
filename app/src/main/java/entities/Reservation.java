package entities;

import java.io.Serializable;

/**
 * Created by student on 05/04/2018.
 */

public class Reservation implements Serializable {

    private int id;
    private String nameReserv;
    private String imageURLReserv;
    private String descriptionReserv;
    private int idMembre;

    public Reservation(int id, String nameReserv, String imageURLReserv, String descriptionReserv, int idMembre) {
        this.id = id;
        this.nameReserv = nameReserv;
        this.imageURLReserv = imageURLReserv;
        this.descriptionReserv = descriptionReserv;
        this.idMembre = idMembre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameReserv() {
        return nameReserv;
    }

    public void setNameReserv(String nameReserv) {
        this.nameReserv = nameReserv;
    }

    public String getImageURLReserv() {
        return imageURLReserv;
    }

    public void setImageURLReserv(String imageURLReserv) {
        this.imageURLReserv = imageURLReserv;
    }

    public String getDescriptionReserv() {
        return descriptionReserv;
    }

    public void setDescriptionReserv(String descriptionReserv) {
        this.descriptionReserv = descriptionReserv;
    }

    public int getIdMembre() {
        return idMembre;
    }

    public void setIdMembre(int idMembre) {
        this.idMembre = idMembre;
    }
}
