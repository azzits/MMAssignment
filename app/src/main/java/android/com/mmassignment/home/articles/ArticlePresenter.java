package android.com.mmassignment.home.articles;

//import android.support.annotation.NonNull;


import android.com.mmassignment.util.MConstants;
import android.com.mmassignment.interfaces.GetArticlesHelper;
import android.com.mmassignment.model.Article;
import android.com.mmassignment.model.ArticleResponse;
import android.com.mmassignment.util.ConnectionUtil;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Ajit on 6/30/2016.
 */


public class ArticlePresenter implements ArticleContract.Presenter {

    private final ArticleContract.View mArticleView;

    private boolean mFirstLoad = true;

    private ArrayList<Article> mArticleList;

    private GetArticlesHelper mGetArticlesHelper;

    private ConnectionUtil mConnectionUtil;

    public ArticlePresenter(@NonNull ArticleContract.View articleView) {
        mArticleView = checkNotNull(articleView, "Article view cannot be null");
        articleView.setPresenter(this);
        mArticleList = new ArrayList<>();

        Gson gson = new Gson();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        mGetArticlesHelper = retrofit.create(GetArticlesHelper.class);
        mConnectionUtil = new ConnectionUtil();
    }

    @Override
    public void start() {
        if (mFirstLoad) {
            loadArticles(true, null);
        }
        mFirstLoad = false;
    }

    @Override
    public void loadArticles(boolean showLoadingUI, String searchQuery) {
        if (showLoadingUI || mArticleList.size() == 0) {
            mArticleView.showLoading();
        }

        Map<String, String> requestParams;
        requestParams = new HashMap<>();
        requestParams.put("api-key", MConstants.STORY_API_KEY);
        requestParams.put("sort", "newest");
        String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        if (searchQuery != null) {
            requestParams.put("q", searchQuery);
        } else {
            requestParams.put("begin_date", currentDate);
//            requestParams.put("end_date", currentDate);
        }

        getArticles(requestParams);

    }

    public void getArticles(Map<String, String> queryParams) {
        if(!mConnectionUtil.isNetworkAvailable()){
            mArticleView.showNetworkError();
            return;
        }
        mArticleView.showRefreshCompleted();

        Call<ArticleResponse> articles = mGetArticlesHelper.getArticles(queryParams);
        articles.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                ArticleResponse articleResponse = response.body();

                mArticleView.showArticles(articleResponse.getResponse().getArticleList());

            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                mArticleView.showErrorLoading(mArticleList.size() == 0);
            }
        });
    }

}
