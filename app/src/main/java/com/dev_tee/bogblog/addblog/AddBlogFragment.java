package com.dev_tee.bogblog.addblog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.dev_tee.bogblog.Injection;
import com.dev_tee.bogblog.R;
import com.dev_tee.bogblog.data.Blog;

import java.io.IOException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import jp.wasabeef.richeditor.RichEditor;

/**
 * Created by Dev_Tee on 1/8/17.
 */

public class AddBlogFragment extends Fragment implements AddBlogContract.View {

    @BindView(R.id.editTitle)
    EditText editTitle;
    @BindView(R.id.editorContent)
    RichEditor editor;
    @BindView(R.id.btnSave)
    RelativeLayout btnSave;

    public static final int PICK_IMAGE_REQUEST = 001;

    private AddBlogPresenter presenter;
    private Blog blog;
    private boolean isEditMode;
    private int blogId;

    public static AddBlogFragment newInstance(boolean isEditMode, int blogId) {
        AddBlogFragment fragment = new AddBlogFragment();
        fragment.isEditMode = isEditMode;
        fragment.blogId = blogId;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new AddBlogPresenter(Injection.provideBlogRepository(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_blog, container, false);
        ButterKnife.bind(this, view);

        editor.setPlaceholder("Start writing here...");

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (isEditMode) {
            presenter.getExistBlog(blogId);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null) {

            Uri selectedImage = data.getData();

            editor.insertImage(selectedImage.toString(), "image");

            /*try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                editor.insertImage(selectedImage.toString(), "image");
            } catch (IOException e) {
                e.printStackTrace();
            }*/

        } else if (resultCode == Activity.RESULT_CANCELED) {

        }

    }

    @OnClick(R.id.btnSave)
    public void onSaveClick(View view) {

        String title = editTitle.getText().toString();
        String content = editor.getHtml();

        if (isEditMode) {
            presenter.updateBlog(blog, title, content);
        } else {
            presenter.saveBlog(title, content);
        }

    }

    @OnClick({R.id.action_heading1, R.id.action_heading2, R.id.action_heading3, R.id.action_heading4,
    R.id.action_heading5, R.id.action_heading6, R.id.action_align_center, R.id.action_align_left,
    R.id.action_align_right, R.id.action_insert_bullets, R.id.action_insert_checkbox, R.id.action_insert_numbers,
    R.id.action_insert_image, R.id.action_insert_link, R.id.action_indent, R.id.action_outdent,
    R.id.action_bg_color, R.id.action_blockquote, R.id.action_italic, R.id.action_bold, R.id.action_underline,
    R.id.action_undo, R.id.action_redo, R.id.action_strikethrough, R.id.action_subscript, R.id.action_superscript,
    R.id.action_txt_color})
    public void onEditorClick(View view) {

        switch (view.getId()) {
            case R.id.action_heading1:
                editor.setHeading(1);
                break;
            case R.id.action_heading2:
                editor.setHeading(2);
                break;
            case R.id.action_heading3:
                editor.setHeading(3);
                break;
            case R.id.action_heading4:
                editor.setHeading(4);
                break;
            case R.id.action_heading5:
                editor.setHeading(5);
                break;
            case R.id.action_heading6:
                editor.setHeading(6);
                break;
            case R.id.action_align_center:
                editor.setAlignCenter();
                break;
            case R.id.action_align_left:
                editor.setAlignLeft();
                break;
            case R.id.action_align_right:
                editor.setAlignRight();
                break;
            case R.id.action_insert_bullets:
                editor.setBullets();
                break;
            case R.id.action_insert_checkbox:
                editor.insertTodo();
                break;
            case R.id.action_insert_numbers:
                editor.setNumbers();
                break;
            case R.id.action_blockquote:
                editor.setBlockquote();
                break;
            case R.id.action_indent:
                editor.setIndent();
                break;
            case R.id.action_outdent:
                editor.setOutdent();
                break;
            case R.id.action_italic:
                editor.setItalic();
                break;
            case R.id.action_bold:
                editor.setBold();
                break;
            case R.id.action_underline:
                editor.setUnderline();
                break;
            case R.id.action_strikethrough:
                editor.setStrikeThrough();
                break;
            case R.id.action_subscript:
                editor.setSubscript();
                break;
            case R.id.action_superscript:
                editor.setSuperscript();
                break;
            case R.id.action_undo:
                editor.undo();
                break;
            case R.id.action_redo:
                editor.redo();
                break;
            case R.id.action_txt_color:
                //Open color wheel
                break;
            case R.id.action_insert_image:
                //Open image picker
                openImagePicker();
                break;
            case R.id.action_insert_link:
                //Open link dialog
                break;
            case R.id.action_bg_color:
                //Open color wheel
                break;
        }
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void showBlogList() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void showExistBlog(Blog item) {
        blog = item;
        editTitle.setText(item.getTitle());
        editor.setHtml(item.getContent());
    }

    @Override
    public void showEmptyEntryMsg() {

    }
}
