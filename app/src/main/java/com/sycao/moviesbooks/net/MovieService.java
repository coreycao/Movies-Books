package com.sycao.moviesbooks.net;

import com.sycao.moviesbooks.model.MovieEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by sycao on 2017/8/15.
 * 豆瓣电影api
 */

public interface MovieService {
    /**
     * 获取豆瓣top250榜单电影列表
     * @param start 数据起始位置
     * @param count 数据量
     * @return Observable<MovieEntity>
     */
    @GET("movie/top250")
    Observable<MovieEntity> getMoviesTop250(@Query("start") int start, @Query("count") int count);

    /**
     * 根据电影ID获取电影详细信息
     * @param id 电影ID
     */
    @GET("movie/subject/{id}")
    void getMovieSubjectById(@Path("id") String id);

    /**
     * 根据影人ID获取该影人详细信息
     * @param id 影人ID
     */
    @GET("movie/celebrity/{id}")
    void getCelebrityById(@Path("id") String id);

    /**
     * 搜索电影条目
     * @param start 起始值
     * @param count 数量
     * @param searchText 检索文本
     */
    @GET("movie/search")
    void searchMovie(@Query("start") int start, @Query("count") int count,@Query("q") String searchText);

    /**
     * 根据标签获取电影条目
     * @param start 起始值
     * @param count 数量
     * @param tag 电影标签
     */
    @GET("movie/search")
    void searchMovieByTag(@Query("start") int start, @Query("count") int count,@Query("tag") String tag);

    /**
     * 获取北美票房榜信息
     */
    @GET("movie/us_box")
    void getUSBox();
}
