package com.lmterminiello.melilist.view.detail;

import android.animation.ObjectAnimator;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lmterminiello.melilist.R;
import com.lmterminiello.melilist.databinding.ProductDetailFragmentBinding;
import com.lmterminiello.melilist.view.ProductActivity;
import com.lmterminiello.melilist.view.widgets.SliderImageViewAdapter;
import com.lmterminiello.melilist.viewmodel.ProductViewModelFactory;
import com.lmterminiello.melilist.viewmodel.detail.ProductDetailViewModel;

public class ProductDetailFragment extends Fragment {

    public static final String TAG = "ProductDetailFragment";
    private static final int ANIMATION_DURATION = 150;

    private ProductDetailFragmentBinding binding;
    private ProductViewModelFactory productViewModelFactory;
    private ProductDetailViewModel viewModel;
    private String productId;
    private FeatureAdapter adapterFeature;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public static ProductDetailFragment newInstance(ProductViewModelFactory productViewModelFactory, String productId) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        fragment.setProductViewModelFactory(productViewModelFactory);
        fragment.setProductId(productId);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.product_detail_fragment, container, false);

        ((ProductActivity) getActivity()).setSupportActionBar(binding.toolbar);

        ((ProductActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((ProductActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.setLifecycleOwner(this);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.featureList.setLayoutManager(new GridLayoutManager(getContext(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this, productViewModelFactory).get(ProductDetailViewModel.class);
        viewModel.execute(productId);

        initObservables();
    }

    public void initObservables() {
        viewModel.getFinishLoading().observe(this, finishLoading -> {
            if (finishLoading) {
                binding.setFinishLoading(finishLoading);
                binding.setSuccess(finishLoading);
                binding.setViewModel(viewModel);
            }
        });

        viewModel.getImagersUrl().observe(this, imagesUrl -> {
            SliderImageViewAdapter adapter = new SliderImageViewAdapter(getContext(), imagesUrl);
            binding.sliderImage.setAdapter(adapter);
        });

        viewModel.getAttributeList().observe(this, attributes -> {
            adapterFeature = new FeatureAdapter();
            binding.featureList.setAdapter(adapterFeature);
            adapterFeature.setAttributes(attributes, v -> {
                if (adapterFeature.expandOrCollapse()) {
                    doAnimatedArrowExpand(binding.arrowDown);
                } else {
                    doAnimatedArrowCollapse(binding.arrowDown);
                }
            });
        });

        viewModel.getError().observe(this, error ->{
            binding.setFinishLoading(error);
            binding.errorContainer.setVisibility(View.VISIBLE);
        });
    }

    public void setProductViewModelFactory(ProductViewModelFactory productViewModelFactory) {
        this.productViewModelFactory = productViewModelFactory;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    private void doAnimatedArrowExpand(ImageView arrowIcon) {
        ObjectAnimator arrowAnimator = ObjectAnimator.ofFloat(arrowIcon, "rotation", 0f, 180f);
        arrowAnimator.setDuration(ANIMATION_DURATION);
        arrowAnimator.start();
    }

    private void doAnimatedArrowCollapse(ImageView arrowIcon) {
        ObjectAnimator arrowAnimator = ObjectAnimator.ofFloat(arrowIcon, "rotation", 180f, 0f);
        arrowAnimator.setDuration(ANIMATION_DURATION);
        arrowAnimator.start();
    }
}
