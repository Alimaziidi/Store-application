package com.example.shoppingapp.activity;

import android.os.Bundle;

import com.example.shoppingapp.R;
import com.example.shoppingapp.activity.adapter.FavoriteAdapter;
import com.example.shoppingapp.activity.database.DataSource.FavoriteRepository;
import com.example.shoppingapp.activity.database.DataSource.IFavoriteDataSource;
import com.example.shoppingapp.activity.database.Local.FavoriteDataSource;
import com.example.shoppingapp.activity.database.Local.RoomDataBaseApp;
import com.example.shoppingapp.activity.database.Model.Favorite;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FavoriteActivity extends AppCompatActivity {


    CompositeDisposable compositeDisposable;
    ///List<Favorite> listFavorite = new ArrayList<>();
    FavoriteAdapter adapter;
    RecyclerView recyclerViewFavorite;
    static RoomDataBaseApp roomDatabaseApp;
    static FavoriteRepository favoriteRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        compositeDisposable = new CompositeDisposable();

        initDatabaseRoom();

        recyclerViewFavorite = findViewById(R.id.recyclerView);
        recyclerViewFavorite.setHasFixedSize(true);
        recyclerViewFavorite.setLayoutManager(new LinearLayoutManager(this));



      /*  compositeDisposable.add(favoriteRepository.getListFavoriteItem()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Consumer<List<Favorite>>() {
            @Override
            public void accept(List<Favorite> favorites) throws Exception {

                adapter = new FavoriteAdapter(FavoriteActivity.this,favorites);
                recyclerViewFavorite.setAdapter(adapter);

            }
        }));*/

    }
    private void initDatabaseRoom() {

        roomDatabaseApp = RoomDataBaseApp.getInstance(this);
        favoriteRepository = FavoriteRepository.getInstance((IFavoriteDataSource) FavoriteDataSource.getInstance(roomDatabaseApp.favoriteDao()));



    }

}