package br.com.givailson.popularmovies.retrofit;

import br.com.givailson.popularmovies.model.MovieApiResult;
import br.com.givailson.popularmovies.model.ReviewResult;
import br.com.givailson.popularmovies.model.VideoResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieApiService {

    @GET("3/movie/popular")
    Call<MovieApiResult> listPopular();

    @GET("3/movie/top_rated")
    Call<MovieApiResult> listTopRating();

    @GET("3/movie/{id}/reviews")
    Call<ReviewResult> listReviews(@Path("id") int videoId);

    @GET("3/movie/{id}/videos")
    Call<VideoResult> listTrailers(@Path("id") int videoId);

}
