package android.com.mmassignment.model;

/**
 * Created by Ajit on 6/13/2016.
 */

public class Category {
    private int categoryResId;
    private String categoryTitle;

    public Category(int resId, String title) {
        setCategoryResId(resId);
        setCategoryTitle(title);
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public int getCategoryResId() {
        return categoryResId;
    }

    public void setCategoryResId(int categoryResId) {
        this.categoryResId = categoryResId;
    }
}
