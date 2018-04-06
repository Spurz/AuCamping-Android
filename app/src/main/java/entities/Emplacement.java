package entities;

/**
 * Created by student on 04/04/2018.
 */

public class Emplacement {

    private int id;
    private String name;
    private String imageURL;
    private String description_empla;
    private double lat;
    private double lng;


    public Emplacement(int id, String name, String imageURL, String description_empla, double lat, double lng) {
        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
        this.description_empla = description_empla;
        this.lat = lat;
        this.lng = lng;
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDescription_empla() {
        return description_empla;
    }

    public void setDescription_empla(String description_empla) {
        this.description_empla = description_empla;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
