package android.com.mmassignment.home.articles;

import android.com.mmassignment.R;
import android.com.mmassignment.activity.ArticleDetailActivity;
import android.com.mmassignment.interfaces.OnArticleItemClickListener;
import android.com.mmassignment.model.Article;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.internal.$Gson$Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ajit on 6/13/2016.
 */

public class ArticleFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OnArticleItemClickListener, ArticleContract.View {

    private String searchQuery;

    private View layoutLoading;
    private TextView txtLoading;
    private ProgressBar pbLoading;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mArticleRecyclerView;

    private ArticleAdapter mArticleAdapter;

    private ArticleContract.Presenter mPresenter;

    private boolean isGrid;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchQuery = getArguments() != null ? getArguments().getString("SEARCH_QUERY", null) : null;
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

        mArticleAdapter = new ArticleAdapter(new ArrayList(), this);
        mArticleRecyclerView.setAdapter(mArticleAdapter);

        if (searchQuery == null)
            mPresenter.start();
        else
            mPresenter.loadArticles(true, searchQuery);
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
    public void onResume() {
        super.onResume();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
//            mPresenter.start();
        }
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                mPresenter.loadArticles(false, searchQuery);
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

    @Override
    public void setPresenter(ArticlePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading() {
        layoutLoading.setVisibility(View.VISIBLE);
        txtLoading.setText(getString(R.string.loading));
        txtLoading.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setVisibility(View.GONE);

    }

    @Override
    public void showRefreshing() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void showDataView() {
        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        layoutLoading.setVisibility(View.GONE);
    }

    @Override
    public void showArticles(List<Article> articles) {
        mArticleAdapter.replaceData(articles);
        showDataView();
    }

    @Override
    public void showRefreshCompleted() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showNetworkError() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
            showDataView();
            final Snackbar snackbar = Snackbar
                    .make(mSwipeRefreshLayout, getString(R.string.no_internet_connection), Snackbar.LENGTH_SHORT)
                    .setAction(R.string.retry, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mPresenter.loadArticles(true, searchQuery);
                        }
                    });
            snackbar.show();
        }
    }

    @Override
    public void showErrorLoading(boolean showLoadingInfoView) {
        mSwipeRefreshLayout.setRefreshing(false);
        txtLoading.setText(getString(R.string.error_loading_data));
        pbLoading.setVisibility(View.GONE);
        if (showLoadingInfoView) {
            layoutLoading.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_toggleview) {
            mArticleRecyclerView.setLayoutManager(isGrid ? new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) : new LinearLayoutManager(getActivity()));
            mArticleRecyclerView.setAdapter(mArticleAdapter);
            isGrid = !isGrid;
        }
        return super.onOptionsItemSelected(item);
    }
}
