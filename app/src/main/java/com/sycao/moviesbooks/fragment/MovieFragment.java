package com.sycao.moviesbooks.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sycao.moviesbooks.R;
import com.sycao.moviesbooks.activity.MovieDetailActivity;
import com.sycao.moviesbooks.adapter.MovieListAdapter;
import com.sycao.moviesbooks.adapter.OnRecyclerItemClickListener;
import com.sycao.moviesbooks.model.MovieEntity;
import com.sycao.moviesbooks.net.RetrofitUtil;
import com.sycao.moviesbooks.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sycao on 2017/5/19.
 * 豆瓣电影top250列表页
 */

public class MovieFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private final int pageSize = 10;
    private int page = 0;


    @BindView(R.id.rv_movie)
    RecyclerView recyclerView;

    @BindView(R.id.pb_movie)
    ProgressBar progressBar;

    Disposable disposable;

    MovieListAdapter movieListAdapter;

    List<MovieEntity.SubjectsBean> subjectsBeanList;

    public static MovieFragment newInstance(int page) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        MovieFragment fragment = new MovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        movieListAdapter = new MovieListAdapter(getContext());
        recyclerView.setAdapter(movieListAdapter);
        recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                if (vh.getAdapterPosition() == (subjectsBeanList.size() - 1)) {
                    loadMoreData();
                } else {
                    Toast.makeText(getContext(), String.valueOf(vh.getAdapterPosition()), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                    intent.putExtra(Constant.ParamKey.MOVIE_ID, subjectsBeanList.get(vh.getAdapterPosition()).getId());
                    startActivity(intent);
                }
            }
        });



        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("onScrollStateChanged", String.valueOf(newState));
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                Log.d("onScrolled",String.valueOf(dy));
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
/*                Log.d("onScrolled-last",String.valueOf(linearLayoutManager.findLastVisibleItemPosition()));
                Log.d("onScrolled-complete",String.valueOf(linearLayoutManager.findLastCompletelyVisibleItemPosition()));*/
                if (dy >= 0 && linearLayoutManager.findLastVisibleItemPosition()==(subjectsBeanList.size()-1)){
                    // TODO: 滚动到底部后会触发多次 待解决
                    loadMoreData();
                    Toast.makeText(getContext(),"loading...",Toast.LENGTH_LONG).show();
                }
            }
        });


        disposable = RetrofitUtil.getInstance().getMovieService()
                .getMoviesTop250(page * pageSize, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MovieEntity>() {
                    @Override
                    protected void onStart() {
                        Log.d("disposable:", "onStart");
                    }

                    @Override
                    public void onNext(@NonNull MovieEntity movieEntity) {
                        Log.d("disposable:", "onNext");
                        subjectsBeanList = movieEntity.getSubjects();
                        movieListAdapter.setData(subjectsBeanList);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("disposable:", "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("disposable:", "onComplete");
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                });

        return view;
    }

    private void loadMoreData() {
        this.page++;
        disposable = RetrofitUtil.getInstance().getMovieService()
                .getMoviesTop250(page * pageSize, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MovieEntity>() {
                    @Override
                    public void onNext(@NonNull MovieEntity movieEntity) {
                        subjectsBeanList.addAll(movieEntity.getSubjects());
//                        movieListAdapter.setData(subjectsBeanList);
                        movieListAdapter.loadMoreData(movieEntity.getSubjects());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
