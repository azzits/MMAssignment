package android.com.mmassignment.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ajit on 6/13/2016.
 */

public class ArticleResponse {

    @SerializedName("response")
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public class Response {
        @SerializedName("docs")
        private List<Article> articleList;

        public List<Article> getArticleList() {
            return articleList;
        }

        public void setArticleList(List<Article> articleList) {
            this.articleList = articleList;
        }
    }


}
