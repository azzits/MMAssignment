package android.com.mmassignment.adapter;

import android.com.mmassignment.R;
import android.com.mmassignment.model.Category;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ajit on 6/13/2016.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {
    private List<Category> categoriesList;

    public CategoriesAdapter(List<Category> categoriesList) {
        this.categoriesList = categoriesList;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_layout, parent, false);
        CategoryViewHolder categoryViewHolder = new CategoryViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Category category = categoriesList.get(position);
        holder.txtCategoryTitle.setText(category.getCategoryTitle());
        holder.imgCategoryImage.setImageResource(category.getCategoryResId());
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        public TextView txtCategoryTitle;
        public ImageView imgCategoryImage;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            txtCategoryTitle = (TextView) itemView.findViewById(R.id.txtCategoryTitle);
            imgCategoryImage = (ImageView) itemView.findViewById(R.id.imgCategoryImage);
        }


    }
}
