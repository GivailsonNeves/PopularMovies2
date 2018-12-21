package br.com.givailson.popularmovies.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.database.Cursor;

@Dao
public interface MovieFavoriteDao {

    @Query("SELECT * FROM " + MovieFavoriteContract.MovieFavoriteEntry.TABLE_NAME)
    Cursor listAll();

    @Query("SELECT * FROM "+ MovieFavoriteContract.MovieFavoriteEntry.TABLE_NAME+" WHERE "+ MovieFavoriteContract.MovieFavoriteEntry.ID+" = :id")
    Cursor getById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(MovieFavorite movieFavorite);

    @Query("DELETE FROM "+ MovieFavoriteContract.MovieFavoriteEntry.TABLE_NAME + " WHERE id = :id")
    int deleteById(long id);
}
