package com.lmterminiello.melilist.view.widgets;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lmterminiello.melilist.R;


public class SliderView extends RelativeLayout implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager pager;
    private TextView textInfo;
    private IndicatorDotView pagerIndicator;
    private PagerAdapter mAdapter;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    
    public SliderView(Context context) {
        super(context);
        init(context);
    }
    
    public SliderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.slider_view, this, true);
        
        pager = findViewById(R.id.pager);
        pagerIndicator = findViewById(R.id.viewPagerCountDots);

        textInfo = findViewById(R.id.photoCount);
        
    }
    
    public void setActiveDotColor(@ColorRes int color) {
        pagerIndicator.setActiveDotColor(color);
    }
    
    public void setInactiveDotColor(@ColorRes int color) {
        pagerIndicator.setInactiveDotColor(color);
    }
    
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (onPageChangeListener!=null) {
            onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }
    
    @Override
    public void onPageSelected(int position) {
        textInfo.setText(getContext().getResources().getString(R.string.photoCount,position + 1,mAdapter.getCount()));
        pagerIndicator.onPageChange(position);
        if (onPageChangeListener!=null) {
            onPageChangeListener.onPageSelected(position);
        }
    }
    
    @Override
    public void onPageScrollStateChanged(int state) {
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrollStateChanged(state);
        }
    }
    
    @Override
    public void onClick(View v) {
        //Do nothing
    }
    
    public void setAdapter(PagerAdapter pagerAdapter) {
        mAdapter = pagerAdapter;
        pager.setAdapter(mAdapter);
        pager.setCurrentItem(0);
        pager.addOnPageChangeListener(this);
        pagerIndicator.setNoOfPages(mAdapter.getCount());
        textInfo.setText(getContext().getResources().getString(R.string.photoCount,1,mAdapter.getCount()));
    }
    
    public void setHeight(int height) {
        getLayoutParams().height = height;
        requestLayout();
    }
    
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }

    public ViewPager getViewPager(){
        return pager;
    }
}