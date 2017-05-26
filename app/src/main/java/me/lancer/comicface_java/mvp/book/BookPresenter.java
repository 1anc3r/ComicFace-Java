package me.lancer.comicface_java.mvp.book;

import java.util.List;

import me.lancer.comicface_java.mvp.base.IBasePresenter;

/**
 * Created by HuangFangzhi on 2017/5/25.
 */

public class BookPresenter implements IBasePresenter<IBookView>, IBookPresenter {

    private IBookView view;
    private BookModel model;

    public BookPresenter(IBookView view) {
        attachView(view);
        model = new BookModel(this);
    }

    @Override
    public void attachView(IBookView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    public void loadList() {
        if (view != null) {
            view.showLoad();
            model.loadList();
        }
    }

    @Override
    public void loadListSuccess(List<BookBean> list) {
        if (view != null) {
            view.showList(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadListFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }

    public void loadRankTitle() {
        if (view != null) {
            view.showLoad();
            model.loadRankTitle();
        }
    }

    @Override
    public void loadRankSuccess(List<BookBean> list) {
        if (view != null) {
            view.showRank(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadRankFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }

    public void loadSortTitle() {
        if (view != null) {
            view.showLoad();
            model.loadSortTitle();
        }
    }

    public void loadSortContent(String url) {
        if (view != null) {
            view.showLoad();
            model.loadSortContent(url);
        }
    }

    @Override
    public void loadSortSuccess(List<BookBean> list) {
        if (view != null) {
            view.showSort(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadSortFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }
}
