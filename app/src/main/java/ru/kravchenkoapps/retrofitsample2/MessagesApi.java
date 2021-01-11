package ru.kravchenkoapps.retrofitsample2;

import retrofit2.Call;

import java.sql.Array;
import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MessagesApi {
    @GET("onecall")//&exclude=hourly - указывая перед токеном мы иключаем тем самым это поле
    Call <Message2> getData(@Query("lat") Double latitude, @Query("lon") Double longitude,
                            @Query ("appid") String appid, @Query("lang") String language );// для STRING - Call <String> messages();
}
//@GET("onecall?lat={lat}&lon={lon}&appid=cdd2419e8e7fc4298657695d61878840&lang={lang}")