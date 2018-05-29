package com.lmterminiello.melilist.view.list;

import android.app.ActionBar;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.SearchView;
import android.widget.ImageView;

import com.lmterminiello.melilist.R;
import com.lmterminiello.melilist.databinding.ProductListFragmentBinding;
import com.lmterminiello.melilist.model.Result;
import com.lmterminiello.melilist.utils.RecyclerViewScrollListener;
import com.lmterminiello.melilist.view.ProductActivity;
import com.lmterminiello.melilist.view.ShowDetailListener;
import com.lmterminiello.melilist.viewmodel.list.ProductListViewModel;
import com.lmterminiello.melilist.viewmodel.ProductViewModelFactory;

public class ProductListFragment extends Fragment implements ProductAdapter.ProductOnClickListener {

    public static final String TAG = "ProductListViewModel";

    private ProductListFragmentBinding binding;
    private ProductListViewModel viewModel;
    private ProductAdapter adapter;
    private ProductViewModelFactory productViewModelFactory;
    private ShowDetailListener detailListener;

    public static ProductListFragment newInstance(ProductViewModelFactory productViewModelFactory) {
        ProductListFragment fragment = new ProductListFragment();
        fragment.setProductViewModelFactory(productViewModelFactory);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        adapter = new ProductAdapter(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            detailListener = (ShowDetailListener) context;
        } catch (ClassCastException castException) {
            Log.e("error", "The activity does not implement the listener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.product_list_fragment, container, false);

        ((ProductActivity) getActivity()).setSupportActionBar(binding.toolbar);

        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }

    private void initRecyclerView() {
        binding.productsList.setAdapter(adapter);
        binding.productsList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.productsList.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        binding.productsList.addOnScrollListener(new RecyclerViewScrollListener() {
            @Override
            public void onScrollUp() {

            }

            @Override
            public void onScrollDown() {

            }

            @Override
            public void onLoadMore() {
                viewModel.nextPage();
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this, productViewModelFactory).get(ProductListViewModel.class);
        binding.setViewModel(viewModel);
        subscriberObserver();
    }


    private void subscriberObserver() {
        viewModel.getProducts().observe(this, products ->
                adapter.setResultList(products)
        );

        viewModel.getErrorMessage().observe(this, s -> {
            binding.imageView.setImageResource(R.drawable.ic_error);
            binding.loadingTv.setText(s);
        });

        viewModel.isLoading().observe(this, isLoading -> {
            if (isLoading) {
                adapter.initLoading();
            } else {
                adapter.dismissLoading();
            }
        });
    }

    @Override
    public void onClickProduct(Result result) {
        detailListener.showDetail(result.getId());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list_toolbar, menu);
        MenuItem mSearch = menu.findItem(R.id.action_search);
        initSearchView(mSearch);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initSearchView(MenuItem mSearch) {
        SearchView searchView = (SearchView) mSearch.getActionView();
        searchView.setQueryHint(getString(R.string.searchLabel));
        searchView.setLayoutParams(new ActionBar.LayoutParams(Gravity.RIGHT));
        ImageView searchClose = searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        searchClose.setImageResource(R.drawable.ic_close_black);

        SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(getResources().getColor(R.color.secondaryTitle));
        searchAutoComplete.setTextColor(getResources().getColor(R.color.primaryTitle));

        binding.toolbar.setOnClickListener(onClick -> {
            mSearch.expandActionView();
            searchView.setIconified(false);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                binding.productsList.scrollToPosition(0);
                adapter.clearItems();
                viewModel.search(query);
                searchView.setIconified(true);
                searchView.clearFocus();
                mSearch.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

    public void setProductViewModelFactory(ProductViewModelFactory productViewModelFactory) {
        this.productViewModelFactory = productViewModelFactory;
    }
}
