package com.example.shoppingapp.activity.database.Local;


import android.content.Context;

import com.example.shoppingapp.activity.database.Model.Favorite;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Favorite.class} , version = 1)
public abstract class RoomDataBaseApp extends RoomDatabase {

    public abstract FavoriteDao favoriteDao();
    public static   RoomDataBaseApp instance;

    public static RoomDataBaseApp getInstance(Context context){

        if (instance==null){

            instance = Room.databaseBuilder(context , RoomDataBaseApp.class , "Shopping Database")
                    .allowMainThreadQueries()
                    .build();

        }

        return instance;

    }


}
