package com.example.shoppingapp.activity.database.Local;


import com.example.shoppingapp.activity.database.Model.Favorite;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface FavoriteDao {

    @Query("Select * From Favorite WHERE add_to_favorite = 1 ")
    boolean getListFavoriteItem();

    @Query("Select Exists (Select 1 From Favorite Where id=:item_id)")
    int isFavorite(int item_id);

    @Insert
    void InsertFavorite(Favorite...favorites);

    @Delete
    void DeleteFavorite(Favorite favorite);


}
