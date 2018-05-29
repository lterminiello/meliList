package com.lmterminiello.melilist.view;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lmterminiello.melilist.R;
import com.lmterminiello.melilist.usecase.product.ProductUseCaseFactory;
import com.lmterminiello.melilist.view.detail.ProductDetailFragment;
import com.lmterminiello.melilist.view.list.ProductListFragment;
import com.lmterminiello.melilist.viewmodel.ProductViewModelFactory;

public class ProductActivity extends AppCompatActivity implements ShowDetailListener {

    private ProductViewModelFactory productViewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list_activity);

        productViewModelFactory = new ProductViewModelFactory(new ProductUseCaseFactory(),getApplicationContext());

        if (savedInstanceState == null) {
            ProductListFragment fragment = ProductListFragment.newInstance(productViewModelFactory);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment, ProductListFragment.TAG).commit();
        }
    }

    @Override
    public void showDetail(String productId) {
        Fragment newFragment = ProductDetailFragment.newInstance(productViewModelFactory,productId);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, newFragment,ProductDetailFragment.TAG).addToBackStack(null).commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
