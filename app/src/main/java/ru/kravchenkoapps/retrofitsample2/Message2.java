package ru.kravchenkoapps.retrofitsample2;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message2 {

    @SerializedName("lat")
    @Expose
    public Double lat;
    @SerializedName("lon")
    @Expose
    public Double lon;
    @SerializedName("timezone")
    @Expose
    public String timezone;
    @SerializedName("timezone_offset")
    @Expose
    public Integer timezoneOffset;
    @SerializedName("current")
    @Expose
    public Current current;
    @SerializedName("minutely")
    @Expose
    public List<Minutely> minutely = null;
    @SerializedName("hourly")
    @Expose
    public List<Hourly> hourly = null;
    @SerializedName("daily")
    @Expose
    public List<Daily> daily ;
    @SerializedName("alerts")
    @Expose
    public List<Alert> alerts = null;

    public Message2(Double lat, Double lon, String timezone, Integer timezoneOffset, Current current, List<Minutely> minutely, List<Hourly> hourly, List<Daily> daily, List<Alert> alerts) {
        this.lat = lat;
        this.lon = lon;
        this.timezone = timezone;
        this.timezoneOffset = timezoneOffset;
        this.current = current;
        this.minutely = minutely;
        this.hourly = hourly;
        this.daily = daily;
        this.alerts = alerts;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public void setTimezoneOffset(Integer timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public void setMinutely(List<Minutely> minutely) {
        this.minutely = minutely;
    }

    public void setHourly(List<Hourly> hourly) {
        this.hourly = hourly;
    }

    public void setDaily(List<Daily> daily) {
        this.daily = daily;
    }

    public void setAlerts(List<Alert> alerts) {
        this.alerts = alerts;
    }

    //GETTERS


    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public String getTimezone() {
        return timezone;
    }

    public Integer getTimezoneOffset() {
        return timezoneOffset;
    }

    public Current getCurrent() {
        return current;
    }

    public List<Minutely> getMinutely() {
        return minutely;
    }

    public List<Hourly> getHourly() {
        return hourly;
    }

    public List<Daily> getDaily() {
        return daily;
    }

    public List<Alert> getAlerts() {
        return alerts;
    }
}
