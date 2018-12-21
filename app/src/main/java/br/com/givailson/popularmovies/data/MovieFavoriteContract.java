package br.com.givailson.popularmovies.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class MovieFavoriteContract {

    public static final String CONTENT_AUTHORITY = "br.com.givailson.popularmovies.app";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class MovieFavoriteEntry implements BaseColumns {

        public static final String ID =  "id";
        public static final String TITLE = "title";
        public static final String URI_PHOTO = "uri_photo";
        public static final String OVERVIEW = "overview";
        public static final String RATE = "rate";
        public static final String RELEASE_DATE = "release_date";


        public static final String TABLE_NAME = "movie_favorit";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_NAME).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        // create cursor of base type item for single entry
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE +"/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        public static Uri buildFlavorsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}
