package com.sycao.moviesbooks.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sycao.moviesbooks.R;
import com.sycao.moviesbooks.model.MovieEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sycao on 2017/8/15.
 * 电影列表adapter
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    private final Context context;
    private final List<MovieEntity.SubjectsBean> data = new ArrayList<>();

    public MovieListAdapter(Context context) {
        this.context = context;
//        this.data = movieList;
    }

    public void setData(List<MovieEntity.SubjectsBean> movieList) {
        data.clear();
        data.addAll(movieList);
        notifyDataSetChanged();
    }

    public void loadMoreData(List<MovieEntity.SubjectsBean> movieList) {
        data.addAll(movieList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvMovieTitle.setText("TOP"+String.valueOf(position+1)+"："+data.get(position).getTitle());
        StringBuilder sb = new StringBuilder();
        for (MovieEntity.SubjectsBean.CastsBean cast : data.get(position).getCasts()) {
            if (data.get(position).getCasts().indexOf(cast) == 0) {
                sb.append(cast.getName());
            } else {
                sb.append("/" + cast.getName());
            }
        }
        holder.tvCasts.setText("主演：" + sb.toString());


        sb.delete(0, sb.length());
        for (MovieEntity.SubjectsBean.DirectorsBean director : data.get(position).getDirectors()) {
            if (data.get(position).getDirectors().indexOf(director) == 0) {
                sb.append(director.getName());
            } else {
                sb.append("/" + director.getName());
            }
        }

        holder.tvDirector.setText("导演：" + sb.toString());
        Picasso.with(context).load(data.get(position).getImages().getLarge()).into(holder.ivMoviePost);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_movie_item_post)
        ImageView ivMoviePost;

        @BindView(R.id.tv_movie_item_title)
        TextView tvMovieTitle;

        @BindView(R.id.tv_movie_item_cast)
        TextView tvCasts;

        @BindView(R.id.tv_movie_item_director)
        TextView tvDirector;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
