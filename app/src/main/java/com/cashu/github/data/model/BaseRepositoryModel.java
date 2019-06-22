package com.cashu.github.data.model;


import androidx.annotation.IntDef;

public class BaseRepositoryModel<T> {
    private T mData;

    private boolean mIsErrored;

    private Exception mError;

    @DataSource
    private int mDataSource;

    public BaseRepositoryModel(T data, boolean isErrored, Exception error, @DataSource int dataSource) {
        this.mData = data;
        this.mIsErrored = isErrored;
        this.mError = error;
        this.mDataSource = dataSource;
    }

    public T getData() {
        return mData;
    }

    public boolean isErrored() {
        return mIsErrored;
    }

    public Exception getError() {
        return mError;
    }

    public int getDataSource() {
        return mDataSource;
    }

    @IntDef({DataSource.API, DataSource.DATABASE})
    public @interface DataSource {
        int API = 1;
        int DATABASE = 2;
    }
}
