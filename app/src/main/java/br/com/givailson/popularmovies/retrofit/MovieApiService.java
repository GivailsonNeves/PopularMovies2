package br.com.givailson.popularmovies.retrofit;

import br.com.givailson.popularmovies.model.MovieApiResult;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieApiService {

    @GET("3/movie/popular")
    Call<MovieApiResult> listPopular();

    @GET("3/movie/top_rated")
    Call<MovieApiResult> listTopRating();
}
