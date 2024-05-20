package com.example.shoppingapp.activity.database.DataSource;

import com.example.shoppingapp.activity.database.Model.Favorite;

public class FavoriteRepository implements IFavoriteDataSource {

    private IFavoriteDataSource iFavoriteDataSource ;

    private static FavoriteRepository instance;

    public FavoriteRepository(IFavoriteDataSource iFavoriteDataSource) {
        this.iFavoriteDataSource = iFavoriteDataSource;
    }

    public static FavoriteRepository getInstance(IFavoriteDataSource iFavoriteDataSource){

        if (instance == null){
            instance = new FavoriteRepository(iFavoriteDataSource);

        }
        return instance;
    }


    @Override
    public boolean getListFavoriteItem() {
        return iFavoriteDataSource.getListFavoriteItem();
    }

    @Override
    public int isFavorite(int item_id) {
        return iFavoriteDataSource.isFavorite(item_id);
    }

    @Override
    public void InsertFavorite(Favorite... favorites) {
        iFavoriteDataSource.InsertFavorite(favorites);
    }

    @Override
    public void DeleteFavorite(Favorite favorite) {

        iFavoriteDataSource.DeleteFavorite(favorite);

    }
}
