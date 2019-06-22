package com.cashu.github.presenter;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter {
    private CompositeDisposable mDisposables;

    public BasePresenter() {
        mDisposables = new CompositeDisposable();
    }

    public void onDestroyView() {
        mDisposables.dispose();
    }

    abstract class BaseObserver<T> implements Observer<T> {
        @Override
        public void onSubscribe(Disposable d) {
            mDisposables.add(d);
        }
    }
}

