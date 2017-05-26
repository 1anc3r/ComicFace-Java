package me.lancer.comicface_java.mvp.chapter;

import java.util.List;

import me.lancer.comicface_java.mvp.base.IBaseView;
import me.lancer.comicface_java.mvp.book.BookBean;

/**
 * Created by HuangFangzhi on 2017/5/25.
 */

public interface IChapterView extends IBaseView {

    void showList(List<ChapterBean> list);
}
