package com.dev_tee.bogblog.details;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.choota.dev.ctimeago.TimeAgo;
import com.dev_tee.bogblog.Injection;
import com.dev_tee.bogblog.R;
import com.dev_tee.bogblog.data.Blog;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.richeditor.RichEditor;

/**
 * Created by Dev_Tee on 1/8/17.
 */
public class BlogDetailsActivity extends AppCompatActivity implements DetailsContract.View{

    @BindView(R.id.imgBtnBack)
    AppCompatImageView imgBtnBack;
    @BindView(R.id.txtOwnerName)
    TextView txtOwnerName;
    @BindView(R.id.txtDateCreated)
    TextView txtDateCreated;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtContent)
    RichEditor txtContent;

    public static final String EXTRA_BLOG_ID = "blogId";
    private Context context;
    private DetailsPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        context = getApplicationContext();
        presenter = new DetailsPresenter(Injection.provideBlogRepository(), this);

        imgBtnBack.setColorFilter(ContextCompat.getColor(context, android.R.color.black),
                PorterDuff.Mode.SRC_IN);

        int blogId = getIntent().getIntExtra(EXTRA_BLOG_ID, 0);

        presenter.openBlog(blogId);
    }

    @Override
    public void showContent(Blog item) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeAgo timeAgo = new TimeAgo().locale(context).with(sdf);

        txtDateCreated.setText(timeAgo.getTimeAgo(item.getTimeCreated()));

        txtTitle.setText(item.getTitle());
        txtContent.setHtml(item.getContent());
    }

    @Override
    public void showMissingBlog() {

    }

    @OnClick(R.id.btnBack)
    public void onBackClick(View view) {
        onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
