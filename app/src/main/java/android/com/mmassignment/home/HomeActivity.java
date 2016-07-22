package android.com.mmassignment.home;

import android.com.mmassignment.R;
import android.com.mmassignment.activity.SearchResultActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class HomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, MaterialSearchView.SearchViewListener, MaterialSearchView.OnQueryTextListener {

    private FloatingActionButton fBtnSearch;
    private TabLayout mTabLayout;

    private ViewPager mHomeViewPager;

    private HomeViewPagerAdapter mHomeViewPagerAdapter;
    private MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
        setUpViewPager();
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fBtnSearch = (FloatingActionButton) findViewById(R.id.fbtn_search);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mHomeViewPager = (ViewPager) findViewById(R.id.content_home);
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        fBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!searchView.isSearchOpen()) {
                    searchView.showSearch();

                } else {
                    searchView.closeSearch();

                }
            }
        });

        searchView.setHintTextColor(R.color.colorTabTextNormal);
        searchView.setHint(getString(R.string.search_articles));

        searchView.setOnSearchViewListener(this);
        searchView.setOnQueryTextListener(this);
    }

    private void setUpViewPager() {
        String[] pageTitles = getResources().getStringArray(R.array.home_page_titles);
        mHomeViewPagerAdapter = new HomeViewPagerAdapter(getSupportFragmentManager(), pageTitles);
        mHomeViewPager.setAdapter(mHomeViewPagerAdapter);
        mTabLayout.setupWithViewPager(mHomeViewPager);
        mHomeViewPager.addOnPageChangeListener(this);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        fBtnSearch.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
        searchView.closeSearch();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();

        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onSearchViewShown() {
        fBtnSearch.setImageResource(R.drawable.ic_clear);
    }

    @Override
    public void onSearchViewClosed() {
        fBtnSearch.setImageResource(R.drawable.ic_search);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if(!query.trim().isEmpty()){
            Intent intent = new Intent(HomeActivity.this, SearchResultActivity.class);
            intent.putExtra("SEARCH_QUERY", query.trim());
            startActivity(intent);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
