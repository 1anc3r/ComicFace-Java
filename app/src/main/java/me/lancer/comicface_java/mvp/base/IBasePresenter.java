package me.lancer.comicface_java.mvp.base;

/**
 * Created by HuangFangzhi on 2017/5/24.
 */

public interface IBasePresenter<V> {

    void attachView(V view);

    void detachView();
}