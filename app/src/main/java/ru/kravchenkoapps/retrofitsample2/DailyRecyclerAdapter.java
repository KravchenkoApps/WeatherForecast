package ru.kravchenkoapps.retrofitsample2;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DailyRecyclerAdapter extends RecyclerView.Adapter<DailyRecyclerAdapter.ViewHolder> {

    private List<Daily> listDaily;

    public DailyRecyclerAdapter(List<Daily> listDaily) {
        this.listDaily = listDaily;
    }

    @NonNull
    @Override
    public DailyRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily, parent, false);
        return new ViewHolder(v);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Daily oneDay = listDaily.get(position);
        //получение ДАТЫ
        long currentUnixTime = oneDay.dt;//получаем время в формате UNIX
        Date date = new Date(currentUnixTime*1000L); // *1000 получаем миллисекунды
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM");
        String formattedDate = sdf.format(date);

        DecimalFormat dF = new DecimalFormat( "#.#" );//определяем формат выводимых данных для температуры, ветра

        //установка значений в полях
        holder.tvDailyDate.setText(formattedDate);
        holder.tvDailyTempDay.setText(dF.format(getDegreeCelcium(oneDay.temp.day)) + " °C / ");
        holder.tvDailyTempNight.setText(dF.format(getDegreeCelcium(oneDay.temp.night)) + " °C");
        holder.tvDailyWind.setText(dF.format(oneDay.windSpeed) +" М/С");
        holder.tvDailyClouds.setImageResource(R.drawable.thunderstorm_2xx);

        //oneDay.weather.get(0).id

    }

    @Override
    public int getItemCount() {

        if (listDaily == null)
            return 0;
        return listDaily.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvDailyDate;
        TextView tvDailyTempDay;
        TextView tvDailyTempNight;
        TextView tvDailyWind;
        ImageView tvDailyClouds;

        public ViewHolder(View itemView) {

            super(itemView);
            tvDailyDate = (TextView) itemView.findViewById(R.id.tvDailyDate);
            tvDailyTempDay = (TextView) itemView.findViewById(R.id.tvDailyTempDay);
            tvDailyTempNight = (TextView) itemView.findViewById(R.id.tvDailyTempNight);
            tvDailyWind = (TextView) itemView.findViewById(R.id.tvDailyWind);
            tvDailyClouds = (ImageView) itemView.findViewById(R.id.tvDailyClouds);

        }
    }

    //переводим Кельвины в Цельсии
    public Double getDegreeCelcium(Double i) {

        return i - 273.15;

    }

    public void clear() {

        final int size = listDaily.size();
        listDaily.clear();
        notifyItemRangeRemoved(0, size);

    }
}
