package ru.kravchenkoapps.retrofitsample2;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Alert {
    @SerializedName("sender_name")
    @Expose
    public String senderName;
    @SerializedName("event")
    @Expose
    public String event;
    @SerializedName("start")
    @Expose
    public Integer start;
    @SerializedName("end")
    @Expose
    public Integer end;
    @SerializedName("description")
    @Expose
    public String description;

}
