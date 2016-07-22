package android.com.mmassignment.home;

import android.com.mmassignment.home.articles.ArticleFragment;
import android.com.mmassignment.home.categories.CategoriesFragment;
import android.com.mmassignment.home.settings.SettingsFragment;
import android.com.mmassignment.home.articles.ArticlePresenter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Ajit on 6/13/2016.
 */

public class HomeViewPagerAdapter extends FragmentStatePagerAdapter {

    private String[] pageTitles;

    public HomeViewPagerAdapter(FragmentManager fm, String[] pageTitles) {
        super(fm);
        this.pageTitles = pageTitles;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ArticleFragment articleFragment = new ArticleFragment();
                ArticlePresenter presenter = new ArticlePresenter(articleFragment);
                return articleFragment;
            case 1:
                return new CategoriesFragment();
            case 2:
                return new SettingsFragment();
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles[position];
    }
}
