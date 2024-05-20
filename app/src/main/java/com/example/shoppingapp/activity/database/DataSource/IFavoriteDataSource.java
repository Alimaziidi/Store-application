package com.example.shoppingapp.activity.database.DataSource;

import com.example.shoppingapp.activity.database.Model.Favorite;

public interface IFavoriteDataSource {

    boolean getListFavoriteItem();
    int isFavorite(int item_id);
    void InsertFavorite(Favorite...favorites);
    void DeleteFavorite(Favorite favorite);


}
