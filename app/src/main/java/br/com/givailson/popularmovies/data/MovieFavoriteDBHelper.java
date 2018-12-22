package br.com.givailson.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieFavoriteDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;

    public MovieFavoriteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_FAVORITE_TABLE = "CREATE TABLE " + MovieFavoriteContract.MovieFavoriteEntry.TABLE_NAME +
                "(" + MovieFavoriteContract.MovieFavoriteEntry.ID + " INTEGER PRIMARY KEY, "+
                MovieFavoriteContract.MovieFavoriteEntry.TITLE + " TEXT NOT NULL, " +
                MovieFavoriteContract.MovieFavoriteEntry.URI_PHOTO + " TEXT NOT NULL, " +
                MovieFavoriteContract.MovieFavoriteEntry.OVERVIEW + " TEXT NOT NULL, " +
                MovieFavoriteContract.MovieFavoriteEntry.RATE + " REAL NOT NULL, " +
                MovieFavoriteContract.MovieFavoriteEntry.RELEASE_DATE + " TEXT NOT NULL )";
        db.execSQL(SQL_CREATE_MOVIE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop the table
        db.execSQL("DROP TABLE IF EXISTS " + MovieFavoriteContract.MovieFavoriteEntry.TABLE_NAME);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"+MovieFavoriteContract.MovieFavoriteEntry.TABLE_NAME+"'");

        // re-create database
        onCreate(db);

    }
}
