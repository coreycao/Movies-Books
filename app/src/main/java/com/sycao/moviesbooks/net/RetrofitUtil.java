package com.sycao.moviesbooks.net;

import com.sycao.moviesbooks.utils.Constant;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sycao on 2017/8/15.
 * RetrofitUtil 工具类
 */

public class RetrofitUtil {

    private static RetrofitUtil instance = null;

    private Retrofit retrofit;
    private MovieService movieService;

    /**
     * 私有构造方法
     */
    private RetrofitUtil() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        movieService = retrofit.create(MovieService.class);

    }

    public static RetrofitUtil getInstance() {
        if (instance == null) {
            return new RetrofitUtil();
        } else {
            return instance;
        }
    }

    public MovieService getMovieService(){
        return this.movieService;
    }


}
