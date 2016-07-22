package android.com.mmassignment.activity;

import android.com.mmassignment.R;
import android.com.mmassignment.home.articles.ArticleFragment;
import android.com.mmassignment.home.articles.ArticlePresenter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class SearchResultActivity extends AppCompatActivity {
    String searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        initData();
        initToolbar();
        loadSearchFragment();
    }

    private void loadSearchFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ArticleFragment fragment = new ArticleFragment();
        Bundle bundle = new Bundle();
        bundle.putString("SEARCH_QUERY", searchQuery);
        fragment.setArguments(bundle);
        transaction.replace(R.id.layout_content, fragment);
//        transaction.addToBackStack(null);
        ArticlePresenter presenter = new ArticlePresenter(fragment);
        transaction.commit();
    }

    private void initData() {
        searchQuery = getIntent().getStringExtra("SEARCH_QUERY");
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.search_results);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
