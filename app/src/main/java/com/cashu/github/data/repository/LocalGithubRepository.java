package com.cashu.github.data.repository;

import com.cashu.github.data.database.GithubDatabase;
import com.cashu.github.data.model.GithubEntity;
import com.cashu.github.data.repository.contract.BaseGithubRepository;
import io.reactivex.Observable;

import java.util.List;

public class LocalGithubRepository implements BaseGithubRepository<GithubEntity> {

    private GithubDatabase database;

    public LocalGithubRepository(GithubDatabase database) {
        this.database = database;
    }

    @Override
    public Observable<List<GithubEntity>> getUserRepos(String userName, int pageNumber, int pageCount) {
        return database.githubDao().getUserRepos(userName).toObservable();
    }

    @Override
    public void add(List<GithubEntity> entities) {
        database.githubDao().insertRepos(entities);
    }

    @Override
    public void deleteAll() {
        database.githubDao().deleteAll();
    }


}
