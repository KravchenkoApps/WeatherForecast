package ru.kravchenkoapps.retrofitsample2;

public class CityFirebase {

    public String city, district, region;
    Double lat, lon;

    public CityFirebase() {
    }

    public CityFirebase(String city, String district, String region, Double lat, Double lon) {
        this.city = city;
        this.district = district;
        this.region = region;
        this.lat = lat;
        this.lon = lon;
    }

    //GETTERS

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getRegion() {
        return region;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    //SETTERS


    public void setCity(String city) {
        this.city = city;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
