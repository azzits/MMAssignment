package android.com.mmassignment.executor;

import android.com.mmassignment.adapter.ArticleAdapter;
import android.com.mmassignment.helper.GeneralUtils;
import android.com.mmassignment.helper.MConstants;
import android.com.mmassignment.interfaces.ArticleViewHelper;
import android.com.mmassignment.interfaces.GetArticlesHelper;
import android.com.mmassignment.model.Article;
import android.com.mmassignment.model.ArticleResponse;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.QueryMap;

/**
 * Created by Ajit on 6/13/2016.
 */

public class GetArticles{

    private final GetArticlesHelper getArticlesHelper;

    ArticleViewHelper articleViewHelper;

    Context context;

    public GetArticles(Context context, ArticleViewHelper articleViewHelper) {
        this.articleViewHelper = articleViewHelper;
        this.context = context;
        Gson gson = new Gson();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        getArticlesHelper = retrofit.create(GetArticlesHelper.class);
    }

    public void getArticles(Map<String, String> queryParams) {
        if(!GeneralUtils.isNetworkAvailable(context)){
            articleViewHelper.onNetworkError();
            return;
        }
        Call<ArticleResponse> articles = getArticlesHelper.getArticles(queryParams);
        articles.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                Log.e("GetArticles", "response");
                ArticleResponse articleResponse = response.body();
                articleViewHelper.loadArticles(articleResponse.getResponse().getArticleList());

            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                Log.e("GetArticles", "failure");
                articleViewHelper.onErrorLoadingArticle();
            }
        });
    }
}
