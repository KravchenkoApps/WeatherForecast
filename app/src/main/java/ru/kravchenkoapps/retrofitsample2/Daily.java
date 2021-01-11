package ru.kravchenkoapps.retrofitsample2;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Daily {

    @SerializedName("dt")
    @Expose
    public Integer dt;
    @SerializedName("sunrise")
    @Expose
    public Integer sunrise;
    @SerializedName("sunset")
    @Expose
    public Integer sunset;
    @SerializedName("temp")
    @Expose
    public Temp temp;
    @SerializedName("feels_like")
    @Expose
    public FeelsLike feelsLike;
    @SerializedName("pressure")
    @Expose
    public Integer pressure;
    @SerializedName("humidity")
    @Expose
    public Integer humidity;
    @SerializedName("dew_point")
    @Expose
    public Double dewPoint;
    @SerializedName("wind_speed")
    @Expose
    public Double windSpeed;
    @SerializedName("wind_deg")
    @Expose
    public Integer windDeg;
    @SerializedName("weather")
    @Expose
    public List<Weather__> weather ; // =null
    @SerializedName("clouds")
    @Expose
    public Integer clouds;
    @SerializedName("pop")
    @Expose
    public Double pop;
    @SerializedName("uvi")
    @Expose
    public Double uvi;

    //SETTERS

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public void setSunrise(Integer sunrise) {
        this.sunrise = sunrise;
    }

    public void setSunset(Integer sunset) {
        this.sunset = sunset;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    public void setFeelsLike(FeelsLike feelsLike) {
        this.feelsLike = feelsLike;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public void setDewPoint(Double dewPoint) {
        this.dewPoint = dewPoint;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setWindDeg(Integer windDeg) {
        this.windDeg = windDeg;
    }

    public void setWeather(List<Weather__> weather) {
        this.weather = weather;
    }

    public void setClouds(Integer clouds) {
        this.clouds = clouds;
    }

    public void setPop(Double pop) {
        this.pop = pop;
    }

    public void setUvi(Double uvi) {
        this.uvi = uvi;
    }

    //GETTERS

    public Integer getDt() {
        return dt;
    }

    public Integer getSunrise() {
        return sunrise;
    }

    public Integer getSunset() {
        return sunset;
    }

    public Temp getTemp() {
        return temp;
    }

    public FeelsLike getFeelsLike() {
        return feelsLike;
    }

    public Integer getPressure() {
        return pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public Double getDewPoint() {
        return dewPoint;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public Integer getWindDeg() {
        return windDeg;
    }

    public List<Weather__> getWeather() {
        return weather;
    }

    public Integer getClouds() {
        return clouds;
    }

    public Double getPop() {
        return pop;
    }

    public Double getUvi() {
        return uvi;
    }
}
