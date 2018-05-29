package com.lmterminiello.melilist.viewmodel.list;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lmterminiello.melilist.MeliApp;
import com.lmterminiello.melilist.model.Result;
import com.lmterminiello.melilist.utils.StringUtils;

import java.text.NumberFormat;

public class ProductListItemViewModel extends BaseObservable {

    private Result result;

    public ProductListItemViewModel(Result result) {
        this.result = result;
    }


    public String getTitle() {
        return result.getTitle();
    }

    public String getPrice() {
        String currency = MeliApp.getCurrency(result.getCurrencyId());
        return currency + StringUtils.SPACE + NumberFormat.getInstance().format(result.getPrice());
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext()).load(imageUrl).into(view);
    }

    public String getImageUrl() {
        return result.getThumbnail();
    }

    public void setResult(Result result) {
        this.result =result;
        notifyChange();
    }

    public Result getResult() {
        return result;
    }

    public boolean getFreeShipping(){
        return result.getShipping().getFreeShipping();
    }
}
