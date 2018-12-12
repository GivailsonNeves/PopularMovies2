package br.com.givailson.popularmovies.retrofit;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import br.com.givailson.popularmovies.MainActivity;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Properties;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import br.com.givailson.popularmovies.R;

public class RetrofitMovieDB {

    private static String BASE_MOVIE_API_PATH = "";
    private static String APP_KEY = "";
    private Retrofit retrofit;

    private OkHttpClient okHttpClient = new OkHttpClient()
            .newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {

                    Request original = chain.request();
                    HttpUrl originalHttpUrl = original.url();

                    HttpUrl url = originalHttpUrl.newBuilder()
                            .addQueryParameter("api_key", APP_KEY)
                            .build();

                    Request request = original.newBuilder()
                            .url(url)
                            .build();

                    return chain.proceed(request);
                }
            })
            .build();

    private void prepareRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_MOVIE_API_PATH)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public RetrofitMovieDB(Context context) {
        Resources resources = context.getResources();

        try {
            InputStream rawResource = resources.openRawResource(R.raw.config);
            Properties properties = new Properties();
            properties.load(rawResource);
            BASE_MOVIE_API_PATH = properties.getProperty("movie_api_url");
            APP_KEY = properties.getProperty("movie_api_key");
            prepareRetrofit();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MovieApiService movieApiService() {
        return retrofit.create(MovieApiService.class);
    }

}
