package android.com.mmassignment.home.categories;

import android.com.mmassignment.model.Category;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.com.mmassignment.R;

import java.util.ArrayList;
import java.util.List;


public class CategoriesFragment extends Fragment {

    private RecyclerView rvCategory;

    private CategoriesAdapter mCategoriesAdapter;

    private List<Category> mCategoriesList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadCategories();
        mCategoriesAdapter = new CategoriesAdapter(mCategoriesList);
        rvCategory.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        rvCategory.setLayoutManager(layoutManager);
        rvCategory.setAdapter(mCategoriesAdapter);
    }

    private void initViews(View view) {
        rvCategory = (RecyclerView) view.findViewById(R.id.rvCategory);
    }

    private void loadCategories() {
        mCategoriesList = new ArrayList<>();

        mCategoriesList.add(new Category(R.drawable.ic_business, getString(R.string.business)));
        mCategoriesList.add(new Category(R.drawable.ic_politics, getString(R.string.politics)));
        mCategoriesList.add(new Category(R.drawable.ic_arts, getString(R.string.arts)));
        mCategoriesList.add(new Category(R.drawable.ic_science, getString(R.string.science)));
        mCategoriesList.add(new Category(R.drawable.ic_sports, getString(R.string.sports)));
    }
}
