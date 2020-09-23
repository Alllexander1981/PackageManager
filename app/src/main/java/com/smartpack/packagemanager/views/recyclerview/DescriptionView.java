package com.smartpack.packagemanager.views.recyclerview;

import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.smartpack.packagemanager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on February 11, 2020
 * Based on the original implementation on Kernel Adiutor by
 * Willi Ye <williye97@gmail.com>
 */

public class DescriptionView extends RecyclerViewItem {

    public interface OnCheckBoxListener {
        void onChanged(DescriptionView descriptionView, boolean isChecked);
    }

    private List<OnCheckBoxListener> mOnCheckBoxListeners = new ArrayList<>();

    private View mRootView;
    private AppCompatImageView mImageView;
    private AppCompatTextView mTitleView;
    private AppCompatTextView mSummaryView;
    private AppCompatCheckBox mCheckBox;

    private boolean mChecked;
    private Drawable mImage;
    private CharSequence mTitle;
    private CharSequence mSummary;

    @Override
    public int getLayoutRes() {
        return R.layout.rv_description_view;
    }

    @Override
    public void onCreateView(View view) {
        mRootView = view;

        mImageView = view.findViewById(R.id.image);
        mTitleView = view.findViewById(R.id.title);
        if (mTitleView != null) {
            mTitleView.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {
                    mRootView.requestFocus();
                }
            });
        }
        mSummaryView = view.findViewById(R.id.summary);
        if (mSummaryView != null) {
            mSummaryView.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {
                    mRootView.requestFocus();
                }
            });
        }
        mCheckBox = view.findViewById(R.id.checkbox);

        super.onCreateView(view);

        mCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mChecked = isChecked;
            List<OnCheckBoxListener> applied = new ArrayList<>();
            for (OnCheckBoxListener onCheckBoxListener : mOnCheckBoxListeners) {
                if (applied.indexOf(onCheckBoxListener) == -1) {
                    onCheckBoxListener.onChanged(this, isChecked);
                    applied.add(onCheckBoxListener);
                }
            }
        });
    }

    public void setDrawable(Drawable drawable) {
        mImage = drawable;
        refresh();
    }

    public void setTitle(CharSequence title) {
        mTitle = title;
        refresh();
    }

    public void setSummary(CharSequence summary) {
        mSummary = summary;
        refresh();
    }

    public void setOnCheckBoxListener(OnCheckBoxListener OnCheckBoxListener) {
        mOnCheckBoxListeners.add(OnCheckBoxListener);
    }

    public void setChecked(boolean checked) {
        mChecked = checked;
        refresh();
    }

    @Override
    protected void refresh() {
        super.refresh();
        if (mImageView != null && mImage != null) {
            mImageView.setImageDrawable(mImage);
            mImageView.setVisibility(View.VISIBLE);
        }
        if (mTitleView != null) {
            if (mTitle != null) {
                mTitleView.setText(mTitle);
            } else {
                mTitleView.setVisibility(View.GONE);
            }
        }
        if (mSummaryView != null) {
            if (mSummary != null) {
                mSummaryView.setText(mSummary);
            } else {
                mSummaryView.setVisibility(View.GONE);
            }
        }
        if (mCheckBox != null) {
            mCheckBox.setVisibility(View.VISIBLE);
            mCheckBox.setChecked(mChecked);
        }
        if (mRootView != null && getOnItemClickListener() != null && mTitleView != null
                && mSummaryView != null) {
            mTitleView.setTextIsSelectable(false);
            mSummaryView.setTextIsSelectable(false);
            mRootView.setOnClickListener(v -> {
                if (getOnItemClickListener() != null) {
                    getOnItemClickListener().onClick(DescriptionView.this);
                }
            });
        }
    }

}