package android.com.mmassignment.interfaces;

import android.com.mmassignment.helper.MConstants;
import android.com.mmassignment.model.Article;
import android.com.mmassignment.model.ArticleResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Ajit on 6/13/2016.
 */

public interface GetArticlesHelper {

    @GET(MConstants.STORY_LIST_URL)
    Call<ArticleResponse> getArticles(@QueryMap Map<String, String> query);
}
