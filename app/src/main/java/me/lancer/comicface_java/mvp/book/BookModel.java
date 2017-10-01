package me.lancer.comicface_java.mvp.book;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.lancer.comicface_java.util.ContentGetterSetter;
import me.lancer.comicface_java.util.URL;

/**
 * Created by HuangFangzhi on 2017/5/25.
 */

public class BookModel {

    IBookPresenter presenter;

    ContentGetterSetter contentGetterSetter = new ContentGetterSetter();

    public BookModel(IBookPresenter presenter) {
        this.presenter = presenter;
    }

    public void loadList() {
        String content = contentGetterSetter.getContentFromHtml("Book.loadList", URL.HOME_URL);
        List<BookBean> list;
        if (!content.contains("获取失败!")) {
            list = getListFromContent(content);
            presenter.loadListSuccess(list);
        } else {
            presenter.loadListFailure(content);
            Log.e("loadList", content);
        }
    }

    public void loadRankTitle() {
        Log.e("load.list", URL.RANK_TITLE_URL);
        String content = contentGetterSetter.getContentFromHtml("Book.loadRankTitle", URL.RANK_TITLE_URL);
        List<BookBean> list;
        if (!content.contains("获取失败!")) {
            list = getRankTitleFromContent(content);
            presenter.loadRankSuccess(list);
        } else {
            presenter.loadRankFailure(content);
            Log.e("loadRankTitle", content);
        }
    }

    public List<BookBean> loadRankContent(String url) {
        Log.e("load.item", url);
        String content = contentGetterSetter.getContentFromHtml("Book.loadRankContent", url);
        List<BookBean> list = null;
        if (!content.contains("获取失败!")) {
            list = getContentFromContent(content);
        } else {
            Log.e("loadRankContent", content);
        }
        return list;
    }

    public void loadSortTitle() {
        String content = contentGetterSetter.getContentFromHtml("Book.loadSortTitle", URL.SORT_TITLE_URL);
        List<BookBean> list;
        if (!content.contains("获取失败!")) {
            list = getSortTitleFromContent(content);
            presenter.loadSortSuccess(list);
        } else {
            presenter.loadSortFailure(content);
            Log.e("loadSortTitle", content);
        }
    }

    public void loadSortContent(String url) {
        String content = contentGetterSetter.getContentFromHtml("Book.loadRankContent", url);
        List<BookBean> list = null;
        if (!content.contains("获取失败!")) {
            list = getContentFromContent(content);
            presenter.loadSortSuccess(list);
        } else {
            presenter.loadSortFailure(content);
            Log.e("loadRankContent", content);
        }
    }

    private List<BookBean> getListFromContent(String content) {
        try {
            List<BookBean> list = new ArrayList<>();
            JSONObject all = new JSONObject(content);
            int code = all.getInt("code");
            if (code == 1) {
                JSONObject data = all.getJSONObject("data");
                int stateCode = data.getInt("stateCode");
                String message = data.getString("message");
                if (stateCode == 1 && message.equals("成功")) {
                    JSONObject returnData = data.getJSONObject("returnData");
                    JSONArray comicsLists = returnData.getJSONArray("comicLists");
                    for (int i = 0; i < comicsLists.length(); i++) {
                        JSONObject comicsList = comicsLists.getJSONObject(i);
                        String itemTitle = comicsList.getString("itemTitle");
                        String itemIcon = comicsList.getString("titleIconUrl");
                        BookBean bean = new BookBean(itemTitle, "", 0, itemIcon, "");
                        list.add(bean);
                        JSONArray comics = comicsList.getJSONArray("comics");
                        if (comics.length() > 0) {
                            for (int j = 0; j < comicsList.length(); j++) {
                                JSONObject comic = comics.getJSONObject(j);
                                String title = comic.getString("name");
                                String category = comic.getString("short_description");
                                String cover = comic.getString("cover");
                                String link = URL.BOOK_URL + comic.getInt("comicId");
                                BookBean item = new BookBean(title, category, 1, cover, link);
                                list.add(item);
                            }
                        }
                    }
                }
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<BookBean> getRankTitleFromContent(String content) {
        try {
            List<BookBean> list = new ArrayList<>();
            JSONObject all = new JSONObject(content);
            int code = all.getInt("code");
            if (code == 1) {
                JSONObject data = all.getJSONObject("data");
                int stateCode = data.getInt("stateCode");
                String message = data.getString("message");
                if (stateCode == 1 && message.equals("成功")) {
                    JSONObject returnData = data.getJSONObject("returnData");
                    JSONArray rankingList = returnData.getJSONArray("rankinglist");
                    for (int i = 0; i < rankingList.length(); i++) {
                        JSONObject ranking = rankingList.getJSONObject(i);
                        String title = ranking.getString("title")+"排行";
                        String category = ranking.getString("subTitle");
                        String link = URL.RANK_CONTENT_URL+ranking.getInt("argValue");
                        BookBean bean = new BookBean(title, category, 0, "", link);
                        list.add(bean);
                        list.addAll(loadRankContent(link));
                    }
                }
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<BookBean> getSortTitleFromContent(String content) {
        try {
            List<BookBean> list = new ArrayList<>();
            JSONObject all = new JSONObject(content);
            int code = all.getInt("code");
            if (code == 1) {
                JSONObject data = all.getJSONObject("data");
                int stateCode = data.getInt("stateCode");
                String message = data.getString("message");
                if (stateCode == 1 && message.equals("成功")) {
                    JSONObject returnData = data.getJSONObject("returnData");
                    JSONArray rankingList = returnData.getJSONArray("rankingList");
                    for (int i = 0; i < rankingList.length(); i++) {
                        JSONObject ranking = rankingList.getJSONObject(i);
                        String title = ranking.getString("sortName");
                        String cover = ranking.getString("cover");
                        String link = URL.SORT_CONTENT_URL+"&argValue="+ranking.getInt("argValue")+"&argName="+ranking.getString("argName");
                        BookBean bean = new BookBean(title, "", 1, cover, link);
                        list.add(bean);
                    }
                }
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<BookBean> getContentFromContent(String content) {
        try {
            List<BookBean> list = new ArrayList<>();
            JSONObject all = new JSONObject(content);
            int code = all.getInt("code");
            if (code == 1) {
                JSONObject data = all.getJSONObject("data");
                int stateCode = data.getInt("stateCode");
                String message = data.getString("message");
                if (stateCode == 1 && message.contains("成功")) {
                    JSONObject returnData = data.getJSONObject("returnData");
                    JSONArray comics = returnData.getJSONArray("comics");
                    for (int i = 0; i < comics.length(); i++) {
                        JSONObject comic = comics.getJSONObject(i);
                        String title = comic.getString("name");
                        String category = comic.getString("description");
                        String cover = comic.getString("cover");
                        String link = URL.BOOK_URL + comic.getInt("comicId");
                        BookBean bean = new BookBean(title, category, 1, cover, link);
                        list.add(bean);
                    }
                }
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
