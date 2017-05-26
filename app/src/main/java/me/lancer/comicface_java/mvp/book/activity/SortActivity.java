package me.lancer.comicface_java.mvp.book.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import me.lancer.comicface_java.R;
import me.lancer.comicface_java.mvp.base.activity.PresenterActivity;
import me.lancer.comicface_java.mvp.book.BookBean;
import me.lancer.comicface_java.mvp.book.BookBean;
import me.lancer.comicface_java.mvp.book.BookPresenter;
import me.lancer.comicface_java.mvp.book.IBookView;
import me.lancer.comicface_java.mvp.book.adapter.BookAdapter;
import me.lancer.comicface_java.util.LruImageCache;

public class SortActivity extends PresenterActivity<BookPresenter> implements IBookView {

    private String link, title, cover;
    private Toolbar toolbar;
    private NetworkImageView ivCover;
    private List<BookBean> mData = new ArrayList<BookBean>();
    private BookAdapter mAdapter;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
                case 1:
                    mSwipeRefreshLayout.setRefreshing(true);
                    break;
                case 2:
                    Log.e("log", (String) msg.obj);
                    break;
                case 3:
                    if (msg.obj != null) {
                        mData = (List<BookBean>) msg.obj;
                        mAdapter = new BookAdapter(SortActivity.this, mData);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };

    private Runnable loadTop = new Runnable() {
        @Override
        public void run() {
            presenter.loadSortContent(link);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        init();
    }

    public void init(){
        link = getIntent().getStringExtra("link");
        title = getIntent().getStringExtra("title");
        cover = getIntent().getStringExtra("cover");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        RequestQueue mQueue = Volley.newRequestQueue(this);
        LruImageCache cache = LruImageCache.instance();
        ImageLoader loader = new ImageLoader(mQueue, cache);
        ivCover = (NetworkImageView) findViewById(R.id.imageView);
        ivCover.setImageUrl(cover, loader);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue, R.color.teal, R.color.green, R.color.yellow, R.color.orange, R.color.red, R.color.pink, R.color.purple);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                new Thread(loadTop).start();
                Message msg = new Message();
                msg.what = 0;
                handler.sendMessageDelayed(msg, 800);
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new BookAdapter(this, mData);
        mRecyclerView.setAdapter(mAdapter);
        new Thread(loadTop).start();
    }

    public static void startActivity(Activity activity, String link, String cover, String title, NetworkImageView networkImageView) {
        Intent intent = new Intent();
        intent.setClass(activity, SortActivity.class);
        intent.putExtra("link", link);
        intent.putExtra("title", title);
        intent.putExtra("cover", cover);
        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity, networkImageView, "transitionPic");
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected BookPresenter onCreatePresenter() {
        return new BookPresenter(this);
    }

    @Override
    public void showList(List<BookBean> list) {
    }

    @Override
    public void showRank(List<BookBean> list) {
    }

    @Override
    public void showSort(List<BookBean> list) {
        Message msg = new Message();
        msg.what = 3;
        msg.obj = list;
        handler.sendMessage(msg);
    }

    @Override
    public void showMsg(String log) {
        Message msg = new Message();
        msg.what = 2;
        msg.obj = log;
        handler.sendMessage(msg);
    }

    @Override
    public void showLoad() {
        Message msg = new Message();
        msg.what = 1;
        handler.sendMessage(msg);
    }

    @Override
    public void hideLoad() {
        Message msg = new Message();
        msg.what = 0;
        handler.sendMessage(msg);
    }
}
