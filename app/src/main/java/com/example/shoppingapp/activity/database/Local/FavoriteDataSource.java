package com.example.shoppingapp.activity.database.Local;

import com.example.shoppingapp.activity.database.DataSource.IFavoriteDataSource;
import com.example.shoppingapp.activity.database.Model.Favorite;

public class FavoriteDataSource implements IFavoriteDataSource {

    private FavoriteDao favoriteDao;
    private static FavoriteDataSource instance;

    public FavoriteDataSource(FavoriteDao favoriteDao) {
        this.favoriteDao = favoriteDao;
    }

    public static FavoriteDataSource getInstance(FavoriteDao favoriteDao){

        if (instance==null){
            instance = new FavoriteDataSource(favoriteDao);

        }
        return instance;
    }


    @Override
    public boolean getListFavoriteItem() {
        return favoriteDao.getListFavoriteItem();
    }

    @Override
    public int isFavorite(int item_id) {
        return favoriteDao.isFavorite(item_id);
    }

    @Override
    public void InsertFavorite(Favorite... favorites) {
        favoriteDao.InsertFavorite(favorites);
    }

    @Override
    public void DeleteFavorite(Favorite favorite) {
        favoriteDao.DeleteFavorite(favorite);
    }
}
