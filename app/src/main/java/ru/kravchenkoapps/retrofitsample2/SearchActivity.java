package ru.kravchenkoapps.retrofitsample2;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private String query;
    private DatabaseReference mDataBase;
    private List<CityFirebase> listCityFB;

    private RecyclerView rvCities;
    SearchRecyclerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mDataBase = FirebaseDatabase.getInstance().getReference();

        //RV
        listCityFB = new ArrayList<>();
        rvCities = findViewById(R.id.rvCities);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvCities.setLayoutManager(layoutManager);
        adapter = new SearchRecyclerAdapter(listCityFB);
        rvCities.setAdapter(adapter);

        //ПОИСК
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {

            Log.w("MY","tvHeader.setText(\"Включили поиск\"); ");
            query = intent.getStringExtra(SearchManager.QUERY);
            Log.w("MY","tvHeader.setText(\"Включили поиск\"); " + query);

        }

        setOnClickItem();

    }//конец onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_main, menu);//отображаем поиск (лупа)

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(onQueryTextListener);

        return true; //super.onCreateOptionsMenu(menu);
    }


    public android.widget.SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            firebaseCall(newText);
            Log.w("MY","onQueryTextChange " + newText);
            return true;
        }

    };

    //Отправка запроса в Firebase и получение результата
    public void firebaseCall(String nameCity) {

        Query query_search = mDataBase.orderByChild("city").startAt(nameCity);
        listCityFB.clear();
        query_search.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot data : snapshot.getChildren()) {
                    CityFirebase cityFirebase = data.getValue(CityFirebase.class);
                    listCityFB.add(cityFirebase);


                    Double lat = cityFirebase.getLat();
                    Double lon = cityFirebase.getLon();

                    Log.w("MY","Double lat = cityFirebase.getLat();" + lat);
                    //forecastCall(lat, lon);
                }
                rvCities.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //выбор населенного пункта
    private void setOnClickItem() {
        rvCities.addOnItemTouchListener(new RecyclerItemClickListener(this, rvCities, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            CityFirebase cityFirebase = listCityFB.get(position);//по индексу (номеру) нажатой позиции получаем все данные
            Intent i = new Intent(SearchActivity.this, MainActivity.class);
            i.putExtra(Constant.CITY_NAME, cityFirebase.city);
            Log.w("MY", "i.putExtra(Constant.CITY_NAME, cityFirebase.city); = " + cityFirebase.city);
            i.putExtra(Constant.CITY_LAT, cityFirebase.lat);
                Log.w("MY", "CITY_LAT = " + cityFirebase.lat);
            i.putExtra(Constant.CITY_LON, cityFirebase.lon);
                Log.w("MY", "CITY_LON = " + cityFirebase.lon);
            startActivity(i);//перемещаемся на главный экран
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
    }

}
