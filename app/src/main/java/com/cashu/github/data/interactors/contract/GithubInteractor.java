package com.cashu.github.data.interactors.contract;

import com.cashu.github.data.model.BaseRepositoryModel;
import io.reactivex.Observable;

import java.util.List;

public interface GithubInteractor<T> {
    Observable<BaseRepositoryModel<List<T>>> getNextUserRepos(String userName);
}
