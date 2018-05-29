package entities;

/**
 * Created by student on 05/04/2018.
 */

public class Activite {

    int id, activeReserv;
    String name, type, date;

    public Activite(int id, String name, String type, String date, int activeReserv) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.date = date;
        this.activeReserv = activeReserv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getActiveReserv() {
        return activeReserv;
    }

    public void setActiveReserv(int activeReserv) {
        this.activeReserv = activeReserv;
    }
}
