package com.sycao.moviesbooks.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sycao.moviesbooks.R;
import com.sycao.moviesbooks.model.MovieEntity;
import com.sycao.moviesbooks.net.RetrofitUtil;
import com.sycao.moviesbooks.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sycao on 2017/8/17.
 * 电影详情页面
 */

public class MovieDetailActivity extends AppCompatActivity {

    String id;
    Disposable disposable;

    @BindView(R.id.tv_movie_detail_title)
    TextView tvMovieTitle;

    @BindView(R.id.iv_movie_detail_post)
    ImageView ivMoviePost;

    @BindView(R.id.bg_movie_detail_post)
    FrameLayout bgMoviePost;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);

        id = getIntent().getExtras().getString(Constant.ParamKey.MOVIE_ID);

        createObservable();

    }

    void createObservable() {
        disposable = RetrofitUtil.getInstance().getMovieService().getMovieSubjectById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MovieEntity.SubjectsBean>() {

                    @Override
                    protected void onStart() {
                        progressDialog.show();
                    }

                    @Override
                    public void onNext(@NonNull MovieEntity.SubjectsBean subjectsBean) {
                        progressDialog.dismiss();
                        tvMovieTitle.setText(subjectsBean.getTitle());
                        Picasso.with(getApplicationContext()).load(subjectsBean.getImages().getLarge()).into(ivMoviePost);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        progressDialog.dismiss();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
