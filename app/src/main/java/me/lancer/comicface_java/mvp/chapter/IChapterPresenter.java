package me.lancer.comicface_java.mvp.chapter;

import java.util.List;

import me.lancer.comicface_java.mvp.book.BookBean;

/**
 * Created by HuangFangzhi on 2017/5/25.
 */

public interface IChapterPresenter {

    void loadListSuccess(List<ChapterBean> list);

    void loadListFailure(String log);
}
