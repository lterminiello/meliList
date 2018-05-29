package com.lmterminiello.melilist.usecase;

public abstract class AbstractPaginateUseCase<T> extends AbstractBaseUseCase<T> {

    protected int totalItemArchived = 0;
    protected int totalItems;

    public boolean isLastPage() {
        return totalItems == 0 ? false : totalItemArchived >= totalItems;
    }

    protected void calculatePagination(int totalNewItem, int totalItems) {
        this.totalItemArchived += totalNewItem;
        this.totalItems = totalItems;
    }

    public void resetPage(){
        totalItemArchived = 0;
    }

    protected int getCurrentOffset(){
        return totalItemArchived;
    }

}
