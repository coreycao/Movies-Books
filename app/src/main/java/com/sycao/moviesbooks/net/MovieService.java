package com.sycao.moviesbooks.net;

import com.sycao.moviesbooks.model.MovieEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by sycao on 2017/8/15.
 */

public interface MovieService {
    @GET("movie/top250")
    Observable<MovieEntity> getMoviesTop250(@Query("start") int start, @Query("count") int count);
}
