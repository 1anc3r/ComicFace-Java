package me.lancer.comicface_java.mvp.book;

import java.util.List;

import me.lancer.comicface_java.mvp.base.IBaseView;

/**
 * Created by HuangFangzhi on 2017/5/25.
 */

public interface IBookView extends IBaseView {

    void showList(List<BookBean> list);

    void showRank(List<BookBean> list);

    void showSort(List<BookBean> list);
}
