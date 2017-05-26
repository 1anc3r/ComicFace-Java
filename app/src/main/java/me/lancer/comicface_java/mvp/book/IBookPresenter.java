package me.lancer.comicface_java.mvp.book;

import java.util.List;

/**
 * Created by HuangFangzhi on 2017/5/25.
 */

public interface IBookPresenter {

    void loadListSuccess(List<BookBean> list);

    void loadListFailure(String log);

    void loadRankSuccess(List<BookBean> list);

    void loadRankFailure(String log);

    void loadSortSuccess(List<BookBean> list);

    void loadSortFailure(String log);
}
