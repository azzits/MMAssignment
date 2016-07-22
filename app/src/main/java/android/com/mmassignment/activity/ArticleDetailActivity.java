package android.com.mmassignment.activity;

import android.com.mmassignment.R;
import android.com.mmassignment.util.GeneralUtils;
import android.com.mmassignment.util.ImageLoaderOptions;
import android.com.mmassignment.util.MConstants;
import android.com.mmassignment.model.Article;
import android.com.mmassignment.model.Comment;
import android.com.mmassignment.model.Multimedia;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ajit on 6/14/2016.
 */

public class ArticleDetailActivity extends AppCompatActivity {

    private TextView txtPublishedDate, txtArticleTitle, txtAuthor, txtArticleSnippet, txtArticleParagraph;
    private ImageView imgArticleBanner, imgArticleDesc;
    private LinearLayout layoutComments;

    private Article selectedArticle;

    private DisplayImageOptions imageOptions;
    private CollapsingToolbarLayout collapsingToolbar;

    private List<Comment> commentsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        initViews();
        initData();
        loadArticle();
    }

    private void initViews() {
        txtPublishedDate = (TextView) findViewById(R.id.txtPubDate);
        txtArticleTitle = (TextView) findViewById(R.id.txtArticleTitle);
        txtAuthor = (TextView) findViewById(R.id.txtAuthor);
        txtArticleSnippet = (TextView) findViewById(R.id.txtArticleSnippet);
        txtArticleParagraph = (TextView) findViewById(R.id.txtArticleParagraph);
        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutComments = (LinearLayout) findViewById(R.id.layoutComments);
        imgArticleBanner = (ImageView) findViewById(R.id.imgArticleBanner);
        imgArticleDesc = (ImageView) findViewById(R.id.imgArticleDesc);
    }

    private void initData() {
        selectedArticle = (Article) getIntent().getSerializableExtra("ARTICLE");
        imageOptions = ImageLoaderOptions.INSTANCE.getDisplayImageOptions();
        commentsList = new ArrayList<>();
        Comment comment = new Comment("October 23, 2015 11:04 pm", "Posts that are flagged will be reviewed by the social network, which will provide resources to the concerned friend and offer help to the person at risk.");
        commentsList.add(comment);
        comment = new Comment("October 23, 2015 11:04 pm", "Members of the mustelid family, including badgers, ferrets and otters, have evolved into fierce, fleet, quick-witted carnivores that can compete for food against big cats, wolves and bears.");
        commentsList.add(comment);
        comment = new Comment("October 23, 2015 11:04 pm", "Apple to Offer App Developers Access to Siri and iMessage");
        commentsList.add(comment);
    }

    private void loadArticle() {
        collapsingToolbar.setTitle(selectedArticle.getHeadline().heading + "");
        txtArticleSnippet.setText(selectedArticle.getSnippet());
        txtArticleParagraph.setText(selectedArticle.getLeadParagraph());
        txtArticleTitle.setText(selectedArticle.getHeadline().heading);
        txtAuthor.setText(selectedArticle.getAuthor().author);
        txtPublishedDate.setText(GeneralUtils.getFormattedDate(selectedArticle.getPublishedDate()));
        View commentLayout;
        LayoutInflater mInflater = getLayoutInflater();
        for (Comment comment : commentsList) {
            commentLayout = mInflater.inflate(R.layout.comment_item_layout, null);
            ((TextView)commentLayout.findViewById(R.id.txtCommentDate)).setText(comment.getCommentDate());
            ((TextView)commentLayout.findViewById(R.id.txtComment)).setText(comment.getCommentStr());
            layoutComments.addView(commentLayout);
        }

        String descImageUrl = getImageUrl();
        ImageLoader.getInstance().displayImage(descImageUrl, imgArticleBanner, imageOptions, null);
        if(descImageUrl !=null){
            imgArticleDesc.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(descImageUrl, imgArticleDesc, imageOptions, null);
        }

    }


    private String getImageUrl() {


        for (Multimedia image : selectedArticle.getMultimedia()) {
            if (image.type.equalsIgnoreCase("wide")) {
                return MConstants.IMAGE_BASE_URL + image.url;
            }
        }
        if (selectedArticle.getMultimedia().size() > 0) {
            return MConstants.IMAGE_BASE_URL + selectedArticle.getMultimedia().get(0).url;
        }
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
