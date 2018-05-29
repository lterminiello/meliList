package com.lmterminiello.melilist.view.widgets;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lmterminiello.melilist.R;
import com.lmterminiello.melilist.utils.StringUtils;

import java.util.List;

public class SliderImageViewAdapter extends PagerAdapter {

    public interface OnClickImageListener {
        void onClickImage();
    }

    private Context mContext;
    private List<String> links;
    private OnClickImageListener onClickImageListener;


    public SliderImageViewAdapter(Context mContext, List<String> links) {
        this.mContext = mContext;
        this.links = links;
    }

    @Override
    public int getCount() {
        return links.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.slider_item, container, false);

        ImageView imageView = itemView.findViewById(R.id.imagePagerItem);

        imageView.setVisibility(View.VISIBLE);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickImageListener != null) {
                    onClickImageListener.onClickImage();
                }
            }
        });

        Glide.with(imageView.getContext()).load(links.get(position)).into(imageView);


        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}