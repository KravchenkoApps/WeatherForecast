package ru.kravchenkoapps.retrofitsample2;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder>{

    private List<CityFirebase> listCities;

    public SearchRecyclerAdapter(List<CityFirebase> listCities) {
        this.listCities = listCities;
    }

    @NonNull
    @Override
    public SearchRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_search, parent, false);
        return new SearchRecyclerAdapter.ViewHolder(v);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerAdapter.ViewHolder holder, int position) {

        CityFirebase cityFirebase = listCities.get(position);
        //получение ДАТЫ
        //long currentUnixTime = oneDay.dt;//получаем время в формате UNIX
        //Date date = new Date(currentUnixTime*1000L); // *1000 получаем миллисекунды
        //@SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM");
        //String formattedDate = sdf.format(date);

        //DecimalFormat dF = new DecimalFormat( "#.#" );//определяем формат выводимых данных для температуры, ветра

        //установка значений в полях
        holder.tvCitySearch.setText(cityFirebase.city);
        holder.tvDistSearch.setText(cityFirebase.district);

    }

    @Override
    public int getItemCount() {

        if (listCities == null)
            return 0;
        return listCities.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCitySearch;
        TextView tvDistSearch;


        public ViewHolder(View itemView) {

            super(itemView);
            tvCitySearch = (TextView) itemView.findViewById(R.id.tvCitySearch);
            tvDistSearch = (TextView) itemView.findViewById(R.id.tvDistSearch);

        }
    }

    public void clear() {

        final int size = listCities.size();
        listCities.clear();
        notifyItemRangeRemoved(0, size);

    }

}
