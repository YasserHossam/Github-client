package com.cashu.github.data.repository.contract;

import io.reactivex.Observable;

import java.util.List;

public interface BaseGithubRepository<T> {

    Observable<List<T>> getUserRepos(String userName, int pageNumber,
                                                int pageCount);

    void add(List<T> entities);

    void deleteAll();
}
