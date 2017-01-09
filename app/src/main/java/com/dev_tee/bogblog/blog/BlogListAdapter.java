package com.dev_tee.bogblog.blog;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.choota.dev.ctimeago.TimeAgo;
import com.dev_tee.bogblog.R;
import com.dev_tee.bogblog.data.Blog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dev_Tee on 1/8/17.
 */
public class BlogListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String regrexPttr = "\\<img.*?>";

    private Context context;
    private List<Blog> blogList;
    private BlogFragment.BlogItemListener blogItemListener;

    public BlogListAdapter(Context context, List<Blog> blogList, BlogFragment.BlogItemListener blogItemListener) {
        this.context = context;
        this.blogList = blogList;
        this.blogItemListener = blogItemListener;
    }

    public void replaceData(List<Blog> items) {
        setList(items);
        notifyDataSetChanged();
    }

    public void deleteItem(Blog deleteBlog) {
        blogList.remove(deleteBlog);
        notifyDataSetChanged();
    }

    private void setList(List<Blog> items) {
        blogList = (items);
    }

    public Blog getItem(int position) {
        return blogList.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_blog_list_item, parent, false);
        return new BlogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        BlogViewHolder viewHolder = (BlogViewHolder) holder;
        final Blog currentBlog = getItem(position);

     /*   Glide.with(context).load(currentBlog.getOwnerImageURL())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(viewHolder.imgOwnerThumbnail);*/

        if (currentBlog.getContentThumbnail() == null || currentBlog.getContentThumbnail().contentEquals("")) {
            viewHolder.imgThumbnail.setVisibility(View.GONE);
        } else {
            viewHolder.imgThumbnail.setVisibility(View.VISIBLE);
            Glide.with(context).load(currentBlog.getContentThumbnail())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(viewHolder.imgThumbnail);
        }

    //    viewHolder.txtOwnerName.setText(currentBlog.getOwnerName());
        viewHolder.txtTitle.setText(currentBlog.getTitle());
        viewHolder.txtContent.setText(filterImgTag(currentBlog.getContent()));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeAgo timeAgo = new TimeAgo().locale(context).with(sdf);

        viewHolder.txtDateCreated.setText(timeAgo.getTimeAgo(currentBlog.getTimeCreated()));

        //Open blog details
        viewHolder.layoutContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blogItemListener.onBlogClick(currentBlog);
            }
        });

        //Delete item
        viewHolder.btnActions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blogItemListener.onDeleteClick(currentBlog);
            }
        });
    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }

    private String filterImgTag(String content) {
        String filtered = Jsoup.parse(content).text();
        return filtered;
    }

    public class BlogViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layoutContainer)
        LinearLayout layoutContainer;
        @BindView(R.id.imgOwnerThumbnail)
        ImageView imgOwnerThumbnail;
        @BindView(R.id.txtOwnerName)
        TextView txtOwnerName;
        @BindView(R.id.txtDateCreated)
        TextView txtDateCreated;
        @BindView(R.id.btnActions)
        AppCompatImageView btnActions;
        @BindView(R.id.imgThumbnail)
        ImageView imgThumbnail;
        @BindView(R.id.txtTitle)
        TextView txtTitle;
        @BindView(R.id.txtContent)
        TextView txtContent;

        public BlogViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
