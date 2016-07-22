package android.com.mmassignment.home.articles;

import android.com.mmassignment.BasePresenter;
import android.com.mmassignment.BaseView;
import android.com.mmassignment.model.Article;

import java.util.List;

/**
 * Created by Ajit on 6/30/2016.
 */

public interface ArticleContract {

    interface View extends BaseView<ArticlePresenter> {
        void showLoading();
        void showRefreshing();
        void showDataView();
        void showArticles(List<Article> articles);
        void showRefreshCompleted();
        void showNetworkError();
        void showErrorLoading(boolean showLoadingInfoView);
    }

    interface Presenter extends BasePresenter {
        void loadArticles(boolean showLoadingUI, String searchQuery);

    }
}
