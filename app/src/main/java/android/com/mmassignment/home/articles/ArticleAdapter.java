package android.com.mmassignment.home.articles;

import android.com.mmassignment.R;
import android.com.mmassignment.util.ImageLoaderOptions;
import android.com.mmassignment.util.MConstants;
import android.com.mmassignment.interfaces.OnArticleItemClickListener;
import android.com.mmassignment.model.Article;
import android.com.mmassignment.model.Multimedia;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Ajit on 6/13/2016.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private List<Article> mArticleList;

    private DisplayImageOptions options;
    private OnArticleItemClickListener onArticleItemClickListener;

    public ArticleAdapter(List<Article> mArticleList, OnArticleItemClickListener onArticleItemClickListener) {
        this.mArticleList = mArticleList;
        options = ImageLoaderOptions.INSTANCE.getDisplayImageOptions();
        this.onArticleItemClickListener = onArticleItemClickListener;
    }

    public void replaceData(List<Article> articles) {
        this.mArticleList = checkNotNull(articles, "data should not be null");
        notifyDataSetChanged();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder {
        public TextView txtArticleHeader, txtPublished;
        public ImageView imgArticleThumb;
        private View itemView;

        public ArticleViewHolder(View view) {

            super(view);
            this.itemView = view;
            txtArticleHeader = (TextView) view.findViewById(R.id.txtArticleTitle);
            txtPublished = (TextView) view.findViewById(R.id.txtArticleTimeStamp);
            imgArticleThumb = (ImageView) view.findViewById(R.id.imgArticleThumb);
        }

        public void bindListener(final Article article, final OnArticleItemClickListener onArticleItemClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onArticleItemClickListener.onArticleItemClick(article);
                }
            });
        }
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item_layout, parent, false);
        ArticleViewHolder articleViewHolder = new ArticleViewHolder(view);
        return articleViewHolder;
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        Article article = mArticleList.get(position);
        holder.txtArticleHeader.setText(article.getHeadline().heading);
        holder.txtPublished.setText(getFormattedDate(article.getPublishedDate()));
        String imageThumbUrl = getImageThumbUrl(article.getMultimedia());
        ImageLoader.getInstance().displayImage(imageThumbUrl, holder.imgArticleThumb, options, null);
        holder.bindListener(article, onArticleItemClickListener);
    }

    private String getImageThumbUrl(List<Multimedia> multimedia) {


        for (Multimedia image : multimedia) {
            if (image.type.equalsIgnoreCase("thumbnail")) {
                return MConstants.IMAGE_BASE_URL + image.url;
            }
        }
        if (multimedia.size() > 0) {
            return MConstants.IMAGE_BASE_URL + multimedia.get(0).url;
        }
        return null;
    }

    private String getFormattedDate(String strDate) {
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        java.util.Date date = null;
        try {
            date = form.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat newFormat = new SimpleDateFormat("MM/dd/yyyy h:mm a");
        return newFormat.format(date);
    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }
}
