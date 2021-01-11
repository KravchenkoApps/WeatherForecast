package ru.kravchenkoapps.retrofitsample2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView tvFeelTemp, tvCity, tvDate, tvTemp, tvWind, tvHumidity, tvPressure, tvWindDir,
            tvDescription;
    private ImageView ivRain;
    private String cityName;
    private Double coordLat, coordLon;
    private EditText edLat, edLon;
    MessagesApi messagesApi;
    private Retrofit retrofit;
    private List<Daily> listDaily;
    RecyclerView rvDaily;
    DailyRecyclerAdapter adapter;

    SharedPreferences cityPref;
    boolean TAG_SET_CITY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        long time_long = Calendar.getInstance().getTimeInMillis();

        tvFeelTemp = findViewById(R.id.tvFeelTemp);
        tvDate = findViewById(R.id.tvDate);
        tvDate.setText(df.format(time_long));
        tvCity = findViewById(R.id.tvCity);
        tvTemp = findViewById(R.id.tvTemp);
        tvWind = findViewById(R.id.tvWindSpeed);
        tvWindDir = findViewById(R.id.tvWindDir);
        tvPressure = findViewById(R.id.tvPressure);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvDescription = findViewById(R.id.tvDescription);

        edLat = findViewById(R.id.edLat);
        edLon = findViewById(R.id.edLon);

        ivRain = findViewById(R.id.ivRain);

        listDaily = new ArrayList<>();
        rvDaily = findViewById(R.id.rvDaily);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvDaily.setLayoutManager(layoutManager);

        //здесь добавляем инициализацию адаптера и установку адаптера на rvDaily
        adapter = new DailyRecyclerAdapter(listDaily);
        rvDaily.setAdapter(adapter);
        rvDaily.setNestedScrollingEnabled(false); // выключаем прокрутку rv, чтобы в портретном режиме было более корректно

        //инициализируем SharedPreferences
        cityPref = getSharedPreferences(Constant.CITY_NAME, Context.MODE_PRIVATE);


        //RETROFIT

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())//Конвертер STRING ScalarsConverterFactory.create() , GSON - GsonConverterFactory.create()
                .build();

        messagesApi = retrofit.create(MessagesApi.class);

      //  forecastCall(45.55, 41.18);

        getNewCity();

        if (!TAG_SET_CITY && !cityPref.getString(Constant.CITY_NAME, "").equals("")) {
        // если у нас нет входящего интента, то значит мы устанавливаем город и его координаты из настроек

        forecastCall(Double.parseDouble(cityPref.getString(Constant.CITY_LAT, "")),
                Double.parseDouble(cityPref.getString(Constant.CITY_LON, "")),
                cityPref.getString(Constant.CITY_NAME, ""));
        }

    }//конец onCreate

    public void forecastCall (Double latitude, Double longitude, String cityName) {
        Call<Message2> messages = messagesApi.getData(latitude, longitude, "cdd2419e8e7fc4298657695d61878840","ru");
        adapter.clear();
        messages.enqueue(new Callback<Message2>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Message2> call, Response<Message2> response) {
                if (response.isSuccessful()) {
                    //Log.w("MY","ОТВЕТ =" + response.body().toString());
                    //ТЕКУЩАЯ ПОГОДА
                    //заполнение левой части макета ТЕКУЩАЯ ПОГОДА
                    tvCity.setText(cityName); // название города
                    assert response.body() != null;
                    setCurrentTime(response.body().current.dt); //дата и время (из ответа сервера)
                    setCurrentTemp(response.body().getCurrent().temp); // температура
                    Log.w("MY","temp = " + response.body().getCurrent().temp);
                    setFeelTemp(response.body().getCurrent().feelsLike); // ощущается температура
                    setWindSpeed(response.body().getCurrent().windSpeed); // скорость ветра
                    setWindDirection(response.body().getCurrent().windDeg); // направление ветра
                    tvHumidity.setText("Влажность " + response.body().getCurrent().humidity + "%"); // влажность
                    tvPressure.setText("Давление " + response.body().getCurrent().pressure + " hPa"); // давление

                    //заполнение правой части макета ТЕКУЩАЯ ПОГОДА
                    List<Weather> weather = response.body().getCurrent().weather;
                    setRainIcon(weather.get(0).id); //устанвка иконки погоды
                    setTvDescription(weather.get(0).description); //описание текущей погоды

                    //заполнение данных на неделю
                    listDaily.addAll(response.body().getDaily());//добавляем в список данные из Daily
                    rvDaily.getAdapter().notifyDataSetChanged();

                }
                else {
                    Log.w("MY","response code = " + response.code());// Чтобы получить полный текст ошибки сервера, используем - response.errorBody().string()

                }
            }

            @Override
            public void onFailure(Call<Message2> call, Throwable t) {
                Log.w("MY","onFailure " + t);
            }
        });
    }

    //кнопка ОБНОВИТЬ

    public void onClickReload (View view) {
        //forecastCall(Double.parseDouble(edLat.getText().toString()), Double.parseDouble(edLon.getText().toString()));
    }

    //меню в Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_item, menu);//отображаем поиск (лупа)

        return true; //super.onCreateOptionsMenu(menu);
    }


    @Override
    //обработчик кнопок в toolbar
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //пишем обработчик кнопки "Назад" (стрелочки) в actionBar
        if (item.getItemId() == R.id.button_search) //если кнопка назад = кнопке home
        //а эта кнопка в логику андроид именно так и вшита, то
        {
            Intent i = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(i);
        }
        return true;
    }

    //получаем выбранный населенный пункт (наименование, координаты)
    private void getNewCity() {

        //получаем интент
        Intent intent = getIntent();
        if (intent.getStringExtra(Constant.CITY_NAME) != null) { //если есть входящий интент, если нет, то берем город из SharedPreferences
            Log.w("MY","сработал if (intent != null)");
        cityName = intent.getStringExtra(Constant.CITY_NAME);
        coordLat = intent.getDoubleExtra(Constant.CITY_LAT, 0);
        coordLon = intent.getDoubleExtra(Constant.CITY_LON, 0);

        //сохраняем в настройках

        SharedPreferences.Editor editor = cityPref.edit();
        editor.putString(Constant.CITY_NAME, cityName);
        editor.putString(Constant.CITY_LAT, String.valueOf(coordLat));
        editor.putString(Constant.CITY_LON, String.valueOf(coordLon));
        editor.apply();
        //делаем запрос на обновление данных погоды и отображаем новые погодные данные
        forecastCall(coordLat, coordLon, cityName);
        TAG_SET_CITY = true;
        }

    }

    //установка иконки текущей погоды
    public void setRainIcon(int id) {
        switch (String.valueOf(id)) {
            //ГРОЗА - Thunderstorm
            case "200" :
                ivRain.setImageResource(R.drawable.thunderstorm_2xx);
                break;
            case "201" :
                ivRain.setImageResource(R.drawable.thunderstorm_2xx);
                break;
            case "202" :
                ivRain.setImageResource(R.drawable.thunderstorm_2xx);
                break;
            case "210" :
                ivRain.setImageResource(R.drawable.thunderstorm_2xx);
                break;
            case "211" :
                ivRain.setImageResource(R.drawable.thunderstorm_2xx);
                break;
            case "212" :
                ivRain.setImageResource(R.drawable.thunderstorm_2xx);
                break;
            case "221" :
                ivRain.setImageResource(R.drawable.thunderstorm_2xx);
                break;
            case "230" :
                ivRain.setImageResource(R.drawable.thunderstorm_2xx);
                break;
            case "231" :
                ivRain.setImageResource(R.drawable.thunderstorm_2xx);
                break;
            case "232" :
                ivRain.setImageResource(R.drawable.thunderstorm_2xx);
                break;
            //МОРОСЬ - Drizzle
            case "300" :
                ivRain.setImageResource(R.drawable.drizzle_3xx_1);
                break;
            case "301" :
                ivRain.setImageResource(R.drawable.drizzle_3xx_1);
                break;
            case "302" :
                ivRain.setImageResource(R.drawable.drizzle_3xx_2);
                break;
            case "310" :
                ivRain.setImageResource(R.drawable.drizzle_3xx_1);
                break;
            case "311" :
                ivRain.setImageResource(R.drawable.drizzle_3xx_1);
                break;
            case "312" :
                ivRain.setImageResource(R.drawable.drizzle_3xx_2);
                break;
            case "313" :
                ivRain.setImageResource(R.drawable.drizzle_3xx_2);
                break;
            case "314" :
                ivRain.setImageResource(R.drawable.drizzle_3xx_2);
                break;
            case "321" :
                ivRain.setImageResource(R.drawable.drizzle_3xx_2);
                break;
            // ДОЖДЬ - Rain
            case "500" :
                ivRain.setImageResource(R.drawable.drizzle_3xx_2);
                break;
            case "501" :
                ivRain.setImageResource(R.drawable.rain_5xx_1);
                break;
            case "502" :
                ivRain.setImageResource(R.drawable.rain_5xx_1);
                break;
            case "503" :
                ivRain.setImageResource(R.drawable.rain_5xx_2);
                break;
            case "504" :
                ivRain.setImageResource(R.drawable.rain_5xx_2);
                break;
            case "511" :
                ivRain.setImageResource(R.drawable.rain_5xx_3);
                break;
            case "520" :
                ivRain.setImageResource(R.drawable.rain_5xx_1);
                break;
            case "521" :
                ivRain.setImageResource(R.drawable.rain_5xx_1);
                break;
            case "522" :
                ivRain.setImageResource(R.drawable.rain_5xx_2);
                break;
            case "531" :
                ivRain.setImageResource(R.drawable.rain_5xx_2);
                break;
            // СНЕГ - Snow
            case "600" :
                ivRain.setImageResource(R.drawable.snow_6xx_1);
                break;
            case "601" :
                ivRain.setImageResource(R.drawable.snow_6xx_2);
                break;
            case "602" :
                ivRain.setImageResource(R.drawable.snow_6xx_3);
                break;
            case "611" :
                ivRain.setImageResource(R.drawable.snow_6xx_4);
                break;
            case "612" :
                ivRain.setImageResource(R.drawable.snow_6xx_4);
                break;
            case "613" :
                ivRain.setImageResource(R.drawable.snow_6xx_5);
                break;
            case "615" :
                ivRain.setImageResource(R.drawable.snow_6xx_4);
                break;
            case "616" :
                ivRain.setImageResource(R.drawable.snow_6xx_5);
                break;
            case "620" :
                ivRain.setImageResource(R.drawable.snow_6xx_6);
                break;
            case "621" :
                ivRain.setImageResource(R.drawable.snow_6xx_3);
                break;
            case "622" :
                ivRain.setImageResource(R.drawable.snow_6xx_3);
                break;

            // ТУМАН - Atmosphere
            case "701" :
                ivRain.setImageResource(R.drawable.atmosphere_7xx_1);
                break;
            case "711" :
                ivRain.setImageResource(R.drawable.atmosphere_7xx_1);
                break;
            case "721" :
                ivRain.setImageResource(R.drawable.atmosphere_7xx_1);
                break;
            case "731" :
                ivRain.setImageResource(R.drawable.atmosphere_7xx_1);
                break;
            case "741" :
                ivRain.setImageResource(R.drawable.atmosphere_7xx_1);
                break;
            case "751" :
                ivRain.setImageResource(R.drawable.atmosphere_7xx_1);
                break;
            case "761" :
                ivRain.setImageResource(R.drawable.atmosphere_7xx_1);
                break;
            case "762" :
                ivRain.setImageResource(R.drawable.atmosphere_7xx_1);
                break;
            case "771" :
                ivRain.setImageResource(R.drawable.atmosphere_7xx_1);
                break;
            case "781" :
                ivRain.setImageResource(R.drawable.atmosphere_7xx_2);
                break;

            //НЕБО - Clear
            case "800" :
                ivRain.setImageResource(R.drawable.clear_sky_day);
                break;

            //ОБЛАЧНСТЬ - Clouds
            case "801" :
                ivRain.setImageResource(R.drawable.clouds_801);
                break;
            case "802" :
                ivRain.setImageResource(R.drawable.clouds_802);
                break;
            case "803" :
                ivRain.setImageResource(R.drawable.clouds_803);
                break;
            case "804" :
                ivRain.setImageResource(R.drawable.clouds_804);
                break;

        }


    }//конец setRainIcon

    //устанвка текущей даты и времени (из ответа сервера)
    public void setCurrentTime(long currentUnixTime) {
        Date date = new Date(currentUnixTime*1000L); // *1000 получаем миллисекунды
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm, EEEE, dd MMMM"); // yyyy-MM-dd HH:mm:ss z - какой формат нужен, выбираем
        String formattedDate = sdf.format(date);
        tvDate.setText(formattedDate);
     }

     //устанвка текущей температуры
    @SuppressLint("SetTextI18n")
    public void setCurrentTemp(Double kelvinTemp) {
        DecimalFormat dF = new DecimalFormat( "#.#" );//определяем формат выводимых данных для температуры
        tvTemp.setText(dF.format(kelvinTemp - 273.15) + " °C");
    }

    //устанвка текущей температуры
    @SuppressLint("SetTextI18n")
    public void setFeelTemp(Double kelvinTemp) {
        DecimalFormat dF = new DecimalFormat( "#.#" );//определяем формат выводимых данных для температуры
        tvFeelTemp.setText("Ощущается " + dF.format(kelvinTemp - 273.15) + " °C");
    }

    //установка скорости ветра
    @SuppressLint("SetTextI18n")
    public void setWindSpeed(Double windSpeed) {
        DecimalFormat dF = new DecimalFormat( "#.#" );//определяем формат выводимых данных для  ветра
        tvWind.setText("Ветер " + dF.format(windSpeed) + " М/С,");
    }

    //установка направления ветра
    public void setWindDirection(int windDir) {
        String direction;
        if (windDir >= 22 && windDir <= 67) {
            direction = getString(R.string.direction_sv);
        }
        else if (windDir > 67 && windDir < 112) {
            direction = getString(R.string.direction_v);
        }
        else if (windDir >= 112 && windDir <= 157) {
            direction = getString(R.string.direction_yv);
        }
        else if (windDir > 157 && windDir < 202) {
            direction = getString(R.string.direction_y);
        }
        else if (windDir >= 202 && windDir <= 247) {
            direction = getString(R.string.direction_yz);
        }
        else if (windDir > 247 && windDir < 292) {
            direction = getString(R.string.direction_z);
        }
        else if (windDir >= 292 && windDir <= 337) {
            direction = getString(R.string.direction_sz);
        }
        else  {
            direction = getString(R.string.direction_s);
        }

        tvWindDir.setText(direction);
    }

    //
    public  void setTvDescription(String description) {
        tvDescription.setText(description);
    }

}