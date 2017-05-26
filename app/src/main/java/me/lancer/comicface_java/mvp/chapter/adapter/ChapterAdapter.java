package me.lancer.comicface_java.mvp.chapter.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.List;

import me.lancer.comicface_java.R;
import me.lancer.comicface_java.mvp.book.BookBean;
import me.lancer.comicface_java.mvp.chapter.ChapterBean;
import me.lancer.comicface_java.mvp.page.activity.PagerActivity;
import me.lancer.comicface_java.ui.activity.AboutActivity;
import me.lancer.comicface_java.util.LruImageCache;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ViewHolder> {

    private List<ChapterBean> list;
    private Context context;

    public ChapterAdapter(Context context, List<ChapterBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chapter, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        if (list.get(position) != null) {
            ChapterBean bean = list.get(position);
            viewHolder.tvTitle.setText(bean.getTitle());
            viewHolder.tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("link", list.get(position).getLink());
                    intent.setClass(context, PagerActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;

        public ViewHolder(View rootView) {
            super(rootView);
            tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
        }
    }
}
