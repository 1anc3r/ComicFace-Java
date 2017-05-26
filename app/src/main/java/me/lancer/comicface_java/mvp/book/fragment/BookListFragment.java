package me.lancer.comicface_java.mvp.book.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.lancer.comicface_java.R;
import me.lancer.comicface_java.mvp.base.fragment.PresenterFragment;
import me.lancer.comicface_java.mvp.book.BookBean;
import me.lancer.comicface_java.mvp.book.BookPresenter;
import me.lancer.comicface_java.mvp.book.IBookView;
import me.lancer.comicface_java.mvp.book.adapter.BookAdapter;

/**
 * Created by HuangFangzhi on 2016/12/18.
 */

public class BookListFragment extends PresenterFragment<BookPresenter> implements IBookView {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private BookAdapter mAdapter;

    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private List<BookBean> mList = new ArrayList<>();

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
                        mList = (List<BookBean>) msg.obj;
                        mAdapter = new BookAdapter(getActivity(), mList);
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
            presenter.loadList();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        initData();
    }

    private void initData() {
        new Thread(loadTop).start();
    }

    private void initView(View view) {

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new BookAdapter(getActivity(), mList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected BookPresenter onCreatePresenter() {
        return new BookPresenter(this);
    }

    @Override
    public void showList(List<BookBean> list) {
        Message msg = new Message();
        msg.what = 3;
        msg.obj = list;
        handler.sendMessage(msg);
    }

    @Override
    public void showRank(List<BookBean> list) {
    }

    @Override
    public void showSort(List<BookBean> list) {

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
