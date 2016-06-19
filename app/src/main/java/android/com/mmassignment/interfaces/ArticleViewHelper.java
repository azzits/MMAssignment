package android.com.mmassignment.interfaces;

import android.com.mmassignment.model.Article;

import java.util.List;

/**
 * Created by Ajit on 6/13/2016.
 */

public interface ArticleViewHelper {
    void loadArticles(List<Article> articleList);
    void onErrorLoadingArticle();
    void onNetworkError();
}
