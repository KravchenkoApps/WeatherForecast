package ru.kravchenkoapps.retrofitsample2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Minutely {
    @SerializedName("dt")
    @Expose
    public Integer dt;
    @SerializedName("precipitation")
    @Expose
    public Double precipitation;

}
