package android.com.mmassignment.fragment;

import android.com.mmassignment.R;
import android.com.mmassignment.activity.ArticleDetailActivity;
import android.com.mmassignment.adapter.ArticleAdapter;
import android.com.mmassignment.executor.GetArticles;
import android.com.mmassignment.helper.GeneralUtils;
import android.com.mmassignment.helper.MConstants;
import android.com.mmassignment.interfaces.ArticleViewHelper;
import android.com.mmassignment.interfaces.OnArticleItemClickListener;
import android.com.mmassignment.model.Article;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ajit on 6/13/2016.
 */

public class ArticleFragment extends Fragment implements ArticleViewHelper, SwipeRefreshLayout.OnRefreshListener, OnArticleItemClickListener {

    private String searchQuery;

    private View layoutLoading;
    private TextView txtLoading;
    private ProgressBar pbLoading;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mArticleRecyclerView;

    private GetArticles getArticles;
    private List<Article> mArticleList;
    private ArticleAdapter mArticleAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchQuery = getArguments() != null ? getArguments().getString("SEARCH_QUERY", null) : null;
        getArticles = new GetArticles(getActivity(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_articles, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mArticleList = new ArrayList<>();
        mArticleAdapter = new ArticleAdapter(mArticleList, this);
        mArticleRecyclerView.setAdapter(mArticleAdapter);
        if(searchQuery !=null){
            fetchArticles();
        }
    }

    private void initViews(View view) {
        layoutLoading = view.findViewById(R.id.layoutLoadingView);
        mArticleRecyclerView = (RecyclerView) view.findViewById(R.id.rvArticle);
        mArticleRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mArticleRecyclerView.setLayoutManager(layoutManager);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.layoutDataView);
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        pbLoading = (ProgressBar) view.findViewById(R.id.progressBar);
        txtLoading = (TextView) view.findViewById(R.id.txtLoading);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            fetchArticles();
        }
    }

    private void fetchArticles() {
        if (mSwipeRefreshLayout != null && !mSwipeRefreshLayout.isRefreshing() && mArticleList.size() == 0) {
            showLoadingView();
        }
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("api-key", MConstants.STORY_API_KEY);
        requestParams.put("sort", "newest");
        String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        if (searchQuery != null) {
            requestParams.put("q", searchQuery);
        } else {
            requestParams.put("begin_date", currentDate);
            requestParams.put("end_date", currentDate);
        }

        getArticles.getArticles(requestParams);
    }

    @Override
    public void loadArticles(List<Article> articleList) {

        mSwipeRefreshLayout.setRefreshing(false);
        mArticleList.clear();
        mArticleList.addAll(articleList);
        mArticleAdapter.notifyDataSetChanged();
        showDataView();

    }

    @Override
    public void onErrorLoadingArticle() {
        mSwipeRefreshLayout.setRefreshing(false);
        txtLoading.setText(getString(R.string.error_loading_data));
        pbLoading.setVisibility(View.GONE);
        if (mArticleList.size() == 0) {
            layoutLoading.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNetworkError() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
            showDataView();
            final Snackbar snackbar = Snackbar
                    .make(mSwipeRefreshLayout, getString(R.string.no_internet_connection), Snackbar.LENGTH_SHORT)
                    .setAction(R.string.retry, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            fetchArticles();
                        }
                    });
            snackbar.show();
        }

    }

    private void showLoadingView() {
        layoutLoading.setVisibility(View.VISIBLE);
        txtLoading.setText(getString(R.string.loading));
        txtLoading.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setVisibility(View.GONE);
    }

    private void showDataView(){
        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        layoutLoading.setVisibility(View.GONE);
    }
    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                fetchArticles();
            }
        });
        txtLoading.setVisibility(View.GONE);
    }

    @Override
    public void onArticleItemClick(Article article) {
        Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
        intent.putExtra("ARTICLE", article);
        startActivity(intent);
    }
}
